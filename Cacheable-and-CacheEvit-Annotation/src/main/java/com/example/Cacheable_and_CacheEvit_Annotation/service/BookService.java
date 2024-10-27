package com.example.Cacheable_and_CacheEvit_Annotation.service;

import com.example.Cacheable_and_CacheEvit_Annotation.model.Book;
import com.example.Cacheable_and_CacheEvit_Annotation.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;
	
	/**
	 * @Cacheable(value = "books", key = "#id"): Cache kết quả của phương thức getBookById
	 * Khi phương thức được gọi lần đầu tiên với một id cụ thể, kết quả sẽ được lưu vào
	 * cache với key = id. Lần gọi tiếp theo với id đó sẽ lấy dữ liệu từ cache thay vì truy
	 * vấn cơ sở dữ liệu.
	 */
	
	@Cacheable(value = "books", key = "#id")
	public Optional<Book> getBookById(Long id) {
		System.out.println("Fetching book from database with id: " + id);
		return bookRepository.findById(id);
	}
	
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
	
	/**
	 * @CacheEvict(value = "books", key = "#id"): Khi xóa một Book, dữ liệu tương ứng
	 * trong cache cũng sẽ bị xóa.
	 */
	
	@CacheEvict(value = "books", key = "#id")
	public void deleteBookById(Long id) {
		System.out.println("Deleting book from database and cache with id: " + id);
		bookRepository.deleteById(id);
	}
}
