package jrt.vku.spring.MVC_CRUD_DEMO.service;

import jrt.vku.spring.MVC_CRUD_DEMO.entity.Student;
import jrt.vku.spring.MVC_CRUD_DEMO.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public List<Student> getStudentsList() {
		return studentRepository.findAll();
	}
	
	@Override
	public Student getStudentById(int id) {
		return studentRepository.getReferenceById(id);
	}
	
	@Override
	@Transactional
	public void createStudent(Student student) {
		studentRepository.save(student);
	}
	
	@Override
	public Page<Student> getPaginatedStudents(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return studentRepository.findAll(pageable);
	}
	
	@Override
	@Transactional
	public void updateStudent(Student student) {
		studentRepository.save(student);
	}
	
	@Override
	@Transactional
	public void deleteStudentById(int id) {
		studentRepository.deleteById(id);
	}
}
