package jrt.vku.spring.MVC_Architecture_demo.service;

import jakarta.transaction.Transactional;
import jrt.vku.spring.MVC_Architecture_demo.model.Student;
import jrt.vku.spring.MVC_Architecture_demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
	private final StudentRepository studentRepository;
	
	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}
	
	@Override
	public Student getStudentById(int id) {
		return studentRepository.getReferenceById(id);
	}
	
	@Override
	@Transactional
	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}
	
	@Override
	@Transactional
	public Student updateStudent(Student student) {
		return studentRepository.saveAndFlush(student);
	}
	
	@Override
	@Transactional
	public void deleteStudentById(int id) {
		studentRepository.deleteById(id);
	}
}
