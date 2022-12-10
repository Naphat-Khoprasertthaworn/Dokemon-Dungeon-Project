package buff.type;

import entity.base.Buff;
import entity.base.Unit;

public class Regeneration extends Buff {

	public Regeneration(int ratio) {
		super(0, ratio);
	}

	@Override
	public void activeBuff(Unit unit) {
		unit.receiveHeal( (this.getRatio()*unit.getMaxHealth())/100  );
		
	}

	@Override
	public void removeSelf(Unit unit) {
		
		
	}

}
