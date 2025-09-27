package com.shopcomputer.ui;

import com.shopcomputer.dao.DonHangDAO;
import com.shopcomputer.model.DonHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class DonHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnTaiDuLieu;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DonHangPanel() {
        setLayout(new BorderLayout());

        // Tạo model với các cột: Mã đơn, Mã khách, Mã NV, Ngày tạo, Tổng tiền
        model = new DefaultTableModel(new Object[]{"Mã đơn", "Mã khách", "Mã nhân viên", "Ngày tạo", "Tổng tiền"}, 0);
        table = new JTable(model);

        btnTaiDuLieu = new JButton("Tải dữ liệu");

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnTaiDuLieu, BorderLayout.SOUTH);

        btnTaiDuLieu.addActionListener(e -> loadData());
    }

    private void loadData() {
        try {
            DonHangDAO dao = new DonHangDAO();
            List<DonHang> list = dao.findAll();

            model.setRowCount(0); // Xóa dữ liệu cũ
            for (DonHang dh : list) {
                model.addRow(new Object[]{
                        dh.getMaDh(),
                        dh.getMaKh(),
                        dh.getMaNv(),
                        dh.getNgayTao() != null ? sdf.format(dh.getNgayTao()) : "",
                        dh.getTongTien()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
