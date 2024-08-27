package jrt.vku.spring.MVC_CRUD_DEMO.controller;


import jrt.vku.spring.MVC_CRUD_DEMO.entity.Student;
import jrt.vku.spring.MVC_CRUD_DEMO.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
	private StudentService studentService;
	
	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
//	@GetMapping("/list")
//	public String listStudents(Model model) {
//		List<Student> students = studentService.getStudentsList();
//		model.addAttribute("students", students);
//		return "student-list";
//	}
	
	@GetMapping("/list")
	public String listStudent(Model model,
	                          @RequestParam(defaultValue = "1") int pageNo,
	                          @RequestParam(defaultValue = "5") int pageSize) {
		Page<Student> page = studentService.getPaginatedStudents(pageNo, pageSize);
		model.addAttribute("students", page.getContent());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		return "student-list";
	}

	
	@GetMapping("/create")
	public String createStudent(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "add-student";
	}
	
	@PostMapping("/add-student")
	public String addStudent(@ModelAttribute("student") Student student) {
		studentService.createStudent(student);
		return "redirect:/students/list";
	}
	
	@GetMapping("/edit")
	public String editStudent(@RequestParam int id, Model model) {
		Student student = studentService.getStudentById(id);
		model.addAttribute("student", student);
		return "edit-student";
	}
	
	@PostMapping("/update")
	public String updateStudent(@ModelAttribute("student") Student student) {
		studentService.updateStudent(student);
		return "redirect:/students/list";
	}
	
	@GetMapping("/delete")
	public String deleteStudent(@RequestParam int id) {
		studentService.deleteStudentById(id);
		return "redirect:/students/list";
	}
	
	@GetMapping("/detail")
	public String detailStudent(@RequestParam int id, Model model) {
		Student student = studentService.getStudentById(id);
		model.addAttribute("student", student);
		return "detail-student";
	}
}
