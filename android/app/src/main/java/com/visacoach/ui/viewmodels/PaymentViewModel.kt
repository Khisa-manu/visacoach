package com.visacoach.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visacoach.data.remote.VisaCoachApi
import com.visacoach.domain.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PaymentUiState {
    object Idle : PaymentUiState()
    object Initiating : PaymentUiState()
    data class AwaitingPin(val checkoutRequestId: String, val message: String) : PaymentUiState()
    data class Completed(val receipt: String, val amount: Double) : PaymentUiState()
    data class Failed(val reason: String) : PaymentUiState()
}

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val api: VisaCoachApi
) : ViewModel() {

    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Idle)
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    private val _subscription = MutableStateFlow<SubscriptionModel?>(null)
    val subscription: StateFlow<SubscriptionModel?> = _subscription.asStateFlow()

    init {
        refreshSubscription()
    }

    fun refreshSubscription() {
        viewModelScope.launch {
            try {
                val res = api.getSubscription()
                if (res.isSuccessful) {
                    _subscription.value = res.body()
                }
            } catch (e: Exception) {
                // Keep current state
            }
        }
    }

    fun initiateMpesaStkPush(phoneNumber: String, planCode: String = "PREMIUM") {
        viewModelScope.launch {
            _uiState.value = PaymentUiState.Initiating
            try {
                val res = api.initiateStkPush(StkPushRequest(phoneNumber, planCode))
                if (res.isSuccessful && res.body() != null) {
                    val checkoutId = res.body()!!.checkoutRequestId
                    _uiState.value = PaymentUiState.AwaitingPin(checkoutId, res.body()!!.customerMessage)
                    pollPaymentStatus(checkoutId)
                } else {
                    _uiState.value = PaymentUiState.Failed("Could not trigger M-Pesa STK Push. Please verify phone number.")
                }
            } catch (e: Exception) {
                _uiState.value = PaymentUiState.Failed(e.localizedMessage ?: "Payment connection error")
            }
        }
    }

    private fun pollPaymentStatus(checkoutRequestId: String) {
        viewModelScope.launch {
            var attempts = 0
            while (attempts < 15) {
                delay(3000)
                attempts++
                try {
                    val statusRes = api.checkPaymentStatus(checkoutRequestId)
                    if (statusRes.isSuccessful && statusRes.body() != null) {
                        val body = statusRes.body()!!
                        if (body.status == "COMPLETED") {
                            _uiState.value = PaymentUiState.Completed(
                                receipt = body.mpesaReceipt ?: "MPE${System.currentTimeMillis()}",
                                amount = body.amount ?: 1499.0
                            )
                            refreshSubscription()
                            return@launch
                        } else if (body.status == "FAILED") {
                            _uiState.value = PaymentUiState.Failed(body.resultDesc ?: "Payment declined or cancelled by user.")
                            return@launch
                        }
                    }
                } catch (e: Exception) {
                    // Continue polling
                }
            }
            _uiState.value = PaymentUiState.Failed("STK prompt timed out. If money was deducted, tap refresh.")
        }
    }

    fun reset() {
        _uiState.value = PaymentUiState.Idle
    }
}
