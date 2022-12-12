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
		
		HBox herosCardBox = new HBox();
		HBox monsterCardBox = new HBox();
		String imgPath = ClassLoader.getSystemResource(backGroundImageURL).toString();
	
		herosCardBox.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		monsterCardBox.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));

		herosCardBox.setPrefWidth( 500);
		herosCardBox.setPrefHeight(200);
		monsterCardBox.setPrefWidth(500);
		monsterCardBox.setPrefHeight(200);
		
		BackgroundImage bgImg = new BackgroundImage(combatBackground,BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		//GameLogic.getInstance().generateMonsters();
		ArrayList<Unit> heros = GameLogic.getInstance().getHeros();
		
		ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
		System.out.println(heros);
		System.out.println(monsters);
		for( int i = heros.size()-1;i>=0;i--) {
			UnitCard heroCard = new UnitCard();
			heroCard.update(heros.get(i));
			herosCardBox.getChildren().add(heroCard);
			
		}
		for(int i = 0;i<monsters.size();i++) {
			UnitCard monsterCard = new UnitCard();
			monsterCard.update(monsters.get(i));
			monsterCardBox.getChildren().add(monsterCard);
		}
		//this.setBackground(new Background(bgImg));
		
		herosCardBox.setAlignment(Pos.BOTTOM_LEFT);
		monsterCardBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		//this.setAlignment();
		this.getChildren().add(herosCardBox);
		this.getChildren().add(monsterCardBox);
		
	}

}
