package com.example.NameEntityGraph_and_EntityGraph_Annotations;

import com.example.NameEntityGraph_and_EntityGraph_Annotations.Entity.Characteristic;
import com.example.NameEntityGraph_and_EntityGraph_Annotations.Entity.Item;
import com.example.NameEntityGraph_and_EntityGraph_Annotations.repository.CharacteristicRepository;
import com.example.NameEntityGraph_and_EntityGraph_Annotations.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntityGraphIntegrationTest {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private CharacteristicRepository characteristicRepo;
	
	@Test
	@Rollback
	public void givenEntityGraph_whenCalled_shouldReturnDefinedFields(){
		Item item = itemRepo.findByName("Table");
		assertThat(item.getId()).isEqualTo(1L);
		assertThat(item.getCharacteristics()).isNotEmpty();
	}
	
	@Test
	@Rollback
	public void givenAdhocEntityGraph_whenCalled_shouldReturnDefinedFields(){
		Characteristic characteristic = characteristicRepo.findByType("Rigid");
		assertThat(characteristic.getId()).isEqualTo(1L);
		assertThat(characteristic.getId()).isNotNull();
	}
	
	
}
