package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.TeacherDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDetailDaoImpl implements TeacherDetailDao {
	private EntityManager entityManager;
	
	@Autowired
	public TeacherDetailDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Override
	@Transactional
	public void save(TeacherDetail teacherDetail) {
		entityManager.persist(teacherDetail);
	}
}
