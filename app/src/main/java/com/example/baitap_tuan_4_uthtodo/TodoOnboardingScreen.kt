package com.example.baitap_tuan_4_uthtodo


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoOnboardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { TodoOnboardingData.items.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // Căn giữa theo chiều dọc
        ) {
            // Logo nhỏ bên trái (Icon + Chữ)
            Row(verticalAlignment = Alignment.CenterVertically) {
                UthAppIcon(size = 32.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF1C1C1E), fontWeight = FontWeight.Bold)) {
                            append("UTH ")
                        }
                        withStyle(style = SpanStyle(
                            color = Color(0xFF2F5BEA),
                            fontWeight = FontWeight.Bold
                        )
                        ) {
                            append("Todo")
                        }
                    },
                    fontSize = 18.sp
                )
            }

            // Nút Bỏ qua bên phải
            TextButton(onClick = { navController.navigate("bai2_library") }) {
                Text("Bỏ qua", color = Color.Black)
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val item = TodoOnboardingData.items[page]
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(200.dp).background(Color(0xFFF0F4FF), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = item.icon, contentDescription = null, tint = Color(0xFF2F5BEA), modifier = Modifier.size(80.dp))
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = item.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = item.description, fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Center) {
            repeat(TodoOnboardingData.items.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFF2F5BEA) else Color.LightGray
                Box(modifier = Modifier.padding(4.dp).clip(CircleShape).background(color).size(8.dp))
            }
        }

        Button(
            onClick = {
                if (pagerState.currentPage < TodoOnboardingData.items.size - 1) {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                } else {
                    navController.navigate("bai2_library")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp).height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F5BEA))
        ) {
            Text(if (pagerState.currentPage == TodoOnboardingData.items.size - 1) "Bắt đầu ngay" else "Tiếp tục", color = Color.White, fontSize = 16.sp)
        }
    }
}