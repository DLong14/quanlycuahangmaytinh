package com.shopcomputer.service;

import com.shopcomputer.dao.NhanVienDAO;
import com.shopcomputer.model.NhanVien;

import java.util.List;

public class NhanVienService {
    private NhanVienDAO dao = new NhanVienDAO();

    public List<NhanVien> getAllNhanVien() throws Exception {
        return dao.findAll();
    }

    public boolean addNhanVien(NhanVien nv) throws Exception {
        return dao.insert(nv);
    }

    public boolean updateNhanVien(NhanVien nv) throws Exception {
        return dao.update(nv);
    }

    public boolean deleteNhanVien(int id) throws Exception {
        return dao.delete(id);
    }

    public NhanVien getNhanVienById(int id) throws Exception {
        return dao.findById(id);
    }
}
