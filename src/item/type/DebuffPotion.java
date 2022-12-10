package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;

public abstract class DebuffPotion extends Item {

	public DebuffPotion(String name, String text, int amount) {
		super(name, text, amount);
		
	}
	
	@Override
	public void selectTarget() {
		int index = GameLogic.getInstance().getTargetedMonster();
		Unit monsterUnit = GameLogic.getInstance().getMonsters().get(index);
		this.activeEffect(monsterUnit);
	}
	public abstract void activeEffect(Unit u);
	
}
