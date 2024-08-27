package jrt.vku.spring.CRUD_API_DEMO.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jrt.vku.spring.CRUD_API_DEMO.enity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {
	private final EntityManager entityManager;
	
	@Autowired
	public StudentDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Student> findAll() {
		String jpql = "SELECT s FROM Student s";
		TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
		return query.getResultList();
	}
	
	@Override
	public Student findById(int id) {
		return entityManager.find(Student.class, id);
	}
	
	@Override
	public Student save(Student student) {
		entityManager.persist(student);
		return student;
	}
	
	@Override
	public Student saveAndFlush(Student student) {
		student = entityManager.merge(student);
		return student;
	}
	
	@Override
	public void deleteById(int id) {
		Student student = findById(id);
		entityManager.remove(student);
	}
}
