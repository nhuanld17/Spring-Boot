package com.example.Transfer_Data_By_ModelAttribute;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {
	
	/* Phương thức này sẽ được gọi trước bất kì phương thức nào
	*  Nó tạo 1 đối tượng Student mới và gán nó vào mô hình với tên "student"
	*  Đối tượng này sẽ có sẵn cho tất cả các View được trả về từ Controller
	*  Dễ dàng sử dụng đối tượng Student trong các form mà không cần phải tạo
	*  lại nó trong từng phương thức xử lí yêu cầu
	* */
	@ModelAttribute("student")
	public Student populateStudent(){
		return new Student(); // Trả về 1 đối tượng sinh viên mới
	}
	
	@GetMapping("/student/form")
	public String showForm(Model model){
		/* Trả về view để hiển thị form
		*  Trong view này, ta có thể sử dụng đối tượng Student đã được tạo ở trên*/
		return "studentForm";
	}
	
	/* @ModelAttribute("student") Student student: Spring sẽ tự động gán dữ liệu
	 * từ form vào đối tượng Student. Nếu form có các trường giống với thuộc tính
	 * của Student, chúng sẽ được tự động ánh xạ.*/
	@PostMapping("/student/save")
	public String saveStudent(@ModelAttribute("student") Student student,
	                          RedirectAttributes redirectAttributes){
		// Lưu sinh viên (Chưa có logic trong ví dụ này)
		redirectAttributes.addFlashAttribute("message", "Student saved successfully: " +student.getName());
		return "redirect:/student/form";
	}
	
}
