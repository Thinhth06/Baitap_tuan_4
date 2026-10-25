package com.example.baitap_tuan_4_uthtodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.baitap_tuan_4_uthtodo.ui.theme.Baitap_tuan_4_uthtodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Baitap_tuan_4_uthtodoTheme { // Đảm bảo tên này khớp với Theme.kt
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // ĐIỂM BẮT ĐẦU LÀ MÀN HÌNH START
    NavHost(navController = navController, startDestination = "start") {

        // --- MÀN HÌNH START (NỀN XANH, LOGO) ---
        composable("start") {
            StartScreen(navController)

            // Tự động chuyển sang Bài 1 sau 2.5 giây
            androidx.compose.runtime.LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(2500L)
                navController.navigate("bai1_onboarding") {
                    popUpTo("start") { inclusive = true } // Xóa Start khỏi lịch sử
                }
            }
        }

        // --- BÀI 1: ONBOARDING ---
        composable("bai1_onboarding") { TodoOnboardingScreen(navController) }

        // --- BÀI 2: QUẢN LÝ THƯ VIỆN ---
        composable("bai2_library") { LibraryScreen(navController) }

        // --- BÀI 3: DATA FLOW ---
        composable("bai3_forget") { AuthForgetPasswordScreen(navController) }

        composable(
            route = "bai3_verify/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            AuthVerifyCodeScreen(navController, email)
        }

        composable(
            route = "bai3_newpass/{email}/{otp}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("otp") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            AuthCreatePasswordScreen(navController, email, otp)
        }

        composable(
            route = "bai3_confirm/{email}/{otp}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("otp") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            AuthConfirmScreen(navController, email, otp, password)
        }
    }
}