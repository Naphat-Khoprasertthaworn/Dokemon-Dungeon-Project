package component;

import java.util.ArrayList;

import entity.base.Skill;
import entity.base.Unit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logic.GameLogic;


/**
 * SkillPane Class
 */
public class SkillPane extends HBox {
	/**
	 * ArrayList of skills
	 */
	private ArrayList<Skill> skills;
	
	/**
	 * Array of SkillCard
	 */
	private SkillCard[] skillPanes;
	
	/**
	 * max skill card that display in skill pane
	 */
	static final int MAX_SKILL = 3;
	
	/**
	 * Constructor of SkillPane
	 */
	public SkillPane() {
		this.setPrefHeight(230);
		this.setPrefWidth(640);
		//this.setAlignment(Pos.CENTER);
		
		this.setSpacing(20);
		skills = new ArrayList<Skill>();
		this.skillPanes =  new SkillCard[MAX_SKILL];
		
		for(int i=0;i<MAX_SKILL;i++) {
			SkillCard skillCard = new SkillCard();
			
			skillPanes[i] = skillCard;
			this.setMargin( skillCard,new Insets(20,0,20,0) );
			this.getChildren().add(skillCard);
		}
		this.setAlignment(Pos.CENTER);
		//this.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

	}
	/**
	 * update SkillPane by call updateSkillCard all SkillCard in SkillPane.
	 */
	public void updateState() {
		skills = GameLogic.getInstance().getCurrentHero().getSkills();
		for(int i = 0;i<skills.size();i++) {
			skillPanes[i].updateSkillCard( skills.get(i) );
		}
	}
	
}
