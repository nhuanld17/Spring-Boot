package jrt.vku.spring.MVC_SECURITY.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@GetMapping("")
	public String showManagerPage(){
		return "manager/manager";
	}
}
