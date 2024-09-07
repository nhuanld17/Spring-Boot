package jrt.vku.spring.OneToOne_And_Uni_Directional.dao;

import jrt.vku.spring.OneToOne_And_Uni_Directional.entity.Course;

import java.util.List;

public interface CourseDao {
	public void save(Course course);
	public List<Course> findCourseByTeacherId(int id);
	public Course findCourseById(int id);
	public Course findCourseAndStudentByCourseId(int id);
}
