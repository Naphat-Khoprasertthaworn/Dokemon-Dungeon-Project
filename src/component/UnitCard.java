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
/**
 * UnitCard Class
 */
public class UnitCard extends VBox {

	/**
	 * unit in this UnitCard
	 */
	private Unit unit;
	
	/**
	 * health unit bar
	 */
	private ProgressBar healthBar;
	
	/**
	 * unit image
	 */
	private ImageView unitImage;
	
	/**
	 * blank image
	 */
	private Image blankImage = new Image(ClassLoader.getSystemResource("image/blank.png").toString(), 50,50, false, true);
	
	/**
	 * pointer image
	 */
	private ImageView pointerImageView = new ImageView( new Image(ClassLoader.getSystemResource("image/pointer.png").toString()) );
	
	/**
	 * damage reduction buff image
	 */
	private ImageView damageReductionBuff = new ImageView( new Image(ClassLoader.getSystemResource("image/damageReductionBuffURL.png").toString()) );
	
	/**
	 * enhance buff image
	 */
	private ImageView enhanceBuff = new ImageView( new Image(ClassLoader.getSystemResource("image/enhanceBuffURL.png").toString()) );
	
	/**
	 * exhaust buff image
	 */
	private ImageView exhaustBuff = new ImageView( new Image(ClassLoader.getSystemResource("image/exhaustBuffURL.png").toString() ));
	
	/**
	 * vulnerability buff image
	 */
	private ImageView vulnetabilityBuff = new ImageView( new Image(ClassLoader.getSystemResource("image/vulnetabilityBuffURL.png").toString()) );
	
	/**
	 * dead icon image
	 */
	private ImageView deadBuff = new ImageView( new Image(ClassLoader.getSystemResource("image/deadURL.png").toString()) );
	
	/**
	 * Constructor UnitCard.
	 */
	public UnitCard() {
		unitImage = new ImageView(blankImage);
		unitImage.setFitHeight(200);
		unitImage.setFitWidth(170);
		
		pointerImageView.setVisible(false);
		pointerImageView.setFitHeight(50);
		pointerImageView.setFitWidth(50);
		
		healthBar = new ProgressBar(100);
		healthBar.setPrefHeight(10);
		healthBar.setPrefWidth(100);
		healthBar.setVisible(false);	
		
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
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				onClickHandler();
			}
		});
		this.resetBuffBox();
	}
	
	/**
	 * update unit in unit card.
	 * @param u unit
	 */
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
	
	/**
	 * reset all unit buff
	 */
	public void resetBuffBox() {
		this.damageReductionBuff.setVisible(false);
		this.enhanceBuff.setVisible(false);
		this.exhaustBuff.setVisible(false);
		this.vulnetabilityBuff.setVisible(false);
		this.deadBuff.setVisible(false);
	}
	
	/**
	 * update HealthBar according to unit health.
	 */
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
	
	/**
	 * unit card on click event.
	 */
	public void onClickHandler() {
		if(unit instanceof Monster) {
			GameLogic.getInstance().setTargetedMonster(this.unit);
		}else if(unit instanceof Unit) {
			GameLogic.getInstance().setTargetedHero(this.unit);
		}
		GameLogic.getInstance().getCombatController().getCombatDisplay().updatePointer();
		this.attackAnimation();
	}
	
	/**
	 * unit card on click event.
	 */
	public void attackAnimation() {
		SoundManager.playSound("audio/unitAttackSound.mp3",50);
		Random random = new Random();
		Thread thread = new Thread() {
			public void run () {
				//SoundManager.playSound("audio/unitAttackSound.mp3",50);
				GameLogic.getInstance().animationRunning = true;
				System.out.println("Atk Thread Running");

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
	
	/**
	 * unit card on click event.
	 */
	public void gotDamagedAnimation() {
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

		Thread thread = new Thread() {
			
			public void run () {
				GameLogic.animationRunning = true;
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
				GameLogic.animationRunning = false;
			}
		};
		thread.start();
	}
	
	/**
	 * Getter unit in this UnitCard.
	 * @return unit unit
	 */
	public Unit getUnit() {
		return unit;
	}
	
	/**
	 * Setter unit in this UnitCard.
	 * @param unit unit
	 */
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	/**
	 * Getter pointer image in this UnitCard.
	 * @return pointerImageView pointer image
	 */
	public ImageView getPointerImageView() {
		return pointerImageView;
	}

	/**
	 * Setter pointer in this UnitCard.
	 * @param pointerImageView pointer image
	 */
	public void setPointerImageView(ImageView pointerImageView) {
		this.pointerImageView = pointerImageView;
	}
}
