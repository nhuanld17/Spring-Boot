package com.example.NameEntityGraph_and_EntityGraph_Annotations.repository;

import com.example.NameEntityGraph_and_EntityGraph_Annotations.Entity.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	/**
	 * Trong trường hợp này, khi bạn gọi phương thức findByName, Spring Data JPA sẽ
	 * sử dụng Entity Graph đã được định nghĩa, tự động tải characteristics cùng với
	 * Item.
	 */
	@EntityGraph(value = "Item.characteristics")
	Item findByName(String name);
}
