package com.shopcomputer.model;

import java.math.BigDecimal;

public class SanPham {
    private int id;
    private String maSp;
    private String tenSp;
    private String loaiSp;
    private int soLuongTon; 
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
    
    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }
    
    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }
    
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
}
