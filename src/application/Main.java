package application;

import java.util.ArrayList;
import java.util.Scanner;

import entity.base.Unit;
import logic.GameLogic;

public class Main {
	
	private static Scanner keyBoard;
	//private static int Distance;
	private static boolean isGameActive;
	private static boolean isCombatMode;
	private static boolean isHeroTurn;
	private static boolean isStageClear;
	private static boolean isStageFail;
	public static void main(String[] args) {
		
		
		keyBoard = new Scanner(System.in);
		
		while(true){
			System.out.println("=============================");
			System.out.println("Welcome to");
			System.out.println("Overdue! All You Can Rush");
			System.out.println("=============================");
			System.out.println("What are you doing?");
			System.out.println("1) Start Game");
			System.out.println("2) Quit");
			System.out.println("=============================");
			int results = getChoice();
			
			if(results==1) {
				//System.out.println("=============================");
				startGame();
			}else if(results==2){
				break;
			}else {
				System.out.println("Invalid Input, Terminate the game.");
				break;
			}
		}
	}
	
	public static int getChoice() {
		System.out.print(">> ");
		String input = keyBoard.nextLine();
		try{
			return Integer.parseInt(input);
		}catch(NumberFormatException e){
            return -1;
        }
	}
	
	public static void startGame() {
		isGameActive = true;
		GameLogic.getInstance().newGame();
		
		while(isGameActive) {
			int ni = GameLogic.getInstance().rollDice();
			Boolean bossStage = GameLogic.getInstance().setDistance( GameLogic.getInstance().getDistance() + ni);
			System.out.println( "distance NOW : "+GameLogic.getInstance().getDistance() );
			if(bossStage) {
				isGameActive = false;
				System.err.println("GAME END !!!");
				break;
			}
			GameLogic.getInstance().generateMonsters();
			isStageClear = false;
			isStageFail = false;
			isCombatMode = true;
			while(isCombatMode) {
				playerTurn();
				monsterTurn();
				GameLogic.getInstance().countdownGame();
				if(GameLogic.getInstance().stageClear()) {
					GameLogic.getInstance().resetUnits();
					break;
				}else if(GameLogic.getInstance().stageFail()) {
					isGameActive = false;
					GameLogic.getInstance().resetUnits();
					System.out.println("You died");
					break;
				}
				
			}
			//System.out.println( GameLogic.getInstance().getHeros() );
			//break;
		}
		
		
		
	}
	public static void updateStageGame() {
		isStageClear = GameLogic.getInstance().stageClear();
		isStageFail = GameLogic.getInstance().stageFail();
	}
	
	public static void showCombat() {
		System.out.println("======================== Monsters =========================");
		ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
		ArrayList<Unit> heros = GameLogic.getInstance().getHeros();
		for(int i = 0;i<monsters.size();i++) {
			System.out.println("---------------------------- "+i+" ----------------------------" );
			System.out.println( monsters.get(i) );
			System.out.println("------------------------- Skills --------------------------" );
			System.out.println( monsters.get(i).getSkills() );
		}
		System.out.println("========================== Heros ==========================");
		for(int i = 0;i<heros.size();i++) {
			System.out.println("---------------------------- "+i+" ----------------------------" );
			System.out.println( heros.get(i) );
			System.out.println("------------------------- Skills --------------------------" );
			System.out.println( heros.get(i).getSkills() );
		}
	}
	
	
	
	public static void playerTurn() {
		System.out.println( "* * * * * * * * * * * * PLAYER TURN *  * * * * * * * * * **" );
		ArrayList<Unit> heros = GameLogic.getInstance().getHeros();
		Unit hero;
		for(int i = 0;i<heros.size();i++) {
			showCombat();
			hero = GameLogic.getInstance().getUnitByPosition(i,heros);
			isHeroTurn = true;
			
			System.out.println( "|| "+hero.getName() + " Turn ||" );
			
			if(!hero.isAlive()) {
				continue;
			}
			
			while(isHeroTurn) {
				int order = getChoice();
				if(hero.getSkills().get(order).readySkill()) {
					hero.useSkill(order);
					GameLogic.getInstance().updateTargetPointer();
					isHeroTurn = false;
				}else {
					System.out.println("skill is not ready");
				}
				
			}
			updateStageGame();
			if(isStageClear || isStageFail) {
				return;
			}
			
		}
		
	}
	
	public static void monsterTurn() {
		System.out.println( "* * * * * * * * * * * * MONSTER TURN * * * * * * * * * * **" );
		ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
		Unit monster;
		for(int i = 0;i<monsters.size();i++) {
			showCombat();
			monster = GameLogic.getInstance().getUnitByPosition(i,monsters);
			if(!monster.isAlive()) {
				continue;
			}
			
			for(int j = monster.getSkills().size()-1;j>=0;j--) {
				if(monster.getSkills().get(j).readySkill()) {
					monster.useSkill(j);
					break;
				}
			}
			
			
		}
		if(isStageClear || isStageFail) {
			return;
		}
		
	}
	

}
