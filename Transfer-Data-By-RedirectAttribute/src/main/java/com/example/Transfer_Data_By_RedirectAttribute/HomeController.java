package com.example.Transfer_Data_By_RedirectAttribute;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(){
		return "home";
	}
	
	@PostMapping("/submitForm")
	public String submitForm(@RequestParam("name") String name,
	                         RedirectAttributes redirectAttributes){
		// Thêm dữ liệu vào RedirectAttribute để chuyển tiếp
		redirectAttributes.addFlashAttribute("message", "Hello " + name +"!");
		return "redirect:/result"; // Chuyển hướng đến /result
	}
	
	@GetMapping("/result")
	public String result(){
		// Dữ liệu "message" sẽ có sẵn ở đây sau khi
		// chuyển hướng
		return "result";
	}
}
