package com.shopcomputer.ui;

import com.shopcomputer.dao.KhachHangDAO;
import com.shopcomputer.model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnTaiDuLieu;

    public KhachHangPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Mã KH", "Họ tên", "Số điện thoại", "Địa chỉ", "Email"}, 0);
        table = new JTable(model);

        btnTaiDuLieu = new JButton("Tải dữ liệu");

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnTaiDuLieu, BorderLayout.SOUTH);

        btnTaiDuLieu.addActionListener(e -> loadData());
    }

    private void loadData() {
        try {
            KhachHangDAO dao = new KhachHangDAO();
            List<KhachHang> list = dao.findAll();

            model.setRowCount(0);
            for (KhachHang kh : list) {
                model.addRow(new Object[]{
                        kh.getMaKh(),
                        kh.getHoTen(),
                        kh.getSoDienThoai(),
                        kh.getDiaChi(),
                        kh.getEmail()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
