package com.visacoach.payment

import com.visacoach.domain.repository.PaymentRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class PaymentController(
    private val darajaMpesaService: DarajaMpesaService,
    private val paymentRepository: PaymentRepository
) {

    @PostMapping("/payments/mpesa/stk-push")
    fun initiateStkPush(
        @AuthenticationPrincipal userId: String,
        @RequestBody request: StkPushRequestDto
    ): ResponseEntity<StkPushResponseDto> {
        val response = darajaMpesaService.initiateStkPush(userId, request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/payments/mpesa/callback")
    fun handleMpesaCallback(@RequestBody rawJson: String): ResponseEntity<Map<String, String>> {
        val result = darajaMpesaService.handleCallback(rawJson)
        return ResponseEntity.ok(mapOf("ResultCode" to "0", "ResultDesc" to result))
    }

    @GetMapping("/payments/status/{checkoutRequestId}")
    fun checkPaymentStatus(
        @AuthenticationPrincipal userId: String,
        @PathVariable checkoutRequestId: String
    ): ResponseEntity<Map<String, Any?>> {
        val payment = paymentRepository.findByCheckoutRequestId(checkoutRequestId)
            .orElseThrow { IllegalArgumentException("Payment not found") }

        return ResponseEntity.ok(
            mapOf(
                "checkoutRequestId" to payment.checkoutRequestId,
                "status" to payment.status,
                "amount" to payment.amount,
                "mpesaReceipt" to payment.mpesaReceiptNumber,
                "resultDesc" to payment.resultDesc
            )
        )
    }

    @GetMapping("/subscription")
    fun getSubscription(@AuthenticationPrincipal userId: String): ResponseEntity<SubscriptionStatusDto> {
        val status = darajaMpesaService.getSubscriptionStatus(userId)
        return ResponseEntity.ok(status)
    }

    @GetMapping("/plans")
    fun getPlans(): ResponseEntity<List<PlanDto>> {
        val plans = darajaMpesaService.getAllPlans()
        return ResponseEntity.ok(plans)
    }
}
