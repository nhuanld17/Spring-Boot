package jrt.vku.spring.CRUD_API_DEMO.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class UserConfiguraton {
	
	@Bean
	@Autowired
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		// Class DataSource đã được quản lí 1 cách tự động bởi Spring, đọc thêm ở
		// thư mục doc
		JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
		return userDetailsManager;
	}
	
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
