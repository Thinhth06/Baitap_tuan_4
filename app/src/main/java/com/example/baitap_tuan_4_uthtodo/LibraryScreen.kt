package com.example.baitap_tuan_4_uthtodo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LibraryScreen(navController: NavController) {
    val sinhVien = remember { SinhVien("SV001", "Nguyen Van A") }
    val danhSachSach = remember {
        mutableStateListOf(Sach("S01", "Sách 01"), Sach("S02", "Sách 02"), Sach("S03", "Sách 03"))
    }
    var thongBao by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Hệ thống Quản lý Thư viện", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(sinhVien.hienThiThongTin(), color = Color(0xFF2F5BEA), fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        Text("Danh sách sách:", fontWeight = FontWeight.Bold)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(danhSachSach) { sach ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(sach.hienThiThongTin(), modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                val thanhCong = sinhVien.muonSach(sach)
                                thongBao = if (thanhCong) "Mượn thành công ${sach.hienThiThongTin()}" else "Sách đã có người mượn!"
                            },
                            enabled = sach.isAvailable()
                        ) { Text("Mượn") }
                    }
                }
            }
        }

        if (thongBao.isNotEmpty()) {
            Text(thongBao, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Button(onClick = { navController.navigate("bai3_forget") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Đi tới Bài 3 (Quên mật khẩu)")
        }
    }
}