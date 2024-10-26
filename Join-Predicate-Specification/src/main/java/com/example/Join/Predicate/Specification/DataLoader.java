package com.example.Join.Predicate.Specification;

import com.example.Join.Predicate.Specification.domain.Article;
import com.example.Join.Predicate.Specification.domain.Brand;
import com.example.Join.Predicate.Specification.domain.Category;
import com.example.Join.Predicate.Specification.domain.Size;
import com.example.Join.Predicate.Specification.repository.ArticleRepository;
import com.example.Join.Predicate.Specification.repository.BrandRepository;
import com.example.Join.Predicate.Specification.repository.CategoryRepository;
import com.example.Join.Predicate.Specification.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private SizeRepository sizeRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void run(String... args) throws Exception {
//		// Tạo các đối tượng Article và thêm dữ liệu mẫu
//		Article article1 = new Article();
//		article1.setTitle("T-Shirt");
//		article1.setPrice(100);
//
//		// Lưu Article trước
//		articleRepository.save(article1);
//
//		// Tạo và liên kết các Size cho Article
//		Size sizeS = new Size();
//		sizeS.setValue("S");
//		sizeS.setArticle(article1);
//
//		Size sizeM = new Size();
//		sizeM.setValue("M");
//		sizeM.setArticle(article1);
//
//		Set<Size> sizes = new HashSet<>();
//		sizes.add(sizeS);
//		sizes.add(sizeM);
//		article1.setSizes(sizes);
//
//		// Tạo và liên kết các Brand cho Article
//		Brand brandNike = new Brand();
//		brandNike.setName("Nike");
//		brandNike.setArticle(article1);
//
//		Set<Brand> brands = new HashSet<>();
//		brands.add(brandNike);
//		article1.setBrands(brands);
//
//		// Tạo và liên kết các Category cho Article
//		Category categoryShirt = new Category();
//		categoryShirt.setName("Shirt");
//		categoryShirt.setArticle(article1);
//
//		Set<Category> categories = new HashSet<>();
//		categories.add(categoryShirt);
//		article1.setCategories(categories);
//
//		// Lưu các thực thể liên quan sau khi lưu Article
//		sizeRepository.saveAll(sizes);
//		brandRepository.saveAll(brands);
//		categoryRepository.saveAll(categories);
//
//		// Tạo thêm dữ liệu mẫu
//		Article article2 = new Article();
//		article2.setTitle("Sneakers");
//		article2.setPrice(200);
//
//		// Lưu Article trước
//		articleRepository.save(article2);
//
//		// Tạo và liên kết Size cho Article
//		Size sizeL = new Size();
//		sizeL.setValue("L");
//		sizeL.setArticle(article2);
//
//		Set<Size> sizes2 = new HashSet<>();
//		sizes2.add(sizeL);
//		article2.setSizes(sizes2);
//
//		// Tạo và liên kết Brand cho Article
//		Brand brandAdidas = new Brand();
//		brandAdidas.setName("Adidas");
//		brandAdidas.setArticle(article2);
//
//		Set<Brand> brands2 = new HashSet<>();
//		brands2.add(brandAdidas);
//		article2.setBrands(brands2);
//
//		// Tạo và liên kết Category cho Article
//		Category categoryShoes = new Category();
//		categoryShoes.setName("Shoes");
//		categoryShoes.setArticle(article2);
//
//		Set<Category> categories2 = new HashSet<>();
//		categories2.add(categoryShoes);
//		article2.setCategories(categories2);
//
//		// Lưu các thực thể liên quan sau khi lưu Article
//		sizeRepository.saveAll(sizes2);
//		brandRepository.saveAll(brands2);
//		categoryRepository.saveAll(categories2);
//
//		System.out.println("Data initialized successfully.");
	}
}
