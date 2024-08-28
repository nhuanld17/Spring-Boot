package jrt.vku.spring.MVC_Security.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class MySecurity {

	@Bean
	@Autowired
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("select id, pw, active from accounts where id=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select id, role from roles where id=?");
		return jdbcUserDetailsManager;
		
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				configurer->configurer
						.requestMatchers("/public/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIn")
						.requestMatchers(("/manager/**")).hasAnyRole("ADMIN","MANAGER")
						.requestMatchers("/teacher/**").hasAnyRole("ADMIN","MANAGER","TEACHER")
						.anyRequest().permitAll()
		/*
		anyRequest().authenticated():Bất kỳ yêu cầu nào cũng phải được xác thực, tức là
		người dùng phải đăng nhập thì mới có thể truy cập vào bất kỳ trang nào của ứng dụng.
		* */
		).formLogin(
				form->form.loginPage("/showLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.defaultSuccessUrl("/home", true)
						.permitAll()
		).logout(
				logout->logout.permitAll()
		).exceptionHandling(
				configurer->configurer.accessDeniedPage("/showPage403")
		);
		return http.build();
	}
}
