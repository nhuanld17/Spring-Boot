package jrt.vku.spring.security_JPA.service;

import jrt.vku.spring.security_JPA.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	public User findByUsername(String userName);
	
}
