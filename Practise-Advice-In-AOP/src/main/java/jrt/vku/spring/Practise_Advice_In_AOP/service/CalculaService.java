package jrt.vku.spring.Practise_Advice_In_AOP.service;


import org.springframework.stereotype.Service;

@Service
public class CalculaService {
	public double add(double a, double b) {
		System.out.println("a + b = " + (a + b));
		return a + b;
	}
	
	public double subtract(double a, double b) {
		System.out.println("a + b = " + (a - b));
		return a - b;
	}
	
	public double multiply(double a, double b) {
		System.out.println("a + b = " + (a*b));
		return a * b;
	}
	
	public double divide(double a, double b) {
		System.out.println("a + b = " + (a/b));
		return (double) a / b;
	}
}
