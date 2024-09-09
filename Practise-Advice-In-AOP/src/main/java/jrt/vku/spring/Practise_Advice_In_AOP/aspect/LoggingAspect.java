package jrt.vku.spring.Practise_Advice_In_AOP.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	@Before("execution(* jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(..))")
	public void beforeAdding(JoinPoint joinPoint) {
		System.out.println("Running adding method");
	}
	// Sau dấu * là
	@After("execution(* jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(..))")
	public void afterThrowCalculating(JoinPoint joinPoint) {
		System.out.println("Error?????");
	}
	
	@AfterThrowing("execution(* jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.*)")
	public void afterAdding(JoinPoint joinPoint) {
		System.out.println("Done adding");
	}
}
