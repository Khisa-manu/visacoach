package com.visacoach.domain.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(name = "phone_number", nullable = false, unique = true, length = 20)
    var phoneNumber: String = "",

    @Column(name = "full_name", length = 120)
    var fullName: String? = null,

    @Column(name = "password_hash")
    var passwordHash: String? = null,

    @Column(name = "is_phone_verified", nullable = false)
    var isPhoneVerified: Boolean = false,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Column(name = "role", nullable = false, length = 30)
    var role: String = "ROLE_USER",

    @Column(name = "failed_attempts", nullable = false)
    var failedAttempts: Int = 0,

    @Column(name = "lockout_until")
    var lockoutUntil: Instant? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var profile: UserProfile? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var interviews: MutableList<Interview> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var payments: MutableList<Payment> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var subscriptions: MutableList<Subscription> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var devices: MutableList<Device> = mutableListOf()
)

@Entity
@Table(name = "visa_types")
data class VisaType(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true, length = 30)
    val code: String = "",

    @Column(nullable = false, length = 100)
    val name: String = "",

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true
)

@Entity
@Table(name = "user_profiles")
data class UserProfile(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: User? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visa_type_id")
    var visaType: VisaType? = null,

    @Column(name = "full_name", nullable = false, length = 150)
    var fullName: String = "",

    @Column(name = "purpose_of_travel", nullable = false, columnDefinition = "TEXT")
    var purposeOfTravel: String = "",

    @Column(name = "intended_travel_date", length = 60)
    var intendedTravelDate: String? = null,

    @Column(name = "intended_duration", length = 60)
    var intendedDuration: String? = null,

    @Column(length = 100)
    var occupation: String? = null,

    @Column(length = 150)
    var employer: String? = null,

    @Column(name = "employment_duration", length = 60)
    var employmentDuration: String? = null,

    @Column(name = "income_range", length = 80)
    var incomeRange: String? = null,

    @Column(name = "sponsor_type", length = 100)
    var sponsorType: String? = null,

    @Column(name = "international_travel_history", columnDefinition = "TEXT")
    var internationalTravelHistory: String? = null,

    @Column(name = "us_visa_history", columnDefinition = "TEXT")
    var usVisaHistory: String? = null,

    @Column(name = "us_family_info", columnDefinition = "TEXT")
    var usFamilyInfo: String? = null,

    @Column(name = "accommodation_details", columnDefinition = "TEXT")
    var accommodationDetails: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()
)

@Entity
@Table(name = "questions")
data class Question(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visa_type_id", nullable = false)
    val visaType: VisaType,

    @Column(nullable = false, length = 50)
    val category: String = "",

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    val questionText: String = "",

    @Column(nullable = false)
    val difficulty: Int = 1,

    @Column(name = "follow_up_hint", columnDefinition = "TEXT")
    val followUpHint: String? = null,

    @Column(name = "evaluation_criteria", columnDefinition = "TEXT")
    val evaluationCriteria: String? = null,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true
)

@Entity
@Table(name = "interviews")
data class Interview(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "visa_type_id", nullable = false)
    val visaType: VisaType,

    @Column(name = "session_id", nullable = false, unique = true, length = 64)
    val sessionId: String = UUID.randomUUID().toString(),

    @Column(nullable = false, length = 30)
    var status: String = "PENDING",

    @Column(name = "started_at")
    var startedAt: Instant? = null,

    @Column(name = "completed_at")
    var completedAt: Instant? = null,

    // Audio storage reference string only, never binary audio in MySQL
    @Column(name = "audio_recording_storage_ref", length = 255)
    var audioRecordingStorageRef: String? = null,

    @Column(name = "total_questions", nullable = false)
    var totalQuestions: Int = 0,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now(),

    @Column(name = "deleted_at")
    var deletedAt: Instant? = null,

    @OneToMany(mappedBy = "interview", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var questions: MutableList<InterviewQuestion> = mutableListOf(),

    @OneToOne(mappedBy = "interview", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var evaluation: Evaluation? = null
)

@Entity
@Table(name = "interview_questions")
data class InterviewQuestion(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    val interview: Interview,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    val question: Question? = null,

    @Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
    val questionText: String = "",

    @Column(nullable = false, length = 50)
    val category: String = "",

    @Column(name = "sequence_order", nullable = false)
    val sequenceOrder: Int = 1,

    @Column(name = "is_follow_up", nullable = false)
    val isFollowUp: Boolean = false,

    @Column(name = "audio_prompt_storage_ref", length = 255)
    var audioPromptStorageRef: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @OneToOne(mappedBy = "interviewQuestion", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var answer: Answer? = null
)

@Entity
@Table(name = "answers")
data class Answer(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_question_id", nullable = false, unique = true)
    val interviewQuestion: InterviewQuestion,

    @Column(name = "transcript_text", columnDefinition = "TEXT")
    var transcriptText: String? = null,

    @Column(name = "audio_response_storage_ref", length = 255)
    var audioResponseStorageRef: String? = null,

    @Column(name = "duration_seconds", nullable = false)
    var durationSeconds: Int = 0,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)

@Entity
@Table(name = "evaluations")
data class Evaluation(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false, unique = true)
    val interview: Interview,

    @Column(name = "relevance_score", nullable = false, precision = 4, scale = 2)
    var relevanceScore: BigDecimal = BigDecimal.ZERO,

    @Column(name = "clarity_score", nullable = false, precision = 4, scale = 2)
    var clarityScore: BigDecimal = BigDecimal.ZERO,

    @Column(name = "consistency_score", nullable = false, precision = 4, scale = 2)
    var consistencyScore: BigDecimal = BigDecimal.ZERO,

    @Column(name = "conciseness_score", nullable = false, precision = 4, scale = 2)
    var concisenessScore: BigDecimal = BigDecimal.ZERO,

    @Column(name = "completeness_score", nullable = false, precision = 4, scale = 2)
    var completenessScore: BigDecimal = BigDecimal.ZERO,

    @Column(name = "communication_score", nullable = false, precision = 4, scale = 2)
    var communicationScore: BigDecimal = BigDecimal.ZERO,

    @Column(name = "overall_score", nullable = false, precision = 4, scale = 2)
    var overallScore: BigDecimal = BigDecimal.ZERO,

    @Column(name = "strengths_json", columnDefinition = "JSON")
    var strengthsJson: String? = null,

    @Column(name = "areas_to_practice_json", columnDefinition = "JSON")
    var areasToPracticeJson: String? = null,

    @Column(name = "potential_inconsistencies_json", columnDefinition = "JSON")
    var potentialInconsistenciesJson: String? = null,

    @Column(name = "recommendations_json", columnDefinition = "JSON")
    var recommendationsJson: String? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "evaluation", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var feedbackList: MutableList<Feedback> = mutableListOf()
)

@Entity
@Table(name = "feedback")
data class Feedback(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    val evaluation: Evaluation,

    @Column(nullable = false, length = 50)
    val category: String = "",

    @Column(name = "feedback_text", nullable = false, columnDefinition = "TEXT")
    val feedbackText: String = "",

    @Column(nullable = false, length = 20)
    val severity: String = "INFO",

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)

@Entity
@Table(name = "practice_sessions")
data class PracticeSession(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false, length = 50)
    val category: String = "",

    @Column(name = "question_count", nullable = false)
    val questionCount: Int = 0,

    @Column(name = "average_score", nullable = false, precision = 4, scale = 2)
    val averageScore: BigDecimal = BigDecimal.ZERO,

    @CreationTimestamp
    @Column(name = "completed_at", nullable = false)
    val completedAt: Instant = Instant.now()
)

