package com.shopcomputer.controller;

import com.shopcomputer.model.SanPham;
import com.shopcomputer.service.SanPhamService;
import com.shopcomputer.ui.SanPhamPanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

public class SanPhamController {
    private SanPhamPanel view;
    private SanPhamService service;

    public SanPhamController(SanPhamPanel view) {
        this.view = view;
        this.service = new SanPhamService();

        // Đăng ký sự kiện
        view.getBtnTaiDuLieu().addActionListener(e -> loadData());
        view.getBtnThem().addActionListener(e -> themSanPham());
        view.getBtnSua().addActionListener(e -> suaSanPham());
        view.getBtnXoa().addActionListener(e -> xoaSanPham());
  
        // Load dữ liệu lần đầu
        loadData();
    }

    private void loadData() {
        try {
            List<SanPham> list = service.getAllSanPham();
            view.updateTable(list);
            view.clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private void themSanPham() {
        try {
            SanPham sp = view.getSanPhamFromForm();
            service.addSanPham(sp);
            JOptionPane.showMessageDialog(view, "Thêm sản phẩm thành công!");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi thêm sản phẩm: " + e.getMessage());
        }
    }

    private void suaSanPham() {
        try {
            SanPham sp = view.getSanPhamFromForm();
            service.updateSanPham(sp);
            JOptionPane.showMessageDialog(view, "Cập nhật sản phẩm thành công!");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    private void xoaSanPham() {
        try {
            String maSp = view.getSelectedMaSp();
            if (maSp == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn sản phẩm cần xóa!");
                return;
            }
            service.deleteSanPham(maSp);
            JOptionPane.showMessageDialog(view, "Xóa sản phẩm thành công!");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi xóa sản phẩm: " + e.getMessage());
        }
    }
}
