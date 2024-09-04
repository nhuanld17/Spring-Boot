package jrt.vku.spring.security_JPA.service;


import jrt.vku.spring.security_JPA.dao.RoleRepository;
import jrt.vku.spring.security_JPA.dao.UserRepository;
import jrt.vku.spring.security_JPA.entity.Role;
import jrt.vku.spring.security_JPA.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService{
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public User findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

// Cái này tạo khi vừa mới chạy chương trình đề thêm data vào
// khi có data mẫu r thì comment
//	@PostConstruct
//	public void insertUser(){
//		User user1 = new User();
//		user1.setUsername("Nhuan");
//		user1.setPassword("$2a$12$PVOp62u2uoYHd91YI9JpmuYG8Jeth0rgPtna5nuLZVBUyUWedHgXG");
//		user1.setEnabled(true);
//
//		Role role1 = new Role();
//		role1.setName("ROLE_ADMIN");
//
//		Collection<Role> roles = new ArrayList<>();
//		roles.add(role1);
//
//		user1.setRoles(roles);
//		userRepository.save(user1);
//	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
		return roles.stream().map(role ->new SimpleGrantedAuthority(role.getName())).toList();
	}
}
