package com.example.Join.Predicate.Specification.repository;

import com.example.Join.Predicate.Specification.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
