package com.shopcomputer.ui;

import com.shopcomputer.dao.KhachHangDAO;
import com.shopcomputer.dao.NhanVienDAO;
import com.shopcomputer.dao.SanPhamDAO;
import com.shopcomputer.model.ChiTietDonHang;
import com.shopcomputer.model.DonHang;
import com.shopcomputer.model.KhachHang;
import com.shopcomputer.model.NhanVien;
import com.shopcomputer.model.SanPham;
import com.shopcomputer.service.DonHangService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
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

    private DonHangService donHangService = new DonHangService();

    public TaoDonHangPanel() {
        setLayout(new BorderLayout());

        // Phần trên: chọn khách hàng, nhân viên, sản phẩm, số lượng, thêm sản phẩm
        JPanel pnlTop = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin đơn hàng"));

        cboKhachHang = new JComboBox<>();
        cboNhanVien = new JComboBox<>();
        cboSanPham = new JComboBox<>();
        txtSoLuong = new JTextField("1");
        btnThemSanPham = new JButton("Thêm sản phẩm");

        pnlTop.add(new JLabel("Khách hàng:"));
        pnlTop.add(cboKhachHang);
        pnlTop.add(new JLabel("Nhân viên:"));
        pnlTop.add(cboNhanVien);
        pnlTop.add(new JLabel("Sản phẩm:"));
        pnlTop.add(cboSanPham);
        pnlTop.add(new JLabel("Số lượng:"));
        pnlTop.add(txtSoLuong);

        add(pnlTop, BorderLayout.NORTH);

        // Nút thêm sản phẩm bên dưới
        JPanel pnlButtonThem = new JPanel();
        pnlButtonThem.add(btnThemSanPham);
        add(pnlButtonThem, BorderLayout.CENTER);

        // Bảng chi tiết đơn hàng
        modelChiTiet = new DefaultTableModel(new Object[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"}, 0);
        tableChiTiet = new JTable(modelChiTiet);
        add(new JScrollPane(tableChiTiet), BorderLayout.SOUTH);

        // Tổng tiền và nút lưu đơn hàng
        JPanel pnlBottom = new JPanel(new BorderLayout());
        lblTongTien = new JLabel("Tổng tiền: 0");
        btnLuuDonHang = new JButton("Lưu đơn hàng");
        pnlBottom.add(lblTongTien, BorderLayout.WEST);
        pnlBottom.add(btnLuuDonHang, BorderLayout.EAST);
        add(pnlBottom, BorderLayout.PAGE_END);

        loadData();

        btnThemSanPham.addActionListener(e -> themSanPham());
        btnLuuDonHang.addActionListener(e -> luuDonHang());
    }

    private void loadData() {
        try {
            // Load khách hàng
            KhachHangDAO khDao = new KhachHangDAO();
            List<KhachHang> listKh = khDao.findAll();
            cboKhachHang.removeAllItems();
            for (KhachHang kh : listKh) {
                cboKhachHang.addItem(kh);
            }

            // Load nhân viên
            NhanVienDAO nvDao = new NhanVienDAO();
            List<NhanVien> listNv = nvDao.findAll();
            cboNhanVien.removeAllItems();
            for (NhanVien nv : listNv) {
                cboNhanVien.addItem(nv);
            }

            // Load sản phẩm
            SanPhamDAO spDao = new SanPhamDAO();
            List<SanPham> listSp = spDao.findAll();
            cboSanPham.removeAllItems();
            for (SanPham sp : listSp) {
                cboSanPham.addItem(sp);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage());
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

        // Kiểm tra tồn kho
        if (soLuong > sp.getSoLuong()) {
            JOptionPane.showMessageDialog(this, "Số lượng vượt quá tồn kho.");
            return;
        }

        // Kiểm tra sản phẩm đã thêm chưa, nếu rồi cộng dồn số lượng
        boolean found = false;
        for (ChiTietDonHang ct : dsChiTiet) {
            if (ct.getMaSp().equals(sp.getMaSp())) {
                int newQty = ct.getSoLuong() + soLuong;
                if (newQty > sp.getSoLuong()) {
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
        dh.setMaDh("DH" + System.currentTimeMillis());
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
            donHangService.addDonHang(dh);
            JOptionPane.showMessageDialog(this, "Lưu đơn hàng thành công.");
            dsChiTiet.clear();
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi lưu đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
