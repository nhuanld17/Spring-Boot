package jrt.vku.spring.security_JPA.security;


import jrt.vku.spring.security_JPA.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserService userService){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(
				configurer->configurer
						.anyRequest().authenticated()
		).formLogin(
				form->form.loginPage("/showLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.permitAll()
		).logout(
				logout->logout.permitAll()
		).exceptionHandling(
				configurer->configurer.accessDeniedPage("/showPage403")
		);
		
		return http.build();
	}
}
