package skill.type;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import logic.GameLogic;

public abstract class AttackSkill extends Skill {

	public AttackSkill(String name, String text, int ratio, int cd,String imagePath) {
		super(name, text, ratio, cd,imagePath);
		
	}
	
	public boolean skillActive(Unit userUnit) {
		if( this.getInCombatCd() != 0 ) {
			return false;
		}

		if(userUnit == null) {
			return false;
		}
		this.skillEffect(GameLogic.getInstance().findParty(userUnit, false), GameLogic.getInstance().findTarget(userUnit, false),userUnit);
		this.setInCombatCd(getCd());
		return true;
	};
	
	public abstract void skillEffect(ArrayList<Unit> units, Unit targetUnit,Unit owner);
	
}
