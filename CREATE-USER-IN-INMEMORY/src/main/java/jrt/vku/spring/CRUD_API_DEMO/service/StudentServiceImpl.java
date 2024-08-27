package jrt.vku.spring.CRUD_API_DEMO.service;

import jrt.vku.spring.CRUD_API_DEMO.dao.StudentRepository;
import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@Override
	@Transactional
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	@Override
	@Transactional
	public Student findById(int id) {
		return studentRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Student save(Student student) {
		studentRepository.save(student);
		return student;
	}
	
	@Override
	@Transactional
	public Student update(Student student) {
		return studentRepository.save(student);
	}
	
	@Override
	@Transactional
	public void deleteById(int id) {
		studentRepository.deleteById(id);
	}
}
