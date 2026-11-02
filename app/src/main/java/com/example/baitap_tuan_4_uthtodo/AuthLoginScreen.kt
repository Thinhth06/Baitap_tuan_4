package com.example.baitap_tuan_4_uthtodo

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.google.firebase.analytics.FirebaseAnalytics

@Composable
fun AuthLoginScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val analytics = FirebaseAnalytics.getInstance(context)
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                coroutineScope.launch {
                    try {
                        auth.signInWithCredential(credential).await()
                        Toast.makeText(context, "Đăng nhập Google thành công!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home") {
                            popUpTo("auth_login") { inclusive = true }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Lỗi xác thực: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(context, "Lỗi Google Sign In", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Logo hộp màu xanh bo góc tinh tế
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color(0xFF4A7DFF), shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tiêu đề
        Text(
            text = "Chào mừng đến với\nUTH Todo",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Subtitle được làm to hơn và dễ đọc hơn (14.sp)
        Text(
            text = "Đăng nhập để lưu công việc và đồng bộ\ntên mọi thiết bị của bạn.",
            fontSize = 14.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Input Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email hoặc số điện thoại") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        // Input Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mật khẩu") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))
        
        // Quên mật khẩu
        Text(
            text = "Quên mật khẩu?",
            color = Color(0xFF4A7DFF),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { navController.navigate("bai3_forget") }
        )
        
        Spacer(modifier = Modifier.height(20.dp))

        // Nút Đăng nhập
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            auth.signInWithEmailAndPassword(email, password).await()
                            analytics.logEvent("user_login_success", null)
                            Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                            navController.navigate("home") {
                                popUpTo("auth_login") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
                        } finally {
                            isLoading = false
                        }
                    }
                } else {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A7DFF)),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Đăng nhập", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Divider "hoặc"
        Row(
            verticalAlignment = Alignment.CenterVertically, 
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
            Text(
                text = "hoặc", 
                modifier = Modifier.padding(horizontal = 16.dp), 
                color = Color.Gray, 
                fontSize = 13.sp
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Nút Google
        OutlinedButton(
            onClick = {
                try {
                    val clientIdRes = context.resources.getIdentifier("default_web_client_id", "string", context.packageName)
                    if (clientIdRes == 0) {
                        Toast.makeText(context, "Chưa có SHA-1 hoặc google-services.json", Toast.LENGTH_LONG).show()
                        return@OutlinedButton
                    }
                    
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.resources.getString(clientIdRes))
                        .requestEmail()
                        .build()

                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
        ) {
            Text(text = "G", fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Tiếp tục với Google", color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Nút Facebook
        OutlinedButton(
            onClick = { 
                Toast.makeText(context, "Cần cấu hình App ID trên Meta Developer", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF1877F2))
        ) {
            Text(text = "f", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Tiếp tục với Facebook", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dùng thử không cần tài khoản
        TextButton(onClick = { navController.navigate("home") }) {
            Text(text = "Dùng thử không cần tài khoản", color = Color(0xFF4A7DFF), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chuyển sang màn hình Đăng ký
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Chưa có tài khoản? ", color = Color.Gray, fontSize = 14.sp)
            TextButton(onClick = { navController.navigate("auth_register") }) {
                Text("Đăng ký ngay", color = Color(0xFF4A7DFF), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        // Footer to hơn và dễ đọc hơn (12.sp)
        Text(
            text = "Khi tiếp tục, bạn đồng ý với Điều khoản sử dụng và Chính sách quyền riêng tư của UTH Todo.",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 32.dp)
        )
    }
}
