package jrt.vku.spring.Practise_Advice_In_AOP;

import jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class PractiseAdviceInAopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractiseAdviceInAopApplication.class, args);
	}
	
	@Bean
	@Autowired
	public CommandLineRunner runner(CalculaService calculaService) {
		return args -> {
			calculaService.add(5,10);
			calculaService.divide(5, 0);
		};
	}
}
