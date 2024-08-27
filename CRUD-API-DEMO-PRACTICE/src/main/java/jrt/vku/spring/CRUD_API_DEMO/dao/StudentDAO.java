package jrt.vku.spring.CRUD_API_DEMO.dao;

import jrt.vku.spring.CRUD_API_DEMO.entity.Student;

import java.util.List;

public interface StudentDAO {
	public List<Student> getAllStudent();
	public Student getStudentById(int id);
	public Student saveStudent(Student student);
	public Student updateStudent(Student student);
	public Student deleteStudent(Student student);
}
