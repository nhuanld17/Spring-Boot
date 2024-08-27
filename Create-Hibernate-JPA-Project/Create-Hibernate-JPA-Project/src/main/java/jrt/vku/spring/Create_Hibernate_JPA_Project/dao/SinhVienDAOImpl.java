package jrt.vku.spring.Create_Hibernate_JPA_Project.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jrt.vku.spring.Create_Hibernate_JPA_Project.entity.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SinhVienDAOImpl implements SinhVienDAO{
	private EntityManager entityManager;

	@Autowired
	public SinhVienDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void save(SinhVien sinhVien) {
		this.entityManager.persist(sinhVien);
	}

	@Override
	@Transactional
	public SinhVien getById(int id) {
		return this.entityManager.find(SinhVien.class, id);
	}
}
