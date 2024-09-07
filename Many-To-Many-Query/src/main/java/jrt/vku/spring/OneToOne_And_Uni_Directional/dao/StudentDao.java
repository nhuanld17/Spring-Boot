package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Student;

public interface StudentDao {
	public void save(Student student);
	public Student findStudentById(int id);
	public Student findStudentAndCourseByStudentId(int id);
}
