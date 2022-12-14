package buff.type;



import org.junit.runners.ParentRunner;

import entity.base.Buff;
import entity.base.Unit;


/**
 * Regeneration Class.
 */
public class Regeneration extends Buff{

	/**
	 * Constructor of Regeneration
	 * @param ratio ratio of this buff
	 */
	public Regeneration(int ratio) {
		super(0, ratio);
	}
	/**
	 * Copy constructor of Regeneration
	 * @param r parent buff
	 */
	public Regeneration(Regeneration r) {
		this(r.getRatio());
	}
	
	@Override
	/**
	 * active buff
	 * @param unit unit that have this buff
	 */
	public void activeBuff(Unit unit) {
		unit.receiveHeal( (this.getRatio()*unit.getMaxHealth())/100  );
		
	}

	@Override
	/**
	 * remove this from arraylist of buff in unit
	 * @param unit unit that have this buff
	 */
	public void removeSelf(Unit unit) {
		
		
	}

}
