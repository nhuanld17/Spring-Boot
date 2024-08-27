package jrt.vku.spring.CRUD_API_DEMO.service;

import jrt.vku.spring.CRUD_API_DEMO.dao.StudentDAO;
import jrt.vku.spring.CRUD_API_DEMO.enity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
	private StudentDAO studentDAO;
	
	@Autowired
	public StudentServiceImpl(StudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}
	
	@Override
	@Transactional // Đọc thêm về @Transactional ở thư mục doc
	public List<Student> getAllStudents() {
		return studentDAO.findAll();
	}
	
	@Override
	@Transactional
	public Student getStudentById(int id) {
		return studentDAO.findById(id);
	}
	
	@Override
	@Transactional
	public Student addStudent(Student student) {
		return studentDAO.save(student);
	}
	
	@Override
	@Transactional
	public Student updateStudent(Student student) {
		return studentDAO.saveAndFlush(student);
	}
	
	@Override
	@Transactional
	public void deleteStudentById(int id) {
		 studentDAO.deleteById(id);
	}
}
