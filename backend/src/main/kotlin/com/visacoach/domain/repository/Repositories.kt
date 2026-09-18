package com.visacoach.domain.repository

import com.visacoach.domain.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findByPhoneNumber(phoneNumber: String): Optional<User>
    fun existsByPhoneNumber(phoneNumber: String): Boolean
}

@Repository
interface VisaTypeRepository : JpaRepository<VisaType, String> {
    fun findByCode(code: String): Optional<VisaType>
    fun findAllByIsActiveTrue(): List<VisaType>
}

@Repository
interface UserProfileRepository : JpaRepository<UserProfile, String> {
    fun findByUserId(userId: String): Optional<UserProfile>
}

@Repository
interface QuestionRepository : JpaRepository<Question, String> {
    fun findAllByVisaTypeIdAndIsActiveTrue(visaTypeId: String): List<Question>
    fun findAllByVisaTypeIdAndCategoryAndIsActiveTrue(visaTypeId: String, category: String): List<Question>
}

@Repository
interface InterviewRepository : JpaRepository<Interview, String> {
    fun findBySessionId(sessionId: String): Optional<Interview>
    fun findAllByUserIdOrderByCreatedAtDesc(userId: String): List<Interview>
    fun countByUserIdAndCreatedAtAfter(userId: String, timestamp: Instant): Long
}

@Repository
interface InterviewQuestionRepository : JpaRepository<InterviewQuestion, String> {
    fun findAllByInterviewIdOrderBySequenceOrderAsc(interviewId: String): List<InterviewQuestion>
}

@Repository
interface AnswerRepository : JpaRepository<Answer, String> {
    fun findByInterviewQuestionId(interviewQuestionId: String): Optional<Answer>
}

@Repository
interface EvaluationRepository : JpaRepository<Evaluation, String> {
    fun findByInterviewId(interviewId: String): Optional<Evaluation>
}

@Repository
interface FeedbackRepository : JpaRepository<Feedback, String> {
    fun findAllByEvaluationId(evaluationId: String): List<Feedback>
}

@Repository
interface PracticeSessionRepository : JpaRepository<PracticeSession, String> {
    fun findAllByUserIdOrderByCompletedAtDesc(userId: String): List<PracticeSession>
}

@Repository
interface SubscriptionPlanRepository : JpaRepository<SubscriptionPlan, String> {
    fun findByCode(code: String): Optional<SubscriptionPlan>
    fun findAllByIsActiveTrue(): List<SubscriptionPlan>
}

@Repository
interface SubscriptionRepository : JpaRepository<Subscription, String> {
    fun findByUserIdAndStatus(userId: String, status: String): Optional<Subscription>
    
    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId AND s.status = 'ACTIVE' AND s.expiryDate > :now ORDER BY s.expiryDate DESC")
    fun findActiveSubscription(@Param("userId") userId: String, @Param("now") now: Instant): Optional<Subscription>
}

@Repository
interface PaymentRepository : JpaRepository<Payment, String> {
    fun findByCheckoutRequestId(checkoutRequestId: String): Optional<Payment>
    fun findByMerchantRequestId(merchantRequestId: String): Optional<Payment>
    fun findByMpesaReceiptNumber(mpesaReceiptNumber: String): Optional<Payment>
    fun findAllByUserIdOrderByCreatedAtDesc(userId: String): List<Payment>
}

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun findByTokenHash(tokenHash: String): Optional<RefreshToken>
    fun deleteAllByUserId(userId: String)
}

@Repository
interface DeviceRepository : JpaRepository<Device, String> {
    fun findByUserIdAndDeviceId(userId: String, deviceId: String): Optional<Device>
}
