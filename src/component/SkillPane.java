package component;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logic.GameLogic;

public class SkillPane extends HBox {
	
	private ArrayList<Skill> skills;
	private SkillCard[] skillPanes;
	static final int MAX_SKILL = 3;
	
	public SkillPane() {
		skills = new ArrayList<Skill>();
		this.skillPanes =  new SkillCard[3];
		
		for(int i=0;i<MAX_SKILL;i++) {
			SkillCard skillCard = new SkillCard();
			
			skillPanes[i] = skillCard;
			this.getChildren().add(skillCard);
		}
		this.setSpacing(20);
		this.setPrefHeight(230);
		this.setPrefWidth(640);
		//this.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

	}
	
	public void updateState() {
		skills = GameLogic.getInstance().getCurrentHero().getSkills();
		for(int i = 0;i<skills.size();i++) {
			skillPanes[i].updateSkillCard( skills.get(i) );
		}
	}
}
