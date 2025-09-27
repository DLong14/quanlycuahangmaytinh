package com.shopcomputer.model;

import java.math.BigDecimal;

public class SanPham {
    private int id;
    private String maSp;
    private String tenSp;
    private String loaiSp;
    private int soLuong;
    private BigDecimal gia;
    private String moTa;

    // Getters và setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaSp() { return maSp; }
    public void setMaSp(String maSp) { this.maSp = maSp; }

    public String getTenSp() { return tenSp; }
    public void setTenSp(String tenSp) { this.tenSp = tenSp; }

    public String getLoaiSp() { return loaiSp; }
    public void setLoaiSp(String loaiSp) { this.loaiSp = loaiSp; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    // Override toString để hiển thị tên sản phẩm
    @Override
    public String toString() {
        return tenSp; // hoặc "maSp - tenSp" nếu muốn hiển thị thêm mã sản phẩm
    }
}
