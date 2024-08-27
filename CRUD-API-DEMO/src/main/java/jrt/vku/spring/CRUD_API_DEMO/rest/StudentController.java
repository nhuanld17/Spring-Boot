package jrt.vku.spring.CRUD_API_DEMO.rest;

import jrt.vku.spring.CRUD_API_DEMO.enity.Student;
import jrt.vku.spring.CRUD_API_DEMO.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	private StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

//	@GetMapping
//	public List<Student> getStudents() {
//		return studentService.getAllStudents();
//	}
	
	@GetMapping
	public ResponseEntity<List<Student>> getStudents() {
		List<Student> students = studentService.getAllStudents();
		
		if (students.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}

//	@GetMapping("/{id}")
//	public Student getStudentById(@PathVariable int id) {
//		return studentService.getStudentById(id);
//	}
	
	// Sử dụng ResponseEntity, đọc thêm ở thư mục doc
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id) {
		Student student = studentService.getStudentById(id);
		if (student != null) {
			return ResponseEntity.ok(student);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	// Đọc thêm về RequestBody tại thư mục doc
	public ResponseEntity<Student> addStudent(@RequestBody Student student) {
		student.setId(0);
		student = studentService.addStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(student);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
		Student existingStudent = studentService.getStudentById(id);
		if (existingStudent != null) {
			existingStudent.setFirstName(student.getFirstName());
			existingStudent.setLastName(student.getLastName());
			existingStudent.setEmail(student.getEmail());
			studentService.updateStudent(existingStudent);
			return ResponseEntity.ok(existingStudent);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudentById(@PathVariable int id) {
		Student existingStudent = studentService.getStudentById(id);
		if (existingStudent != null) {
			studentService.deleteStudentById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
