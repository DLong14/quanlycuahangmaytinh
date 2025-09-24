package com.shopcomputer.model;

import java.math.BigDecimal;

public class ChiTietDonHang {
    private int id;
    private String maDh;
    private String maSp;
    private int soLuong;
    private BigDecimal donGia;

    // Getters và setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaDh() { return maDh; }
    public void setMaDh(String maDh) { this.maDh = maDh; }

    public String getMaSp() { return maSp; }
    public void setMaSp(String maSp) { this.maSp = maSp; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
}
