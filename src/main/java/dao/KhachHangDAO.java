package com.shopcomputer.dao;

import com.shopcomputer.model.KhachHang;
import com.shopcomputer.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public List<KhachHang> findAll() throws Exception {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setId(rs.getInt("id"));
                kh.setMaKh(rs.getString("ma_kh"));
                kh.setHoTen(rs.getString("ho_ten"));
                kh.setSoDienThoai(rs.getString("so_dien_thoai"));
                kh.setDiaChi(rs.getString("dia_chi"));
                kh.setEmail(rs.getString("email"));
                list.add(kh);
            }
        }
        return list;
    }

    public KhachHang findById(int id) throws Exception {
        String sql = "SELECT * FROM khach_hang WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    KhachHang kh = new KhachHang();
                    kh.setId(rs.getInt("id"));
                    kh.setMaKh(rs.getString("ma_kh"));
                    kh.setHoTen(rs.getString("ho_ten"));
                    kh.setSoDienThoai(rs.getString("so_dien_thoai"));
                    kh.setDiaChi(rs.getString("dia_chi"));
                    kh.setEmail(rs.getString("email"));
                    return kh;
                }
            }
        }
        return null;
    }

    public boolean insert(KhachHang kh) throws Exception {
        String sql = "INSERT INTO khach_hang (ma_kh, ho_ten, so_dien_thoai, dia_chi, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getMaKh());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getEmail());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(KhachHang kh) throws Exception {
        String sql = "UPDATE khach_hang SET ma_kh = ?, ho_ten = ?, so_dien_thoai = ?, dia_chi = ?, email = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getMaKh());
            ps.setString(2, kh.getHoTen());
            ps.setString(3, kh.getSoDienThoai());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getEmail());
            ps.setInt(6, kh.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE FROM khach_hang WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}
