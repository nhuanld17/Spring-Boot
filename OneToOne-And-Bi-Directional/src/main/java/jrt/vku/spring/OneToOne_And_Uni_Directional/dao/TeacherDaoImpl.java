package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDaoImpl implements TeacherDao{
	private EntityManager entityManager;
	
	@Autowired
	public TeacherDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@Transactional
	public void save(Teacher teacher) {
		entityManager.persist(teacher);
	}
	
	@Override
	public Teacher findByTeacherId(int id) {
		return entityManager.find(Teacher.class, id);
	}
	
	@Override
	public void deleteTeacherById(int id) {
		// Find teacher by ID
		Teacher teacher = entityManager.find(Teacher.class, id);
		// Delete
		entityManager.remove(teacher);
	}
}
