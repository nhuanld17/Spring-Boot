package jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.rest;

import jakarta.annotation.PostConstruct;
import jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		for(Student student : students){
			if(student.getId() == id){
				return student;
			}
		}
		
		return null;
	}
	
	@GetMapping("/index/{index}")
	public Student getStudentByIndex(@PathVariable int index){
		return students.get(index);
	}
}
	
// Cái này dễ lỗi, khi có lỗi thì tắt và chạy lại chương trình