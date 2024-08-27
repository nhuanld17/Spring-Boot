package jrt.vku.spring.CRUD_API_DEMO.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfiguraton {
	
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		UserDetails nhuan = User
				.withUsername("nhuan")
				.password("{noop}nhuan")
				.roles("USER")
				.build();
		
		UserDetails quang = User
				.withUsername("quang")
				.password("{noop}quang")
				.roles("USER")
				.build();
		
		UserDetails user = User
				.withUsername("user")
				.password("{noop}1234567")
				.roles("USER")
				.build();
		
		UserDetails hoang = User
				.withUsername("hoang")
				.password("{noop}hoang")
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(nhuan, quang, hoang, user);
	}
}
