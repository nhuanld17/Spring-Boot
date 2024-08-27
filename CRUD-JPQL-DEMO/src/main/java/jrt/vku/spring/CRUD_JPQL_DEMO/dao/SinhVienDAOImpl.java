package jrt.vku.spring.CRUD_JPQL_DEMO.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jrt.vku.spring.CRUD_JPQL_DEMO.entity.SinhVien;
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
		entityManager.persist(sinhVien);
	}
	
	@Override
	@Transactional
	public SinhVien findById(int id) {
		return entityManager.find(SinhVien.class, id);
	}
	
	@Override
	@Transactional
	public List<SinhVien> findAll() {
		String jpql = "SELECT s FROM SinhVien s";
		return entityManager.createQuery(jpql, SinhVien.class).getResultList();
	}
	
	@Override
	@Transactional
	public List<SinhVien> getByName(String name) {
		String jpql = "SELECT s FROM SinhVien s WHERE s.ten=:ten";
		TypedQuery<SinhVien> typedQuery = entityManager.createQuery(jpql, SinhVien.class);
		typedQuery.setParameter("ten", name);
		return typedQuery.getResultList();
	}
	
	@Override
	@Transactional
	public void updateInfo(SinhVien sinhVien) {
		entityManager.merge(sinhVien);
	}
	
	@Override
	@Transactional
	public void setAllToOneName(String name) {
		String jpql = "UPDATE SinhVien s SET s.ten=:ten";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("ten", name);
		query.executeUpdate();
	}
	
	@Override
	@Transactional
	public void deleteById(int id) {
		String jpql = "DELETE FROM SinhVien s WHERE s.id=:id";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	@Override
	@Transactional
	public void deleteByName(String name) {
		String jpql = "DELETE FROM SinhVien s WHERE s.ten=:ten";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("ten", name);
		query.executeUpdate();
	}
}
