package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Teacher;
import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.TeacherDetail;

public interface TeacherDetailDao {
	public void save(TeacherDetail teacherDetail);
	public TeacherDetail findByTeacherDetailId(int id);
	public void deleteTeacherDetailById(int id);
}
