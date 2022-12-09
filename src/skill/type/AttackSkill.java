package skill.type;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import logic.GameLogic;

public abstract class AttackSkill extends Skill {

	public AttackSkill(String name, String text, int ratio, int cd) {
		super(name, text, ratio, cd);
		
	}
	
	public boolean skillActive(Unit owner) {
		
		if( this.getInCombatCd() != 0 ) {
			return false;
		}
		this.skillEffect(GameLogic.getInstance().findParty(owner, false), GameLogic.getInstance().findTarget(owner, false),owner);
		this.setInCombatCd(getCd());
		return true;
	};
	
	public abstract void skillEffect(ArrayList<Unit> units, int targetUnit,Unit owner);
	
}
