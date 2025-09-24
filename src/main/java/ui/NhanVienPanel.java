package com.shopcomputer.ui;

import com.shopcomputer.dao.NhanVienDAO;
import com.shopcomputer.model.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnTaiDuLieu;

    public NhanVienPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Mã NV", "Họ tên", "Chức vụ", "Số điện thoại", "Email"}, 0);
        table = new JTable(model);

        btnTaiDuLieu = new JButton("Tải dữ liệu");

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnTaiDuLieu, BorderLayout.SOUTH);

        btnTaiDuLieu.addActionListener(e -> loadData());
    }

    private void loadData() {
        try {
            NhanVienDAO dao = new NhanVienDAO();
            List<NhanVien> list = dao.findAll();

            model.setRowCount(0);
            for (NhanVien nv : list) {
                model.addRow(new Object[]{
                        nv.getMaNv(),
                        nv.getHoTen(),
                        nv.getChucVu(),
                        nv.getSoDienThoai(),
                        nv.getEmail()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
