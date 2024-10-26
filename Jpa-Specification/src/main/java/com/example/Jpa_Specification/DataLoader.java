package com.example.Jpa_Specification;

import com.example.Jpa_Specification.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
	private final UserRepository userRepository;
	
	public DataLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public void run(String... args) throws Exception {
//		userRepository.save(new User("John", "Doe", "john@doe.com", 22));
//		userRepository.save(new User("Tom", "Doe", "tom@doe.com", 26));
	}
}
