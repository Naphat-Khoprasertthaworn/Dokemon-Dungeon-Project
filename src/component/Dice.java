package component;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.Random;
import java.util.Stack;

import javax.naming.spi.DirStateFactory.Result;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Dice extends VBox{
	private ImageView diceImageView ; 
	private boolean isDisable;
	private int resultRoll ;
//	private final String bigDice ;
//	private final String dice1 ; 
//	private final String dice2 ; 
//	private final String dice3 ; 
//	private final String dice4 ; 
//	private final String dice5 ; 
//	private final String dice6 ; 
	public Dice() {
//		bigDice = ClassLoader.getSystemResource("image/Bigdice.png").toString();
//		dice1 = ClassLoader.getSystemResource("image/dice1.png").toString();
//		dice2 = ClassLoader.getSystemResource("image/dice2.png").toString();
//		dice3 = ClassLoader.getSystemResource("image/dice3.png").toString();
//		dice4 = ClassLoader.getSystemResource("image/dice4.png").toString();
//		dice5 = ClassLoader.getSystemResource("image/dice5.png").toString();
//		dice6 = ClassLoader.getSystemResource("image/dice6.png").toString();
		setMaxHeight(100);
		setMinHeight(100);
		diceImageView = new ImageView();
		
		
		String imgPath = ClassLoader.getSystemResource("image/Bigdice.png").toString();
		
		this.diceImageView = new ImageView(new Image(imgPath));
		diceImageView.setFitHeight(100);
		diceImageView.setFitWidth(100);
		this.getChildren().add(diceImageView);
		this.isDisable = false ;
		
		setAlignment(Pos.CENTER);
		
		
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				onClickHandler();
			}
			
		});
	}
	

	protected void onClickHandler() {
		// TODO Auto-generated method stub
		
		Random random = new Random();
		Thread thread = new Thread() {
			public void run () {
				//int resultRoll ; 
				System.out.println("Thread Running");
				try {
					for (int i = 0 ; i < 15 ; i++) {
						resultRoll = random.nextInt(6)+1 ;
						String imgPath = ClassLoader.getSystemResource("image/Dice"+resultRoll + ".png").toString();
						diceImageView.setImage(new Image(imgPath));
						Thread.sleep(50);
					}
					//return resultRoll;
//					String finalImagePath = ClassLoader.getSystemResource("image/Dice"+resultRoll + ".png").toString();
//					diceImageView.setImage(new Image(imgPath));
					//return resultRoll ;
					isDisable = true ;
					System.out.println(resultRoll);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};
		thread.start();
		
	}

	public int getResultRoll() {
		return resultRoll;
	}


	public void setResultRoll(int resultRoll) {
		this.resultRoll = resultRoll;
	}


	
	
}
