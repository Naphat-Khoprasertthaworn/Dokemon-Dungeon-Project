package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buff.type.DamageReduction;
import entity.base.Item;
import entity.base.Unit;
import item.type.DamageReductionPotion;
import item.type.EnhancePotion;
import logic.Inventory;

public class ItemTest {
	
	EnhancePotion e1,e2,e3;
	DamageReductionPotion p1;
	Unit hero;
	Inventory inventory;
	@BeforeEach
	void setUp() throws Exception {
		e1 =  new EnhancePotion(5, 5, 50);
		e2 =  new EnhancePotion(4, 5, 50);
		e3 =  new EnhancePotion(3, 5, 50);
		p1 = new DamageReductionPotion(5,5,50);
		hero = new Unit("test item hero", "no skill", 100, 50, 0, 100);
		inventory  = new Inventory();
	}

	@Test
	void useItem() {
		//assertEquals(null, e1.getClass());
		p1.activeEffect(hero);
		assertEquals(75,hero.getTotalDefense());
		//assertEquals(4, p1.getAmount());
	}
	@Test
	void addToInventory() {
		System.out.println(inventory.getInventory());
		inventory.addItem(e1);
		inventory.addItem(e2);
		inventory.addItem(e3);
		inventory.addItem(p1);
		System.out.println(inventory.getInventory());
		System.out.println(inventory.getInventory().get(0).getAmount());
		assertEquals(e1, inventory.getInventory().get(0));
		assertEquals(12,inventory.getInventory().get(0).getAmount());
		assertEquals(2, inventory.getInventory().size());
		
		
		
	}
}
