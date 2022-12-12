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
	
	private ArrayList<UnitCard> unitCards ;
	private ArrayList<UnitCard> monsterCards;
	
	Image combatBackground = new Image(ClassLoader.getSystemResource("image/combatBackground.png").toString(), 1280,460, false, true);
	
	
	public CombatDisplay(){
		
//		HBox heroesCardBox = new HBox();
//		heroesCardBox.setPrefHeight(460);
//		heroesCardBox.setPrefWidth(640);
//		heroesCardBox.setAlignment(Pos.CENTER);
//		HBox monsterCardBox = new HBox();
//		monsterCardBox.setPrefHeight(460);
//		monsterCardBox.setPrefWidth(640);
//		monsterCardBox.setAlignment(Pos.CENTER);
//		//String imgPath = ClassLoader.getSystemResource(backGroundImageURL).toString();
//		
//		BackgroundImage bgImg = new BackgroundImage(combatBackground,BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
//		GameLogic.getInstance().generateBossStage();
//		ArrayList<Unit> heroes = GameLogic.getInstance().getHeros();
//		ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
//		
//		unitCards = new ArrayList<UnitCard>();
//		
//		for( int i = heroes.size()-1;i>=0;i--) {
//			UnitCard heroCard = new UnitCard();
//			unitCards.add(heroCard);
//			heroCard.update(heroes.get(i));
//			heroesCardBox.getChildren().add(heroCard);
//		}
//		
//		for(int i = 0;i<monsters.size();i++) {
//			UnitCard monsterCard = new UnitCard();
//			unitCards.add(monsterCard);
//			monsterCard.update(monsters.get(i));
//			monsterCardBox.getChildren().add(monsterCard);
//		}
//		this.setAlignment(Pos.CENTER);
//		this.setBackground(new Background(bgImg));
//		this.getChildren().add(heroesCardBox);
//		this.getChildren().add(monsterCardBox);
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
	        //GameLogic.getInstance().generateBossStage();
	        ArrayList<Unit> heroes = GameLogic.getInstance().getHeros();
	        ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
	        unitCards = new ArrayList<UnitCard>();
	        monsterCards = new ArrayList<UnitCard>();
	        for( int i = heroes.size()-1;i>=0;i--) {
	            UnitCard heroCard = new UnitCard();
	            //heroCard.update(heroes.get(i));
	            heroesCardBox.getChildren().add(heroCard);
	            this.unitCards.add(heroCard);
	            
	        }
	        for(int i = 0;i<monsters.size();i++) {
	            UnitCard monsterCard = new UnitCard();
	            //monsterCard.update(monsters.get(i));
	            monsterCardBox.getChildren().add(monsterCard);
	            this.monsterCards.add(monsterCard);
	        }
	        this.setAlignment(Pos.CENTER);
	        this.setBackground(new Background(bgImg));
	        this.getChildren().add(heroesCardBox);
	        this.getChildren().add(monsterCardBox); 
	        updateCombatUnit();
	}
	
	public void updateCombatDisplay() {
		
		System.out.println("test5");
		for(UnitCard u:this.unitCards) {
			//System.err.println("test1");
			u.updateHealthBar();
		}
		for(UnitCard u:this.monsterCards) {
			//System.err.println("test1");
			u.updateHealthBar();
		}
	}
	
	public void updateCombatUnit() {
		ArrayList<Unit> herosArrayList = GameLogic.getInstance().getHeros();
		ArrayList<Unit> monsterArrayList = GameLogic.getInstance().getMonsters();
		for(int i = 0;i<herosArrayList.size();i++) {
			this.unitCards.get(herosArrayList.size()-1-i).update(herosArrayList.get(i) );
		}
		for(int i = 0;i<monsterArrayList.size();i++) {
			this.monsterCards.get(i).update(monsterArrayList.get(i) );
		}
	}

}
