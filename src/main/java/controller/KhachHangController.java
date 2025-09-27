package com.shopcomputer.controller;

import com.shopcomputer.model.KhachHang;
import com.shopcomputer.service.KhachHangService;

import java.util.List;

public class KhachHangController {
    private KhachHangService service = new KhachHangService();

    public List<KhachHang> getAllKhachHang() {
        try {
            return service.getAllKhachHang();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addKhachHang(KhachHang kh) {
        try {
            return service.addKhachHang(kh);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhachHang(KhachHang kh) {
        try {
            return service.updateKhachHang(kh);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhachHang(int id) {
        try {
            return service.deleteKhachHang(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public KhachHang getKhachHangById(int id) {
        try {
            return service.getKhachHangById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
