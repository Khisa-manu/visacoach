package com.visacoach.ui.viewmodels

import com.visacoach.data.local.TokenStorage
import com.visacoach.data.remote.VisaCoachApi
import com.visacoach.domain.models.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private val api: VisaCoachApi = mockk(relaxed = true)
    private val tokenStorage: TokenStorage = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthViewModel(api, tokenStorage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testLogin_Success_SetsOtpSentState() = runTest {
        coEvery { api.login(LoginRequest("0712345678")) } returns Response.success(
            MessageResponse("OTP sent to 0712345678")
        )

        viewModel.login("0712345678")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is AuthUiState.OtpSent)
        assertEquals("0712345678", (state as AuthUiState.OtpSent).phone)
    }

    @Test
    fun testVerifyOtp_Success_SavesTokens() = runTest {
        val authRes = AuthResponse(
            accessToken = "mock-acc-jwt",
            refreshToken = "mock-ref-jwt",
            tokenType = "Bearer",
            expiresInSeconds = 3600,
            user = UserModel(id = "u-1", phoneNumber = "+254712345678", fullName = "Japheth", isPhoneVerified = true, role = "USER")
        )
        coEvery { api.verifyOtp(VerifyOtpRequest("0712345678", "123456")) } returns Response.success(authRes)

        viewModel.verifyOtp("0712345678", "123456")
        advanceUntilIdle()

        coVerify { tokenStorage.saveTokens("mock-acc-jwt", "mock-ref-jwt", "u-1", "+254712345678") }
        val state = viewModel.uiState.value
        assertTrue(state is AuthUiState.Authenticated)
        assertEquals("u-1", (state as AuthUiState.Authenticated).user.id)
    }
}
