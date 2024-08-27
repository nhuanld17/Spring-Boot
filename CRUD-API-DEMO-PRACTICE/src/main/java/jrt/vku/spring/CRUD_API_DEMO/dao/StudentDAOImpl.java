package jrt.vku.spring.CRUD_API_DEMO.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO{
	private EntityManager entityManager;
	
	@Autowired
	public StudentDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Student> getAllStudent() {
		String jpql = "SELECT s FROM Student s";
		TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
		return query.getResultList();
	}
	
	@Override
	public Student getStudentById(int id) {
		return entityManager.find(Student.class, id);
	}
	
	@Override
	public Student saveStudent(Student student) {
		entityManager.persist(student);
		return student;
	}
	
	@Override
	public Student updateStudent(Student student) {
		entityManager.merge(student);
		return student;
	}
	
	@Override
	public Student deleteStudent(Student student) {
		entityManager.remove(student);
		return student;
	}
}
