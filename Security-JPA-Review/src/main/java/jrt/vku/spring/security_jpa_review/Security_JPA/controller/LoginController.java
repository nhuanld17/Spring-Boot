package jrt.vku.spring.security_jpa_review.Security_JPA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/showLoginPage")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/showPage403")
	public String showPage403() {
		return "error/403";
	}
}
