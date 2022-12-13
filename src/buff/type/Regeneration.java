package buff.type;



import org.junit.runners.ParentRunner;

import entity.base.Buff;
import entity.base.Unit;

public class Regeneration extends Buff{


	public Regeneration(int ratio) {
		super(0, ratio);
	}
	
	public Regeneration(Regeneration r) {
		this(r.getRatio());
	}
	
	@Override
	public void activeBuff(Unit unit) {
		unit.receiveHeal( (this.getRatio()*unit.getMaxHealth())/100  );
		
	}

	@Override
	public void removeSelf(Unit unit) {
		
		
	}

}
