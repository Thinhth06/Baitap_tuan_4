package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StartScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2F5BEA)), // Màu nền xanh chủ đạo
        contentAlignment = Alignment.Center
    ) {
        // Logo ở giữa (Gọi từ file UthBrandComponents.kt)
        UthFullBrand()

        // Text phiên bản ở dưới cùng
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Thanh gạch ngang nhỏ
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp)
                    .background(Color.White, shape = RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Phiên bản 2.1.0",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}