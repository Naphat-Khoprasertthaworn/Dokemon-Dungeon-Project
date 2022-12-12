package item.type;

import buff.type.Exhaust;
import entity.base.Unit;

public class ExhaustPotion extends DebuffPotion {

	private Exhaust exhaustBuff;
	//private final String imagePath = "exhaustPotion.png";
	
	public ExhaustPotion( int amount,int turn,int ratio) {
		super("Exhaust Potion", "give exhaust to a monster", amount,"exhaustPotion.png");
		exhaustBuff = new Exhaust(turn, ratio);
	}
	
	public ExhaustPotion(ExhaustPotion dp) {
		this(dp.getAmount(), dp.getBuff().getTurn(), dp.getBuff().getRatio());
	}

	@Override
	public void activeEffect(Unit u) {
		u.addBuff(exhaustBuff);
		
	}

	public Exhaust getBuff() {
		return exhaustBuff;
	}

}
