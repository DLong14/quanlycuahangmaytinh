package com.shopcomputer.controller;

import com.shopcomputer.model.NhanVien;
import com.shopcomputer.service.NhanVienService;

import java.util.List;

public class NhanVienController {
    private NhanVienService service = new NhanVienService();

    public List<NhanVien> getAllNhanVien() {
        try {
            return service.getAllNhanVien();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addNhanVien(NhanVien nv) {
        try {
            return service.addNhanVien(nv);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNhanVien(NhanVien nv) {
        try {
            return service.updateNhanVien(nv);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNhanVien(int id) {
        try {
            return service.deleteNhanVien(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public NhanVien getNhanVienById(int id) {
        try {
            return service.getNhanVienById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
