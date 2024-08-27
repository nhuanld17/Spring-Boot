package jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.rest;

import jakarta.annotation.PostConstruct;
import jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.entity.ErrorRespone;
import jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.entity.Student;
import jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.exception.StudentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
	public List<Student> students;
	
	@PostConstruct
	public void createList(){
		students = new ArrayList<>();
		students.add(new Student(1, "Nhuaanj", 26, 4, "IT"));
		students.add(new Student(2, "Nhuaanjf", 27, 4, "IT"));
		students.add(new Student(3, "Nhuaanjr", 28, 4, "IT"));
		students.add(new Student(4, "Nhuaanjg", 29, 4, "IT"));
		students.add(new Student(5, "Nhuaanjh", 30, 4, "IT"));
	}
	
	@GetMapping("/")
	public List<Student> getStudentList(){
		return students;
		// test hàm này với postman sẽ trả về 1 mảng Object Student
	}
	
	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable int id){
		Student res = null;
		for(Student student : students){
			if(student.getId() == id){
				return student;
			}
		}
		
		if(res == null){
			throw new StudentException("Không tìm thấy sinh viên " + id);
		}
		
		return null;
	}
	
	@GetMapping("/index/{index}")
	public Student getStudentByIndex(@PathVariable int index){
		Student student = null;
		try {
			student = students.get(index);
		} catch (IndexOutOfBoundsException e) {
			throw new StudentException("Không tìm thấy sinh viên");
		}
		return student;
	}
	
	/*
	Khi 1 trong hai phương thức getStudentById và getStudentByIndex gặp lỗi và
	-throw new StudentException-, thì hàm catchError sẽ được tiến hành
	*/
	
	@ExceptionHandler
	public ResponseEntity<ErrorRespone> catchError(StudentException exception){
		ErrorRespone errorRespone = new ErrorRespone(HttpStatus.NOT_FOUND.value(),exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRespone);
	}
	
	/*
	Phương thức catchError(StudentException exception) sẽ xử lý các ngoại lệ
	kiểu StudentException. Nó tạo một đối tượng ErrorRespone chứa mã lỗi và
	thông báo lỗi, và trả về phản hồi với mã trạng thái HTTP 404 NOT FOUND.
	*/
	
	/*
	Vậy sẽ thế nào khi ta send 1 GET Request với URL:
	http://localhost:8080/student/index/mot
	hay là:
	http://localhost:8080/student/mot
	Kết quả trông như 1 đống bùi nhùi, và đây là cách khắc phục
	*/
	
	// Ta sẽ bắt 1 đống lỗi cùng 1 lúc, thay vì StudentException
	// Thì bây giờ ta bắt Exception thôi là được rồi
	// Và thay NOT_FOUND = BAD_REQUEST, vì chúng ta đang bắt lỗi truyền
	// vào 1 chuỗi thay vì 1 số
	@ExceptionHandler
	public ResponseEntity<ErrorRespone> catchAllError(Exception exception){
		ErrorRespone errorRespone = new ErrorRespone(HttpStatus.BAD_REQUEST.value(),exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRespone);
	}
	
	/*
	Lỗi trả về bao gồm những thông tin là tham số của class ErrorRespone
	*/
}
	
