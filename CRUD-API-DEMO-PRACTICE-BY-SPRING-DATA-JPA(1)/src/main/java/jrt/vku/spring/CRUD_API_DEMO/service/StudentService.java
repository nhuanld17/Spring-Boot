package jrt.vku.spring.CRUD_API_DEMO.service;

import jrt.vku.spring.CRUD_API_DEMO.entity.Student;

import java.util.List;

public interface StudentService {
	public List<Student> findAll();
	public Student findById(int id);
	public Student save(Student student);
	public Student update(Student student);
	public void deleteById(int id);
}
