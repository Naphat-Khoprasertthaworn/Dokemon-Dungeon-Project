package skill.type;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;

public abstract class AttackSkill extends Skill {

	public AttackSkill(String name, String text, int ratio, int cd) {
		super(name, text, ratio, cd);
		// TODO Auto-generated constructor stub
	}


	@Override
	public abstract void skillActive(ArrayList<Unit> units, Unit targetUnit,Unit owner);

}
