package com.visacoach.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.visacoach.ui.navigation.Screen
import com.visacoach.ui.screens.*
import com.visacoach.ui.theme.VisaCoachTheme
import com.visacoach.ui.viewmodels.AuthViewModel
import com.visacoach.ui.viewmodels.InterviewViewModel
import com.visacoach.ui.viewmodels.PaymentViewModel
import com.visacoach.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VisaCoachTheme {
                RequestAudioPermission()
                AppNavigation()
            }
        }
    }
}

@Composable
fun RequestAudioPermission() {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handle mic permission result
    }

    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.RECORD_AUDIO)
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToWelcome = {
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.Register.route) {
            val authVm: AuthViewModel = hiltViewModel()
            RegisterScreen(
                viewModel = authVm,
                onNavigateToOtp = { phone -> navController.navigate(Screen.Otp.createRoute(phone)) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Login.route) {
            val authVm: AuthViewModel = hiltViewModel()
            LoginScreen(
                viewModel = authVm,
                onNavigateToOtp = { phone -> navController.navigate(Screen.Otp.createRoute(phone)) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Otp.route) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            val authVm: AuthViewModel = hiltViewModel()
            OtpScreen(
                phoneNumber = phone,
                viewModel = authVm,
                onAuthSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            val paymentVm: PaymentViewModel = hiltViewModel()
            DashboardScreen(
                paymentViewModel = paymentVm,
                onStartInterview = { navController.navigate(Screen.RealTimeInterview.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToSubscription = { navController.navigate(Screen.Subscription.route) },
                onNavigateToPractice = { navController.navigate(Screen.PracticeWeakAreas.route) },
                onNavigateToHistory = { navController.navigate(Screen.History.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Profile.route) {
            val profileVm: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = profileVm,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.VisaType.route) {
            VisaTypeScreen(
                onBack = { navController.popBackStack() },
                onStartPrep = { navController.navigate(Screen.InterviewPrep.route) }
            )
        }

        composable(Screen.InterviewPrep.route) {
            InterviewPrepScreen(
                onBack = { navController.popBackStack() },
                onStartMock = { navController.navigate(Screen.RealTimeInterview.route) }
            )
        }

        composable(Screen.RealTimeInterview.route) {
            val interviewVm: InterviewViewModel = hiltViewModel()
            RealTimeInterviewScreen(
                viewModel = interviewVm,
                onInterviewCompleted = { interviewId ->
                    navController.navigate(Screen.Results.createRoute(interviewId)) {
                        popUpTo(Screen.Dashboard.route)
                    }
                },
                onClose = { navController.popBackStack() }
            )
        }

        composable(Screen.Results.route) {
            InterviewResultsScreen(
                onDone = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onPracticeWeakAreas = { navController.navigate(Screen.PracticeWeakAreas.route) }
            )
        }

        composable(Screen.PracticeWeakAreas.route) {
            PracticeWeakAreasScreen(
                onBack = { navController.popBackStack() },
                onSelectCategory = { navController.navigate(Screen.RealTimeInterview.route) }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Subscription.route) {
            SubscriptionScreen(
                onBack = { navController.popBackStack() },
                onUpgradeMpesa = { navController.navigate(Screen.MpesaPayment.route) }
            )
        }

        composable(Screen.MpesaPayment.route) {
            val paymentVm: PaymentViewModel = hiltViewModel()
            MpesaPaymentScreen(
                viewModel = paymentVm,
                onBack = { navController.popBackStack() },
                onSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            val authVm: AuthViewModel = hiltViewModel()
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onLogout = {
                    authVm.logout()
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
