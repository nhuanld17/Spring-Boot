package jrt.vku.spring.security_jpa_review.Security_JPA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
	
	@GetMapping
	public String showHomePage(Model model) {
		return "home";
	}
}
