package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;

import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import logic.GameLogic;
import sound.SoundManager;
import component.CombatDisplay; 
import component.*;
/**
 * gui controller for combatscene
 */
public class CombatController implements Initializable{

	/**
	  * Pane for skill and item
	  */
	@FXML
	public HBox commandPane ;
	/**
	  * Pane for unit
	  */
	@FXML
	public HBox combatPane ;
	/**
	  * Distance bar
	  */
	@FXML
	public ProgressBar distanceProgress ;
	/**
	  * stage
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
	  * Dice in commandPane
	  */
	public Dice dice ; 
	
	//public StackPane dicePane ;

	/**
	  * sub combatpane
	  */
	private CombatDisplay combatDisplay;
	/**
	  * sub commandpane
	  */
	private HBox skillAndItemPane;
	/**
	  * Pane for skill
	  */
	private SkillPane skillPane;
	/**
	  * Pane for item 
	  */
	private ItemGridPane itemGridPane;
	
	/**
	  * switch to menuscene
	  * @param event mouse clicked event
	  */
	public void switchToMenu (ActionEvent event) {
		try {
			root = FXMLLoader.load(getClass().getResource("/gui/MenuScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			SoundManager.setCurrentBGM("audio/MenuBGM.wav",0.1);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * inititialize combatPane
	  */
	public void initializeCombatPane () {
		combatDisplay = new CombatDisplay() ;
		combatPane.getChildren().add(combatDisplay);
		//System.err.println("it work com bat pane");
		GameLogic.getInstance().initPointer();
		GameLogic.getInstance().updateTargetPointer();
		this.getCombatDisplay().updatePointer();
	}
	
	/**
	  * switch to GameOverScene 
	  */
	public void showGameOver () {
		//root = FXMLLoader.load(getClass().getResource("/gui/MenuScene.fxml"));
		
		scene = new Scene(root);
		stage.setScene(scene);
		SoundManager.setCurrentBGM("audio/MenuBGM.wav",0.1);
		stage.show();
	}
	
	/**
	  * Initialize commandPane
	  */
	public void initializeCommandPane() {
		Circle circle = new Circle() ;
		circle.setRadius(75);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);

		dice = new Dice() ;
		itemGridPane = new ItemGridPane();
		skillPane = new SkillPane();
		
		skillPane.updateState();
		commandPane.getChildren().addAll(skillPane,itemGridPane,dice);
		commandPane.setAlignment(Pos.CENTER);	

	}
	
	/**
	  * exit game
	  * @param event mouse clicked event
	  */
	public void exitGame (ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	/**
	  * Initialize function
	  */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		GameLogic.getInstance().setCombatController(this);
		GameLogic.getInstance().initGame();
		
		System.out.println("Initialized");
	    initializeCombatPane();
	    initializeCommandPane();
	    
	}
	
	/**
	  * Show dice alert
	  */
	public void showRollDiceAlert() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Can't use this right now");
		alert.setContentText("Please roll the dice first !");
		alert.showAndWait();
	}
	
	/**
	  * show cooldown alert
	  */
	public void showOnCooldown() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Skill is on cooldown");
		alert.setContentText("Use other skills !");
		alert.showAndWait();
	}
	
	/**
	  * Getter of combatDisplay
	  * @return combat display
	  */
	public CombatDisplay getCombatDisplay() {
		return combatDisplay;
	}

	/**
	  * Setter of combatDisplay
	  */
	public void setCombatDisplay(CombatDisplay combatDisplay) {
		this.combatDisplay = combatDisplay;
	}

	/**
	  * Getter of skillPane
	  * @return skill pane
	  */
	public SkillPane getSkillPane() {
		return skillPane;
	}

	/**
	  * Setter of skillPane
	  */
	public void setSkillPane(SkillPane skillPane) {
		this.skillPane = skillPane;
	}

	/**
	  * Getter of itemGridPane
	  * @return item gridpane
	  */
	public ItemGridPane getItemGridPane() {
		return itemGridPane;
	}

	/**
	  * Setter of itemGridPane 
	  * @param itemGridPane item grid pane
	  */
	public void setItemGridPane(ItemGridPane itemGridPane) {
		this.itemGridPane = itemGridPane;
	}
	
	/**
	  * update distance bar
	  */
	//@FXML
	public void updateProgressBar() {
		float dis = GameLogic.getInstance().getDistance();
		float max = GameLogic.MAX_DISTANCE;
		distanceProgress.setProgress( dis/max );
	}
	
	/**
	  *  switch to GameOverScene
	  *  @throws IOException
	  */
	public void switchtoGameOver () throws IOException {
		//System.out.println("its work");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameOverScene.fxml"));
		root = loader.load();
		stage = Main.primaryStage; 
		scene = new Scene(root,1280,720);
		stage.setScene(scene);
		stage.show();
		
	}
	
	/**
	  * switch to GameClearScene
	  * @throws IOException
	  */
	public void switchtoGameClear() throws IOException {
		Thread switchToGameClearDelayThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Platform.runLater(()->{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameClearScene.fxml"));
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					stage = Main.primaryStage; 
					scene = new Scene(root,1280,720);
					stage.setScene(scene);
					stage.show();
				});
				
				
			}
		};
		switchToGameClearDelayThread.start();

	}
	

}
