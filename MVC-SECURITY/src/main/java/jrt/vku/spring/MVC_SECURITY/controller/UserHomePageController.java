package jrt.vku.spring.MVC_SECURITY.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class UserHomePageController {
	
	@GetMapping("")
	public String showUserHomePage() {
		return "home";
	}
}
