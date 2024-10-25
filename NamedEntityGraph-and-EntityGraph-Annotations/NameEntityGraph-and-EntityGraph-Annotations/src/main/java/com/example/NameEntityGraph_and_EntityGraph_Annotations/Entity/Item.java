package com.example.NameEntityGraph_and_EntityGraph_Annotations.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @NamedEntityGraph: định nghĩa một Entity Graph được đặt tên cho một
 * entity cụ thể. Nó cho phép bạn xác định các thuộc tính nào của entity
 * sẽ được tải (fetch) một cách "eager" (tải ngay lập tức) khi truy xuất
 * entity đó từ cơ sở dữ liệu.
 *
 * + name = "Item.characteristics": chỉ định tên cho Entity Graph mà bạn
 * đang định nghĩa. Tên này sẽ được sử dụng trong các truy vấn (queries)
 * sau này khi bạn muốn tham chiếu đến Entity Graph này. Trong trường hợp
 * này, Entity Graph có tên là Item.characteristics.
 *
 * + attributeNodes = @NamedAttributeNode("characteristics"): cho phép bạn
 * chỉ định các thuộc tính cụ thể của entity mà bạn muốn tải khi sử dụng
 * Entity Graph. Ở đây, bạn chỉ định rằng thuộc tính characteristics của
 * Item sẽ được tải cùng với Item khi Entity Graph được sử dụng.
 * @NamedAttributeNode("characteristics") là một annotation để xác định tên
 * thuộc tính cần được tải.
 */

@Entity
@NamedEntityGraph(name = "Item.characteristics",
		attributeNodes = @NamedAttributeNode("characteristics"))
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
	private List<Characteristic> characteristics = new ArrayList<Characteristic>();
	
	public Item() {
	
	}
	
	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}
	
	public void setCharacteristics(List<Characteristic> characteristics) {
		this.characteristics = characteristics;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
