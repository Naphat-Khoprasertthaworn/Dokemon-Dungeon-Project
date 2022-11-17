package skill.type;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;

public class AttackSkill extends Skill {

	public AttackSkill(String name, String text, int ratio, int cd, boolean isAOE) {
		super(name, text, ratio, cd, isAOE);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void skillActive(ArrayList<Unit> units, Unit targetUnit) {
		// TODO Auto-generated method stub
		
	}

}
