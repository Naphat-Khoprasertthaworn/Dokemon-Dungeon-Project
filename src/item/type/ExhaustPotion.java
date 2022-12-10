package item.type;

import buff.type.Exhaust;
import entity.base.Unit;

public class ExhaustPotion extends DebuffPotion {

	private Exhaust exhaustBuff;
	
	public ExhaustPotion( int amount,int turn,int ratio) {
		super("Exhaust Potion", "give exhaust to a monster", amount);
		exhaustBuff = new Exhaust(turn, ratio);
	}

	@Override
	public void activeEffect(Unit u) {
		u.addBuff(exhaustBuff);
		
	}

	public Exhaust getBuff() {
		return exhaustBuff;
	}

}
