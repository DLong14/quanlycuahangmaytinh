package com.shopcomputer.ui;

import com.shopcomputer.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SanPhamPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMaSp, txtTenSp, txtLoaiSp, txtGia, txtSoLuongTon, txtMoTa;
    private JButton btnThem, btnSua, btnXoa, btnTaiDuLieu;

    public SanPhamPanel() {
        // Khởi tạo UI
        setLayout(new BorderLayout());

        // Bảng dữ liệu
        model = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Loại SP", "Giá", "Số lượng tồn", "Mô tả"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Mã SP"));
        txtMaSp = new JTextField();
        formPanel.add(txtMaSp);

        formPanel.add(new JLabel("Tên SP"));
        txtTenSp = new JTextField();
        formPanel.add(txtTenSp);

        formPanel.add(new JLabel("Loại SP"));
        txtLoaiSp = new JTextField();
        formPanel.add(txtLoaiSp);

        formPanel.add(new JLabel("Giá"));
        txtGia = new JTextField();
        formPanel.add(txtGia);

        formPanel.add(new JLabel("Số lượng tồn"));
        txtSoLuongTon = new JTextField();
        formPanel.add(txtSoLuongTon);

        formPanel.add(new JLabel("Mô tả"));
        txtMoTa = new JTextField();
        formPanel.add(txtMoTa);

        add(formPanel, BorderLayout.NORTH);

        // Nút chức năng
        JPanel btnPanel = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTaiDuLieu = new JButton("Tải dữ liệu");

        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnTaiDuLieu);

        add(btnPanel, BorderLayout.SOUTH);
    }

    // Cho phép Controller đăng ký sự kiện
    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnTaiDuLieu() { return btnTaiDuLieu; }

    // Cho Controller lấy dữ liệu từ form
    public SanPham getSanPhamFromForm() {
        SanPham sp = new SanPham();
        sp.setMaSp(txtMaSp.getText());
        sp.setTenSp(txtTenSp.getText());
        sp.setLoaiSp(txtLoaiSp.getText());
        sp.setGia(new BigDecimal(txtGia.getText()));
        sp.setSoLuongTon(Integer.parseInt(txtSoLuongTon.getText()));
        sp.setMoTa(txtMoTa.getText());
        return sp;
    }

    // Cập nhật dữ liệu bảng
    public void updateTable(List<SanPham> list) {
        model.setRowCount(0);
        for (SanPham sp : list) {
            model.addRow(new Object[]{
                    sp.getMaSp(),
                    sp.getTenSp(),
                    sp.getLoaiSp(),
                    sp.getGia(),
                    sp.getSoLuongTon(),
                    sp.getMoTa()
            });
        }
    }

    // Xóa trắng form
    public void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtLoaiSp.setText("");
        txtGia.setText("");
        txtSoLuongTon.setText("");
        txtMoTa.setText("");
        txtMaSp.setEnabled(true);
    }

    // Lấy mã sản phẩm của dòng đang chọn (để xóa hoặc sửa)
    public String getSelectedMaSp() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            return model.getValueAt(row, 0).toString();
        }
        return null;
    }

    // Điền dữ liệu vào form từ 1 sản phẩm (khi chọn bảng)
    public void setSanPhamToForm(SanPham sp) {
        if (sp == null) return;
        txtMaSp.setText(sp.getMaSp());
        txtTenSp.setText(sp.getTenSp());
        txtLoaiSp.setText(sp.getLoaiSp());
        txtGia.setText(sp.getGia().toString());
        txtSoLuongTon.setText(String.valueOf(sp.getSoLuongTon()));
        txtMoTa.setText(sp.getMoTa());
        txtMaSp.setEnabled(false);  // Khóa mã SP khi sửa
    }

    // Cho Controller truy cập bảng để đăng ký sự kiện chọn dòng
    public JTable getTable() {
        return table;
    }
}
