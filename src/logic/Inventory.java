package logic;

import java.util.ArrayList;

import entity.base.Item;

public class Inventory {
	private static Inventory instance = null;
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
				//System.out.println("yes");
				i.setAmount( i.getAmount() + item.getAmount() );
				return;
			}
		}
		this.getInventory().add(item);
	}

	public static Inventory getInstance() {
		if(instance == null) {
			instance = new Inventory();
		}
		return instance;
	}
	
	
}
