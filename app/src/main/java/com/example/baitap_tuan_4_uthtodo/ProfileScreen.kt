package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    
    val displayName = "Hữu Thịnh"
    val email = currentUser?.email ?: "tht12306@gmail.com"
    val initials = "HT"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Hồ sơ", 
                        fontWeight = FontWeight.Bold, 
                        modifier = Modifier.fillMaxWidth(), 
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    ) 
                },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Gray)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Cài đặt", color = Color.Gray)
                    }
                },
                actions = { Spacer(modifier = Modifier.width(80.dp)) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F7FA))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Avatar
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFD6E4FF), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(initials, color = Color(0xFF4A7DFF), fontWeight = FontWeight.Bold, fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(email, fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(12.dp))

            // Badge
            Row(
                modifier = Modifier
                    .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Đã liên kết tài khoản Google", color = Color(0xFF4CAF50), fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3 Card riêng biệt cho stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard("2", "Việc đã xong", Modifier.weight(1f))
                StatCard("4", "Đang chờ", Modifier.weight(1f))
                StatCard("33%", "Hoàn thành", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Biểu đồ "Tuần này"
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Tuần này", fontWeight = FontWeight.Bold)
                        Text("Việc xong mỗi ngày", fontSize = 12.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val heights = listOf(60, 40, 80, 30, 50, 20, 70)
                        val days = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")
                        for (i in 0..6) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(modifier = Modifier.width(24.dp).height(heights[i].dp).background(
                                    color = if (i == 1) Color(0xFF4A7DFF) else Color(0xFFD6E4FF),
                                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                ))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(days[i], fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = { 
                    auth.signOut()
                    navController.navigate("start") { popUpTo(0) } 
                },
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Đăng xuất", color = Color.Black)
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp, horizontal = 14.dp)
        ) {
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, color = Color.Gray)
        }
    }
}
