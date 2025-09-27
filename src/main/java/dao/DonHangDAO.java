package com.shopcomputer.dao;

import com.shopcomputer.model.ChiTietDonHang;
import com.shopcomputer.model.DonHang;
import com.shopcomputer.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonHangDAO {

   public List<DonHang> findAll() throws Exception {
    List<DonHang> list = new ArrayList<>();
    String sql = "SELECT dh.*, kh.ho_ten as ten_kh, nv.ho_ten as ten_nv " +
                 "FROM don_hang dh " +
                 "LEFT JOIN khach_hang kh ON dh.ma_kh = kh.ma_kh " +
                 "LEFT JOIN nhan_vien nv ON dh.ma_nv = nv.ma_nv";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            DonHang dh = new DonHang();
            dh.setId(rs.getInt("id"));
            dh.setMaDh(rs.getString("ma_dh"));
            dh.setMaKh(rs.getString("ma_kh"));
            dh.setTenKh(rs.getString("ten_kh"));   // Tên khách hàng lấy từ join
            dh.setMaNv(rs.getString("ma_nv"));
            dh.setTenNv(rs.getString("ten_nv"));   // Tên nhân viên lấy từ join
            dh.setNgayTao(rs.getDate("ngay_tao"));
            dh.setTongTien(rs.getBigDecimal("tong_tien"));

            // Lấy chi tiết đơn hàng
            dh.setChiTietDonHangs(findChiTietByMaDh(dh.getMaDh()));

            list.add(dh);
        }
    }
    return list;
}


    public List<ChiTietDonHang> findChiTietByMaDh(String maDh) throws Exception {
        List<ChiTietDonHang> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_don_hang WHERE ma_dh = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDh);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietDonHang ct = new ChiTietDonHang();
                    ct.setId(rs.getInt("id"));
                    ct.setMaDh(rs.getString("ma_dh"));
                    ct.setMaSp(rs.getString("ma_sp"));
                    ct.setSoLuong(rs.getInt("so_luong"));
                    ct.setDonGia(rs.getBigDecimal("don_gia"));
                    list.add(ct);
                }
            }
        }
        return list;
    }

    public void insertDonHang(DonHang dh) throws Exception {
        String sqlDonHang = "INSERT INTO don_hang (ma_dh, ma_kh, ma_nv, ngay_tao, tong_tien) VALUES (?, ?, ?, ?, ?)";
        String sqlChiTiet = "INSERT INTO chi_tiet_don_hang (ma_dh, ma_sp, so_luong, don_gia) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement psDonHang = null;
        PreparedStatement psChiTiet = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            psDonHang = conn.prepareStatement(sqlDonHang);
            psDonHang.setString(1, dh.getMaDh());
            psDonHang.setString(2, dh.getMaKh());
            psDonHang.setString(3, dh.getMaNv());
            psDonHang.setDate(4, new java.sql.Date(dh.getNgayTao().getTime()));
            psDonHang.setBigDecimal(5, dh.getTongTien());
            psDonHang.executeUpdate();

            psChiTiet = conn.prepareStatement(sqlChiTiet);
            for (ChiTietDonHang ct : dh.getChiTietDonHangs()) {
                psChiTiet.setString(1, dh.getMaDh());
                psChiTiet.setString(2, ct.getMaSp());
                psChiTiet.setInt(3, ct.getSoLuong());
                psChiTiet.setBigDecimal(4, ct.getDonGia());
                psChiTiet.addBatch();
            }
            psChiTiet.executeBatch();

String sqlUpdateTonKho = "UPDATE san_pham SET so_luong_ton = so_luong_ton - ? WHERE ma_sp = ?";
PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateTonKho);
for (ChiTietDonHang ct : dh.getChiTietDonHangs()) {
    psUpdate.setInt(1, ct.getSoLuong());
    psUpdate.setString(2, ct.getMaSp());
    psUpdate.addBatch();
}
psUpdate.executeBatch();
psUpdate.close();

            conn.commit();
        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (psChiTiet != null) psChiTiet.close();
            if (psDonHang != null) psDonHang.close();
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        }
    }
    
}
