package buff.type;


import entity.base.Buff;
import entity.base.Unit;


/**
 * Enhance Class.
 */
public class Enhance extends Buff{
	
	/**
	 * increasing attack
	 */
	private int increasingAttack;
	
	/**
	 * Constructor of Enhance
	 * @param turn number of turn this buff active
	 * @param ratio ratio of this buff
	 */
	public Enhance(int turn,int ratio) {
		super(turn, ratio);
		super.setName("Enhance");
	}
	/**
	 * Copy constructor of Enhance
	 * @param e parent buff
	 */
	public Enhance(Enhance e) {
		this(e.getTurn(), e.getRatio());
	}
	
	@Override
	/**
	 * active buff
	 * @param unit unit that have this buff
	 */
	public void activeBuff(Unit unit) {
		
		this.setIncreasingAttack(unit.getAttack());
		unit.setBuffAttack( unit.getBuffAttack() + this.getIncreasingAttack() );
		
	}
	
	@Override
	/**
	 * remove this from arraylist of buff in unit
	 * @param unit unit that have this buff
	 */
	public void removeSelf(Unit unit) {
		unit.setBuffAttack( unit.getBuffAttack() - this.getIncreasingAttack() );
	}
	
	/**
	 * getter of increasing attack
	 * @return increasingAttack increasing attack
	 */
	public int getIncreasingAttack() {
		return increasingAttack;
	}
	/**
	 * setter of increasing attack.
	 * @param baseAttack base attack of unit.
	 */
	public void setIncreasingAttack(int baseAttack) {
		this.increasingAttack = ( baseAttack*this.getRatio() )/100 ;
	}
	
	
}
