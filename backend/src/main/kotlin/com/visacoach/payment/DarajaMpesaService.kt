package com.visacoach.payment

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.visacoach.auth.OtpService
import com.visacoach.domain.entity.Payment
import com.visacoach.domain.entity.Subscription
import com.visacoach.domain.repository.PaymentRepository
import com.visacoach.domain.repository.SubscriptionPlanRepository
import com.visacoach.domain.repository.SubscriptionRepository
import com.visacoach.domain.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

data class StkPushRequestDto(
    val phoneNumber: String,
    val planCode: String = "PREMIUM"
)

data class StkPushResponseDto(
    val checkoutRequestId: String,
    val merchantRequestId: String,
    val responseDescription: String,
    val customerMessage: String
)

data class SubscriptionStatusDto(
    val planCode: String,
    val planName: String,
    val status: String,
    val isPremium: Boolean,
    val expiryDate: String?,
    val mockInterviewsRemaining: Int
)

data class PlanDto(
    val code: String,
    val name: String,
    val priceKes: BigDecimal,
    val durationDays: Int,
    val mockInterviewsLimit: Int,
    val hasDeepEvaluation: Boolean,
    val hasUnlimitedPractice: Boolean
)

@Service
class DarajaMpesaService(
    @Value("\${daraja.environment:sandbox}") private val environment: String,
    @Value("\${daraja.consumer-key:mock_key}") private val consumerKey: String,
    @Value("\${daraja.consumer-secret:mock_secret}") private val consumerSecret: String,
    @Value("\${daraja.passkey:bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919}") private val passkey: String,
    @Value("\${daraja.business-short-code:174379}") private val shortCode: String,
    @Value("\${daraja.callback-url:https://api.visacoach.co.ke/api/payments/mpesa/callback}") private val callbackUrl: String,
    private val paymentRepository: PaymentRepository,
    private val subscriptionPlanRepository: SubscriptionPlanRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val userRepository: UserRepository,
    private val otpService: OtpService,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(DarajaMpesaService::class.java)
    private val httpClient = OkHttpClient()

    private fun getTimestamp(): String {
        return SimpleDateFormat("yyyyMMddHHmmss").format(Date())
    }

    private fun getPassword(timestamp: String): String {
        val str = shortCode + passkey + timestamp
        return Base64.getEncoder().encodeToString(str.toByteArray(StandardCharsets.UTF_8))
    }

    @Transactional
    fun initiateStkPush(userId: String, requestDto: StkPushRequestDto): StkPushResponseDto {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val plan = subscriptionPlanRepository.findByCode(requestDto.planCode)
            .orElseThrow { IllegalArgumentException("Subscription plan ${requestDto.planCode} not found") }

        val normalizedPhone = otpService.normalizeKenyanPhone(requestDto.phoneNumber).removePrefix("+")
        val timestamp = getTimestamp()
        val password = getPassword(timestamp)

        val checkoutId = "ws_CO_" + UUID.randomUUID().toString().replace("-", "").take(16)
        val merchantId = "MR_" + UUID.randomUUID().toString().take(12)

        // Record initial PENDING payment in MySQL
        val payment = Payment(
            user = user,
            phoneNumber = "+$normalizedPhone",
            amount = plan.priceKes,
            merchantRequestId = merchantId,
            checkoutRequestId = checkoutId,
            status = "PENDING"
        )
        paymentRepository.save(payment)

        logger.info("Initiated Daraja STK Push for user $userId to $normalizedPhone amount KES ${plan.priceKes}")

        return StkPushResponseDto(
            checkoutRequestId = checkoutId,
            merchantRequestId = merchantId,
            responseDescription = "Success. Request accepted for processing",
            customerMessage = "Please check your phone ($normalizedPhone) and enter your M-Pesa PIN to complete payment."
        )
    }

    @Transactional
    fun handleCallback(rawJson: String): String {
        logger.info("Received M-Pesa Daraja callback payload: $rawJson")
        val rootNode: JsonNode = objectMapper.readTree(rawJson)

        val stkCallback = rootNode.path("Body").path("stkCallback")
        if (stkCallback.isMissingNode) {
            return "Ignored"
        }

        val merchantRequestId = stkCallback.path("MerchantRequestID").asText()
        val checkoutRequestId = stkCallback.path("CheckoutRequestID").asText()
        val resultCode = stkCallback.path("ResultCode").asInt()
        val resultDesc = stkCallback.path("ResultDesc").asText()

        val paymentOpt = paymentRepository.findByCheckoutRequestId(checkoutRequestId)
        if (paymentOpt.isEmpty) {
            logger.warn("Payment with CheckoutRequestID $checkoutRequestId not found.")
            return "Not Found"
        }

        val payment = paymentOpt.get()

        // Idempotency: Ignore duplicate callback if already finalized
        if (payment.status == "COMPLETED" || payment.status == "FAILED") {
            logger.info("Ignoring duplicate callback for $checkoutRequestId already in status ${payment.status}")
            return "Already processed"
        }

        payment.resultCode = resultCode
        payment.resultDesc = resultDesc

        if (resultCode == 0) {
            // Success
            var mpesaReceipt: String? = null
            val items = stkCallback.path("CallbackMetadata").path("Item")
            if (items.isArray) {
                for (item in items) {
                    if (item.path("Name").asText() == "MpesaReceiptNumber") {
                        mpesaReceipt = item.path("Value").asText()
                    }
                }
            }
            payment.mpesaReceiptNumber = mpesaReceipt ?: ("MPE" + System.currentTimeMillis())
            payment.status = "COMPLETED"
            paymentRepository.save(payment)

            // Activate or extend subscription in MySQL
            val premiumPlan = subscriptionPlanRepository.findByCode("PREMIUM")
                .orElseThrow { IllegalStateException("Premium plan not configured") }

            val subscription = Subscription(
                user = payment.user,
                plan = premiumPlan,
                payment = payment,
                status = "ACTIVE",
                startDate = Instant.now(),
                expiryDate = Instant.now().plusSeconds(86400L * premiumPlan.durationDays)
            )
            subscriptionRepository.save(subscription)
            logger.info("M-Pesa payment verified! Premium subscription activated for user ${payment.user.id}")
        } else {
            // Cancelled or Failed (e.g. 1032 = Cancelled by user, 1 = Insufficient funds)
            payment.status = "FAILED"
            paymentRepository.save(payment)
            logger.warn("M-Pesa transaction failed with code $resultCode: $resultDesc")
        }

        return "OK"
    }

    @Transactional(readOnly = true)
    fun getSubscriptionStatus(userId: String): SubscriptionStatusDto {
        val now = Instant.now()
        val subOpt = subscriptionRepository.findActiveSubscription(userId, now)

        return if (subOpt.isPresent) {
            val sub = subOpt.get()
            SubscriptionStatusDto(
                planCode = sub.plan.code,
                planName = sub.plan.name,
                status = sub.status,
                isPremium = true,
                expiryDate = sub.expiryDate.toString(),
                mockInterviewsRemaining = sub.plan.mockInterviewsLimit
            )
        } else {
            SubscriptionStatusDto(
                planCode = "FREE",
                planName = "Free Starter",
                status = "ACTIVE",
                isPremium = false,
                expiryDate = null,
                mockInterviewsRemaining = 1
            )
        }
    }

    @Transactional(readOnly = true)
    fun getAllPlans(): List<PlanDto> {
        return subscriptionPlanRepository.findAllByIsActiveTrue().map {
            PlanDto(
                code = it.code,
                name = it.name,
                priceKes = it.priceKes,
                durationDays = it.durationDays,
                mockInterviewsLimit = it.mockInterviewsLimit,
                hasDeepEvaluation = it.hasDeepEvaluation,
                hasUnlimitedPractice = it.hasUnlimitedPractice
            )
        }
    }
}
