package item.type;

import entity.base.Item;
import entity.base.Unit;
import logic.GameLogic;

public abstract class BuffPotion extends Item {

	public BuffPotion(String name, String text, int amount,String imagePath) {
		super(name, text, amount,imagePath);
	}

	@Override
	public void selectTarget() {
		Unit heroUnit = GameLogic.getInstance().getTargetedHero();
		this.activeEffect(heroUnit);
	}
	
	public abstract void activeEffect(Unit u);

}
