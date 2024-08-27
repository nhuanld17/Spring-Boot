package jrt.vku.spring.Create_Hibernate_JPA_Project.dao;

import jrt.vku.spring.Create_Hibernate_JPA_Project.entity.SinhVien;

import java.util.List;

public interface SinhVienDAO {
	 public void save(SinhVien sinhVien);
	 public SinhVien getById(int id);
	 public List<SinhVien> getAll();
	 public List<SinhVien> getByName(String name);
	 public SinhVien update(SinhVien sinhVien);
	 public int updateAllByName(String name);
	 public void deleteById(int id);
	 public void deleteByName(String name);
}
