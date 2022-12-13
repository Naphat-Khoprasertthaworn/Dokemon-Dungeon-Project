package component;

import org.junit.Ignore;

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
	private Background bg;
	private Image img;
	
	public SkillCard() {
		name = new Text();
		name.setFill(Color.WHITE);
		textSkill = new Text();
		textSkill.setWrappingWidth(80);
		textSkill.setFill(Color.WHITE);
		String imgPath = ClassLoader.getSystemResource( blankSkillURL ).toString();
		
		//img = new ImageView( new Image(imgPath) );
		img = new Image(imgPath);
//		img.setPreserveRatio(true);
//		img.setFitHeight(160);
		
		BackgroundImage bgImg = new BackgroundImage(img,BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		
//		bg = new Background(bgImg);
//		bg.
//		
//		this.setStyle("-fx-back");
//		this.setBackground(bg);
		//this.getChildren().add( bg );
		
		
		//img.setFitWidth(85);
		this.setBackground(bg);
		this.getChildren().add(name);
		this.getChildren().add(textSkill);

		this.maxHeight(160);
		this.maxWidth(85);
		//this.setW
		this.setAlignment(Pos.CENTER);
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				onClickHandler();
			}
		});
		
		this.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				enteredHandler();
			}
		});
		this.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				exitedHandler();
			}
		});

		
	}
	
	public void enteredHandler() {
		//System.out.println("enter work");
	}
	
	public void exitedHandler() {
		//System.out.println("exit work");
	}
	
	public void updateSkillCard(Skill s) {
		this.skill = s;
		this.name.setText(s.getName() + " " + s.getInCombatCd());
		this.textSkill.setText(s.getText());
		String imgPath = ClassLoader.getSystemResource( s.getImagePath() ).toString();
		BackgroundImage bgImg = new BackgroundImage(new Image(imgPath),BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		this.setBackground(new Background(bgImg));
		//img.setImage(new Image(imgPath));
	}
	
	public void onClickHandler() {
		if(!GameLogic.getInstance().isGameActive) {
			System.out.println("game END");
			return;
		}
		
		if(this.skill == null) {
			return;
		}
		if(this.skill.readySkill()==false) {
			System.out.println("i am cd");
		}else {
			GameLogic.getInstance().getCurrentHero().useSkill(this.skill);
			GameLogic.getInstance().updateTargetPointer();
			GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatDisplay();
			GameLogic.getInstance().heroAction();
		
			//System.out.println( GameLogic.getInstance().getCurrentHero() );
		
			GameLogic.getInstance().getCombatController().getSkillPane().updateState();
			GameLogic.getInstance().getCombatController().getCombatDisplay().updatePointer();
		}

		
	}
	
	
	
}
