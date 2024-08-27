package jrt.vku.spring.CRUD_JPQL_DEMO.dao;

import jrt.vku.spring.CRUD_JPQL_DEMO.entity.SinhVien;

import java.util.List;

public interface SinhVienDAO {
	public void save(SinhVien sinhVien);
	public SinhVien findById(int id);
	public List<SinhVien> findAll();
	public List<SinhVien> getByName(String name);
	public void updateInfo(SinhVien sinhVien);
	public void setAllToOneName(String name);
	public void deleteById(int id);
	public void deleteByName(String name);
}
