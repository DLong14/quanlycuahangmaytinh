package com.shopcomputer.ui;

import com.shopcomputer.controller.SanPhamController;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Quản lý cửa hàng máy tính");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuQuanLy = new JMenu("Quản lý");
        JMenuItem menuSanPham = new JMenuItem("Sản phẩm");
        JMenuItem menuKhachHang = new JMenuItem("Khách hàng");
        JMenuItem menuNhanVien = new JMenuItem("Nhân viên");
        JMenuItem menuDonHang = new JMenuItem("Đơn hàng");
        JMenuItem menuTaoDonHang = new JMenuItem("Tạo đơn hàng mới");
        // Bạn có thể thêm các menu khách hàng, nhân viên, đơn hàng...

        menuQuanLy.add(menuSanPham);
        menuBar.add(menuQuanLy);
        menuQuanLy.add(menuKhachHang);
        menuQuanLy.add(menuNhanVien);
        menuQuanLy.add(menuDonHang);
        menuQuanLy.add(menuTaoDonHang);
        setJMenuBar(menuBar);

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Khi chọn menu "Sản phẩm", hiển thị panel quản lý sản phẩm
        menuSanPham.addActionListener(e -> {
            mainPanel.removeAll();
            SanPhamPanel spPanel = new SanPhamPanel();
            new SanPhamController(spPanel); // Tạo controller gắn với panel
            mainPanel.add(spPanel, BorderLayout.CENTER);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        
menuKhachHang.addActionListener(e -> {
    mainPanel.removeAll();
    mainPanel.add(new KhachHangPanel(), BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
});

menuNhanVien.addActionListener(e -> {
    mainPanel.removeAll();
    mainPanel.add(new NhanVienPanel(), BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
});

menuDonHang.addActionListener(e -> {
    mainPanel.removeAll();
    mainPanel.add(new DonHangPanel(), BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
});
menuTaoDonHang.addActionListener(e -> {
    mainPanel.removeAll();
    mainPanel.add(new TaoDonHangPanel(), BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
});
    }
    public static void main(String[] args) {
        // Khởi chạy giao diện
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
