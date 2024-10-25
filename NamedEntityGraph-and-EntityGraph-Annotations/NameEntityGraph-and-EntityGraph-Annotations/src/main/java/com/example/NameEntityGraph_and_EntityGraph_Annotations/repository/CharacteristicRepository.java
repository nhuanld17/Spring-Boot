package com.example.NameEntityGraph_and_EntityGraph_Annotations.repository;

import com.example.NameEntityGraph_and_EntityGraph_Annotations.Entity.Characteristic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
	@EntityGraph(attributePaths = {"item"})
	Characteristic findByType(String type);
}
