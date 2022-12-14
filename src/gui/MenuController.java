package gui;

//import java.awt.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sound.SoundManager;
import application.Main;
import component.*;

/**
 * Controller for fxml 
 */
public class MenuController implements Initializable  {
	/**
	  * One of button
	  */
	@FXML
	public Button eieiButton ;
	/**
	  * One of button  
	  */
	@FXML
	public Button testButton ;
	/**
	  *  Vbox for showing game logo
	  */
	@FXML
	public VBox testVBOX ;
	/**
	  *  Progressbar to show distance
	  */
	@FXML
	public ProgressBar distanceProgressBar ;
	/**
	  *  stage
	  */
	@FXML
	public Stage stage ;
	
	/**
	  * scene 
	  */
	@FXML
	public Scene scene ;
	/**
	  * root 
	  */
	@FXML
	public Parent root ;
	/**
	  * Imageview contains game logo 
	  */
	@FXML
	public ImageView gameLogo ;
	
	/**
	  *  Game logo image
	  */
	@FXML
	//Image dota2 = new Image(getClass().getResourceAsStream("image/monsterSkill.png"));
	Image image = new Image(ClassLoader.getSystemResource("image/monsterSkill.png").toString(), 1200,
            800, false, true);

	
	/**
	  *  switch to combat scene
	  *  @param event mouse cliked event
	  */
	public void switchToCombat (ActionEvent event) {
		try {
			
			root = FXMLLoader.load(getClass().getResource("/gui/CombatScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			SoundManager.setCurrentBGM("audio/CombatBGM.wav",0.1);
			
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	  * switch to main menu scene
	  * @param event mouse clicked event 
	  */
	public void switchToMenu (ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/MenuScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			//SoundManager.setCurrentBGM("audio/MenuBGM.wav",0.1);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	  *  exit game
	  *  @param event mouse clicked event
	  */
	public void exitGame (ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("eiei");
	}
	
//	public void switchImage (ActionEvent event) {
//		gameLogo.setImage(image);
//	}
	
//	public void testAdjustImage(ActionEvent event) {
//		Dice dice = new Dice() ;
//		testVBOX.getChildren().add(dice);
//	}
	
	/**
	  * switch to game over scene 
	  * @throws IOException throws exception
	  */
	public void switchtoGameOver () throws IOException {
		System.out.println("haha");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameOverScene.fxml"));
		root = loader.load();
		stage = Main.primaryStage; 
		scene = new Scene(root,1280,720);
		stage.setScene(scene);
		stage.show();
		
	}
	
	/**
	  * switch to game clear scene 
	  * @throws IOException throws exception
	  */
	public void switchtoGameClear() throws IOException {
		System.out.println("huhu");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameClearScene.fxml"));
		root = loader.load();
		stage = Main.primaryStage; 
		scene = new Scene(root,1280,720);
		stage.setScene(scene);
		stage.show();
	}
	
	
	

}
