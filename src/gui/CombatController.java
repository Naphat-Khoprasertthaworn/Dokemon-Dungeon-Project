package gui;
import java.io.IOException;
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
import sound.SoundManager;

public class CombatController {
	@FXML
	public ProgressBar distanceProgressBar ;
	@FXML
	public Stage stage ;
	@FXML
	public Scene scene ;
	@FXML
	public Parent root ;
	
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

}