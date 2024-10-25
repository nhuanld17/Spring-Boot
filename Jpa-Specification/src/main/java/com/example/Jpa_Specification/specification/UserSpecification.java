package com.example.Jpa_Specification.specification;

import com.example.Jpa_Specification.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification implements Specification<User> {
	private SearchCriteria criteria;
	
	public UserSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public Predicate toPredicate
			(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThan(
					root.<String>get(criteria.getKey()), (String) criteria.getValue()
			);
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThan(
					root.<String>get(criteria.getKey()), (String) criteria.getValue()
			);
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(
						root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%"
				);
			} else {
				return builder.equal(root.get(criteria.getKey()), criteria.getValue());
			}
		}
		
		return null;
	}
}
