package com.example.Cacheable_and_CacheEvit_Annotation.controller;

import com.example.Cacheable_and_CacheEvit_Annotation.model.Book;
import com.example.Cacheable_and_CacheEvit_Annotation.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/{id}")
	public Optional<Book> getBookById(@PathVariable("id") Long id) {
		return bookService.getBookById(id);
	}
	
	@PostMapping
	public Book addBook(@RequestBody Book book) {
		return bookService.saveBook(book);
	}
	
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable("id") Long id) {
		bookService.deleteBookById(id);
	}
}
