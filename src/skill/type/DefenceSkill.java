package skill.type;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import logic.GameLogic;

public abstract class DefenceSkill extends Skill {
	public DefenceSkill(String name, String text, int ratio, int cd) {
		super(name, text, ratio, cd);
		// TODO Auto-generated constructor stub
	}


	public boolean skillActive(Unit owner) {
		if( this.getInCombatCd() != 0 ) {
			return false;
		}
		this.skillEffect(GameLogic.getInstance().findParty(owner, true), GameLogic.getInstance().findTarget(owner, true),owner);
		System.out.println(GameLogic.getInstance().findTarget(owner, true));
		//System.out.println("!!!!!!!!!!!!!!!!!!!");
		this.setInCombatCd(getCd());
		return true;
	};
	
	public abstract void skillEffect(ArrayList<Unit> units, int targetUnit,Unit owner);
	

}
