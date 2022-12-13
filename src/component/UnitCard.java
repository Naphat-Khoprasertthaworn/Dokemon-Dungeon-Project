package component;



import java.util.ArrayList;
import java.util.Random;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Vulnetability;
import entity.base.Buff;
import entity.base.Monster;
import entity.base.Unit;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import logic.GameLogic;
import javafx.application.Platform;
import javafx.event.EventHandler;

public class UnitCard extends VBox {
	
	private final String pointerImageURL = "image/pointer.png";
	private final String blankUnitURL = "image/blank.png";
	private final String damageReductionBuffURL = "image/damageReductionBuffURL.png";
	private final String enhanceBuffURL = "image/enhanceBuffURL.png";
	private final String exhaustBuffURL = "image/exhaustBuffURL.png";
	private final String vulnetabilityBuffURL = "image/vulnetabilityBuffURL.png";
	private final String deadURL = "image/deadURL.png";
	
	private Unit unit;
	private ProgressBar healthBar;
	private ImageView unitImage;
	//Image pointerImage = new Image(ClassLoader.getSystemResource("image/pointer.png").toString(), 20,20, false, true);
	Image blankImage = new Image(ClassLoader.getSystemResource("image/overwatch-logo.png").toString(), 50,50, false, true);
	
	private ImageView pointerImageView;
	
	private ImageView damageReductionBuff,enhanceBuff,exhaustBuff,vulnetabilityBuff,deadBuff;
	
	public UnitCard() {
		
		unitImage = new ImageView(new Image(blankUnitURL));
		//unitImage.setOpacity(0);
		unitImage.setFitHeight(200);
		unitImage.setFitWidth(170);
		
		pointerImageView = new ImageView(pointerImageURL);
		pointerImageView.setVisible(false);
		
		pointerImageView.setFitHeight(50);
		pointerImageView.setFitWidth(50);
		healthBar = new ProgressBar(100);
		healthBar.setPrefHeight(10);
		healthBar.setPrefWidth(100);
		
		
		
		
		damageReductionBuff = new ImageView( new Image(ClassLoader.getSystemResource(damageReductionBuffURL).toString()) );
		enhanceBuff = new ImageView( new Image(ClassLoader.getSystemResource(enhanceBuffURL).toString()) );
		exhaustBuff = new ImageView( new Image(ClassLoader.getSystemResource(exhaustBuffURL).toString() ));
		vulnetabilityBuff = new ImageView( new Image(ClassLoader.getSystemResource(vulnetabilityBuffURL).toString()) );
		deadBuff = new ImageView( new Image(ClassLoader.getSystemResource(deadURL).toString()) );
		
		damageReductionBuff.setFitHeight(20);
		enhanceBuff.setFitHeight(20);
		exhaustBuff.setFitHeight(20);
		vulnetabilityBuff.setFitHeight(20);
		deadBuff.setFitHeight(20);
		
		damageReductionBuff.setFitWidth(20);
		enhanceBuff.setFitWidth(20);
		exhaustBuff.setFitWidth(20);
		vulnetabilityBuff.setFitWidth(20);
		deadBuff.setFitWidth(20);
		
		HBox buffBox = new HBox();
		buffBox.getChildren().addAll(deadBuff,damageReductionBuff,enhanceBuff,exhaustBuff,vulnetabilityBuff);
		//buffBox.setPrefHeight(10);
		
		
		this.getChildren().add(pointerImageView);
		this.getChildren().add(buffBox);
		this.getChildren().add(unitImage);
		this.getChildren().add(healthBar);
		this.setAlignment(Pos.BOTTOM_CENTER);
		
		
		
		//pointerImageView.setA
		//healthBar.ali
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				onClickHandler();
			}
		});
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		this.resetBuffBox();
	}
	
	public void update(Unit u) {
		
		this.unit = u;

		String imgPath = ClassLoader.getSystemResource(u.getImagePath()).toString();
		
		unitImage.setImage(new Image(imgPath)); 
		
		
	}
	
	public void resetBuffBox() {
		this.damageReductionBuff.setVisible(false);
		this.enhanceBuff.setVisible(false);
		this.exhaustBuff.setVisible(false);
		this.vulnetabilityBuff.setVisible(false);
		this.deadBuff.setVisible(false);
	}
	
	public void updateHealthBar() {
		if(unit == null) {
			return;
		}
		
		this.resetBuffBox();
		
		ArrayList<Buff> buffs = unit.getBuffs();
		for(Buff b: buffs ) {
			if(b instanceof DamageReduction) {
				this.damageReductionBuff.setVisible(true);
			}else if(b instanceof Enhance) {
				this.enhanceBuff.setVisible(true);
			}else if(b instanceof Exhaust) {
				this.exhaustBuff.setVisible(true);
			}else if(b instanceof Vulnetability) {
				this.vulnetabilityBuff.setVisible(true);
			}else {
				
			}
		}
		
		if(!unit.isAlive()) {
			this.deadBuff.setVisible(true);
		}else {
			this.deadBuff.setVisible(false);
		}
		
		healthBar.setProgress( ((float)unit.getHealth()) /(float)unit.getMaxHealth() );
	}
	
	
	
	public void onClickHandler() {
		if(unit instanceof Monster) {
			//System.out.println(this.unit);
			GameLogic.getInstance().setTargetedMonster(this.unit);
			//System.out.println( GameLogic.getInstance().getTargetedMonster() );
			//System.out.println(this.unit.isTargeted());
		}else if(unit instanceof Unit) {
			GameLogic.getInstance().setTargetedHero(this.unit);
		}
		GameLogic.getInstance().getCombatController().getCombatDisplay().updatePointer();
		this.attackAnimation();
		//this.gotDamagedAnimation();
	}
	

	
	protected void attackAnimation() {
		
		Random random = new Random();
		Thread thread = new Thread() {
			public void run () {
				System.out.println("Thread Running");
				TranslateTransition translate = new TranslateTransition() ;
				translate.setNode(unitImage);
				translate.setDuration(Duration.millis(20));
				translate.setCycleCount(4);
				translate.setByX(20);
				translate.setAutoReverse(true);
				translate.play();		
			}
		};
		thread.start();

	}
	
	public void gotDamagedAnimation() {
		//unitImage.setVisible(false);
		float round = 11 ;
		round = (float) (((round+0.5)*2)/2) ;
		round = (int)round ;
		int newRound = (int) round ;
		//boolean blink = false ;
		TranslateTransition translate = new TranslateTransition() ;
		translate.setNode(unitImage);
		translate.setDuration(Duration.millis(30));
		translate.setCycleCount(8);
		translate.setByY(20);
		translate.setAutoReverse(true);
		translate.play();	

//		RotateTransition rotateTransition = new RotateTransition() ;
//		rotateTransition.setNode(unitImage);
//		rotateTransition.setDuration(Duration.millis(100));
//		rotateTransition.setCycleCount(2);
//		//rotateTransition.setFromAngle(90);
//		rotateTransition.set
//		rotateTransition.setByAngle(90);
//		rotateTransition.setAutoReverse(true);
//	
//		rotateTransition.play();
		
		Random random = new Random();
		Thread thread = new Thread() {
			public void run () {
				System.out.println("Thread Running");
				try {
					for (int i = 0 ;  i < newRound ; i ++) {
						System.out.println(i);
						unitImage.setVisible(i%2 == 0);
						Thread.sleep(50); 
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
		//unitImage.setVisible(true) ;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public ImageView getPointerImageView() {
		return pointerImageView;
	}

	public void setPointerImageView(ImageView pointerImageView) {
		this.pointerImageView = pointerImageView;
	}
}
