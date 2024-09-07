package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
	
}
