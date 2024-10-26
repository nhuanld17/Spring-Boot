package com.example.Join.Predicate.Specification.specification;

import com.example.Join.Predicate.Specification.domain.Brand;
import com.example.Join.Predicate.Specification.domain.Article;
import com.example.Join.Predicate.Specification.domain.Category;
import com.example.Join.Predicate.Specification.domain.Size;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ArticleSpecification {
	public static Specification<Article> filterBy
			(Integer priceLow, Integer priceHigh, List<String> sizes,
			 List<String> categories, List<String> brands, String search) {
		return (Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
			
			List<Predicate> predicates = new ArrayList<>();
			query.distinct(true);
			
			// Kiểm tra và thêm điều kiện cho kích thước
			if (sizes != null && !sizes.isEmpty()) {
				Join<Article, Size> joinSize = root.join("sizes");
				predicates.add(joinSize.get("value").in(sizes));
			}
			
			// Kiểm tra và thêm điều kiện cho danh mục
			if (categories != null && !categories.isEmpty()) {
				Join<Article, Category> joinCategory = root.join("categories");
				predicates.add(joinCategory.get("name").in(categories));
			}
			
			// Kiểm tra và thêm điều kiện cho thương hiệu
			if (brands != null && !brands.isEmpty()) {
				Join<Article, Brand> joinBrand = root.join("brands");
				predicates.add(joinBrand.get("name").in(brands));
			}
			
			// Kiểm tra và thêm điều kiện cho tìm kiếm
			if (search != null && !search.isEmpty()) {
				predicates.add(criteriaBuilder.like(root.get("title"), "%" + search + "%"));
			}
			
			// Kiểm tra và thêm điều kiện cho giá
			if (priceLow != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceLow));
			}
			
			if (priceHigh != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceHigh));
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
