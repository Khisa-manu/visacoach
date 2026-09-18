package com.visacoach.security

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Service
class RateLimitingService {
    private val attemptsCache = ConcurrentHashMap<String, AttemptRecord>()
    private val maxAttempts = 5
    private val lockoutDurationSeconds = 900L // 15 minutes lockout

    data class AttemptRecord(var count: Int, var firstAttemptTime: Instant, var lockoutUntil: Instant?)

    fun isLockedOut(key: String): Boolean {
        val record = attemptsCache[key] ?: return false
        val lockout = record.lockoutUntil ?: return false
        if (Instant.now().isBefore(lockout)) {
            return true
        }
        // Lockout expired
        attemptsCache.remove(key)
        return false
    }

    fun recordFailedAttempt(key: String): Int {
        val record = attemptsCache.compute(key) { _, current ->
            if (current == null) {
                AttemptRecord(1, Instant.now(), null)
            } else {
                current.count += 1
                if (current.count >= maxAttempts) {
                    current.lockoutUntil = Instant.now().plusSeconds(lockoutDurationSeconds)
                }
                current
            }
        }
        return record?.count ?: 0
    }

    fun resetAttempts(key: String) {
        attemptsCache.remove(key)
    }

    fun getRemainingLockoutSeconds(key: String): Long {
        val record = attemptsCache[key] ?: return 0L
        val lockout = record.lockoutUntil ?: return 0L
        val remaining = lockout.epochSecond - Instant.now().epochSecond
        return if (remaining > 0) remaining else 0L
    }
}
