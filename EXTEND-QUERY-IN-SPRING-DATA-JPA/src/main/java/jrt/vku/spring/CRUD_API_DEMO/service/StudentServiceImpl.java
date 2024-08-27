package jrt.vku.spring.CRUD_API_DEMO.service;

import jrt.vku.spring.CRUD_API_DEMO.dao.StudentRepository;
import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
	private StudentRepository studentRepository;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@Override
	@Transactional
	public List<Student> getAllStudent() {
		return studentRepository.findAll();
	}
	
	@Override
	@Transactional
	public Student getStudentById(int id) {
		return studentRepository.getReferenceById(id);
	}
	
	@Override
	public List<Student> getStudentByLastName(String lastName) {
		return studentRepository.findByLastName(lastName);
	}
	
	@Override
	public List<Student> findByFirstNameNot(String firstName) {
		return studentRepository.findByFirstNameNot(firstName);
	}
	
	@Override
	@Transactional
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}
	
	@Override
	@Transactional
	public Student deleteStudent(Student student) {
		studentRepository.delete(student);
		return student;
	}
	
	@Override
	@Transactional
	public Student updateStudent(Student student) {
		return studentRepository.saveAndFlush(student);
	}
}
