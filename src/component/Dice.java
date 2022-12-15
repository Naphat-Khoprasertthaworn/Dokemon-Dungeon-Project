package component;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.Random;
import java.util.Stack;

import javax.naming.spi.DirStateFactory.Result;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;


/**
 * Dice Class.
 */
public class Dice extends VBox{
	
	/**
	 * dice image
	 */
	private ImageView diceImageView ; 
	
	/**
	 * dice enable
	 */
	private boolean isEnable;
	
	/**
	 * random number from dice
	 */
	private int resultRoll ;
	
	/**
	 * Constructor of Dice
	 */
	public Dice() {
		setMaxHeight(100);
		setMinHeight(100);
		diceImageView = new ImageView();

		String imgPath = ClassLoader.getSystemResource("image/Bigdice.png").toString();
		
		this.diceImageView = new ImageView(new Image(imgPath));
		diceImageView.setFitHeight(100);
		diceImageView.setFitWidth(100);
		this.getChildren().add(diceImageView);
		this.isEnable = true ;
		
		setAlignment(Pos.CENTER);
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(isEnable) {
					onClickHandler();
				}else {
					
				}
			}
		});
	}
	
	/**
	 * Dice on click event. 
	 */
	protected void onClickHandler() {
		
		Random random = new Random();
		Thread thread = new Thread() {
			public void run () {
				System.out.println("Thread Running");
				try {
					for (int i = 0 ; i < 15 ; i++) {
						resultRoll = random.nextInt(6)+1 ;
						String imgPath = ClassLoader.getSystemResource("image/Dice"+resultRoll + ".png").toString();
						diceImageView.setImage(new Image(imgPath));
						Thread.sleep(50);
					}
					isEnable = true ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				Platform.runLater(()-> {
					endThread();
				});
			}
		};
		thread.start();

	}
	
	/**
	 * Getter random number from dice.
	 * @return resultRoll random number from dice
	 */
	public int getResultRoll() {
		return resultRoll;
	}

	/**
	 * Setter random number from dice.
	 * @param resultRoll random number from dice
	 */
	public void setResultRoll(int resultRoll) {
		this.resultRoll = resultRoll;
	}
	
	/**
	 * GUI update after roll dice.
	 */
	public void endThread() {
		int ni = this.getResultRoll();
		

		GameLogic.isBossStage = GameLogic.getInstance().setDistance( GameLogic.getInstance().getDistance() + ni);
		GameLogic.getInstance().getCombatController().updateProgressBar();
		GameLogic.getInstance().resetUnits();
		GameLogic.startStageGame();
		
		GameLogic.getInstance().getCombatController().getCombatDisplay().updatePointer();
		GameLogic.getInstance().getCombatController().getSkillPane().updateState();
		
		isEnable = false;
	}
	
	/**
	 * Getter dice enable
	 * @return isEnable dice is enable
	 */
	public boolean isEnable() {
		return isEnable;
	}
	
	/**
	 * Setter dice enable
	 * @param isEnable dice enable
	 */
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	
}
