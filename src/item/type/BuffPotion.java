package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;

/**
 * Buff potion class
 */ 
public abstract class BuffPotion extends Item {
	
    /**
     * Constructor of BuffPotion Class
     * @param name name
     * @param text text
     * @param amount amount 
     * @param imagePath image path
     */ 
	public BuffPotion(String name, String text, int amount,String imagePath) {
		super(name, text, amount,imagePath);
	}

    /**
     * Choose hero to acive buff
     */ 
	@Override
	public void selectTarget() {
		Unit heroUnit = GameLogic.getInstance().getTargetedHero();
		this.activeEffect(heroUnit);
	}
	
    /**
     * Active buff to targeted hero
     * @param u unit to active buff
     */ 
	public abstract void activeEffect(Unit u);

}
