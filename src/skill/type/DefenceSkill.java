package skill.type;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import logic.GameLogic;

public abstract class DefenceSkill extends Skill {
	
	public DefenceSkill(String name, String text, int ratio, int cd) {
		super(name, text, ratio, cd);
	}


	public boolean skillActive(Unit userUnit) {
		if( this.getInCombatCd() != 0 ) {
			return false;
		}
		if(userUnit == null) {
			return false;
		}
		this.skillEffect(GameLogic.getInstance().findParty(userUnit, true), GameLogic.getInstance().findTarget(userUnit, true),userUnit);
		this.setInCombatCd(getCd());
		return true;
	};
	
	public abstract void skillEffect(ArrayList<Unit> units, int targetUnit,Unit owner);
	

}
