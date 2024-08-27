package jrt.vku.spring.CRUD_API_DEMO.rest;

import jrt.vku.spring.CRUD_API_DEMO.entity.Student;
import jrt.vku.spring.CRUD_API_DEMO.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	private final StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<Student>> getAllStudents() {
		List<Student> students = studentService.getAllStudent();
		
		if (students.isEmpty()){
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(students);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable int id){
		Student student = studentService.getStudentById(id);
		if (student == null){
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(student);
		}
	}
	
	@GetMapping("/getByLastName")
	public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam(name = "lastname") String lastName){
		List<Student> students = studentService.getStudentByLastName(lastName);
		if (students.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.ok(students);
		}
	}
	
	@GetMapping("/firstNameNot/{name}")
	public ResponseEntity<List<Student>> getStudentByFirstNameNot(@PathVariable String name){
		List<Student> students = studentService.findByFirstNameNot(name);
		
		if (students.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.ok(students);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<Student> saveStudent(@RequestBody Student student){
		student.setId(0);
		studentService.saveStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(student);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student){
		Student existedStudent = studentService.getStudentById(id);
		if (existedStudent == null){
			return ResponseEntity.notFound().build();
		} else {
			studentService.updateStudent(student);
			return ResponseEntity.ok(student);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable int id){
		Student student = studentService.getStudentById(id);
		if (student == null){
			return ResponseEntity.notFound().build();
		} else {
			studentService.deleteStudent(student);
			return ResponseEntity.ok(student);
		}
	}
}
