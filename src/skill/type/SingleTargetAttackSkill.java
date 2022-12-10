package skill.type;

import java.util.ArrayList;

import entity.base.Buff;
import entity.base.Monster;
import entity.base.Unit;
import logic.GameLogic;

public class SingleTargetAttackSkill extends AttackSkill {
	
	private boolean target;
	
	public SingleTargetAttackSkill(String name, String text, int ratio, int cd , boolean target) {
		super(name, text, ratio, cd);
		this.target = target;
	}

	@Override
	public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
		Unit unit;
		if(this.target) {
			if(owner instanceof Monster) {
				unit = GameLogic.getInstance().getBackLineUnit(units);
			}else {
				unit = GameLogic.getInstance().getUnitByPosition(targetUnit, units);
			}
			
		}else {
			unit = GameLogic.getInstance().getFrontLineUnit(units);
		}
		unit.takeDamage( (this.getRatio()*owner.getTotalAttack())/100 );
		for(Buff b:this.getBuffsSelf()) {
			owner.addBuff(b);
		}
		for(Buff b: this.getBuffsTarget()) {
			unit.addBuff(b);
		}

		
	}



}
