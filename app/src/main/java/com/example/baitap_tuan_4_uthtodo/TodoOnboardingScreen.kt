package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        // Header: Logo nhỏ và nút Bỏ qua
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UthAppIcon(size = 36.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "UTH Todo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            TextButton(onClick = { navController.navigate("bai2_library") }) {
                Text("Bỏ qua", color = Color.Black, fontSize = 14.sp)
            }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            val item = TodoOnboardingData.items[page]
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Vẽ hình minh họa TO HƠN (Kích thước 260dp)
                Box(
                    modifier = Modifier.size(260.dp).background(Color(0xFFF3F4F9), RoundedCornerShape(40.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    when(page) {
                        0 -> { // Minh họa trang 1
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Row(modifier = Modifier.width(180.dp).height(44.dp).background(Color.White, RoundedCornerShape(12.dp)), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.padding(start = 12.dp).size(20.dp).background(Color(0xFF2F5BEA), CircleShape))
                                    Box(modifier = Modifier.height(8.dp).weight(1f).padding(horizontal = 12.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(50)))
                                }
                                Row(modifier = Modifier.width(180.dp).height(44.dp).background(Color.White, RoundedCornerShape(12.dp)), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.padding(start = 12.dp).size(20.dp).background(Color.Transparent))
                                    Box(modifier = Modifier.height(8.dp).weight(1f).padding(horizontal = 12.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(50)))
                                }
                                Row(modifier = Modifier.width(180.dp).height(44.dp).background(Color.White, RoundedCornerShape(12.dp)), verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.padding(start = 12.dp).size(20.dp).background(Color.Transparent))
                                    Box(modifier = Modifier.height(8.dp).weight(1f).padding(horizontal = 12.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(50)))
                                }
                            }
                        }
                        1 -> { // Minh họa trang 2
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Box(modifier = Modifier.width(180.dp).height(60.dp).background(Color.White, RoundedCornerShape(12.dp)).padding(16.dp)) {
                                    Text("Học tập", color = Color(0xFF2F5BEA), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                                Box(modifier = Modifier.width(180.dp).height(60.dp).background(Color.White, RoundedCornerShape(12.dp)).padding(16.dp)) {
                                    Text("Cuộc sống", color = Color(0xFFE5A93D), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        2 -> { // Minh họa trang 3
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(modifier = Modifier.size(80.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Notifications, contentDescription = null, tint = Color(0xFF2F5BEA), modifier = Modifier.size(40.dp))
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Box(modifier = Modifier.width(140.dp).height(44.dp).background(Color.White, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                                    Text("Ôn bài Toán lúc", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))
                // Tiêu đề to, đậm
                Text(
                    text = item.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Mô tả nhỏ, xám
                Text(
                    text = item.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }

        // Dấu chấm
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.Center) {
            repeat(TodoOnboardingData.items.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFF2F5BEA) else Color(0xFFE0E0E0)
                Box(modifier = Modifier.padding(4.dp).clip(CircleShape).background(color).size(8.dp))
            }
        }

        // Nút bấm TO, rõ ràng
        Button(
            onClick = {
                if (pagerState.currentPage < TodoOnboardingData.items.size - 1) {
                    coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                } else {
                    navController.navigate("bai2_library")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp).height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F5BEA)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = if (pagerState.currentPage == TodoOnboardingData.items.size - 1) "Bắt đầu ngay" else "Tiếp tục",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}