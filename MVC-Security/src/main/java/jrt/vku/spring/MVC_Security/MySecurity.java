package jrt.vku.spring.MVC_Security;


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
				configurer->configurer.anyRequest().authenticated()
		).formLogin(
				form->form.loginPage("/showLoginPage")
						.loginProcessingUrl("/authenticateTheUser")
						.permitAll()
		);
		return http.build();
	}
}
