package com.visacoach.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Register : Screen("register")
    object Login : Screen("login")
    object Otp : Screen("otp/{phoneNumber}") {
        fun createRoute(phoneNumber: String) = "otp/$phoneNumber"
    }
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile")
    object VisaType : Screen("visa_type")
    object InterviewPrep : Screen("interview_prep")
    object RealTimeInterview : Screen("real_time_interview")
    object Results : Screen("results/{interviewId}") {
        fun createRoute(interviewId: String) = "results/$interviewId"
    }
    object PracticeWeakAreas : Screen("practice_weak_areas")
    object History : Screen("history")
    object Subscription : Screen("subscription")
    object MpesaPayment : Screen("mpesa_payment")
    object Settings : Screen("settings")
}
