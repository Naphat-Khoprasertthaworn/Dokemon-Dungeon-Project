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
 * Main Stage
 */
public class Main  extends Application{
	private static Scanner keyBoard;
	private static boolean isGameActive;
	private static boolean isCombatMode;
	private static boolean isHeroTurn;
	private static boolean isStageFail;
	private static boolean isStageClear;
    public static Stage primaryStage ;
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
	
	
	
	
