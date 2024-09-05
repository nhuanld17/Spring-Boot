package jrt.vku.spring.security_jpa_review.Security_JPA.security;

import jrt.vku.spring.security_jpa_review.Security_JPA.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	// Cấu hình Bean cho Lớp BCryptPasswordEncoder cho authenticationProvider()
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserService userService) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests(
			config->config
					.requestMatchers("/register/**").permitAll()
					.anyRequest().authenticated()
		).formLogin(
				formLogin -> formLogin.loginPage("/showLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.permitAll()
		).logout(
				logout -> logout.permitAll()
		).exceptionHandling(
				exception -> exception.accessDeniedPage("/showPage403")
		);
		
		return http.build();
	}
	
}
