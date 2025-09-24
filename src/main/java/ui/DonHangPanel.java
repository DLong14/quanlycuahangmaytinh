package com.shopcomputer.ui;

import com.shopcomputer.dao.DonHangDAO;
import com.shopcomputer.model.ChiTietDonHang;
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

    public DonHangPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Mã ĐH", "Mã KH", "Mã NV", "Ngày tạo", "Tổng tiền"}, 0);
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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            model.setRowCount(0);

            for (DonHang dh : list) {
                model.addRow(new Object[]{
                        dh.getMaDh(),
                        dh.getMaKh(),
                        dh.getMaNv(),
                        sdf.format(dh.getNgayTao()),
                        dh.getTongTien()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
