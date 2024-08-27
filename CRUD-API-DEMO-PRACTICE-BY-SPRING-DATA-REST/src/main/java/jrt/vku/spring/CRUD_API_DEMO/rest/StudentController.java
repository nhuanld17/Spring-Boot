package jrt.vku.spring.CRUD_API_DEMO.rest;

import jakarta.persistence.Access;
import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import jrt.vku.spring.CRUD_API_DEMO.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custom")
public class StudentController {
	private final StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping("/listStudents")
	public ResponseEntity<List<Student>> getListStudents() {
		List<Student> students = studentService.getAllStudent();
		if (students.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
		Student student = studentService.getStudentById(id);
		if (student == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(student);
		}
	}
	
	@GetMapping("/getByLastName")
	public ResponseEntity<List<Student>> getListStudentsByName(@RequestParam(name = "lastName") String lastName) {
		List<Student> students = studentService.getStudentByLastName(lastName);
		
		if (students.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}
}
