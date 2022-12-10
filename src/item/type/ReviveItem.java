package item.type;

import entity.base.Item;
import entity.base.Unit;

public class ReviveItem extends BuffPotion{

	public ReviveItem(int amount) {
		super("Revive Item","can revive hero with half hp", amount);
		
	}

	@Override
	public void activeEffect(Unit u) {
		
		
	}

}
