package jrt.vku.spring.Create_Hibernate_JPA_Project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jrt.vku.spring.Create_Hibernate_JPA_Project.entity.SinhVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

	@Override
	@Transactional
	public List<SinhVien> getAll() {
		String jpql = "SELECT s FROM SinhVien s";
		return this.entityManager.createQuery(jpql, SinhVien.class).getResultList();
	}

	@Override
	@Transactional
	public List<SinhVien> getByName(String name) {
		String jpql = "SELECT s FROM SinhVien s WHERE s.ten=:t";
		TypedQuery<SinhVien> query = this.entityManager.createQuery(jpql, SinhVien.class);
		query.setParameter("t", name);
		return query.getResultList();
	}

	@Override
	@Transactional
	public SinhVien update(SinhVien sinhVien) {
		return this.entityManager.merge(sinhVien);
	}

	@Override
	@Transactional
	public int updateAllByName(String name) {
		String jpql = "UPDATE SinhVien s SET s.ten=:t";
		Query query = this.entityManager.createQuery(jpql);
		query.setParameter("t", name);
		return query.executeUpdate();
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		SinhVien sinhVien = entityManager.find(SinhVien.class, id);
		if (sinhVien != null) {
			entityManager.remove(sinhVien);
		}
	}

	@Override
	@Transactional
	public void deleteByName(String name) {
		String jpql = "DELETE FROM SinhVien s WHERE s.ten=:t";
		Query query = this.entityManager.createQuery(jpql);
		query.setParameter("t", name);
		query.executeUpdate();
	}
}
