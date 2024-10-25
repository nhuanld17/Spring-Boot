package com.example.Jpa_Specification.specification;

import com.example.Jpa_Specification.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecificationsBuilder {
	private final List<SearchCriteria> params;
	
	public UserSpecificationsBuilder() {
		params = new ArrayList<>();
	}
	
	public UserSpecificationsBuilder with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}
	
	public Specification<User> build() {
		if (params.isEmpty()) {
			return null;
		}
		
		Specification<User> result = new UserSpecification(params.get(0));
		
		for (int i = 1; i < params.size(); i++) {
			result = Specification.where(result).and(new UserSpecification(params.get(i)));
		}
		
		return result;
		
	}
}
