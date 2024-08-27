package jrt.vku.spring.MVC_Architecture_demo.service;


import jrt.vku.spring.MVC_Architecture_demo.model.Student;

import java.util.List;

public interface StudentService {
	public List<Student> getAllStudents();
	
	public Student getStudentById(int id);
	
	public Student addStudent(Student student);
	
	public Student updateStudent(Student student);
	
	public void deleteStudentById(int id);
	
}
