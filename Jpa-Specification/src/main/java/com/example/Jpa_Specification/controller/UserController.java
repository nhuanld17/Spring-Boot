package com.example.Jpa_Specification.controller;

import com.example.Jpa_Specification.model.User;
import com.example.Jpa_Specification.repository.UserRepository;
import com.example.Jpa_Specification.specification.UserSpecificationsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> search(@RequestParam(value = "search") String search) {
		UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
		Matcher matcher = pattern.matcher(search+",");
		String[] searchParams = search.split(",");
		
		while (matcher.find()) {
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}

		Specification<User> spec = builder.build();
		List<User> users = userRepository.findAll(spec);
		System.out.println("Users found: " + users); // In ra kết quả tìm kiếm
		return users;
	}
}
