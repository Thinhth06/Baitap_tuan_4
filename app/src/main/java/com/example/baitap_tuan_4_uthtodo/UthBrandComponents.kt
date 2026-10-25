package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Icon App (Hình vuông bo góc màu xanh, có dấu tick và các thanh ngang)
@Composable
fun UthAppIcon(modifier: Modifier = Modifier, size: Dp = 80.dp) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.25f)) // Bo góc 25% giống iOS
            .background(Color(0xFF2F5BEA)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(size * 0.2f),
            verticalArrangement = Arrangement.Center
        ) {
            // Hàng 1: Dấu tick + thanh ngang
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(size * 0.2f)
                )
                Spacer(modifier = Modifier.width(size * 0.1f))
                Box(
                    modifier = Modifier
                        .height(size * 0.1f)
                        .weight(1f)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                )
            }
            Spacer(modifier = Modifier.height(size * 0.1f))
            // Hàng 2: Vòng tròn + thanh ngang mờ
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(size * 0.2f)
                        .clip(RoundedCornerShape(50))
                        .background(Color.Transparent) // Vòng tròn rỗng
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color(0xFF9DB5FA)) // Màu xanh nhạt
                    )
                }
                Spacer(modifier = Modifier.width(size * 0.1f))
                Box(
                    modifier = Modifier
                        .height(size * 0.1f)
                        .weight(0.8f)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF9DB5FA))
                )
            }
        }
    }
}

// 2. Logo Chữ (UTH màu đen, Todo màu xanh)
@Composable
fun UthTextLogo(modifier: Modifier = Modifier, textSize: Int = 32) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF1C1C1E), fontWeight = FontWeight.ExtraBold)) {
                append("UTH ")
            }
            withStyle(style = SpanStyle(color = Color(0xFF2F5BEA), fontWeight = FontWeight.ExtraBold)) {
                append("Todo")
            }
        },
        fontSize = textSize.sp,
        modifier = modifier,
        maxLines = 1, // Ép không xuống dòng
        softWrap = false // Không tự động ngắt dòng
    )
}
// 3. Logo ngang đầy đủ (Icon + Chữ + Slogan) dùng cho màn hình Start
@Composable
fun UthFullBrand() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        UthAppIcon(size = 100.dp)
        Spacer(modifier = Modifier.height(16.dp))
        // Vì nền Start màu xanh, nên chữ UTH Todo phải màu trắng
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.ExtraBold)) {
                    append("UTH ")
                }
                withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.ExtraBold)) {
                    append("Todo")
                }
            },
            fontSize = 40.sp,
            maxLines = 1,
            softWrap = false
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Việc học, việc nhà — gọn gàng mỗi ngày",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}