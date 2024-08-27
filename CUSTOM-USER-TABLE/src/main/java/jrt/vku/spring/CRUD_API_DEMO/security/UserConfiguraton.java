package jrt.vku.spring.CRUD_API_DEMO.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class UserConfiguraton {
	
//	@Bean
//	@Autowired
//	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
//		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//		return userDetailsManager;
//	}

	@Bean
	@Autowired
	public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT id, pw, active FROM accounts WHERE id=?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT id, role FROM roles WHERE id=?");
		return jdbcUserDetailsManager;
	}
	
//	@Bean
//	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//		UserDetails nhuan = User
//				.withUsername("nhuan")
//				.password("{noop}nhuan")
//				.roles("TEACHER")
//				.build();
//
//		UserDetails quang = User
//				.withUsername("quang")
//				.password("{noop}quang")
//				.roles("MANAGER")
//				.build();
//
//		UserDetails hoang = User
//				.withUsername("hoang")
//				.password("{noop}hoang")
//				.roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(nhuan, quang, hoang);
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				configures -> {
					configures
							.requestMatchers(HttpMethod.GET, "/students").hasAnyRole("TEACHER", "MANAGER", "ADMIN")
							.requestMatchers(HttpMethod.GET, "/students/**").hasAnyRole("TEACHER","MANAGER", "ADMIN")
							.requestMatchers(HttpMethod.POST, "/students").hasAnyRole("MANAGER", "ADMIN")
							.requestMatchers(HttpMethod.PUT, "/students/**").hasAnyRole("MANAGER", "ADMIN")
							.requestMatchers(HttpMethod.DELETE, "/students/**").hasRole("ADMIN");
							
				}
		);
		http.httpBasic(Customizer.withDefaults());
		http.csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}
}
