package gui;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.GameLogic;
import sound.SoundManager;
import component.CombatDisplay; 
import component.*;

public class CombatController implements Initializable{
	@FXML
	public HBox commandPane ;
	@FXML
	public HBox combatPane ;
	@FXML
	public ProgressBar distanceProgressBar ;
	@FXML
	public Stage stage ;
	@FXML
	public Scene scene ;
	@FXML
	public Parent root ;
	
	public CombatDisplay getCombatDisplay() {
		return combatDisplay;
	}

	public void setCombatDisplay(CombatDisplay combatDisplay) {
		this.combatDisplay = combatDisplay;
	}

	public SkillPane getSkillPane() {
		return skillPane;
	}

	public void setSkillPane(SkillPane skillPane) {
		this.skillPane = skillPane;
	}

	public ItemGridPane getItemGridPane() {
		return itemGridPane;
	}

	public void setItemGridPane(ItemGridPane itemGridPane) {
		this.itemGridPane = itemGridPane;
	}

	private CombatDisplay combatDisplay;
	private HBox skillAndItemPane;
	private SkillPane skillPane;
	private ItemGridPane itemGridPane;
	
	
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
	
	
	public void initializeCombatPane () {
		combatDisplay = new CombatDisplay() ;
//		GameLogic.getInstance().setCombatController(this);
		combatPane.getChildren().add(combatDisplay);
		
	}
	
	public void initializeCommandPane() {
		itemGridPane = new ItemGridPane();
		skillPane = new SkillPane();
		commandPane.getChildren().addAll(skillPane,itemGridPane);
		skillPane.updateState();
		commandPane.setAlignment(Pos.CENTER);
	}
	
	public void exitGame (ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}
	
	public static void updateCombatDisplay() {
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		GameLogic.getInstance().setCombatController(this);
		GameLogic.getInstance().startGame();
		
		System.out.println("Initialized");
	    
	    initializeCommandPane();
	    initializeCombatPane();
	}

}
