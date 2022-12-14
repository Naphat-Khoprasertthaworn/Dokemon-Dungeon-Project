package application;

import java.util.ArrayList;
import java.util.Scanner;

import javax.management.Notification;

import org.junit.platform.launcher.Launcher;

import entity.base.Unit;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameLogic;
import sound.SoundManager;
/**
 * Main class of program
 */
public class Main  extends Application{
	/**
	 * Main Stage
	 */
    public static Stage primaryStage ;
    /**
     * JavaFX start function
     */ 
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        try {
            this.primaryStage = primaryStage ;
            Parent root = FXMLLoader.load(getClass().getResource("/gui/MenuScene.fxml"));
            Scene scene = new Scene(root);
            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle("Progmeth-project");
            this.primaryStage.setResizable(false);
            SoundManager.setCurrentBGM("audio/MenuBGM.wav",0.1);
            this.primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    /**
     * Main entry point
     * @param args arguments
     */ 
	public static void main(String[] args) {
		launch(args);
	}
}
	
	
	
	
