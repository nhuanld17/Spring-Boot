package jrt.vku.spring.security_jpa_review.Security_JPA.service;

import jrt.vku.spring.security_jpa_review.Security_JPA.dao.RoleRepository;
import jrt.vku.spring.security_jpa_review.Security_JPA.dao.UserRepository;
import jrt.vku.spring.security_jpa_review.Security_JPA.entity.Role;
import jrt.vku.spring.security_jpa_review.Security_JPA.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		return roles.stream().map(role ->
				new SimpleGrantedAuthority(role.getName())
		).toList();
	}
	
	// Hàm add dữ liệu mẫu vào database trước, chỉ dùng 1 lần r comment lại
	/*
	@PostConstruct
	public void insertUser(){
		User user = new User();
		user.setUsername("Nhuan");
		user.setPassword("$2a$12$PVOp62u2uoYHd91YI9JpmuYG8Jeth0rgPtna5nuLZVBUyUWedHgXG");
		user.setEnabled(true);
		
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		
		Collection<Role> roles = new ArrayList<>();
		roles.add(role);
		
		user.setRoles(roles);
		userRepository.save(user);
	}
	*/
}
