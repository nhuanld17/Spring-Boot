package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDaoImpl implements StudentDao{
	private EntityManager entityManager;
	
	@Autowired
	public StudentDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@Transactional
	public void save(Student student) {
		entityManager.persist(student);
	}
	
	@Override
	public Student findStudentById(int id) {
		return entityManager.find(Student.class, id);
	}
	
	@Override
	public Student findStudentAndCourseByStudentId(int id) {
		TypedQuery<Student> query = entityManager.createQuery(
				"select s from Student  s " +
						"JOIN FETCH s.courses " +
						"WHERE s.id=:id", Student.class
		);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
}
