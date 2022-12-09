package buff.type;

import entity.base.Buff;
import entity.base.Unit;

public class Exhaust extends Buff{
	
	private int decreasingAttack;
	
	public Exhaust(int turn, int ratio) {
		super(turn, ratio);
		super.setName("Exhaust");
	}

	

	@Override
	public void activeBuff(Unit unit) {
		this.setDecreasingAttack(unit.getAttack());
		unit.setBuffAttack( unit.getBuffAttack() - this.getDecreasingAttack() );
		
	}

	public int getDecreasingAttack() {
		return decreasingAttack;
	}

	public void setDecreasingAttack(int baseAttack) {
		this.decreasingAttack = ( baseAttack*this.getRatio() )/100;
	}

	@Override
	public void removeSelf(Unit unit) {
		unit.setBuffAttack( unit.getBuffAttack() + this.getDecreasingAttack() );
	}

}
