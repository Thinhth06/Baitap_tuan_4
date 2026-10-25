package com.example.baitap_tuan_4_uthtodo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

// 1. Dữ liệu cho màn hình Onboarding (Bài 1)
data class TodoItem(
    val id: Int,
    val title: String,
    val description: String,
    val icon: ImageVector
)

object TodoOnboardingData {
    val items = listOf(
        TodoItem(1, "Mọi việc, gọn trong một chỗ", "Ghi lại bài tập, việc nhà, lịch hẹn. Không còn quên việc nào.", Icons.Default.List),
        TodoItem(2, "Chia theo Học tập và Cuộc sống", "Mỗi việc có nhóm riêng, nhìn là biết hôm nay cần làm gì.", Icons.Default.Menu),
        TodoItem(3, "Nhắc đúng lúc", "Bật thông báo để UTH Todo nhắc bạn trước hạn chót.", Icons.Default.Notifications)
    )
}