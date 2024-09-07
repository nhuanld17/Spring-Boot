package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Teacher;

public interface TeacherDao {
	public void save(Teacher teacher);
	public Teacher findByTeacherId(int id);
	public void deleteTeacherById(int id);
	public void updateTeacher(Teacher teacher);
}
