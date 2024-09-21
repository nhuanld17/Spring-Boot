package com.example.Data_Transmission_By_Model.controller;

import com.example.Data_Transmission_By_Model.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {

	@GetMapping("/products")
	public String getProducts(Model model) {
		// Create product list
		List<Product> productList = Arrays.asList(
				new Product("Laptop", 1200),
				new Product("SmartPhone", 800),
				new Product("Tablet", 400)
		);
		
		// Thêm danh sách sản phẩm vào model để hiển thị trên View
		model.addAttribute("products", productList);
		
		return "products";
	}
}
