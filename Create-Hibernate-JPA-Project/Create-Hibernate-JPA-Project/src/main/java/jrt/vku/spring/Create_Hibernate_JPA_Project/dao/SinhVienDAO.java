package jrt.vku.spring.Create_Hibernate_JPA_Project.dao;

import jrt.vku.spring.Create_Hibernate_JPA_Project.entity.SinhVien;

public interface SinhVienDAO {
	 public void save(SinhVien sinhVien);
	 public SinhVien getById(int id);
}
