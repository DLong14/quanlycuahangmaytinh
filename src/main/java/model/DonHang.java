package com.shopcomputer.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DonHang {
    private int id;
    private String maDh;
    private String maKh;
    private String maNv;
    private Date ngayTao;
    private BigDecimal tongTien;

    private List<ChiTietDonHang> chiTietDonHangs;

    // Getters và setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaDh() { return maDh; }
    public void setMaDh(String maDh) { this.maDh = maDh; }

    public String getMaKh() { return maKh; }
    public void setMaKh(String maKh) { this.maKh = maKh; }

    public String getMaNv() { return maNv; }
    public void setMaNv(String maNv) { this.maNv = maNv; }

    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }

    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public List<ChiTietDonHang> getChiTietDonHangs() { return chiTietDonHangs; }
    public void setChiTietDonHangs(List<ChiTietDonHang> chiTietDonHangs) { this.chiTietDonHangs = chiTietDonHangs; }
}
