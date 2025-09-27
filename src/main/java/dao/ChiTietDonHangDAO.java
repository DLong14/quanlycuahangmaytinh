package com.shopcomputer.dao;

import com.shopcomputer.model.ChiTietDonHang;
import com.shopcomputer.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

    public class ChiTietDonHangDAO {
    public boolean insertBatch(String maDh, List<ChiTietDonHang> chiTietList) throws Exception {
        String sql = "INSERT INTO chi_tiet_don_hang (ma_dh, ma_sp, so_luong, don_gia) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ChiTietDonHang ct : chiTietList) {
                ps.setString(1, maDh);
                ps.setString(2, ct.getMaSp());
                ps.setInt(3, ct.getSoLuong());
                ps.setBigDecimal(4, ct.getDonGia());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        }
    }
}
