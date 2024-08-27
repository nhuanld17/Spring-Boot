package jrt.vku.spring.CRUD_API_DEMO.rest;

import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import jrt.vku.spring.CRUD_API_DEMO.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
	private final StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> students = studentService.findAll();
		
		if (students.isEmpty()){
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
		Student student = studentService.findById(id);
		if (student == null){
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(student);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		student.setId(0);
		Student newStudent = studentService.save(student);
		if (newStudent == null){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} else {
			return ResponseEntity.ok(newStudent);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
		Student existStudent = studentService.findById(id);
		if (existStudent == null){
			return ResponseEntity.notFound().build();
		} else {
			studentService.update(student);
			return ResponseEntity.ok(student);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
		Student student = studentService.findById(id);
		if (student == null){
			return ResponseEntity.notFound().build();
		} else {
			studentService.deleteById(id);
			return ResponseEntity.ok(student);
		}
	}
}
