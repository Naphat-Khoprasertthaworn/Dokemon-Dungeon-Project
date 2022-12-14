package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;

/**
 * Debuff potion class
 */ 
public abstract class DebuffPotion extends Item {
	
	
    /**
     * Constructor of debuff potion
     * @param name name
     * @param text text
     * @param amount amount 
     * @param imagePath imagePath
     */ 
	public DebuffPotion(String name, String text, int amount,String imagePath) {
		super(name, text, amount,imagePath);
		
	}
	
    /**
     * Choose hero to active buff
     */ 
	@Override
	public void selectTarget() {
		Unit monsterUnit = GameLogic.getInstance().getTargetedMonster();
		this.activeEffect(monsterUnit);
	}
    /**
     * Active buff
     * @param u unit to active buff
     */ 
	public abstract void activeEffect(Unit u);
	
}
