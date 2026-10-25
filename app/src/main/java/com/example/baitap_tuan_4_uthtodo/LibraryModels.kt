package com.example.baitap_tuan_4_uthtodo


// ==========================================
// BÀI 2: ÁP DỤNG ĐỦ 4 TÍNH CHẤT CỦA OOP
// ==========================================

// 1. TRỪU TƯỢNG (Abstraction): Định nghĩa bộ khung chung
interface IQuanLy {
    fun hienThiThongTin(): String
}

// Lớp trừu tượng cơ sở
abstract class Nguoi(var ten: String) : IQuanLy

// 2. KẾ THỪA (Inheritance): SinhVien kế thừa Nguoi
class SinhVien(maSV: String, tenSV: String) : Nguoi(tenSV) {
    // 3. ĐÓNG GÓI (Encapsulation): Thuộc tính private, chỉ truy cập qua hàm
    private var maSinhVien: String = maSV
    private var danhSachMuon: MutableList<Sach> = mutableListOf()

    fun muonSach(sach: Sach): Boolean {
        if (sach.isAvailable()) {
            sach.setTrangThai(false) // Đổi trạng thái sách thành đã mượn
            danhSachMuon.add(sach)
            return true
        }
        return false
    }

    // 4. ĐA HÌNH (Polymorphism): Ghi đè phương thức của interface
    override fun hienThiThongTin(): String {
        return "SV: ${this.ten} - Mã: $maSinhVien - Đã mượn: ${danhSachMuon.size} sách"
    }
}

class Sach(maSach: String, tenSach: String) : IQuanLy {
    private var ma: String = maSach
    private var ten: String = tenSach
    private var conHang: Boolean = true // true: chưa mượn, false: đã mượn

    fun isAvailable() = conHang
    fun setTrangThai(trangThai: Boolean) { this.conHang = trangThai }

    override fun hienThiThongTin(): String {
        val status = if (conHang) "Sẵn sàng" else "Đã mượn"
        return "Sách: $ten ($ma) - Trạng thái: $status"
    }
}