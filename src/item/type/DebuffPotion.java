package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;

public abstract class DebuffPotion extends Item {

	public DebuffPotion(String name, String text, int amount,String imagePath) {
		super(name, text, amount,imagePath);
		
	}
	
	@Override
	public void selectTarget() {
		Unit monsterUnit = GameLogic.getInstance().getTargetedMonster();
		this.activeEffect(monsterUnit);
	}
	public abstract void activeEffect(Unit u);
	
}
