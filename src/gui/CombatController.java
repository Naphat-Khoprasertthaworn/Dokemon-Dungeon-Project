package gui;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
	public ProgressBar distanceProgress ;
	@FXML
	public Stage stage ;
	@FXML
	public Scene scene ;
	@FXML
	public Parent root ;
	
	public Dice dice ; 
	
	public StackPane dicePane ;


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
			e.printStackTrace();
		}
	}
	
	
	public void initializeCombatPane () {
		combatDisplay = new CombatDisplay() ;
		combatPane.getChildren().add(combatDisplay);
		//System.err.println("it work com bat pane");
		GameLogic.getInstance().initPointer();
		GameLogic.getInstance().updateTargetPointer();
		this.getCombatDisplay().updatePointer();
	}
	
	public void showGameOver () {
		//root = FXMLLoader.load(getClass().getResource("/gui/MenuScene.fxml"));
		
		scene = new Scene(root);
		stage.setScene(scene);
		SoundManager.setCurrentBGM("audio/MenuBGM.wav",0.1);
		stage.show();
	}
	
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
	
	public void exitGame (ActionEvent event) {
		Platform.exit();
		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		GameLogic.getInstance().setCombatController(this);
		GameLogic.getInstance().initGame();
		
		System.out.println("Initialized");
	    initializeCombatPane();
	    initializeCommandPane();
	    
	}
	
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
	
	
	//@FXML
	public void updateProgressBar() {
		float dis = GameLogic.getInstance().getDistance();
		float max = GameLogic.MAX_DISTANCE;
		distanceProgress.setProgress( dis/max );
	}
	
	
	public void switchtoGameOver () throws IOException {
		//System.out.println("its work");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameOverScene.fxml"));
		root = loader.load();
		stage = Main.primaryStage; 
		scene = new Scene(root,1280,720);
		stage.setScene(scene);
		stage.show();
		
	}
	
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
