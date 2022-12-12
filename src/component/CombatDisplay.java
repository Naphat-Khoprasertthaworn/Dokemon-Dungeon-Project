package component;

import java.util.ArrayList;

import entity.base.Unit;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import logic.GameLogic;

public class CombatDisplay extends HBox {
	
	private final String backGroundImageURL = "combatBackGround.png";
	
	public CombatDisplay(){
		
		HBox herosCardBox = new HBox();
		HBox monsterCardBox = new HBox();
		String imgPath = ClassLoader.getSystemResource(backGroundImageURL).toString();
		
		BackgroundImage bgImg = new BackgroundImage(new Image(imgPath),BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		
		ArrayList<Unit> heros = GameLogic.getInstance().getHeros();
		ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
		
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
		this.setBackground(new Background(bgImg));
		this.getChildren().add(herosCardBox);
		this.getChildren().add(monsterCardBox);
		
	}

}
