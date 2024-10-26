package com.example.Join.Predicate.Specification.controller;

import com.example.Join.Predicate.Specification.domain.Article;
import com.example.Join.Predicate.Specification.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/filter")
	public List<Article> filter(
			@RequestParam(required = false) Integer priceLow,
			@RequestParam(required = false) Integer priceHigh,
			@RequestParam(required = false) List<String> sizes,
			@RequestParam(required = false) List<String> categories,
			@RequestParam(required = false) List<String> brands,
			@RequestParam(required = false) String search
	) {
		return articleService.filterArticles(priceLow, priceHigh, sizes, categories, brands, search);
	}
}
