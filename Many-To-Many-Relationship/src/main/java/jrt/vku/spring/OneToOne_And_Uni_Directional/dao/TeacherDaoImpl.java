package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
	@Transactional
	public void deleteTeacherById(int id) {
		// Find teacher by ID
		Teacher teacher = entityManager.find(Teacher.class, id);
		// Delete
		entityManager.remove(teacher);
	}
	
	@Override
	@Transactional
	public void updateTeacher(Teacher teacher) {
		entityManager.merge(teacher);
		entityManager.flush();
	}
	
	@Override
	public Teacher findTeacherByIdJoinFetch(int id) {
		TypedQuery<Teacher> query = entityManager.createQuery(
				"select t from Teacher t " +
						"JOIN FETCH t.courses " +
						"JOIN FETCH t.teacherDetail " +
						"WHERE t.id=:id", Teacher.class
		);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
}
