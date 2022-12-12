package component;

import java.util.ArrayList;

import entity.base.Unit;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import logic.GameLogic;

public class CombatDisplay extends HBox {
	
	private String backGroundImageURL = "image/combatBackGround.png";
	
	Image combatBackground = new Image(ClassLoader.getSystemResource("image/combatBackground.png").toString(), 1280,460, false, true);
	
	
	public CombatDisplay(){
		
		HBox heroesCardBox = new HBox();
		heroesCardBox.setPrefHeight(460);
		heroesCardBox.setPrefWidth(640);
		heroesCardBox.setAlignment(Pos.CENTER);
		HBox monsterCardBox = new HBox();
		monsterCardBox.setPrefHeight(460);
		monsterCardBox.setPrefWidth(640);
		monsterCardBox.setAlignment(Pos.CENTER);
		//String imgPath = ClassLoader.getSystemResource(backGroundImageURL).toString();
		
		BackgroundImage bgImg = new BackgroundImage(combatBackground,BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		GameLogic.getInstance().generateBossStage();
		ArrayList<Unit> heroes = GameLogic.getInstance().getHeros();
		ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
		
		for( int i = heroes.size()-1;i>=0;i--) {
			UnitCard heroCard = new UnitCard();
			heroCard.update(heroes.get(i));
			heroesCardBox.getChildren().add(heroCard);
			
		}
		for(int i = 0;i<monsters.size();i++) {
			UnitCard monsterCard = new UnitCard();
			monsterCard.update(monsters.get(i));
			monsterCardBox.getChildren().add(monsterCard);
		}
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(bgImg));
		this.getChildren().add(heroesCardBox);
		this.getChildren().add(monsterCardBox);
		
	}

}
