package jrt.vku.spring.THYMELEAF_FIRST_DEMO.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MultiplicationTableController {
	
	@GetMapping("/displayTable/{x}")
	public String displayMultiplicationTable(@PathVariable int x, Model model) {
		model.addAttribute("number", x);
		// Return về tên file Thymeleaf
		return "display";
	}
}
