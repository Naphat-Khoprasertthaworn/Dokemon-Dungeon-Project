package skill.type;

import java.util.ArrayList;

import entity.base.Buff;
import entity.base.Monster;
import entity.base.Unit;
import logic.GameLogic;

public class SingleTargetDefenceSkill extends DefenceSkill {
	
	public SingleTargetDefenceSkill(String name, String text, int ratio, int cd) {
		super(name, text, ratio, cd);

	}

	@Override
	public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
		
		Unit unit;
		if(owner instanceof Monster) {
			unit = GameLogic.getInstance().getLowestHealthUnit(units);
		}else {
			unit = GameLogic.getInstance().getUnitByPosition(targetUnit, units);
		}
		unit.receiveHeal( (this.getRatio()*owner.getTotalAttack())/100 );
		for(Buff b: this.getBuffsTarget()) {
			unit.addBuff(b);
		}
		for(Buff b:this.getBuffsSelf()) {
			owner.addBuff(b);
		}
	}
	

}
