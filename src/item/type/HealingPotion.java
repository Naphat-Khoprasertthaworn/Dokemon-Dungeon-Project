package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;

public class HealingPotion extends BuffPotion {
	
	private int healingRatio;
	
	public HealingPotion(int amount,int ratio) {
		super("Healing Potion", "I can heal a unit", amount);
		this.setHealingRatio(ratio);
	}
	
	public HealingPotion(HealingPotion dp) {
		this(dp.getAmount(), dp.getHealingRatio());
	}
	
	@Override
	public void activeEffect(Unit heroUnit) {
		heroUnit.receiveHeal( ( this.getHealingRatio()*heroUnit.getMaxHealth() )/100  );
	}

	public int getHealingRatio() {
		return healingRatio;
	}

	public void setHealingRatio(int healingRatio) {
		if(healingRatio <  20) {
			this.healingRatio = 20;
		}
		this.healingRatio = healingRatio;
	}
	
	
}
