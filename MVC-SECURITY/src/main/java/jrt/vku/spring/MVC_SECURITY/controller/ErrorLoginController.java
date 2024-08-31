package jrt.vku.spring.MVC_SECURITY.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("showPage403")
public class ErrorLoginController {
	
	@GetMapping("")
	public String showPage403() {
		return "error/page403";
	}
}
