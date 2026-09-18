package com.visacoach.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visacoach.data.local.TokenStorage
import com.visacoach.data.remote.VisaCoachApi
import com.visacoach.domain.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class OtpSent(val phone: String, val message: String) : AuthUiState()
    data class Authenticated(val user: UserModel) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: VisaCoachApi,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun register(phone: String, fullName: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val res = api.register(RegisterRequest(phone, fullName))
                if (res.isSuccessful && res.body() != null) {
                    _uiState.value = AuthUiState.OtpSent(phone, res.body()!!.message)
                } else {
                    _uiState.value = AuthUiState.Error(res.errorBody()?.string() ?: "Registration failed")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.localizedMessage ?: "Network connection error")
            }
        }
    }

    fun login(phone: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val res = api.login(LoginRequest(phone))
                if (res.isSuccessful && res.body() != null) {
                    _uiState.value = AuthUiState.OtpSent(phone, res.body()!!.message)
                } else {
                    _uiState.value = AuthUiState.Error(res.errorBody()?.string() ?: "Login failed")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.localizedMessage ?: "Network connection error")
            }
        }
    }

    fun verifyOtp(phone: String, code: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val res = api.verifyOtp(VerifyOtpRequest(phone, code))
                if (res.isSuccessful && res.body() != null) {
                    val auth = res.body()!!
                    tokenStorage.saveTokens(
                        accessToken = auth.accessToken,
                        refreshToken = auth.refreshToken,
                        userId = auth.user.id,
                        phone = auth.user.phoneNumber
                    )
                    _uiState.value = AuthUiState.Authenticated(auth.user)
                } else {
                    _uiState.value = AuthUiState.Error("Invalid or expired OTP code")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.localizedMessage ?: "Verification error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                api.logout()
            } catch (e: Exception) {
                // Ignore network error on logout
            }
            tokenStorage.clearTokens()
            _uiState.value = AuthUiState.Idle
        }
    }
}
