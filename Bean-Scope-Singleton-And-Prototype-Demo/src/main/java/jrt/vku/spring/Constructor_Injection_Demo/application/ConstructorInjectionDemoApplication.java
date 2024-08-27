package jrt.vku.spring.Constructor_Injection_Demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {
				"jrt.vku.spring.Constructor_Injection_Demo.application",
				"jrt.vku.spring.Constructor_Injection_Demo.rest",
				"jrt.vku.spring.Constructor_Injection_Demo.service"
		}
)
public class ConstructorInjectionDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConstructorInjectionDemoApplication.class, args);
	}
}
