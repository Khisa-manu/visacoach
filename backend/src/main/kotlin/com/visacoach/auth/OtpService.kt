package com.visacoach.auth

import com.visacoach.security.RateLimitingService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

interface SmsGateway {
    fun sendOtp(phoneNumber: String, code: String): Boolean
}

@Service
class MockSmsGateway : SmsGateway {
    private val logger = LoggerFactory.getLogger(MockSmsGateway::class.java)

    override fun sendOtp(phoneNumber: String, code: String): Boolean {
        // Safe development mock logger without exposing to external systems
        logger.info("[MOCK SMS GATEWAY] Sent verification code [$code] to Kenyan phone: $phoneNumber")
        return true
    }
}

@Service
class OtpService(
    private val smsGateway: SmsGateway,
    private val passwordEncoder: PasswordEncoder,
    private val rateLimitingService: RateLimitingService
) {
    private val logger = LoggerFactory.getLogger(OtpService::class.java)
    private val secureRandom = SecureRandom()
    private val otpCache = ConcurrentHashMap<String, HashedOtpEntry>()

    data class HashedOtpEntry(
        val hashedCode: String,
        val expiresAt: Instant,
        var attempts: Int = 0
    )

    fun normalizeKenyanPhone(phone: String): String {
        val cleaned = phone.replace(Regex("[^0-9+]"), "")
        return when {
            cleaned.startsWith("+254") -> cleaned
            cleaned.startsWith("254") -> "+$cleaned"
            cleaned.startsWith("07") || cleaned.startsWith("01") -> "+254" + cleaned.substring(1)
            cleaned.startsWith("7") || cleaned.startsWith("1") -> "+254$cleaned"
            else -> cleaned
        }
    }

    fun isValidKenyanPhone(phone: String): Boolean {
        val normalized = normalizeKenyanPhone(phone)
        // Safaricom, Airtel, Telkom Kenya prefix checks
        return normalized.matches(Regex("^\\+254(7|1)[0-9]{8}$"))
    }

    fun generateAndSendOtp(rawPhone: String): String {
        val phone = normalizeKenyanPhone(rawPhone)
        if (rateLimitingService.isLockedOut(phone)) {
            val remaining = rateLimitingService.getRemainingLockoutSeconds(phone)
            throw IllegalStateException("Too many failed attempts. Account locked for $remaining seconds.")
        }

        // Generate 6-digit numeric OTP
        val codeNumber = 100000 + secureRandom.nextInt(900000)
        val codeString = codeNumber.toString()

        // Never store plain text OTP: store bcrypt/argon2 hash
        val hashedCode = passwordEncoder.encode(codeString)
        otpCache[phone] = HashedOtpEntry(
            hashedCode = hashedCode,
            expiresAt = Instant.now().plusSeconds(300) // 5 minutes validity
        )

        smsGateway.sendOtp(phone, codeString)
        return phone
    }

    fun verifyOtp(rawPhone: String, inputCode: String): Boolean {
        val phone = normalizeKenyanPhone(rawPhone)
        if (rateLimitingService.isLockedOut(phone)) {
            val remaining = rateLimitingService.getRemainingLockoutSeconds(phone)
            throw IllegalStateException("Too many attempts. Locked for $remaining seconds.")
        }

        val entry = otpCache[phone]
        if (entry == null || Instant.now().isAfter(entry.expiresAt)) {
            otpCache.remove(phone)
            rateLimitingService.recordFailedAttempt(phone)
            return false
        }

        entry.attempts += 1
        if (entry.attempts > 3) {
            otpCache.remove(phone)
            rateLimitingService.recordFailedAttempt(phone)
            return false
        }

        val matches = passwordEncoder.matches(inputCode, entry.hashedCode)
        if (matches) {
            otpCache.remove(phone)
            rateLimitingService.resetAttempts(phone)
            return true
        } else {
            rateLimitingService.recordFailedAttempt(phone)
            return false
        }
    }
}
