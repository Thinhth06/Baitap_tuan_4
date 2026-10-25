package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Màn hình 1: Nhập Email
@Composable
fun AuthForgetPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Forget Password?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Your Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("bai3_verify/$email") }, modifier = Modifier.fillMaxWidth()) { Text("Next") }
    }
}

// Màn hình 2: Nhập OTP
@Composable
fun AuthVerifyCodeScreen(navController: NavController, email: String) {
    var otp by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Verify Code", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Sent to: $email", color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = otp, onValueChange = { otp = it }, label = { Text("Enter OTP") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("bai3_newpass/$email/$otp") }, modifier = Modifier.fillMaxWidth()) { Text("Next") }
    }
}

// Màn hình 3: Tạo mật khẩu mới
@Composable
fun AuthCreatePasswordScreen(navController: NavController, email: String, otp: String) {
    var password by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Create new password", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("bai3_confirm/$email/$otp/$password") }, modifier = Modifier.fillMaxWidth()) { Text("Next") }
    }
}

// Màn hình 4: Xác nhận (Hiển thị lại data)
@Composable
fun AuthConfirmScreen(navController: NavController, email: String, otp: String, password: String) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Confirm", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Email: $email", color = Color.Red)
                Text("OTP: $otp", color = Color.Red)
                Text("Password: ${"*".repeat(password.length)}", color = Color.Red)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            // Submit xong quay về màn hình đầu tiên (Bài 1)
            navController.navigate("bai1_onboarding") { popUpTo(0) }
        }, modifier = Modifier.fillMaxWidth()) { Text("Submit") }
    }
}