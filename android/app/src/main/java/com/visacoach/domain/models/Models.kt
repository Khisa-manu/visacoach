package com.visacoach.domain.models

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
    val deviceInfo: String? = "Android Device"
)

data class RefreshRequest(
    val refreshToken: String,
    val deviceInfo: String? = "Android Device"
)

data class MessageResponse(
    val message: String,
    val success: Boolean = true
)

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String,
    val expiresInSeconds: Long,
    val user: UserModel
)

data class UserModel(
    val id: String,
    val phoneNumber: String,
    val fullName: String?,
    val isPhoneVerified: Boolean,
    val role: String
)

data class ProfileModel(
    val id: String? = null,
    val userId: String? = null,
    val visaTypeCode: String = "B1_B2",
    val visaTypeName: String? = "B1/B2 Visitor Visa",
    val fullName: String = "",
    val purposeOfTravel: String = "",
    val intendedTravelDate: String? = null,
    val intendedDuration: String? = null,
    val occupation: String? = null,
    val employer: String? = null,
    val employmentDuration: String? = null,
    val incomeRange: String? = null,
    val sponsorType: String? = null,
    val internationalTravelHistory: String? = null,
    val usVisaHistory: String? = null,
    val usFamilyInfo: String? = null,
    val accommodationDetails: String? = null
)

data class PlanModel(
    val code: String,
    val name: String,
    val priceKes: Double,
    val durationDays: Int,
    val mockInterviewsLimit: Int,
    val hasDeepEvaluation: Boolean,
    val hasUnlimitedPractice: Boolean
)

data class SubscriptionModel(
    val planCode: String,
    val planName: String,
    val status: String,
    val isPremium: Boolean,
    val expiryDate: String?,
    val mockInterviewsRemaining: Int
)

data class StkPushRequest(
    val phoneNumber: String,
    val planCode: String = "PREMIUM"
)

data class StkPushResponse(
    val checkoutRequestId: String,
    val merchantRequestId: String,
    val responseDescription: String,
    val customerMessage: String
)

data class PaymentStatusResponse(
    val checkoutRequestId: String,
    val status: String,
    val amount: Double?,
    val mpesaReceipt: String?,
    val resultDesc: String?
)

data class EvaluationModel(
    val interviewId: String,
    val sessionId: String,
    val completedAt: String,
    val overallScore: Double,
    val scores: Map<String, Double>,
    val strengths: List<String>,
    val areasToPractice: List<String>,
    val potentialInconsistencies: List<String>,
    val recommendations: List<String>,
    val disclaimer: String
)
