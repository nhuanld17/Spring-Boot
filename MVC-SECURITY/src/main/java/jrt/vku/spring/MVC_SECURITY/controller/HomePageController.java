package jrt.vku.spring.MVC_SECURITY.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomePageController {
	
	@GetMapping("")
	public String showHomePage(){
		return "homepage";
	}
}
