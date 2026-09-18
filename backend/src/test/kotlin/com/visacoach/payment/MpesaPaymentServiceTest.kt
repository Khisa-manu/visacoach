package com.visacoach.payment

import com.fasterxml.jackson.databind.ObjectMapper
import com.visacoach.auth.OtpService
import com.visacoach.domain.entity.Payment
import com.visacoach.domain.entity.SubscriptionPlan
import com.visacoach.domain.entity.User
import com.visacoach.domain.repository.PaymentRepository
import com.visacoach.domain.repository.SubscriptionPlanRepository
import com.visacoach.domain.repository.SubscriptionRepository
import com.visacoach.domain.repository.UserRepository
import io.mockk.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class MpesaPaymentServiceTest {

    private val paymentRepository: PaymentRepository = mockk(relaxed = true)
    private val subscriptionPlanRepository: SubscriptionPlanRepository = mockk(relaxed = true)
    private val subscriptionRepository: SubscriptionRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val otpService: OtpService = mockk(relaxed = true)
    private val objectMapper = ObjectMapper()

    private lateinit var darajaMpesaService: DarajaMpesaService

    @BeforeEach
    fun setup() {
        darajaMpesaService = DarajaMpesaService(
            environment = "sandbox",
            consumerKey = "mock_consumer_key",
            consumerSecret = "mock_consumer_secret",
            passkey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
            shortCode = "174379",
            callbackUrl = "https://api.visacoach.co.ke/api/payments/mpesa/callback",
            paymentRepository = paymentRepository,
            subscriptionPlanRepository = subscriptionPlanRepository,
            subscriptionRepository = subscriptionRepository,
            userRepository = userRepository,
            otpService = otpService,
            objectMapper = objectMapper
        )
    }

    @Test
    fun testInitiateStkPush_Success() {
        val user = User(id = "user-123", phoneNumber = "+254712345678")
        val plan = SubscriptionPlan(id = "plan-p", code = "PREMIUM", name = "Visa Pro", priceKes = BigDecimal("1499.00"))

        every { userRepository.findById("user-123") } returns Optional.of(user)
        every { subscriptionPlanRepository.findByCode("PREMIUM") } returns Optional.of(plan)
        every { otpService.normalizeKenyanPhone("0712345678") } returns "+254712345678"

        val response = darajaMpesaService.initiateStkPush("user-123", StkPushRequestDto("0712345678", "PREMIUM"))

        assertNotNull(response.checkoutRequestId)
        assertTrue(response.checkoutRequestId.startsWith("ws_CO_"))
        verify(exactly = 1) { paymentRepository.save(any<Payment>()) }
    }

    @Test
    fun testHandleCallback_SuccessActivatesSubscription() {
        val user = User(id = "user-123", phoneNumber = "+254712345678")
        val payment = Payment(
            id = "pay-1",
            user = user,
            phoneNumber = "+254712345678",
            amount = BigDecimal("1499.00"),
            merchantRequestId = "MR_123",
            checkoutRequestId = "ws_CO_12345",
            status = "PENDING"
        )
        val plan = SubscriptionPlan(id = "plan-p", code = "PREMIUM", name = "Visa Pro", priceKes = BigDecimal("1499.00"), durationDays = 30)

        every { paymentRepository.findByCheckoutRequestId("ws_CO_12345") } returns Optional.of(payment)
        every { subscriptionPlanRepository.findByCode("PREMIUM") } returns Optional.of(plan)

        val callbackJson = """
        {
          "Body": {
            "stkCallback": {
              "MerchantRequestID": "MR_123",
              "CheckoutRequestID": "ws_CO_12345",
              "ResultCode": 0,
              "ResultDesc": "The service request is processed successfully.",
              "CallbackMetadata": {
                "Item": [
                  {"Name": "Amount", "Value": 1499.00},
                  {"Name": "MpesaReceiptNumber", "Value": "QWE1234567"},
                  {"Name": "TransactionDate", "Value": 20260918120000},
                  {"Name": "PhoneNumber", "Value": 254712345678}
                ]
              }
            }
          }
        }
        """.trimIndent()

        val result = darajaMpesaService.handleCallback(callbackJson)

        assertEquals("OK", result)
        assertEquals("COMPLETED", payment.status)
        assertEquals("QWE1234567", payment.mpesaReceiptNumber)
        verify(exactly = 1) { subscriptionRepository.save(any()) }
    }

    @Test
    fun testHandleCallback_UserCancelled() {
        val user = User(id = "user-123", phoneNumber = "+254712345678")
        val payment = Payment(
            id = "pay-1",
            user = user,
            phoneNumber = "+254712345678",
            amount = BigDecimal("1499.00"),
            merchantRequestId = "MR_123",
            checkoutRequestId = "ws_CO_cancel",
            status = "PENDING"
        )

        every { paymentRepository.findByCheckoutRequestId("ws_CO_cancel") } returns Optional.of(payment)

        val callbackJson = """
        {
          "Body": {
            "stkCallback": {
              "MerchantRequestID": "MR_123",
              "CheckoutRequestID": "ws_CO_cancel",
              "ResultCode": 1032,
              "ResultDesc": "Request cancelled by user."
            }
          }
        }
        """.trimIndent()

        val result = darajaMpesaService.handleCallback(callbackJson)

        assertEquals("OK", result)
        assertEquals("FAILED", payment.status)
        verify(exactly = 0) { subscriptionRepository.save(any()) }
    }
}
