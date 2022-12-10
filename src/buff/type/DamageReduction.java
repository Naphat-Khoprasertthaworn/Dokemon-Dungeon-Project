package buff.type;


import entity.base.Buff;
import entity.base.Unit;

public class DamageReduction extends Buff{
	private int increasingDefense;
	
	public DamageReduction(int turn, int ratio) {
		super(turn, ratio);
		super.setName("DamageReduction");
		
	}
	
	public DamageReduction(DamageReduction buff) {
		this(buff.getTurn(), buff.getRatio());
	}

	@Override
	public void activeBuff(Unit unit) {
		this.setIncreasingDefense(unit.getDefense());
		unit.setBuffDefense( unit.getBuffDefense() + this.getIncreasingDefense() );
		
	}

	@Override
	public void removeSelf(Unit unit) {
		unit.setBuffDefense( unit.getBuffDefense() - this.getIncreasingDefense() );
		
	}

	public int getIncreasingDefense() {
		return increasingDefense;
	}

	public void setIncreasingDefense(int baseDefense) {
		this.increasingDefense = (baseDefense*this.getRatio()) / 100;
	}

}
