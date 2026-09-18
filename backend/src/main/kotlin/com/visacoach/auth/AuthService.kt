package com.visacoach.auth

import com.visacoach.domain.entity.RefreshToken
import com.visacoach.domain.entity.User
import com.visacoach.domain.entity.UserProfile
import com.visacoach.domain.repository.RefreshTokenRepository
import com.visacoach.domain.repository.UserProfileRepository
import com.visacoach.domain.repository.UserRepository
import com.visacoach.domain.repository.VisaTypeRepository
import com.visacoach.security.JwtTokenProvider
import com.visacoach.security.RateLimitingService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

data class RegisterRequest(
    val phoneNumber: String,
    val fullName: String?,
    val password: String? = null
)

data class LoginRequest(
    val phoneNumber: String,
    val password: String? = null
)

data class VerifyOtpRequest(
    val phoneNumber: String,
    val otpCode: String,
    val deviceInfo: String? = null
)

data class RefreshRequest(
    val refreshToken: String,
    val deviceInfo: String? = null
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresInSeconds: Long = 3600,
    val user: UserSummary
)

data class UserSummary(
    val id: String,
    val phoneNumber: String,
    val fullName: String?,
    val isPhoneVerified: Boolean,
    val role: String
)

data class MessageResponse(val message: String, val success: Boolean = true)

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val userProfileRepository: UserProfileRepository,
    private val visaTypeRepository: VisaTypeRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val otpService: OtpService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val rateLimitingService: RateLimitingService
) {

    @Transactional
    fun register(request: RegisterRequest): MessageResponse {
        val phone = otpService.normalizeKenyanPhone(request.phoneNumber)
        if (!otpService.isValidKenyanPhone(phone)) {
            throw IllegalArgumentException("Invalid Kenyan phone number format. Use +254 7XX XXX XXX or 07XX XXX XXX.")
        }

        val existingUser = userRepository.findByPhoneNumber(phone)
        val user = if (existingUser.isPresent) {
            existingUser.get()
        } else {
            val newUser = User(
                phoneNumber = phone,
                fullName = request.fullName,
                passwordHash = request.password?.let { passwordEncoder.encode(it) },
                isPhoneVerified = false
            )
            val savedUser = userRepository.save(newUser)

            // Initialize default profile
            val defaultVisa = visaTypeRepository.findByCode("B1_B2").orElse(null)
            val profile = UserProfile(
                user = savedUser,
                visaType = defaultVisa,
                fullName = request.fullName ?: "Applicant",
                purposeOfTravel = "Tourism and leisure"
            )
            userProfileRepository.save(profile)
            savedUser
        }

        // Trigger OTP sending
        otpService.generateAndSendOtp(user.phoneNumber)
        return MessageResponse("Verification code dispatched to ${user.phoneNumber}")
    }

    fun login(request: LoginRequest): MessageResponse {
        val phone = otpService.normalizeKenyanPhone(request.phoneNumber)
        if (rateLimitingService.isLockedOut(phone)) {
            val remaining = rateLimitingService.getRemainingLockoutSeconds(phone)
            throw IllegalStateException("Too many failed attempts. Try again in $remaining seconds.")
        }

        val user = userRepository.findByPhoneNumber(phone)
            .orElseThrow { IllegalArgumentException("No account found for this phone number. Please register.") }

        if (request.password != null && user.passwordHash != null) {
            if (!passwordEncoder.matches(request.password, user.passwordHash)) {
                rateLimitingService.recordFailedAttempt(phone)
                throw IllegalArgumentException("Invalid credentials provided.")
            }
        }

        // Dispatch OTP for 2FA / Kenyan phone verification
        otpService.generateAndSendOtp(phone)
        return MessageResponse("OTP code dispatched to $phone")
    }

    @Transactional
    fun verifyOtp(request: VerifyOtpRequest): AuthResponse {
        val phone = otpService.normalizeKenyanPhone(request.phoneNumber)
        val isVerified = otpService.verifyOtp(phone, request.otpCode)
        if (!isVerified) {
            throw IllegalArgumentException("Invalid or expired OTP code.")
        }

        val user = userRepository.findByPhoneNumber(phone)
            .orElseThrow { IllegalStateException("User not found.") }

        user.isPhoneVerified = true
        userRepository.save(user)

        val accessToken = jwtTokenProvider.generateAccessToken(user.id, user.phoneNumber, user.role)
        val rawRefreshToken = jwtTokenProvider.generateRefreshToken()
        val hashedRefreshToken = jwtTokenProvider.hashToken(rawRefreshToken)

        val refreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = hashedRefreshToken,
            deviceInfo = request.deviceInfo,
            expiresAt = Instant.now().plusSeconds(604800L) // 7 days
        )
        refreshTokenRepository.save(refreshTokenEntity)

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = rawRefreshToken,
            user = UserSummary(user.id, user.phoneNumber, user.fullName, user.isPhoneVerified, user.role)
        )
    }

    @Transactional
    fun refreshToken(request: RefreshRequest): AuthResponse {
        val hashed = jwtTokenProvider.hashToken(request.refreshToken)
        val tokenEntity = refreshTokenRepository.findByTokenHash(hashed)
            .orElseThrow { IllegalArgumentException("Invalid refresh token.") }

        if (tokenEntity.isRevoked || Instant.now().isAfter(tokenEntity.expiresAt)) {
            throw IllegalArgumentException("Refresh token is expired or revoked.")
        }

        val user = tokenEntity.user
        // Refresh token rotation: revoke old token, issue new token
        tokenEntity.isRevoked = true
        refreshTokenRepository.save(tokenEntity)

        val newAccessToken = jwtTokenProvider.generateAccessToken(user.id, user.phoneNumber, user.role)
        val newRawRefreshToken = jwtTokenProvider.generateRefreshToken()
        val newHashed = jwtTokenProvider.hashToken(newRawRefreshToken)

        val newRefreshTokenEntity = RefreshToken(
            user = user,
            tokenHash = newHashed,
            deviceInfo = request.deviceInfo,
            expiresAt = Instant.now().plusSeconds(604800L)
        )
        refreshTokenRepository.save(newRefreshTokenEntity)

        return AuthResponse(
            accessToken = newAccessToken,
            refreshToken = newRawRefreshToken,
            user = UserSummary(user.id, user.phoneNumber, user.fullName, user.isPhoneVerified, user.role)
        )
    }

    @Transactional
    fun logout(userId: String): MessageResponse {
        refreshTokenRepository.deleteAllByUserId(userId)
        return MessageResponse("Successfully logged out and session revoked.")
    }
}
