package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Course;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao{
	private EntityManager entityManager;
	
	@Autowired
	public CourseDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	@Transactional
	public void save(Course course) {
		entityManager.persist(course);
	}
	
	@Override
	public List<Course> findCourseByTeacherId(int id) {
		// create query
		TypedQuery<Course> query = entityManager.createQuery(
				"select c from Course c where c.teacher.id = :teacherId", Course.class
		);
		query.setParameter("teacherId", id);
		return query.getResultList();
	}
	
	@Override
	public Course findCourseById(int id) {
		return entityManager.find(Course.class, id);
	}
	
	@Override
	public Course findCourseAndStudentByCourseId(int id) {
		TypedQuery<Course> query = entityManager.createQuery(
				"select c from Course c " +
						"JOIN FETCH c.students " +
						"WHERE c.id=:id", Course.class
		);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
}
