package logic;

import java.util.ArrayList;

import entity.base.Item;

public class Inventory {
	private ArrayList<Item> inventory;
	
	public Inventory() {
		inventory = new ArrayList<Item>();
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public void addItem(Item item) {
		for(Item i:this.getInventory()) {
			if( i.equals(item) ) {
				i.setAmount( i.getAmount() + item.getAmount() );
				return;
			}
		}
		this.getInventory().add(item);
	}

	
	public void showInventory() {
		for(int i = 0;i<inventory.size();i++) {
			System.out.println( inventory.get(i) );
		}
	}
	
	
}
