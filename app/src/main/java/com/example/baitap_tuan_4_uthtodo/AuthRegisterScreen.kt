package com.example.baitap_tuan_4_uthtodo

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
fun AuthRegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val analytics = FirebaseAnalytics.getInstance(context)

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Launcher cho Google Login
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
                        analytics.logEvent("user_login_success", null)
                        Toast.makeText(context, "Đăng nhập Google thành công!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home") {
                            popUpTo("auth_register") { inclusive = true }
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

        Text(
            text = "Tạo tài khoản mới",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Vui lòng điền thông tin bên dưới",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Input Họ tên
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Họ và tên") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Input Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email hoặc số điện thoại") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
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
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Nút Đăng ký (Email/Pass)
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            auth.createUserWithEmailAndPassword(email, password).await()
                            Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                            navController.navigate("home") {
                                popUpTo("auth_register") { inclusive = true }
                                popUpTo("auth_login") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_LONG).show()
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
                Text(text = "Đăng ký", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, 
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
            Text(
                text = "hoặc đăng ký bằng", 
                modifier = Modifier.padding(horizontal = 16.dp), 
                color = Color.Gray, 
                fontSize = 14.sp
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(text = "G", fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Google", color = Color.Black)
        }
        
        Spacer(modifier = Modifier.height(12.dp))

        // Nút Facebook
        OutlinedButton(
            onClick = { Toast.makeText(context, "Cần cấu hình App ID trên Meta Developer", Toast.LENGTH_SHORT).show() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF1877F2))
        ) {
            Text(text = "f", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Facebook", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Chuyển sang màn hình Đăng nhập
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Đã có tài khoản? ", color = Color.Gray, fontSize = 14.sp)
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Đăng nhập", color = Color(0xFF4A7DFF), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}