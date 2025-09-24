package com.shopcomputer.model;

public class NhanVien {
    private int id;
    private String maNv;
    private String hoTen;
    private String chucVu;
    private String soDienThoai;
    private String email;

    // Getters và setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getMaNv() {
        return maNv;
    }
    public void setMaNv(String maNv) {
        this.maNv = maNv;
    }
    public String getHoTen() {
        return hoTen;
    }
    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getChucVu() {
        return chucVu;
    }
    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
