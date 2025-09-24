package com.shopcomputer.service;

import com.shopcomputer.dao.SanPhamDAO;
import com.shopcomputer.model.SanPham;
import com.shopcomputer.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.List;


public class SanPhamService {
    private SanPhamDAO sanPhamDAO;

    public SanPhamService() {
        this.sanPhamDAO = new SanPhamDAO();
    }

    // Lấy danh sách sản phẩm
    public List<SanPham> getAllSanPham() throws Exception {
        return sanPhamDAO.findAll();
    }

    // Tìm sản phẩm theo mã
    public SanPham getSanPhamByMa(String maSp) throws Exception {
        return sanPhamDAO.findByMaSp(maSp);
    }

    // Thêm sản phẩm mới
    public void addSanPham(SanPham sp) throws Exception {
        if (sp == null || sp.getMaSp() == null || sp.getMaSp().trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống");
        }
        if (sanPhamDAO.findByMaSp(sp.getMaSp()) != null) {
            throw new Exception("Mã sản phẩm đã tồn tại");
        }
        sanPhamDAO.insert(sp);
    }

    // Cập nhật sản phẩm
    public void updateSanPham(SanPham sp) throws Exception {
        if (sp == null || sp.getMaSp() == null || sp.getMaSp().trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống");
        }
        if (sanPhamDAO.findByMaSp(sp.getMaSp()) == null) {
            throw new Exception("Sản phẩm không tồn tại");
        }
        sanPhamDAO.update(sp);
    }

    // Xóa sản phẩm theo mã
    public void deleteSanPham(String maSp) throws Exception {
        if (maSp == null || maSp.trim().isEmpty()) {
            throw new Exception("Mã sản phẩm không được để trống");
        }
        if (sanPhamDAO.findByMaSp(maSp) == null) {
            throw new Exception("Sản phẩm không tồn tại");
        }
        sanPhamDAO.delete(maSp);
    }
}