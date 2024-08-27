package jrt.vku.spring.Bean_Configuaration_By_Java_Code_Demo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyUtil {

	// Khi đánh dấu hàm getMath với @Bean, thì đối tượng Calculator
	// sẽ được tao ra và quản lí bởi SpringContext như cách mà @Component
	// làm với các class được đánh dấu bởi nó
	@Bean
	public Calculator getMath(){
		return new Calculator();
	}

	// Tuy nhiên, @Component được sử dụng với các class mà chúng ta tự
	// tạo ra, còn đối với các thư viện trong java, code trong các Class
	// trong các thư viện không thể bị thay đổi một cách tùy tiện, do đó
	// không thể đánh dấu các class trong thư viện bằng @Component mà
	// phải cấu hình bằng @Configuration và @Bean, khi đó các đối tượng
	// từ class trong các thư viện mới được tạo ra và quản lí bởi Spring
	// Context

}
