package com.shopcomputer.service;

import com.shopcomputer.dao.DonHangDAO;
import com.shopcomputer.model.DonHang;

import java.util.List;

public class DonHangService {
    private DonHangDAO dao = new DonHangDAO();

    public List<DonHang> getAllDonHang() throws Exception {
        return dao.findAll();
    }

    public void addDonHang(DonHang dh) throws Exception {
        dao.insertDonHang(dh);
    }

    // Bạn có thể thêm update, delete nếu cần
}
