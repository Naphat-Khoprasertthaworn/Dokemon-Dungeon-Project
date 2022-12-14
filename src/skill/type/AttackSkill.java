package skill.type;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import logic.GameLogic;

/**
 * AttackSkill Class
 */
public abstract class AttackSkill extends Skill {
	
	/**
	  * Constructor of attack skill class
	  * @param name name
	  * @param text text
	  * @param ratio ratio
	  * @param cd cooldown
	  * @param imagePath path of skill image. 
	  */
	public AttackSkill(String name, String text, int ratio, int cd,String imagePath) {
		super(name, text, ratio, cd,imagePath);
		
	}
	/**
	  * find target unit and target unit party then call skill effect method.
	  * @param userUnit unit that call this skill.
	  * @return isSkillCanActive
	  */
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
	
	/**
	  * active skill
	  * @param units targeted party of unit;
	  * @param targetUnit targeted unit.
	  * @param owner unit that use skill.
	  */
	public abstract void skillEffect(ArrayList<Unit> units, Unit targetUnit,Unit owner);
	
}
