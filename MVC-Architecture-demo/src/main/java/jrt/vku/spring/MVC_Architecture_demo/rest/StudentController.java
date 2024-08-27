package jrt.vku.spring.MVC_Architecture_demo.rest;

import jrt.vku.spring.MVC_Architecture_demo.model.Student;
import jrt.vku.spring.MVC_Architecture_demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
	private final StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping("/list")
	public String listAllStudents(Model model) {
		List<Student> students = studentService.getAllStudents();
		model.addAttribute("students", students);
		return "student/students";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "student/student-form";
	}
	
	@PostMapping("/save")
	// Giá trị trong Model Attribute phải giống với giá trị
	// của th:object trong student-form.html
	public String save(@ModelAttribute("student") Student student) {
		studentService.addStudent(student);
		return "redirect:/students/list"; // Chuyển sang 1 URL mới
		// Tại sao lại không trả về 1 chuỗi là đường dẫn tới trang html ??
		// Ta để ý ở các method ở trên (create, listAllStudents), khi ta
		// return về chuỗi đường dẫn của trang html
		// thì ModelAttribute sẽ đi kèm với đường link đó
		// ta chuyển sang 1 URL vì ta 1 có 1 ModelAttribute nào đi kèm theo
	}
	
	@GetMapping("/edit")
	/*
	 Dùng @RequestParam vì ở students.html, khi bấm nút sửa thì thymeleaf
	 tự động tạo URL có dạng /students/update?id=student.id
	 Do đó dùng @RequestParam để lấy id từ đường link đó
	 */ public String edit(@RequestParam("id") int id, Model model) {
		Student student = studentService.getStudentById(id);
		model.addAttribute("student", student);
		return "student/student-form-update";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute("student") Student student) {
		studentService.updateStudent(student);
		return "redirect:/students/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam int id) {
		 studentService.deleteStudentById(id);
		 return "redirect:/students/list";
	}
}

