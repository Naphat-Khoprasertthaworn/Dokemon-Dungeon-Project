package component;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import javafx.scene.layout.HBox;
import logic.GameLogic;

public class SkillPane extends HBox {
	
	private ArrayList<Skill> skills;
	private SkillCard[] skillPanes;
	static final int MAX_SKILL = 3;
	
	public SkillPane() {
		skills = new ArrayList<Skill>();
		for(int i=0;i<MAX_SKILL;i++) {
			SkillCard skillCard = new SkillCard();
			skillPanes[i] = skillCard;
			this.getChildren().add(skillCard);
		}
	}
	
	public void updateState() {
		skills = GameLogic.getInstance().getCurrentHero().getSkills();
		for(int i = 0;i<skills.size();i++) {
			skillPanes[i].updateSkillCard( skills.get(i) );
		}
	}
}
