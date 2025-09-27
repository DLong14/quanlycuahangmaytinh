package com.shopcomputer.service;

import com.shopcomputer.dao.KhachHangDAO;
import com.shopcomputer.model.KhachHang;

import java.util.List;

public class KhachHangService {
    private KhachHangDAO dao = new KhachHangDAO();

    public List<KhachHang> getAllKhachHang() throws Exception {
        return dao.findAll();
    }

    public boolean addKhachHang(KhachHang kh) throws Exception {
        // Viết thêm hàm insert trong DAO nếu chưa có
        return dao.insert(kh);
    }

    public boolean updateKhachHang(KhachHang kh) throws Exception {
        // Viết thêm hàm update trong DAO nếu chưa có
        return dao.update(kh);
    }

    public boolean deleteKhachHang(int id) throws Exception {
        // Viết thêm hàm delete trong DAO nếu chưa có
        return dao.delete(id);
    }

    public KhachHang getKhachHangById(int id) throws Exception {
        // Viết thêm hàm findById trong DAO nếu chưa có
        return dao.findById(id);
    }
}
