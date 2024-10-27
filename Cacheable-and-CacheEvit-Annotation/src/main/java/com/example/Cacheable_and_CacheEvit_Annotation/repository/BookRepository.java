package com.example.Cacheable_and_CacheEvit_Annotation.repository;

import com.example.Cacheable_and_CacheEvit_Annotation.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
