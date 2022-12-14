package buff.type;


import entity.base.Buff;
import entity.base.Unit;

/**
 * DamageReduction Class.
 */
public class DamageReduction extends Buff{
	
	/**
	 * increasing defense
	 */
	private int increasingDefense;

	/**
	 * Constructor of DamageReduction
	 * @param turn number of turn this buff active
	 * @param ratio ratio of this buff
	 */
	public DamageReduction(int turn, int ratio) {
		super(turn, ratio);
		super.setName("DamageReduction");
		
	}
	
	/**
	 * Copy constructor of DamageReduction
	 * @param buff parent buff
	 */
	public DamageReduction(DamageReduction buff) {
		this(buff.getTurn(), buff.getRatio());
	}

	@Override
	/**
	 * active buff
	 * @param unit unit that have this buff
	 */
	public void activeBuff(Unit unit) {
		this.setIncreasingDefense(unit.getDefense());
		unit.setBuffDefense( unit.getBuffDefense() + this.getIncreasingDefense() );
		
	}

	@Override
	/**
	 * remove this from arraylist of buff in unit
	 * @param unit unit that have this buff
	 */
	public void removeSelf(Unit unit) {
		unit.setBuffDefense( unit.getBuffDefense() - this.getIncreasingDefense() );
		
	}
	
	/**
	 * getter of increasing defense
	 * @return increasingDefence increasing defense
	 */
	public int getIncreasingDefense() {
		return increasingDefense;
	}
	
	/**
	 * setter of increasing defense.
	 * @param baseDefense base defense of unit.
	 */
	public void setIncreasingDefense(int baseDefense) {
		this.increasingDefense = (baseDefense*this.getRatio()) / 100;
	}

}
