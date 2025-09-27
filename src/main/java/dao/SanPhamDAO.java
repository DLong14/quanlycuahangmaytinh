package com.shopcomputer.dao;

import com.shopcomputer.model.SanPham;
import com.shopcomputer.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SanPhamDAO {

    // Lấy danh sách tất cả sản phẩm
    public List<SanPham> findAll() throws Exception {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM san_pham";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPham sp = mapResultSetToSanPham(rs);
                list.add(sp);
            }
        }
        return list;
    }

    // Tìm sản phẩm theo mã sản phẩm
    public SanPham findByMaSp(String maSp) throws Exception {
        String sql = "SELECT * FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSanPham(rs);
                }
            }
        }
        return null;
    }

    // Thêm sản phẩm mới
    public void insert(SanPham sp) throws Exception {
        String sql = "INSERT INTO san_pham(ma_sp, ten_sp, loai_sp, gia, so_luong, mo_ta) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sp.getMaSp());
            ps.setString(2, sp.getTenSp());
            ps.setString(3, sp.getLoaiSp());
            ps.setBigDecimal(4, sp.getGia());
            ps.setInt(5, sp.getSoLuong());
            ps.setString(6, sp.getMoTa());

            ps.executeUpdate();
        }
    }

    // Cập nhật sản phẩm
    public void update(SanPham sp) throws Exception {
        String sql = "UPDATE san_pham SET ten_sp = ?, loai_sp = ?, gia = ?, so_luong = ?, mo_ta = ? WHERE ma_sp = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sp.getTenSp());
            ps.setString(2, sp.getLoaiSp());
            ps.setBigDecimal(3, sp.getGia());
            ps.setInt(4, sp.getSoLuong());
            ps.setString(5, sp.getMoTa());
            ps.setString(6, sp.getMaSp());

            ps.executeUpdate();
        }
    }

    // Xóa sản phẩm theo mã sản phẩm
    public void delete(String maSp) throws Exception {
        String sql = "DELETE FROM san_pham WHERE ma_sp = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maSp);
            ps.executeUpdate();
        }
    }

    // Hàm tiện ích map ResultSet thành đối tượng SanPham
    private SanPham mapResultSetToSanPham(ResultSet rs) throws SQLException {
        SanPham sp = new SanPham();
        sp.setId(rs.getInt("id"));
        sp.setMaSp(rs.getString("ma_sp"));
        sp.setTenSp(rs.getString("ten_sp"));
        sp.setLoaiSp(rs.getString("loai_sp"));
        sp.setGia(rs.getBigDecimal("gia"));
        sp.setSoLuong(rs.getInt("so_luong"));
        sp.setMoTa(rs.getString("mo_ta"));
        return sp;
    }
}
