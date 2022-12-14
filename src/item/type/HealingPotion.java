package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;
/**
 * One class of buff potion 
 */ 
public class HealingPotion extends BuffPotion {
	
    /**
     * amount of heal for this potion
     */ 
	private int healingRatio;
	//private final String imagePath = "healingPotion.png";
	
    /**
     * Constructor of healing potion
     * @param amount amount
     * @param ratio ratio
     */ 
	public HealingPotion(int amount,int ratio) {
		super("Healing Potion", "I can heal a unit", amount,"image/healingPotion.png");
		this.setHealingRatio(ratio);
	}
	
	
    /**
     * Copy constructor 
     * @param dp dp
     */ 
	public HealingPotion(HealingPotion dp) {
		this(dp.getAmount(), dp.getHealingRatio());
	}
	
    /**
     * Active buff
     * @param heroUnit unit to heal
     */ 
	@Override
	public void activeEffect(Unit heroUnit) {
		heroUnit.receiveHeal( ( this.getHealingRatio()*heroUnit.getMaxHealth() )/100  );
	}

    /**
     * Getter for healing ratio
     * @return healing ratio
     */ 
	public int getHealingRatio() {
		return healingRatio;
	}
    /**
     * Setter for healing ratio
     * @param healingRatio healing ratio
     */ 
	public void setHealingRatio(int healingRatio) {
		if(healingRatio <  20) {
			this.healingRatio = 20;
		}
		this.healingRatio = healingRatio;
	}
	
	
}
