package com.visacoach.data.remote

import com.visacoach.domain.models.*
import retrofit2.Response
import retrofit2.http.*

interface VisaCoachApi {

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<MessageResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<MessageResponse>

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<AuthResponse>

    @POST("api/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshRequest): Response<AuthResponse>

    @POST("api/auth/logout")
    suspend fun logout(): Response<MessageResponse>

    @GET("api/profile")
    suspend fun getProfile(): Response<ProfileModel>

    @PUT("api/profile")
    suspend fun updateProfile(@Body profile: ProfileModel): Response<ProfileModel>

    @GET("api/plans")
    suspend fun getPlans(): Response<List<PlanModel>>

    @GET("api/subscription")
    suspend fun getSubscription(): Response<SubscriptionModel>

    @POST("api/payments/mpesa/stk-push")
    suspend fun initiateStkPush(@Body request: StkPushRequest): Response<StkPushResponse>

    @GET("api/payments/status/{checkoutRequestId}")
    suspend fun checkPaymentStatus(@Path("checkoutRequestId") checkoutRequestId: String): Response<PaymentStatusResponse>

    @GET("api/evaluations/{interviewId}")
    suspend fun getEvaluation(@Path("interviewId") interviewId: String): Response<EvaluationModel>
}
