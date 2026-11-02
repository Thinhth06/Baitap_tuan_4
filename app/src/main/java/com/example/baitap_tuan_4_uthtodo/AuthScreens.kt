package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.mutableStateListOf
// Header UTH - To và rõ hơn
@Composable
fun AuthHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 24.dp)) {
        Text("UTH", color = Color(0xFF1E8A8A), fontSize = 48.sp, fontWeight = FontWeight.ExtraBold)
        Text("UNIVERSITY OF TRANSPORT\nHOCHIMINH CITY", color = Color(0xFFB00000), fontSize = 9.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, lineHeight = 12.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("SmartTasks", color = Color(0xFF2F5BEA), fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

// Màn hình 1: Nhập Email
@Composable
fun AuthForgetPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // Nút Back giả lập
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF2F5BEA))
            }
        }

        AuthHeader()
        Spacer(modifier = Modifier.height(32.dp))

        Text("Forget Password?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Enter your Email, we will send you a verification code.", fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Your Email", fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate("bai3_verify/$email") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F5BEA))
        ) { Text("Next", fontSize = 16.sp) }
    }
}

// Màn hình 2: Nhập OTP (Nhập đại cũng được Next)
// Màn hình 2: Nhập OTP (Đã sửa lỗi không gõ được)
@Composable
fun AuthVerifyCodeScreen(navController: NavController, email: String) {
    // Tạo một list chứa 5 ô nhập liệu
    val otpValues = remember { mutableStateListOf("", "", "", "", "") }

    // Hàm kiểm tra xem đã nhập đủ 5 ô chưa để bật nút Next (nếu muốn bắt buộc)
    // Hoặc bạn có thể bỏ qua và cho Next luôn
    val isOtpFilled = otpValues.all { it.isNotEmpty() }
    // Lưu ý: Nếu bạn muốn "nhập đại cũng được Next", hãy đổi dòng trên thành: val isOtpFilled = true

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        // Nút Back
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF2F5BEA))
            }
        }

        AuthHeader()
        Spacer(modifier = Modifier.height(32.dp))

        Text("Verify Code", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Enter the code\nwe just sent you on your registered Email", fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center, lineHeight = 18.sp)
        Spacer(modifier = Modifier.height(32.dp))

        // 5 ô vuông OTP
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            repeat(5) { index ->
                OutlinedTextField(
                    value = otpValues[index], // Gán giá trị tương ứng với từng ô
                    onValueChange = {
                        if (it.length <= 1) {
                            otpValues[index] = it // Cập nhật giá trị cho ô đó
                        }
                    },
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center, fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2F5BEA),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                // Nối các giá trị trong list lại thành 1 chuỗi OTP
                val finalOtp = otpValues.joinToString("")
                navController.navigate("bai3_newpass/$email/$finalOtp")
            },
            enabled = isOtpFilled, // Nếu muốn nhập đại cũng được thì đổi thành 'true'
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2F5BEA),
                disabledContainerColor = Color.LightGray // Màu nút khi chưa nhập đủ
            )
        ) { Text("Next", fontSize = 16.sp) }
    }
}

// Màn hình 3: Tạo mật khẩu mới
@Composable
fun AuthCreatePasswordScreen(navController: NavController, email: String, otp: String) {
    var password by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF2F5BEA))
            }
        }

        AuthHeader()
        Spacer(modifier = Modifier.height(32.dp))

        Text("Create new password", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Your new password must be different form previously used password", fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmPass,
            onValueChange = { confirmPass = it },
            placeholder = { Text("Confirm Password", fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) }
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate("bai3_confirm/$email/$otp/$password") },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F5BEA))
        ) { Text("Next", fontSize = 16.sp) }
    }
}

// Màn hình 4: Xác nhận
@Composable
fun AuthConfirmScreen(navController: NavController, email: String, otp: String, password: String) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF2F5BEA))
            }
        }

        AuthHeader()
        Spacer(modifier = Modifier.height(32.dp))

        Text("Confirm", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("We are here to help you!", fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {},
            readOnly = true,
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = otp,
            onValueChange = {},
            readOnly = true,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = "********",
            onValueChange = {},
            readOnly = true,
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            // điều hướng quay về màn hình Forget password
            onClick = { navController.navigate("bai3_forget") { popUpTo(0) } },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F5BEA))
        ) { Text("Summit", fontSize = 16.sp) }
    }
}