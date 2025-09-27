package com.shopcomputer.ui;

import com.shopcomputer.controller.KhachHangController;
import com.shopcomputer.model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JButton btnTaiDuLieu, btnThem, btnSua, btnXoa;

    private JTextField txtMaKH, txtHoTen, txtSoDT, txtDiaChi, txtEmail;

    private KhachHangController controller = new KhachHangController();

    public KhachHangPanel() {
        setLayout(new BorderLayout());

        // Bảng hiển thị
        model = new DefaultTableModel(new Object[]{"ID", "Mã KH", "Họ tên", "SĐT", "Địa chỉ", "Email"}, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);

        // Panel form nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField();
        inputPanel.add(txtMaKH);

        inputPanel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        inputPanel.add(txtHoTen);

        inputPanel.add(new JLabel("Số điện thoại:"));
        txtSoDT = new JTextField();
        inputPanel.add(txtSoDT);

        inputPanel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField();
        inputPanel.add(txtDiaChi);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        // Panel nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnTaiDuLieu = new JButton("Tải dữ liệu");
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        buttonPanel.add(btnTaiDuLieu);
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        // Kết hợp panel form + nút
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(inputPanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(scroll, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Sự kiện
        btnTaiDuLieu.addActionListener(e -> loadData());
        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());

        table.getSelectionModel().addListSelectionListener(e -> hienThiThongTin());

        // Tải dữ liệu ban đầu
        loadData();
    }

    private void loadData() {
        List<KhachHang> list = controller.getAllKhachHang();
        model.setRowCount(0);
        if (list != null) {
            for (KhachHang kh : list) {
                model.addRow(new Object[]{
                        kh.getId(),
                        kh.getMaKh(),
                        kh.getHoTen(),
                        kh.getSoDienThoai(),
                        kh.getDiaChi(),
                        kh.getEmail()
                });
            }
        }
    }

    private void hienThiThongTin() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaKH.setText(model.getValueAt(row, 1).toString());
            txtHoTen.setText(model.getValueAt(row, 2).toString());
            txtSoDT.setText(model.getValueAt(row, 3).toString());
            txtDiaChi.setText(model.getValueAt(row, 4).toString());
            txtEmail.setText(model.getValueAt(row, 5).toString());
        }
    }

    private void themKhachHang() {
        try {
            KhachHang kh = new KhachHang();
            kh.setMaKh(txtMaKH.getText().trim());
            kh.setHoTen(txtHoTen.getText().trim());
            kh.setSoDienThoai(txtSoDT.getText().trim());
            kh.setDiaChi(txtDiaChi.getText().trim());
            kh.setEmail(txtEmail.getText().trim());

            if (controller.addKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                loadData();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void suaKhachHang() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!");
            return;
        }
        try {
            int id = (int) model.getValueAt(row, 0);
            KhachHang kh = new KhachHang();
            kh.setId(id);
            kh.setMaKh(txtMaKH.getText().trim());
            kh.setHoTen(txtHoTen.getText().trim());
            kh.setSoDienThoai(txtSoDT.getText().trim());
            kh.setDiaChi(txtDiaChi.getText().trim());
            kh.setEmail(txtEmail.getText().trim());

            if (controller.updateKhachHang(kh)) {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
                loadData();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void xoaKhachHang() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) model.getValueAt(row, 0);
                if (controller.deleteKhachHang(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void clearInput() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSoDT.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        table.clearSelection();
    }
}
