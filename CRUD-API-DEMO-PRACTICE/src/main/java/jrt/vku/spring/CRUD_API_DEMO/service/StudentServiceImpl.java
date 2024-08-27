package jrt.vku.spring.CRUD_API_DEMO.service;

import jrt.vku.spring.CRUD_API_DEMO.dao.StudentDAO;
import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
	private StudentDAO studentDAO;
	
	@Autowired
	public StudentServiceImpl(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}
	
	
	@Override
	@Transactional
	public List<Student> getAllStudent() {
		return studentDAO.getAllStudent();
	}
	
	@Override
	@Transactional
	public Student getStudentById(int id) {
		return studentDAO.getStudentById(id);
	}
	
	@Override
	@Transactional
	public Student saveStudent(Student student) {
		return studentDAO.saveStudent(student);
	}
	
	@Override
	@Transactional
	public Student deleteStudent(Student student) {
		return studentDAO.deleteStudent(student);
	}
	
	@Override
	@Transactional
	public Student updateStudent(Student student) {
		return studentDAO.updateStudent(student);
	}
}
