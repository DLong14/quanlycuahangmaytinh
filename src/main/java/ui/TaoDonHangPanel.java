package com.shopcomputer.ui;

import com.shopcomputer.dao.DonHangDAO;
import com.shopcomputer.dao.KhachHangDAO;
import com.shopcomputer.dao.NhanVienDAO;
import com.shopcomputer.dao.SanPhamDAO;
import com.shopcomputer.model.ChiTietDonHang;
import com.shopcomputer.model.DonHang;
import com.shopcomputer.model.KhachHang;
import com.shopcomputer.model.NhanVien;
import com.shopcomputer.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaoDonHangPanel extends JPanel {
    private JComboBox<KhachHang> cboKhachHang;
    private JComboBox<NhanVien> cboNhanVien;
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JButton btnThemSanPham;
    private JTable tableChiTiet;
    private DefaultTableModel modelChiTiet;
    private JLabel lblTongTien;
    private JButton btnLuuDonHang;

    private List<ChiTietDonHang> dsChiTiet = new ArrayList<>();

    public TaoDonHangPanel() {
        setLayout(new BorderLayout());

        JPanel pnlTop = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin đơn hàng"));

        cboKhachHang = new JComboBox<>();
        cboNhanVien = new JComboBox<>();
        cboSanPham = new JComboBox<>();
        txtSoLuong = new JTextField("1");

        pnlTop.add(new JLabel("Khách hàng:"));
        pnlTop.add(cboKhachHang);
        pnlTop.add(new JLabel("Nhân viên:"));
        pnlTop.add(cboNhanVien);
        pnlTop.add(new JLabel("Sản phẩm:"));
        JPanel pnlSanPham = new JPanel(new BorderLayout());
        pnlSanPham.add(cboSanPham, BorderLayout.CENTER);

        btnThemSanPham = new JButton("Thêm");
        pnlSanPham.add(btnThemSanPham, BorderLayout.EAST);

        pnlTop.add(pnlSanPham);

        add(pnlTop, BorderLayout.NORTH);

        modelChiTiet = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        tableChiTiet = new JTable(modelChiTiet);
        add(new JScrollPane(tableChiTiet), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        lblTongTien = new JLabel("Tổng tiền: 0");
        btnLuuDonHang = new JButton("Lưu đơn hàng");
        pnlBottom.add(lblTongTien, BorderLayout.WEST);
        pnlBottom.add(btnLuuDonHang, BorderLayout.EAST);

        add(pnlBottom, BorderLayout.SOUTH);

        loadData();

        btnThemSanPham.addActionListener(e -> themSanPham());
        btnLuuDonHang.addActionListener(e -> luuDonHang());
    }

    private void loadData() {
        try {
            KhachHangDAO khDao = new KhachHangDAO();
            List<KhachHang> listKh = khDao.findAll();
            cboKhachHang.removeAllItems();
            for (KhachHang kh : listKh) {
                cboKhachHang.addItem(kh);
            }

            NhanVienDAO nvDao = new NhanVienDAO();
            List<NhanVien> listNv = nvDao.findAll();
            cboNhanVien.removeAllItems();
            for (NhanVien nv : listNv) {
                cboNhanVien.addItem(nv);
            }

            SanPhamDAO spDao = new SanPhamDAO();
            List<SanPham> listSp = spDao.findAll();
            cboSanPham.removeAllItems();
            for (SanPham sp : listSp) {
                cboSanPham.addItem(sp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void themSanPham() {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        if (sp == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm.");
            return;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText());
            if (soLuong <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ.");
            return;
        }

        if (soLuong > sp.getSoLuongTon()) {
            JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho.");
            return;
        }

        // Kiểm tra đã thêm sản phẩm chưa, nếu rồi cộng dồn số lượng
        boolean found = false;
        for (ChiTietDonHang ct : dsChiTiet) {
            if (ct.getMaSp().equals(sp.getMaSp())) {
                int newQty = ct.getSoLuong() + soLuong;
                if (newQty > sp.getSoLuongTon()) {
                    JOptionPane.showMessageDialog(this, "Tổng số lượng vượt quá tồn kho.");
                    return;
                }
                ct.setSoLuong(newQty);
                found = true;
                break;
            }
        }
        if (!found) {
            ChiTietDonHang ct = new ChiTietDonHang();
            ct.setMaSp(sp.getMaSp());
            ct.setSoLuong(soLuong);
            ct.setDonGia(sp.getGia());
            dsChiTiet.add(ct);
        }

        refreshTable();
    }

    private void refreshTable() {
        modelChiTiet.setRowCount(0);
        BigDecimal tongTien = BigDecimal.ZERO;
        for (ChiTietDonHang ct : dsChiTiet) {
            BigDecimal thanhTien = ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong()));
            modelChiTiet.addRow(new Object[]{
                    ct.getMaSp(),
                    getTenSanPham(ct.getMaSp()),
                    ct.getSoLuong(),
                    ct.getDonGia(),
                    thanhTien
            });
            tongTien = tongTien.add(thanhTien);
        }
        lblTongTien.setText("Tổng tiền: " + tongTien);
    }

    private String getTenSanPham(String maSp) {
        try {
            SanPhamDAO spDao = new SanPhamDAO();
            SanPham sp = spDao.findByMaSp(maSp);
            if (sp != null) return sp.getTenSp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void luuDonHang() {
        if (cboKhachHang.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn khách hàng.");
            return;
        }
        if (cboNhanVien.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn nhân viên.");
            return;
        }
        if (dsChiTiet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa thêm sản phẩm.");
            return;
        }

        DonHang dh = new DonHang();
        dh.setMaDh("DH" + System.currentTimeMillis()); // mã đơn hàng ngẫu nhiên
        dh.setMaKh(((KhachHang) cboKhachHang.getSelectedItem()).getMaKh());
        dh.setMaNv(((NhanVien) cboNhanVien.getSelectedItem()).getMaNv());
        dh.setNgayTao(new Date());

        BigDecimal tongTien = BigDecimal.ZERO;
        for (ChiTietDonHang ct : dsChiTiet) {
            BigDecimal thanhTien = ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong()));
            tongTien = tongTien.add(thanhTien);
        }
        dh.setTongTien(tongTien);
        dh.setChiTietDonHangs(dsChiTiet);

        try {
            DonHangDAO dao = new DonHangDAO();
            dao.insertDonHang(dh);
            JOptionPane.showMessageDialog(this, "Lưu đơn hàng thành công.");
            dsChiTiet.clear();
            refreshTable();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi lưu đơn hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
