package gui;

//import java.awt.Image;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
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
import logic.GameLogic;
import sound.SoundManager;
import application.Main;

public class MenuController implements Initializable  {

	@FXML
	public Button eieiButton ;
	@FXML
	public Button testButton ;
	@FXML
	public VBox testVBOX ;
	@FXML
	public ProgressBar distanceProgressBar ;
	@FXML
	public Stage stage ;
	@FXML
	public Scene scene ;
	@FXML
	public Parent root ;
	@FXML
	public ImageView gameLogo ;
	@FXML
	//Image dota2 = new Image(getClass().getResourceAsStream("image/monsterSkill.png"));
	Image image = new Image(ClassLoader.getSystemResource("image/monsterSkill.png").toString(), 1200,
            800, false, true);

	
	
	public void switchToCombat (ActionEvent event) {
		try {
			GameLogic.getInstance().startGame();
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
	
	public void switchToMenu (ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/MenuScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			SoundManager.setCurrentBGM("audio/MenuBGM.wav",0.1);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exitGame (ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	
	public void addButtonOnVBOX (ActionEvent event) {
		Button newButt = new Button();
		testVBOX.getChildren().add(newButt);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		System.out.println("eiei");
	}
	
//	public void switchImage (ActionEvent event) {
//		gameLogo.setImage(image);
//	}
	
	public void testAdjustImage(ActionEvent event) {
		gameLogo.setFitWidth(300);
		gameLogo.setFitHeight(300);
	}
	
	
	

}
