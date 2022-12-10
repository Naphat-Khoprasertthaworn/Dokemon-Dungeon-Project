package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;

public abstract class BuffPotion extends Item {

	public BuffPotion(String name, String text, int amount) {
		super(name, text, amount);
	}

	@Override
	public void selectTarget() {
		int index = GameLogic.getInstance().getTargetedHero();
		Unit heroUnit = GameLogic.getInstance().getHeros().get(index);
		this.activeEffect(heroUnit);
	}
	
	public abstract void activeEffect(Unit u);

}
