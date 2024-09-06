package jrt.vku.spring.MVC_CRUD_DEMO.service;

import jrt.vku.spring.MVC_CRUD_DEMO.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
	public List<Student> getStudentsList();
	public Student getStudentById(int id);
	Page<Student> getPaginatedStudents(int pageNo, int pageSize);
	public void createStudent(Student student);
	public void updateStudent(Student student);
	public void deleteStudentById(int id);
}
