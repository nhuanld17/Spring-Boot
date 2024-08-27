package jrt.vku.spring.RestController_And_Check_By_PostMan_Demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
	
	@GetMapping("/hello")
	public String hello(){
		return "Hello World";
	}
	
	@GetMapping("/hello/vietnamese")
	public String helloVietNamese(){
		return "Xin chao";
	}
}
