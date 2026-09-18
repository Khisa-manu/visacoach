package com.visacoach.interview

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

data class LiveInterviewSession(
    val sessionId: String,
    val userId: String,
    val visaTypeCode: String,
    var currentState: InterviewState = InterviewState.CONNECTING,
    var currentQuestionNumber: Int = 0,
    var currentCategory: String = "TRAVEL_PURPOSE",
    val categoriesCovered: MutableList<String> = mutableListOf(),
    val conversationHistory: MutableList<Pair<String, String>> = mutableListOf(),
    var lastHeartbeat: Long = System.currentTimeMillis()
)

@Service
class RedisSessionManager(
    private val redisTemplate: StringRedisTemplate?
) {
    private val logger = LoggerFactory.getLogger(RedisSessionManager::class.java)
    // Local fallback in case Redis cluster is unreachable during unit testing
    private val localFallback = ConcurrentHashMap<String, String>()

    fun setLiveState(sessionId: String, state: InterviewState) {
        try {
            redisTemplate?.opsForValue()?.set(
                "interview:state:$sessionId",
                state.name,
                Duration.ofHours(2)
            ) ?: run { localFallback["interview:state:$sessionId"] = state.name }
        } catch (e: Exception) {
            logger.warn("Redis write failed, using local in-memory fallback: ${e.message}")
            localFallback["interview:state:$sessionId"] = state.name
        }
    }

    fun getLiveState(sessionId: String): InterviewState {
        val raw = try {
            redisTemplate?.opsForValue()?.get("interview:state:$sessionId")
                ?: localFallback["interview:state:$sessionId"]
        } catch (e: Exception) {
            localFallback["interview:state:$sessionId"]
        }
        return raw?.let { runCatching { InterviewState.valueOf(it) }.getOrNull() } ?: InterviewState.IDLE
    }

    fun recordHeartbeat(sessionId: String) {
        try {
            redisTemplate?.opsForValue()?.set("interview:heartbeat:$sessionId", System.currentTimeMillis().toString(), Duration.ofMinutes(5))
        } catch (e: Exception) {
            localFallback["interview:heartbeat:$sessionId"] = System.currentTimeMillis().toString()
        }
    }

    fun removeSession(sessionId: String) {
        try {
            redisTemplate?.delete("interview:state:$sessionId")
            redisTemplate?.delete("interview:heartbeat:$sessionId")
        } catch (e: Exception) {
            // Ignored
        }
        localFallback.remove("interview:state:$sessionId")
        localFallback.remove("interview:heartbeat:$sessionId")
    }
}
