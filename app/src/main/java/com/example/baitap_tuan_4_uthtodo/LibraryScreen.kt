package com.example.baitap_tuan_4_uthtodo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
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

@Composable
fun LibraryScreen(navController: NavController) {
    val sinhVien = remember { SinhVien("SV001", "Nguyen Van A") }
    var tenSV by remember { mutableStateOf(sinhVien.ten) }
    val danhSachSach = remember {
        mutableStateListOf(Sach("S01", "Sách 01"), Sach("S02", "Sách 02"), Sach("S03", "Sách 03"))
    }
    var thongBao by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(24.dp)) },
                    label = { Text("Quản lý", fontSize = 10.sp) },
                    selected = true,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFF2F5BEA), selectedTextColor = Color(0xFF2F5BEA), indicatorColor = Color.Transparent)
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = null, modifier = Modifier.size(24.dp)) },
                    label = { Text("DS Sách", fontSize = 10.sp) },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray)
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(24.dp)) },
                    label = { Text("Sinh viên", fontSize = 10.sp) },
                    selected = false,
                    onClick = { },
                    colors = NavigationBarItemDefaults.colors(unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray)
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(20.dp)) {
            // Tiêu đề
            Text(
                "Hệ thống\nQuản lý Thư viện",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 28.sp
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Phần Sinh viên
            Text("Sinh viên", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = tenSV,
                    onValueChange = { tenSV = it },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(8.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E4CA1)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(50.dp)
                ) {
                    Text("Thay đổi", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Danh sách sách", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // Khung xám chứa danh sách sách
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(danhSachSach) { sach ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = sach.isAvailable(),
                                onCheckedChange = null,
                                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFB00000))
                            )
                            Text(sach.getTen(), fontSize = 14.sp, modifier = Modifier.weight(1f))

                            Button(
                                onClick = {
                                    val thanhCong = sinhVien.muonSach(sach)
                                    thongBao = if (thanhCong) "Đã mượn ${sach.getTen()}" else "Sách đã mượn!"
                                },
                                enabled = sach.isAvailable(),
                                modifier = Modifier.height(32.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F5BEA))
                            ) {
                                Text("Mượn", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            if (thongBao.isNotEmpty()) {
                Text(thongBao, color = Color.Red, modifier = Modifier.padding(vertical = 8.dp), fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(0.6f).height(48.dp).align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E4CA1)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Thêm", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { navController.navigate("bai3_forget") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F5BEA))
            ) {
                Text("Đi tới Bài 3 (Quên mật khẩu)")
            }
        }
    }
}