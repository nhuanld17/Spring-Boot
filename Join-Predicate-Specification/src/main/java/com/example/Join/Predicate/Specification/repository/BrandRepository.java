package com.example.Join.Predicate.Specification.repository;

import com.example.Join.Predicate.Specification.domain.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends CrudRepository<Brand, Long> {
}
