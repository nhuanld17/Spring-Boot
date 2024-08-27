package jrt.vku.spring.MVC_Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

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
}
