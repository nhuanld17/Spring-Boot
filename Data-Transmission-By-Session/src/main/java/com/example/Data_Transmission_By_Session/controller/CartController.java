package com.example.Data_Transmission_By_Session.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
	// Trang hiển thị giỏ hàng
	@GetMapping("/cart")
	public String viewCart(HttpSession session, Model model) {
		// Lấy danh sách sản phẩm từ Session
		List<String> cart = (List<String>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>(); // Nếu giỏ hàng rỗng, tạo giỏ hàng mới
		}
		// Truyền giỏ hàng vào Model để hiển thị ở View
		model.addAttribute("cart", cart);
		return "cart";
	}
	
	// Thêm sản phẩm vào giỏ hàng
	@PostMapping("/cart/add")
	public String addToCart(@RequestParam("product") String product, HttpSession session) {
		// Lấy giỏ hàng từ Session
		List<String> cart = (List<String>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>();
		}
		cart.add(product);
		session.setAttribute("cart", cart);
		return "redirect:/cart";
	}
	
	// Xóa toàn bộ giỏ hàng
	@GetMapping("/cart/clear")
	public String cleanCart(HttpSession session) {
		// Xóa giỏ hàng khỏi session
		session.removeAttribute("cart");
		return "redirect:/cart";
	}
}