@Entity
@Table(name = "subscription_plans")
data class SubscriptionPlan(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true, length = 30)
    val code: String = "",

    @Column(nullable = false, length = 80)
    val name: String = "",

    @Column(name = "price_kes", nullable = false, precision = 10, scale = 2)
    val priceKes: BigDecimal = BigDecimal.ZERO,

    @Column(name = "duration_days", nullable = false)
    val durationDays: Int = 30,

    @Column(name = "mock_interviews_limit", nullable = false)
    val mockInterviewsLimit: Int = 1,

    @Column(name = "has_deep_evaluation", nullable = false)
    val hasDeepEvaluation: Boolean = false,

    @Column(name = "has_unlimited_practice", nullable = false)
    val hasUnlimitedPractice: Boolean = false,

    @Column(name = "is_active", nullable = false)
    val isActive: Boolean = true
)

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "phone_number", nullable = false, length = 20)
    val phoneNumber: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    val amount: BigDecimal = BigDecimal.ZERO,

    @Column(name = "merchant_request_id", nullable = false, length = 80)
    val merchantRequestId: String = "",

    @Column(name = "checkout_request_id", nullable = false, unique = true, length = 80)
    val checkoutRequestId: String = "",

    @Column(name = "mpesa_receipt_number", unique = true, length = 80)
    var mpesaReceiptNumber: String? = null,

    @Column(nullable = false, length = 30)
    var status: String = "PENDING",

    @Column(name = "result_code")
    var resultCode: Int? = null,

    @Column(name = "result_desc")
    var resultDesc: String? = null,

    @Column(name = "transaction_timestamp", nullable = false)
    var transactionTimestamp: Instant = Instant.now(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()
)

@Entity
@Table(name = "subscriptions")
data class Subscription(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", nullable = false)
    val plan: SubscriptionPlan,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    var payment: Payment? = null,

    @Column(nullable = false, length = 30)
    var status: String = "ACTIVE",

    @Column(name = "start_date", nullable = false)
    var startDate: Instant = Instant.now(),

    @Column(name = "expiry_date", nullable = false)
    var expiryDate: Instant = Instant.now().plusSeconds(86400L * 30),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.now()
)

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "token_hash", nullable = false, unique = true, length = 255)
    val tokenHash: String = "",

    @Column(name = "device_info", length = 150)
    val deviceInfo: String? = null,

    @Column(name = "is_revoked", nullable = false)
    var isRevoked: Boolean = false,

    @Column(name = "expires_at", nullable = false)
    val expiresAt: Instant = Instant.now().plusSeconds(604800L),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)

@Entity
@Table(name = "devices")
data class Device(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "device_id", nullable = false, length = 100)
    val deviceId: String = "",

    @Column(name = "device_model", length = 100)
    val deviceModel: String? = null,

    @Column(name = "os_version", length = 50)
    val osVersion: String? = null,

    @Column(name = "fcm_token", length = 255)
    var fcmToken: String? = null,

    @Column(name = "last_active_at", nullable = false)
    var lastActiveAt: Instant = Instant.now(),

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)
