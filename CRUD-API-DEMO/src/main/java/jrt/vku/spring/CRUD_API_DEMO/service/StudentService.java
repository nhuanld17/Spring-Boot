package jrt.vku.spring.CRUD_API_DEMO.service;

import jrt.vku.spring.CRUD_API_DEMO.enity.Student;

import java.util.List;

public interface StudentService {
	public List<Student> getAllStudents();
	public Student getStudentById(int id);
	public Student addStudent(Student student);
	public Student updateStudent(Student student);
	public void deleteStudentById(int id);
}
