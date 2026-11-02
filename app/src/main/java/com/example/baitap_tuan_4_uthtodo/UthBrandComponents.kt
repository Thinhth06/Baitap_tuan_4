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

@Composable
fun UthAppIcon(modifier: Modifier = Modifier, size: Dp = 80.dp) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.25f))
            .background(Color(0xFF2F5BEA)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(size * 0.2f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(size * 0.2f)
                )
                Spacer(modifier = Modifier.width(size * 0.1f))
                Box(
                    modifier = Modifier.height(size * 0.1f).weight(1f)
                        .clip(RoundedCornerShape(50)).background(Color.White)
                )
            }
            Spacer(modifier = Modifier.height(size * 0.1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(size * 0.2f).clip(RoundedCornerShape(50)).background(Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(3.dp)
                            .clip(RoundedCornerShape(50)).background(Color(0xFF9DB5FA))
                    )
                }
                Spacer(modifier = Modifier.width(size * 0.1f))
                Box(
                    modifier = Modifier.height(size * 0.1f).weight(0.8f)
                        .clip(RoundedCornerShape(50)).background(Color(0xFF9DB5FA))
                )
            }
        }
    }
}

@Composable
fun UthTextLogo(modifier: Modifier = Modifier, textSize: Int = 32, isDarkBackground: Boolean = false) {
    val colorUTH = if (isDarkBackground) Color.White else Color(0xFF1C1C1E)
    val colorTodo = Color(0xFF2F5BEA)

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = colorUTH, fontWeight = FontWeight.ExtraBold)) { append("UTH ") }
            withStyle(style = SpanStyle(color = if(isDarkBackground) Color.White else colorTodo, fontWeight = FontWeight.ExtraBold)) { append("Todo") }
        },
        fontSize = textSize.sp,
        modifier = modifier,
        maxLines = 1,
        softWrap = false
    )
}

@Composable
fun UthFullBrand() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        UthAppIcon(size = 100.dp)
        Spacer(modifier = Modifier.height(16.dp))
        UthTextLogo(textSize = 40, isDarkBackground = true)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Việc học, việc nhà — gọn gàng mỗi ngày",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}