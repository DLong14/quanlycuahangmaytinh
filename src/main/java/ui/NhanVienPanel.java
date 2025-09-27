package com.shopcomputer.ui;

import com.shopcomputer.controller.NhanVienController;
import com.shopcomputer.model.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JButton btnTaiDuLieu, btnThem, btnSua, btnXoa;

    private JTextField txtMaNV, txtHoTen, txtChucVu, txtSoDT, txtEmail;

    private NhanVienController controller = new NhanVienController();

    public NhanVienPanel() {
        setLayout(new BorderLayout());

        // Bảng hiển thị
        model = new DefaultTableModel(new Object[]{"ID", "Mã NV", "Họ tên", "Chức vụ", "SĐT", "Email"}, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);

        // Panel form nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.add(new JLabel("Mã NV:"));
        txtMaNV = new JTextField();
        inputPanel.add(txtMaNV);

        inputPanel.add(new JLabel("Họ tên:"));
        txtHoTen = new JTextField();
        inputPanel.add(txtHoTen);

        inputPanel.add(new JLabel("Chức vụ:"));
        txtChucVu = new JTextField();
        inputPanel.add(txtChucVu);

        inputPanel.add(new JLabel("Số điện thoại:"));
        txtSoDT = new JTextField();
        inputPanel.add(txtSoDT);

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
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());

        table.getSelectionModel().addListSelectionListener(e -> hienThiThongTin());

        // Tải dữ liệu ban đầu
        loadData();
    }

    private void loadData() {
        List<NhanVien> list = controller.getAllNhanVien();
        model.setRowCount(0);
        if (list != null) {
            for (NhanVien nv : list) {
                model.addRow(new Object[]{
                        nv.getId(),
                        nv.getMaNv(),
                        nv.getHoTen(),
                        nv.getChucVu(),
                        nv.getSoDienThoai(),
                        nv.getEmail()
                });
            }
        }
    }

    private void hienThiThongTin() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaNV.setText(model.getValueAt(row, 1).toString());
            txtHoTen.setText(model.getValueAt(row, 2).toString());
            txtChucVu.setText(model.getValueAt(row, 3).toString());
            txtSoDT.setText(model.getValueAt(row, 4).toString());
            txtEmail.setText(model.getValueAt(row, 5).toString());
        }
    }

    private void themNhanVien() {
        try {
            NhanVien nv = new NhanVien();
            nv.setMaNv(txtMaNV.getText().trim());
            nv.setHoTen(txtHoTen.getText().trim());
            nv.setChucVu(txtChucVu.getText().trim());
            nv.setSoDienThoai(txtSoDT.getText().trim());
            nv.setEmail(txtEmail.getText().trim());

            if (controller.addNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                loadData();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void suaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa!");
            return;
        }
        try {
            int id = (int) model.getValueAt(row, 0);
            NhanVien nv = new NhanVien();
            nv.setId(id);
            nv.setMaNv(txtMaNV.getText().trim());
            nv.setHoTen(txtHoTen.getText().trim());
            nv.setChucVu(txtChucVu.getText().trim());
            nv.setSoDienThoai(txtSoDT.getText().trim());
            nv.setEmail(txtEmail.getText().trim());

            if (controller.updateNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
                loadData();
                clearInput();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void xoaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) model.getValueAt(row, 0);
                if (controller.deleteNhanVien(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                    loadData();
                    clearInput();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        }
    }

    private void clearInput() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtChucVu.setText("");
        txtSoDT.setText("");
        txtEmail.setText("");
        table.clearSelection();
    }
}
