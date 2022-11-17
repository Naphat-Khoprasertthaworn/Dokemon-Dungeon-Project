package buff.type;

import entity.base.Buff;
import entity.base.Unit;

public class Enhance extends Buff{

	private int increasingAttack;
	
	public Enhance(int turn,int ratio) {
		super(turn, ratio);
	}

	@Override
	public void activeBuff(Unit unit) {
		
		this.setIncreasingAttack(unit.getAttack());
		unit.setBuffAttack( unit.getBuffAttack() + this.getIncreasingAttack() );
		
	}
	
	public void removeSelf(Unit unit) {
		unit.setBuffAttack( unit.getBuffAttack() - this.getIncreasingAttack() );
	}

	public int getIncreasingAttack() {
		return increasingAttack;
	}

	public void setIncreasingAttack(int baseAttack) {
		this.increasingAttack = ( baseAttack*this.getRatio() )/100 ;
	}
}
