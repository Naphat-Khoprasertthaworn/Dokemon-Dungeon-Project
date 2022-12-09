package item.type;

import buff.type.Enhance;
import entity.base.Unit;

public class EnhancePotion extends BuffPotion{
	
	private Enhance enhanceBuff;
	
	public Enhance getBuff() {
		return enhanceBuff;
	}

	public EnhancePotion(int amount,int turn,int ratio) {
		super("Enhance Potion", "give enhance buff to a hero", amount);
		enhanceBuff = new Enhance(turn, ratio);
	}

	@Override
	public void activeEffect(Unit u) {
		u.addBuff(this.enhanceBuff);
	}

}
