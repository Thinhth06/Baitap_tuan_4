package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

data class TaskModel(
    val id: Int,
    val title: String,
    val time: String,
    val tag: String,
    var isDone: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    
    // Lấy tên từ Firebase Auth, nếu không có thì lấy phần trước dấu @ của email, nếu không có nữa thì là "Hữu Thịnh"
    val rawName = currentUser?.displayName ?: currentUser?.email?.substringBefore("@") ?: "Hữu Thịnh"
    val displayName = if (rawName.equals("tht12306", ignoreCase = true) || rawName.isBlank()) "Hữu Thịnh" else rawName
    
    val initials = "HT"

    // Danh sách task đúng chuẩn ảnh mẫu
    val taskList = remember {
        mutableStateListOf(
            TaskModel(1, "Tập thể dục 30 phút", "06:30", "Cuộc sống", true),
            TaskModel(2, "Nộp bài tập Lập trình di động", "09:00", "Học tập", true),
            TaskModel(3, "Ôn chương 3 – Cấu trúc dữ liệu", "14:00", "Học tập", false),
            TaskModel(4, "Đi chợ mua đồ cho tuần", "17:30", "Cuộc sống", false)
        )
    }

    val completedCount = taskList.count { it.isDone }
    val totalCount = taskList.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f
    val progressPercent = (progress * 100).toInt()

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.White,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomNavIcon("Trang chủ", Icons.Default.Home, Color(0xFF4A7DFF))
                    BottomNavIcon("Danh sách", Icons.Default.List, Color.Gray)
                    
                    FloatingActionButton(
                        onClick = { /* Add task */ },
                        containerColor = Color(0xFF4A7DFF),
                        shape = CircleShape,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                    
                    BottomNavIcon("Thông báo", Icons.Default.Notifications, Color.Gray)
                    BottomNavIcon("Cài đặt", Icons.Default.Settings, Color.Gray) {
                        navController.navigate("profile")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F7FA))
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFFD6E4FF), shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(initials, color = Color(0xFF4A7DFF), fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Thứ Ba, 20 tháng 10", fontSize = 12.sp, color = Color.Gray)
                            Text("Chào $displayName!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Icon(Icons.Default.Notifications, contentDescription = "Alert", tint = Color.Red)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Progress Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF4A7DFF))
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Tiến độ hôm nay", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                            Text("$completedCount/$totalCount việc", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text("Đã hơn nửa chặng, cố lên!", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                        }
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = progress,
                                color = Color.White,
                                strokeWidth = 6.dp,
                                modifier = Modifier.size(60.dp)
                            )
                            Text("$progressPercent%", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Categories
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CategoryCard("Học tập", "2 việc còn lại", Icons.Default.List, Modifier.weight(1f))
                    CategoryCard("Cuộc sống", "2 việc còn lại", Icons.Default.Home, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Việc hôm nay", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Xem tất cả", fontSize = 14.sp, color = Color(0xFF4A7DFF))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            // Task List tương tác
            items(taskList) { task ->
                TaskItemInteractive(
                    task = task,
                    onToggle = {
                        val index = taskList.indexOf(task)
                        if (index != -1) {
                            taskList[index] = task.copy(isDone = !task.isDone)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun TaskItemInteractive(task: TaskModel, onToggle: () -> Unit) {
    val (tagBg, tagColor) = if (task.tag == "Học tập") {
        Pair(Color(0xFFE5EDFF), Color(0xFF4A7DFF))
    } else {
        Pair(Color(0xFFFFF3E0), Color(0xFFD97706))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onToggle, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = if (task.isDone) Color(0xFF4A7DFF) else Color.LightGray
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title, 
                    fontWeight = FontWeight.Bold, 
                    color = if (task.isDone) Color.Gray else Color.Black,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = task.tag, 
                        fontSize = 10.sp, 
                        color = tagColor, 
                        modifier = Modifier
                            .background(tagBg, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(task.time, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}

@Composable
fun CategoryCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = Color(0xFF4A7DFF))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun BottomNavIcon(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color, onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)) {
        IconButton(onClick = onClick, modifier = Modifier.size(24.dp)) {
            Icon(icon, contentDescription = label, tint = tint)
        }
        Text(label, fontSize = 10.sp, color = tint)
    }
}
