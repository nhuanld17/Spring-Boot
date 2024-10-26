package com.example.Join.Predicate.Specification.repository;

import com.example.Join.Predicate.Specification.domain.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends CrudRepository<Size, Long> {
}
