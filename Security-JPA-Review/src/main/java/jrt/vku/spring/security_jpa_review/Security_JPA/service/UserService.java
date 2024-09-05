package jrt.vku.spring.security_jpa_review.Security_JPA.service;

import jrt.vku.spring.security_jpa_review.Security_JPA.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	public User findByUsername(String username);
	public void saveUser(User user);
}
