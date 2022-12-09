package buff.type;

import entity.base.Buff;
import entity.base.Unit;

public class Vulnetability extends Buff {
	
	private int decreasingDefense;
	
	public Vulnetability(int turn, int ratio) {
		super(turn, ratio);
		super.setName("Vulnetability");
	}

	@Override
	public void activeBuff(Unit unit) {
		this.setDecreasingDefense(unit.getDefense());
		unit.setBuffDefense( unit.getBuffDefense() - this.getDecreasingDefense() );
		
	}

	@Override
	public void removeSelf(Unit unit) {
		unit.setBuffDefense( unit.getBuffDefense() + this.getDecreasingDefense() );
		
	}

	public int getDecreasingDefense() {
		return decreasingDefense;
	}

	public void setDecreasingDefense(int baseDefense) {
		this.decreasingDefense = (baseDefense*this.getRatio()) / 100;
	}

}
