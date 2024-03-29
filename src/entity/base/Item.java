package entity.base;

import item.type.DamageReductionPotion;
import item.type.EnhancePotion;
import item.type.ExhaustPotion;
import item.type.HealingPotion;
import item.type.VulnerabilityPotion;
import logic.GameLogic;

/**
 * Item class
 */
public abstract class Item {
	/**
	  * Amount of this item
	  */
	private int amount;
	/**
	  * Item's name
	  */
	private String name;
	/**
	  * Item's text
	  */
	private String text;
	/**
	  * Item's image path
	  */
	private String imagePath;
	
	/**
	  * Constructor of item
	  * @param name name of item
	  * @param text text of item
	  * @param amount amount of item
	  * @param imagePath image path of item
	  */
	public Item(String name,String text,int amount,String imagePath) {
		this.setAmount(amount);
		this.setName(name);
		this.setText(text);
		this.setImagePath(imagePath);
	}

	/**
	  * Check whether item is usable 
	  */
	public boolean active() {
		if(amount <= 0) {
			return false;
		}
		this.selectTarget();
		this.setAmount(this.getAmount()-1);
		//System.out.println(getAmount());
		return true;
	}
	
	/**
	  * Choose target
	  */
	public abstract void selectTarget() ;
	
	
	/**
	  * Getter of amount
	  * @return amount
	  */
	public int getAmount() {
		return amount;
	}

	/**
	  * Setter of amount
	  * @param amount amount to set
	  */
	public void setAmount(int amount) {
		if(amount <= 0) {
			GameLogic.getInstance().getInventory().remove(this);
			this.amount = 0;
			return;
		}
		
		this.amount = amount;
	}
	
	/**
	  * Getter of name
	  * @return name
	  */
	public String getName() {
		return name;
	}
	
	/**
	  * Setter of name
	  * @param name name to set
	  */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	  * Getter of text
	  * @return text
	  * 
	  */
	public String getText() {
		return text;
	}
	
	/**
	  * Setter of text
	  * @param text text to set
	  */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	  * Check equality for item class
	  * @param o other item to check equal 
	  */
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
		if( this instanceof VulnerabilityPotion ) {
			return ((VulnerabilityPotion)this).getBuff().equals(((VulnerabilityPotion)o).getBuff());
		}
		
		return false;
	}
	
	/**
	  * To string function
	  * @return string form of this class
	  */
	public String toString() {
		return this.getName() + " x " + this.getAmount();
	}
	
	/**
	  * Getter of image path
	  * @return image path
	  */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	  * Setter of image path
	  * @param imagePath image path of this item
	  */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
