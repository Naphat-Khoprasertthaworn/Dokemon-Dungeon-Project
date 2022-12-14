package component;



import java.util.ArrayList;
import java.util.Random;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Vulnerability;
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
import sound.SoundManager;
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
		healthBar.setVisible(false);
		damageReductionBuff = new ImageView( new Image(ClassLoader.getSystemResource(damageReductionBuffURL).toString()) );
		enhanceBuff = new ImageView( new Image(ClassLoader.getSystemResource(enhanceBuffURL).toString()) );
		exhaustBuff = new ImageView( new Image(ClassLoader.getSystemResource(exhaustBuffURL).toString() ));
		vulnetabilityBuff = new ImageView( new Image(ClassLoader.getSystemResource(vulnetabilityBuffURL).toString()) );
		deadBuff = new ImageView( new Image(ClassLoader.getSystemResource(deadURL).toString()) );
		
		damageReductionBuff.setFitHeight(30);
		enhanceBuff.setFitHeight(30);
		exhaustBuff.setFitHeight(30);
		vulnetabilityBuff.setFitHeight(30);
		deadBuff.setFitHeight(30);
		
		damageReductionBuff.setFitWidth(30);
		enhanceBuff.setFitWidth(30);
		exhaustBuff.setFitWidth(30);
		vulnetabilityBuff.setFitWidth(30);
		deadBuff.setFitWidth(30);
		
		HBox buffBox = new HBox();
		buffBox.getChildren().addAll(deadBuff,damageReductionBuff,enhanceBuff,exhaustBuff,vulnetabilityBuff);
		buffBox.setAlignment(Pos.CENTER);
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
		//this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
		this.resetBuffBox();
	}
	
	public void update(Unit u) {
		healthBar.setVisible(true);
		this.unit = u;
		this.healthBar.setVisible(true);
		if(u instanceof Monster) {
			this.healthBar.setStyle( "-fx-accent:red" );
		}
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
			}else if(b instanceof Vulnerability) {
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
			GameLogic.getInstance().setTargetedMonster(this.unit);
		}else if(unit instanceof Unit) {
			GameLogic.getInstance().setTargetedHero(this.unit);
		}
		GameLogic.getInstance().getCombatController().getCombatDisplay().updatePointer();
		this.attackAnimation();
		//this.gotDamagedAnimation();
	}
	

	
	public void attackAnimation() {
		SoundManager.playSound("audio/unitAttackSound.mp3",50);
		Random random = new Random();
		Thread thread = new Thread() {
			public void run () {
				//SoundManager.playSound("audio/unitAttackSound.mp3",50);
				GameLogic.getInstance().animationRunning = true;
				System.out.println("Atk Thread Running");
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				Platform.runLater( ()->{
					TranslateTransition translate = new TranslateTransition() ;
					translate.setNode(unitImage);
					translate.setDuration(Duration.millis(20));
					translate.setCycleCount(4);
					translate.setByX(50);
					translate.setAutoReverse(true);
					translate.play();	
				});
				
				GameLogic.getInstance().animationRunning = false;
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
		Platform.runLater(()->{
			
			TranslateTransition translate = new TranslateTransition() ;
			translate.setNode(unitImage);
			translate.setDuration(Duration.millis(30));
			translate.setCycleCount(8);
			translate.setByY(20);
			translate.setAutoReverse(true);
			translate.play();	
		});

		
		Random random = new Random();
		Thread thread = new Thread() {
			
			public void run () {
				
				GameLogic.getInstance().animationRunning = true;
				System.out.println("Take Dmg Thread Running");
				try {
					for (int i = 0 ;  i < newRound ; i ++) {
						System.out.println(i);
						if(i%2 == 0) {
							Platform.runLater(()->{
								unitImage.setVisible(true);
							});
						}else {
							Platform.runLater(()->{
								unitImage.setVisible(false);
							});
						}
						

						
						Thread.sleep(50); 
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				GameLogic.getInstance().animationRunning = false;
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
