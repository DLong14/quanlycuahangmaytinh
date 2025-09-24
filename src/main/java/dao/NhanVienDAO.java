package com.shopcomputer.dao;

import com.shopcomputer.model.NhanVien;
import com.shopcomputer.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    public List<NhanVien> findAll() throws Exception {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("id"));
                nv.setMaNv(rs.getString("ma_nv"));
                nv.setHoTen(rs.getString("ho_ten"));
                nv.setChucVu(rs.getString("chuc_vu"));
                nv.setSoDienThoai(rs.getString("so_dien_thoai"));
                nv.setEmail(rs.getString("email"));
                list.add(nv);
            }
        }
        return list;
    }
}
