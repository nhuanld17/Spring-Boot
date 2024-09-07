package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
