package jrt.vku.spring.MVC_Security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping()
	public String showHomePage(Model model) {
		return "home";
	}
}
