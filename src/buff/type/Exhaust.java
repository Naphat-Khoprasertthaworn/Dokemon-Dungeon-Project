package buff.type;


import entity.base.Buff;
import entity.base.Unit;

/**
 * Exhaust Class.
 */
public class Exhaust extends Buff{
	/**
	 * decreasing attack
	 */
	private int decreasingAttack;

	/**
	 * Constructor of Exhaust
	 * @param turn number of turn this buff active
	 * @param ratio ratio of this buff
	 */
	public Exhaust(int turn, int ratio) {
		super(turn, ratio);
		super.setName("Exhaust");
	}
	/**
	 * Copy constructor of Exhaust
	 * @param e parent buff
	 */
	public Exhaust(Exhaust e) {
		this(e.getTurn(), e.getRatio());
	}

	@Override
	/**
	 * active buff
	 * @param unit unit that have this buff
	 */
	public void activeBuff(Unit unit) {
		this.setDecreasingAttack(unit.getAttack());
		unit.setBuffAttack( unit.getBuffAttack() - this.getDecreasingAttack() );
		
	}

	/**
	 * getter of decreasing attack
	 * @return decreasingAttack decreasing attack
	 */
	public int getDecreasingAttack() {
		return decreasingAttack;
	}
	/**
	 * setter of decreasing attack.
	 * @param baseAttack base attack of unit.
	 */
	public void setDecreasingAttack(int baseAttack) {
		this.decreasingAttack = ( baseAttack*this.getRatio() )/100;
	}
	/**
	 * remove this from arraylist of buff in unit
	 * @param unit unit that have this buff
	 */
	@Override
	public void removeSelf(Unit unit) {
		unit.setBuffAttack( unit.getBuffAttack() + this.getDecreasingAttack() );
	}

}
