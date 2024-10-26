package com.example.Join.Predicate.Specification.service;

import com.example.Join.Predicate.Specification.domain.Article;
import com.example.Join.Predicate.Specification.repository.ArticleRepository;
import com.example.Join.Predicate.Specification.specification.ArticleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	
	public List<Article> filterArticles(Integer priceLow, Integer priceHigh, List<String> sizes,
	                                    List<String> categories, List<String> brands, String search) {
		Specification<Article> spec = ArticleSpecification.filterBy(priceLow, priceHigh, sizes, categories, brands, search);
		return articleRepository.findAll(spec);
	}
}
