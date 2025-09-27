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

    public NhanVien findById(int id) throws Exception {
        String sql = "SELECT * FROM nhan_vien WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setId(rs.getInt("id"));
                    nv.setMaNv(rs.getString("ma_nv"));
                    nv.setHoTen(rs.getString("ho_ten"));
                    nv.setChucVu(rs.getString("chuc_vu"));
                    nv.setSoDienThoai(rs.getString("so_dien_thoai"));
                    nv.setEmail(rs.getString("email"));
                    return nv;
                }
            }
        }
        return null;
    }

    public boolean insert(NhanVien nv) throws Exception {
        String sql = "INSERT INTO nhan_vien (ma_nv, ho_ten, chuc_vu, so_dien_thoai, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNv());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getChucVu());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(NhanVien nv) throws Exception {
        String sql = "UPDATE nhan_vien SET ma_nv = ?, ho_ten = ?, chuc_vu = ?, so_dien_thoai = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getMaNv());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getChucVu());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getEmail());
            ps.setInt(6, nv.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM nhan_vien WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
