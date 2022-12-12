package component;

import entity.base.Skill;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.GameLogic;

public class SkillCard extends VBox{
	private Skill skill;
	private Text name;
	private Text textSkill;
	private final String blankSkillURL = "blankSkill.png";
	
	public SkillCard() {
		name = new Text();
		textSkill = new Text();
		String imgPath = ClassLoader.getSystemResource( blankSkillURL ).toString();
		BackgroundImage bgImg = new BackgroundImage(new Image(imgPath),BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		this.setBackground(new Background(bgImg));
		this.getChildren().add(name);
		this.getChildren().add(textSkill);
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				onClickHandler();
			}
		});
	}
	
	public void updateSkillCard(Skill s) {
		this.name.setText(s.getName());
		this.textSkill.setText(s.getText());
		String imgPath = ClassLoader.getSystemResource( s.getImagePath() ).toString();
		BackgroundImage bgImg = new BackgroundImage(new Image(imgPath),BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		this.setBackground(new Background(bgImg));
	}
	
	public void onClickHandler() {
		if(this.skill == null) {
			return;
		}
		GameLogic.getInstance().getCurrentHero().useSkill(this.skill);
	}
	
	
	
}
