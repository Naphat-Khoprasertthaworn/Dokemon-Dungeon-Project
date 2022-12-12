package component;

import entity.base.Skill;
import gui.CombatController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.GameLogic;

public class SkillCard extends VBox{
	private Skill skill;
	private Text name;
	private Text textSkill;
	private final String blankSkillURL = "image/overwatch-logo.png";
	
	public SkillCard() {
		name = new Text();
		name.setFill(Color.WHITE);
		textSkill = new Text();
		textSkill.setWrappingWidth(80);
		textSkill.setFill(Color.WHITE);
		String imgPath = ClassLoader.getSystemResource( blankSkillURL ).toString();
		BackgroundImage bgImg = new BackgroundImage(new Image(imgPath),BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		this.setBackground(new Background(bgImg));
		this.getChildren().add(name);
		this.getChildren().add(textSkill);
		this.prefHeight(160);
		this.prefWidth(85);
		this.setAlignment(Pos.CENTER);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				//System.out.println("it work");
				onClickHandler();
			}
		});
	}
	
	public void updateSkillCard(Skill s) {
		this.skill = s;
		this.name.setText(s.getName());
		this.textSkill.setText(s.getText());
		String imgPath = ClassLoader.getSystemResource( s.getImagePath() ).toString();
		BackgroundImage bgImg = new BackgroundImage(new Image(imgPath),BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		this.setBackground(new Background(bgImg));
	}
	
	public void onClickHandler() {
		if(this.skill == null) {
			System.out.println("this");
			return;
		}
		System.out.println("ot this");
		GameLogic.getInstance().getCurrentHero().useSkill(this.skill);
		GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatDisplay();
		GameLogic.getInstance().heroAction();
		
		System.out.println( GameLogic.getInstance().getCurrentHero() );
		
		GameLogic.getInstance().getCombatController().getSkillPane().updateState();
		
	}
	
	
	
}
