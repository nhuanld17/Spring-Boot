package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
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
}
