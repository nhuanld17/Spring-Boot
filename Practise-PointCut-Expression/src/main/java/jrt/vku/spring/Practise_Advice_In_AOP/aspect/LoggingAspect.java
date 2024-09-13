package jrt.vku.spring.Practise_Advice_In_AOP.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
//	@Before("execution(* jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(..))")
//	public void beforeAdding(JoinPoint joinPoint) {
//		System.out.println("Running adding method");
//	}
//	// Sau dấu * là
//	@After("execution(* jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.add(..))")
//	public void afterThrowCalculating(JoinPoint joinPoint) {
//		System.out.println("Error?????");
//	}
//
//	@AfterThrowing("execution(* jrt.vku.spring.Practise_Advice_In_AOP.service.CalculaService.*)")
//	public void afterAdding(JoinPoint joinPoint) {
//		System.out.println("Done adding");
//	}
	
	@Pointcut("execution(* jrt.vku.spring.Practise_Advice_In_AOP.service.*.*(..))")
	public void myPointCut() {
		System.out.println("Printing results: ");
	}
	
//	@Before("myPointCut()") // Ghi long sau khi chạy hàm myPointCut()
//	public void beforeAdd(JoinPoint joinPoint) {
//		System.out.println("before: "+joinPoint.getSignature().getName());
//	}
//
//	@After("myPointCut()") // Ghi long sau khi chạy hàm myPointCut()
//	public void afterAdd(JoinPoint joinPoint) {
//		System.out.println("Done: "+joinPoint.getSignature().getName());
//	}
	
	@Around("myPointCut()")
	public Object measureExcutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		System.out.println("result: "+result);
		System.out.println("Execution time: "+executionTime);
		
		return result;
	}
}
