package com.example.NameEntityGraph_and_EntityGraph_Annotations.Entity;

import jakarta.persistence.*;

@Entity
public class Characteristic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Item item;
	
	public Characteristic() {
	}
	
	public Characteristic(String type, Item item) {
		this.type = type;
		this.item = item;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
