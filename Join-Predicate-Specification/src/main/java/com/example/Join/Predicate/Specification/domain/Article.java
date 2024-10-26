package com.example.Join.Predicate.Specification.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private Integer price;
	
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Size> sizes;
	
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Category> categories;
	
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Brand> brands;
	
	public Article() {
	}
	
	public Article(String title, Integer price, Set<Size> sizes, Set<Category> categories, Set<Brand> brands) {
		this.title = title;
		this.price = price;
		this.sizes = sizes;
		this.categories = categories;
		this.brands = brands;
	}
	
	public Set<Brand> getBrands() {
		return brands;
	}
	
	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
	
	public Set<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Set<Size> getSizes() {
		return sizes;
	}
	
	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
