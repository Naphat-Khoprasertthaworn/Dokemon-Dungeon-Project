package entity.base;

import java.util.Objects;

import org.junit.validator.PublicClassValidator;

import buff.type.Exhaust;
import item.type.BuffPotion;
import item.type.DamageReductionPotion;
import item.type.EnhancePotion;
import item.type.ExhaustPotion;
import item.type.HealingPotion;
import item.type.VulnetabilityPotion;
import logic.GameLogic;

public abstract class Item {
	private int amount;
	private String name;
	private String text;
	private String imagePath;
	
	public Item(String name,String text,int amount,String imagePath) {
		this.setAmount(amount);
		this.setName(name);
		this.setText(text);
		this.setImagePath(imagePath);
	}

	public boolean active() {
		if(amount <= 0) {
			return false;
		}
		this.selectTarget();
		this.setAmount(getAmount()-1);
		return true;
	}
	
	public abstract void selectTarget() ;
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if(amount <= 0) {
			GameLogic.getInstance().getInventory().remove(this);
			return;
		}
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean equals(Object o) {
		//System.out.println("item equal work!");
		if(o == this) {
			return true;
		}
		if( !o.getClass().equals( this.getClass()) ) {
			return false;
		}
		
		if(this instanceof HealingPotion) {
			HealingPotion hp = (HealingPotion)o;
			if( hp.getHealingRatio() == ((HealingPotion)this ).getHealingRatio()  ) {
				return true;
			}
		}
		if( this instanceof EnhancePotion ) {
			return ((EnhancePotion)this).getBuff().equals(((EnhancePotion)o).getBuff());
		}
		if( this instanceof DamageReductionPotion ) {
			return ((DamageReductionPotion)this).getBuff().equals(((DamageReductionPotion)o).getBuff());
		}
		if( this instanceof ExhaustPotion ) {
			return ((ExhaustPotion)this).getBuff().equals(((ExhaustPotion)o).getBuff());
		}
		if( this instanceof VulnetabilityPotion ) {
			return ((VulnetabilityPotion)this).getBuff().equals(((VulnetabilityPotion)o).getBuff());
		}
		
		return false;
	}
	
	public String toString() {
		return this.getName() + " x " + this.getAmount();
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
