package item.type;

import buff.type.DamageReduction;
import entity.base.Unit;

public class DamageReductionPotion extends BuffPotion{
	
	private DamageReduction dmgRedBuff;
	
	public DamageReduction getBuff() {
		return dmgRedBuff;
	}

	public DamageReductionPotion(int amount,int turn,int ratio) {
		super("Damage Ruduction", "give dmg reduction to hero", amount);
		dmgRedBuff = new DamageReduction(turn, ratio);
	}

	@Override
	public void activeEffect(Unit u) {
		u.addBuff(dmgRedBuff);
	}
	
}
