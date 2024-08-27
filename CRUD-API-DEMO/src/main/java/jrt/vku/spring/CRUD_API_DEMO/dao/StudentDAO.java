package jrt.vku.spring.CRUD_API_DEMO.dao;

import jrt.vku.spring.CRUD_API_DEMO.enity.Student;

import java.util.List;

public interface StudentDAO {
	public List<Student> findAll();
	public Student findById(int id);
	public Student save(Student student);
	
	// Update
	public Student saveAndFlush(Student student);
	
	public void deleteById(int id);
}
