package item.type;

import buff.type.DamageReduction;
import entity.base.Unit;

public class DamageReductionPotion extends BuffPotion{
	
	private DamageReduction dmgRedBuff;
	
	public DamageReduction getBuff() {
		return dmgRedBuff;
	}

	public DamageReductionPotion(int amount,int turn,int ratio) {
		super("Damage Reduction", "give dmg reduction to hero", amount);
		dmgRedBuff = new DamageReduction(turn, ratio);
	}
	
	public DamageReductionPotion(DamageReductionPotion dp) {
		this(dp.getAmount(), dp.getBuff().getTurn(), dp.getBuff().getRatio());
	}

	@Override
	public void activeEffect(Unit u) {
		u.addBuff(dmgRedBuff);
	}
	
}
