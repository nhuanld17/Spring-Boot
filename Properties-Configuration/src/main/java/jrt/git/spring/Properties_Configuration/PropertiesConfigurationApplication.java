package jrt.git.spring.Properties_Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PropertiesConfigurationApplication {
	@Value("${khoahoc.name}")
	private String courseName;

	@Value("${khoahoc.teacher}")
	private String courseTeacher;

	public static void main(String[] args) {
		SpringApplication.run(PropertiesConfigurationApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "Hello World";
	}

	@GetMapping("/info")
	public String info(){
		return
				"Tên: " + courseName + "</br>" +
				"GVHD: " + courseTeacher;

	}
}
