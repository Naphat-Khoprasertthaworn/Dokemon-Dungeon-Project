package logic;

import java.io.IOException;
import java.io.NotActiveException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.processing.Generated;

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.EnumSelector;
import org.junit.validator.PublicClassValidator;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Regeneration;
import buff.type.Vulnerability;
import component.UnitCard;
import entity.base.Buff;
import entity.base.Item;
import entity.base.Monster;
import entity.base.Unit;
import gui.CombatController;
import gui.MenuController;
import item.type.BuffPotion;
import item.type.DamageReductionPotion;
import item.type.EnhancePotion;
import item.type.ExhaustPotion;
import item.type.HealingPotion;
import item.type.VulnerabilityPotion;
import javafx.application.Platform;
import javafx.scene.image.Image;
import skill.type.AttackSkill;
import skill.type.DefenseSkill;
import skill.type.MultiTargetAttackSkill;
import skill.type.MultiTargetDefenseSkill;
import skill.type.SingleTargetAttackSkill;
import skill.type.SingleTargetDefenseSkill;

/**
 * GameLogic
 */
public class GameLogic {
	/**
	  * GameLogic is game controller of this game.
	  */
	private static GameLogic instance = null;
	
	/**
	  * Arraylist of hero units.
	  */
	private ArrayList<Unit> heros;
	
	/**
	  * ArrayList of monsters units in one stage.
	  */
	private ArrayList<Unit> monsters;
	
	/**
	  * Arraylist of all monsters(execpt boss).
	  */
	private ArrayList<Monster> poolMonsters;
	
	/**
	  * Arraylist of all item(execpt boss).
	  */
	private ArrayList<Item> poolItems;
	
	
	/**
	  * Hero unit targeted by player.
	  */
	private Unit targetedHero;
	
	/**
	  * Monster unit targeted by player.
	  */
	private Unit targetedMonster;
	
	/**
	  * Hero units this turn.
	  */
	private Unit currentHero;
	
	/**
	  * Game distance progress.
	  */
	private int distance;
	
	/**
	  * Max game distance progress.
	  */
	public static final int MAX_DISTANCE = 15;
	
	/**
	  * Number of unit in party.
	  */
	public static final int MAX_PARTY = 3;
	
	/**
	  * Number of item that drop each stage.
	  */
	public static final int ITEM_DROP = 3;
	
	/**
	  * Game active state.
	  */
	public static boolean isGameActive;
	
	/**
	  * Game combat mode state.
	  */
	public static boolean isCombatMode;
	
	/**
	  * Game hero turn state.
	  */
	public static boolean isHeroTurn;
	
	/**
	  * Game fail state.
	  */
	public static boolean isStageFail;
	
	/**
	  * Game clear state.
	  */
	public static boolean isStageClear;
	
	/**
	  * Order of hero in party in this turn. 
	  */
	private static int heroOrder;
	
	/**
	  * Combat controller of this game. 
	  */
	private CombatController combatController;
	
	/**
	  * Game boss stage state 
	  */
	public static boolean isBossStage;
	
	/**
	  * Game animation running state. 
	  */
	public static boolean animationRunning;
	
	/**
	  * Game monster turn state. 
	  */
	public static boolean isMonsterTurn;

	/**
	  * ArrayList of player item. 
	  */
	private ArrayList<Item> inventory;
	
	/**
	  * Comparator of unit in unit party. 
	  */
	private Comparator<Unit> compUnit = (Unit u1,Unit u2)->{
		if(u1.getPosition() > u2.getPosition()) {
			return 1;
		}
		return -1;
	};
	
	/**
	  * Getter of combat controller of this game.
	  * @return combatController Combat controller.
	  */
	public CombatController getCombatController() {
		return combatController;
	}
	
	/**
	  * Setter of combat controller of this game.
	  * @param combatController combat controller of this game.
	  */
	public void setCombatController(CombatController combatController) {
		this.combatController = combatController;
	}
	
	/**
	  * Getter of isMonsterTurn of this game.
	  * @return isMonsterTurn.
	  */
	public static boolean isMonsterTurn() {
		return isMonsterTurn;
	}
	
	/**
	  * Setter of isMonsterTurn of this game.
	  * @param isMonsterTurn game monster turn state.
	  */
	public static void setMonsterTurn(boolean isMonsterTurn) {
		GameLogic.isMonsterTurn = isMonsterTurn;
	}
	
	/**
	  * Getter of isAnimationRunning of this game.
	  * @return animationRunning
	  */
	public boolean isAnimationRunning() {
		return animationRunning;
	}
	
	/**
	  * Setter of isAnimationRunning of this game.
	  * @param animationRunning game animation running state.
	  */
	public static void setAnimationRunning(boolean animationRunning) {
		GameLogic.animationRunning = animationRunning;
	}
	
	
		//######## GAME LOGIC ########
	
	/**
	  * Contractor of GameLogic.
	  */
	private GameLogic() {
		this.newGame();
	}
	
	/**
	  * Getter of GameLogic.
	  * @return instance
	  */
	public static GameLogic getInstance() {
		if(instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}
	

	//######## DISTANCE & DICE ########
	/**
	  * Getter of game distance.
	  * @return distance
	  */
	public int getDistance() {
		return distance;
	}
	
	/**
	  * Setter of game distance.
	  * @param distance game distance.
	  * @return isBossStage.
	  */
	public boolean setDistance(int distance) {
		
		if( distance >= MAX_DISTANCE ) {
			this.distance = MAX_DISTANCE;
			return true;
		}
		this.distance = distance;
		return false;
	}
	
	//######## TURN & STAGE HANDLER ########
	
	/**
	  * countdown all countdownable object.
	  */
	public void countdownGame() {
		for(Unit unit:this.getHeros()) {
			unit.countdownAll();
		}
		for(Unit unit:this.getMonsters()) {
			unit.countdownAll();
		}
	}
	
	/**
	  * reset all unit in array of hero and array of monster.
	  */
	public void resetUnits() {
		for(Unit unit:this.getHeros()) {
			unit.reset();
		}
		for(Unit unit:this.getMonsters()) {
			unit.reset();
		}
		this.getMonsters().clear();
	}
	
	/**
	  * auto set of targetHero and targetMonster.
	  */
	public void initPointer() {
		this.setTargetedHero( this.getFrontLineUnit(heros) );
		this.setTargetedMonster( this.getFrontLineUnit(monsters) );

	}
	
	/**
	  * check stage clear of combat mode.
	  * @return isStageClear
	  */
	public boolean stageClear() {
		for(Unit unit :this.getMonsters()) {
			if(unit.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	  * check stage fail of combat mode.
	  * @return isStageFail
	  */
	public boolean stageFail() {
		for(Unit unit :this.getHeros()) {
			if(unit.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	  * update targetPointer of hero and monster.
	  */
	public void updateTargetPointer() {
		Unit unit;

		if(this.getTargetedHero() == null) {
			return;
		}
		if(!this.getTargetedHero().isAlive()) {
			unit = this.getFrontLineUnit(heros);
			if(unit == null) {
				this.setTargetedHero( null );
			}else {
				this.setTargetedHero( unit );
			}

		}
		if(this.getTargetedMonster() == null) {
			return;
		}
		if(!this.getTargetedMonster().isAlive()) {
			unit = this.getFrontLineUnit(monsters);
			if(unit == null) {
				this.setTargetedMonster( null );
			}else {
				this.setTargetedMonster( unit );
			}
		}
	}
	
	//######## INVENTORY ########
	/**
	  * getter of inventory.
	  * @return inventory
	  */
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	/**
	  * setter of inventory.
	  * @param inventory ArrayList of Item.
	  */
	public void setInventory(ArrayList<Item> inventory) {
		this.inventory = inventory;
	}
	
	/**
	  * add item to inventory
	  * @param item item
	  */
	public void addItem(Item item) {
		for(Item i:this.getInventory()) {
			if( i.equals(item) ) {
				i.setAmount( i.getAmount() + item.getAmount() );
				return;
			}
		}
		this.getInventory().add(item);
	}

	/**
	  * print all Item in inventory.
	  */
	public void showInventory() {
		for(int i = 0;i<inventory.size();i++) {
			System.out.println( inventory.get(i) );
		}
	}
	
	//######## TARGET POINTER HANDLER ########
	
	/**
	  * find targeted unit in hero party or monster party.
	  * @param unit used skill unit.
	  * @param b b is true if we want to find target unit is in same party.
	  * @return targetedUnit
	  */
	public Unit findTarget(Unit unit,boolean b) {
		if(this.getHeros().contains(unit) == b) {
			return this.getTargetedHero();
		}else if(this.getMonsters().contains(unit) == b) {
			return this.getTargetedMonster();
		}else {
			return null;
		}
	}
	
	/**
	  * Getter of targeted hero.
	  * @return targetHero
	  */
	public Unit getTargetedHero() {
		return targetedHero;
	}
	
	/**
	  * Setter of targeted hero.
	  * @param targetedHero targeted hero.
	  */
	public void setTargetedHero(Unit targetedHero) {
		if(targetedHero == null) {
			return;//add disable all pointer
		}
		if(targetedHero.isAlive()) {
			if( !(this.getTargetedHero() == null) ) {
				this.getTargetedHero().setTargeted(false);
			}
			
			targetedHero.setTargeted(true);
			this.targetedHero = targetedHero;
		}
	}
	
	/**
	  * Getter of targeted monster.
	  * @return targetedMonster
	  */
	public Unit getTargetedMonster() {
		return targetedMonster;
	}
	
	/**
	  * Setter of target monster.
	  * @param targetedMonster targeted monster.
	  */
	public void setTargetedMonster(Unit targetedMonster) {
		if(targetedMonster == null) {
			return;//add disable all pointer
		}
		if(targetedMonster.isAlive()) {
			
			if( !(this.getTargetedMonster() == null) ) {
				this.getTargetedMonster().setTargeted(false);
			}
			
			targetedMonster.setTargeted(true);
			this.targetedMonster = targetedMonster;
		}
	}

	//######## SINGLE UNIT HANDLER ########
	/**
	  * Getter unit in array of unit by position.
	  * @param p position.
	  * @param units array of unit.
	  * @return unit
	  */
	public Unit getUnitByPosition(int p,ArrayList<Unit> units) {
		for(int i = 0;i<MAX_PARTY;i++) {
			if(units.get(i).getPosition() == p) {
				return units.get(i);
			}
		}
		return null;
	}
	
	/**
	  * Getter less position unit in array of unit.
	  * @param units array of unit.
	  * @return frontLineUnit
	  */
	public Unit getFrontLineUnit(ArrayList<Unit> units) {
		for( int i = 0;i<MAX_PARTY;i++ ) {
			for(Unit u :units) {
				if(u.getPosition()==i && u.isAlive()) {
					return u;
				}
			}
		}
		return null;
	}
	
	/**
	  * Getter greater position unit in array of unit.
	  * @param units array of unit.
	  * @return unit
	  */
	public Unit getBackLineUnit(ArrayList<Unit> units) {
		for( int i = MAX_PARTY-1;i>=0;i-- ) {
			for(Unit u :units) {
				if(u.getPosition()==i && u.isAlive()) {
					return u;
				}
			}
		}
		return null;
	}
	
	/**
	  * Getter less health unit in array of unit.
	  * @param units array of unit.
	  * @return unit
	  */
	public Unit getLowestHealthUnit( ArrayList<Unit> units ) {
		Unit u = getFrontLineUnit(units);
		for(Unit unit:units) {
			if(unit.isAlive() && unit.getHealth() < u.getHealth() ) {
				u = unit;
			}
		}
		return u;
	}
	
	/**
	  * Getter of hero unit in this turn.
	  * @return currentHero
	  */
	public Unit getCurrentHero() {
		return currentHero;
	}
	
	/**
	  * Setter of hero unit in this turn.
	  * @param currentHero next turn hero unit.
	  */
	public void setCurrentHero(Unit currentHero) {
		this.currentHero = currentHero;
	}
	
	//######## PARTY HANDLER ########
	/**
	  * Getter of arraylist of hero unit.
	  * @return heros arraylist of hero
	  */
	public ArrayList<Unit> getHeros() {
		return heros;
	}

	/**
	  * add hero unit to arraylist of hero.
	  * @param heros hero unit.
	  */
	public void addHeros(Unit heros) {
		this.heros.add(heros);
		Collections.sort( this.heros,compUnit );
	}

	/**
	  * Getter of arraylist of monster unit.
	  * @return monsters arraylist of monsters
	  */
	public ArrayList<Unit> getMonsters() {
		return monsters;
	}
	
	/**
	  * add monster unit to arraylist of monster.
	  * @param monsters monster unit.
	  */
	public void addMonsters(Unit monsters) {
		this.monsters.add(monsters);
		Collections.sort( this.monsters,compUnit );
	}

	/**
	  * find arraylist of monsters or arraylist of heros.
	  * @param u unit.
	  * @param b b is true if we want to find party that contain this unit.
	  * @return units arraylist of units
	  */
	public ArrayList<Unit> findParty(Unit u,boolean b){
		if( this.getHeros().contains(u)==b ) {
			return this.getHeros();
		}else if(this.getMonsters().contains(u)==b) {
			return this.getMonsters();
		}else {
			return null;
		}
	}
	
	
	//######## INITIAL GAME ########
	/**
	  * generate all object in this game execpt boss.
	  */
	public void newGame() {
		this.setDistance(0);
		this.inventory = new ArrayList<Item>();
		this.gennerateHerosParty();
		this.generatePoolItems();
		this.monsters = new ArrayList<Unit>();
		this.generatePoolMonsters();
		
	}
	
	/**
	  * generate hero party.
	  */
	public void gennerateHerosParty() {
		this.heros = new ArrayList<Unit>();
		
		Unit warriorUnit = new Unit("Warrior", "I am warrior.", 50, 30, 0, 100,"image/warriorUnit.png");
		Unit archerUnit = new Unit("Archer", "I am archer.", 50, 10, 1, 70,"image/archerUnit.png");
		Unit medicUnit = new Unit("Medic", "I am medic", 40, 20, 2, 80,"image/medicUnit.png");
		
		this.addHeros(archerUnit);
		this.addHeros(medicUnit);
		this.addHeros(warriorUnit);
		SingleTargetAttackSkill warriorAutoAttack = new SingleTargetAttackSkill("Auto Attack","can't target", 100,0, false,"image/warriorAutoAttack.png");
		SingleTargetAttackSkill warriorSkill1 = new SingleTargetAttackSkill("Attack then Buff","can't target",120,3,false,"image/warriorSkill1.png");
		warriorSkill1.addBuffsSelf( new DamageReduction(2, 30) );
		MultiTargetAttackSkill warriorSkill2 = new MultiTargetAttackSkill("AOE Damage","AOE",120,5,"image/warriorSkill2.png");
		warriorSkill2.addBuffsSelf( new Exhaust(2, 30) );
		
		SingleTargetAttackSkill medicAutoAttack = new SingleTargetAttackSkill("Auto Attack","can't target",100,0, false,"image/medicAutoAttack.png");
		medicAutoAttack.addBuffsTarget(new Exhaust(1 , 30));
		SingleTargetDefenseSkill medicSkill1 = new SingleTargetDefenseSkill("Single Heal","can target an allie",100,3,"image/medicSkill1.png");
		MultiTargetDefenseSkill medicSkill2 = new MultiTargetDefenseSkill("Buff","AOE buff",0,6,"image/medicSkill2.png");
		medicSkill2.addBuffsTarget( new DamageReduction(1, 100) );
		medicSkill2.addBuffsTarget( new Enhance(2, 100) );
		
		SingleTargetAttackSkill archerAutoAttack = new SingleTargetAttackSkill("Auto Attack","can target an enemy",100,0, true,"image/archerAutoAttack.png");
		archerAutoAttack.addBuffsTarget(new Vulnerability(2, 20));
		SingleTargetAttackSkill archerSkill1 = new SingleTargetAttackSkill("DPS skill","can target an enemy",200,2, true,"image/archerSkill1.png");
		archerSkill1.addBuffsTarget(new Vulnerability(2, 30));
		archerSkill1.addBuffsSelf( new Enhance(3, 20) );
		MultiTargetAttackSkill archerSkill2 = new MultiTargetAttackSkill("Debuff","AOE",100,4,"image/archerSkill2.png");
		archerSkill2.addBuffsTarget(new Vulnerability(2, 30));
		archerSkill2.addBuffsTarget(new Exhaust(1, 30));

		warriorUnit.addSkills(warriorAutoAttack);
		warriorUnit.addSkills(warriorSkill1);
		warriorUnit.addSkills(warriorSkill2);
		
		archerUnit.addSkills(archerAutoAttack);
		archerUnit.addSkills(archerSkill1);
		archerUnit.addSkills(archerSkill2);
		
		medicUnit.addSkills(medicAutoAttack);
		medicUnit.addSkills(medicSkill1);
		medicUnit.addSkills(medicSkill2);
	
	}
	
	/**
	  * generate all monster in this game.
	  */
	public void generatePoolMonsters() {
		this.poolMonsters = new ArrayList<Monster>();
		Monster golemUnit = new Monster("Golem", "I am golem", 50, 30, 0,300,"image/golemUnit.png");
		Monster slimeUnit = new Monster("Slime", "I am slime", 40, 10, 0, 200,"image/slimeUnit.png");
		Monster oniUnit = new Monster("Oni", "I am Oni", 50 , 30, 0, 250,"image/oniUnit.png");
		Monster bloodHawkUnit = new Monster("Blood Hawk", "I am blood hawk" ,50, 10, 0, 200,"image/bloodHawkUnit.png");
		Monster gnomeUnit = new Monster("Gnome", "I am Gnome", 50, 10, 0, 200,"image/gnomeUnit.png");
		
		SingleTargetAttackSkill golemAutoAttack = new SingleTargetAttackSkill("Auto attack","attack front line hero",100,0, false,"image/monsterSkill.png");
		MultiTargetAttackSkill golemSkill1 = new MultiTargetAttackSkill("Eathquake","AOE attack",100,5,"image/monsterSkill.png");

		SingleTargetAttackSkill slimeAutoAttack = new SingleTargetAttackSkill("Auto attack" , "heal self and give front line exhaust" , 100,0, false,"image/monsterSkill.png");
		slimeAutoAttack.addBuffsSelf(new Regeneration(40));
		SingleTargetAttackSkill slimeSkill1 = new SingleTargetAttackSkill("normal debuff","give debuff to front line",100,3, false,"image/monsterSkill.png");
		
		slimeSkill1.addBuffsTarget(new Exhaust(3, 20));
		slimeSkill1.addBuffsTarget(new Vulnerability(2, 20));
		
		SingleTargetAttackSkill oniAutoAttack = new SingleTargetAttackSkill("Auto attack" , "target back line first" , 100,1, true,"image/monsterSkill.png");
		SingleTargetAttackSkill oniSkill1 = new SingleTargetAttackSkill("heavy attack","attack front line",150,5, false,"image/monsterSkill.png");
		oniSkill1.addBuffsTarget(new Vulnerability(2, 30));
		
		SingleTargetAttackSkill bloodHawkAutoAttack = new SingleTargetAttackSkill("Auto attack" , "target back line first" , 100,0,true,"image/monsterSkill.png");
		bloodHawkAutoAttack.addBuffsTarget(new Vulnerability(4, 10));
		SingleTargetAttackSkill bloodHawkSkill1 = new SingleTargetAttackSkill("super dangerous vulnetability" , "" , 100,2,true,"image/monsterSkill.png");
		bloodHawkSkill1.addBuffsTarget(new Vulnerability(2, 30));

		SingleTargetDefenseSkill gnomeAutoAttack = new SingleTargetDefenseSkill("heal lowest hp monster","single heal",150,2,"image/monsterSkill.png");
		MultiTargetDefenseSkill gnomeSkill1 = new MultiTargetDefenseSkill("Heal monster","AOE heal",100,3,"image/monsterSkill.png");
		
		
		golemUnit.addSkills(golemAutoAttack);
		golemUnit.addSkills(golemSkill1);
		
		slimeUnit.addSkills(slimeAutoAttack);
		slimeUnit.addSkills(slimeSkill1);
		
		oniUnit.addSkills(oniAutoAttack);
		oniUnit.addSkills(oniSkill1);
		
		bloodHawkUnit.addSkills(bloodHawkAutoAttack);
		bloodHawkUnit.addSkills(bloodHawkSkill1);
		
		gnomeUnit.addSkills(gnomeAutoAttack);
		gnomeUnit.addSkills(gnomeSkill1);
		
		this.poolMonsters.add(golemUnit);
		this.poolMonsters.add(slimeUnit);
		this.poolMonsters.add(oniUnit);
		this.poolMonsters.add(bloodHawkUnit);
		this.poolMonsters.add(gnomeUnit);
		for(Monster m:this.poolMonsters) {
			m.reset();
		}
	}
	/**
	  * generate all item in this game.
	  */
	public void generatePoolItems() {
		this.poolItems = new ArrayList<Item>();
		
		DamageReductionPotion damageReductionPotion = new DamageReductionPotion(1, 3, 30);
		EnhancePotion enhancePotion = new EnhancePotion(1, 3, 30);
		ExhaustPotion exhaustPotion = new ExhaustPotion(1, 3, 30);
		HealingPotion healingPotion = new HealingPotion(1, 30);
		VulnerabilityPotion vulnetabilityPotion = new VulnerabilityPotion(1, 3, 30);
		
		this.poolItems.add(damageReductionPotion);
		this.poolItems.add(enhancePotion);
		this.poolItems.add(exhaustPotion);
		this.poolItems.add(healingPotion);
		this.poolItems.add(vulnetabilityPotion);
	}
	
	
	//######## INITIAL STAGE ########
	/**
	  * generate monsters in each stage.
	  */
	public void generateMonsters() {
		int rand;
		ArrayList<Integer> nums = new ArrayList<Integer>();
		int i = 0;
		while(true) {
			rand = (int) ((Math.random()*5));
			
			if(nums.contains(rand)) {
				
			}else {
				nums.add(rand);
				Unit monsterUnit = this.poolMonsters.get(rand);
				monsterUnit.setPosition(i);
				this.addMonsters(monsterUnit);
				
				i++;
				if(i==MAX_PARTY) {
					return;
				}
			}
		}
	}
	
	/**
	  * generate boss in boss stage.
	  */
	public void generateBossStage() {
		Monster bossMonster = new Monster("Boss", "I am Boss of this game", 70, 50, 0, 300,"image/bossUnit.png");
		MultiTargetAttackSkill bossAutoAttack = new MultiTargetAttackSkill("boss auto attack", "AOE debuff and buff self", 10, 3,"image/monsterSkill.png");
		bossAutoAttack.addBuffsSelf( new Enhance(3, 30) );
		bossAutoAttack.addBuffsSelf( new DamageReduction(3, 30) );
		bossAutoAttack.addBuffsTarget( new Vulnerability(3, 30) );
		bossAutoAttack.addBuffsTarget( new Exhaust(3, 30) );
		
		
		MultiTargetAttackSkill bossSkill1 = new MultiTargetAttackSkill("AOE DMG", "aoe", 80 , 4,"image/monsterSkill.png");
		bossSkill1.addBuffsSelf( new Vulnerability(3,50) );
		
		MultiTargetAttackSkill bossSkill2 = new MultiTargetAttackSkill("ULT", "AOE", 120, 5,"image/monsterSkill.png");
		bossSkill2.addBuffsSelf( new Vulnerability(2,50) );
		
		bossMonster.addSkills(bossAutoAttack);
		bossMonster.addSkills(bossSkill1);
		bossMonster.addSkills(bossSkill2);
		bossMonster.reset();
		
		
		
		this.monsters.add(bossMonster);

	}
	
	//######## END STAGE ########
	/**
	  * generate item when we clear each stage and add to inventory.
	  * @return itemDrop arraylist item drop.
	  */
	public ArrayList<Item> generateItemDrop(){
		
		ArrayList<Item> itemDrop = new ArrayList<Item>();
		int rand;
		for(int i = 0;i<ITEM_DROP;i++) {
			rand = (int) ((Math.random()*5));
			Item item = this.poolItems.get(rand);
			Item newItem;
			if(item instanceof DamageReductionPotion) {
				newItem = new DamageReductionPotion((DamageReductionPotion)item);
			}else if(item instanceof EnhancePotion) {
				newItem = new EnhancePotion((EnhancePotion)item);
			}else if(item instanceof ExhaustPotion) {
				newItem = new ExhaustPotion((ExhaustPotion)item);
			}else if(item instanceof HealingPotion) {
				newItem = new HealingPotion((HealingPotion)item);
			}else if(item instanceof VulnerabilityPotion) {
				newItem = new VulnerabilityPotion((VulnerabilityPotion)item);
			}else {
				System.out.println("Item Drop error");
				newItem = null;
			}
			
			this.addItem(newItem);
			itemDrop.add( item );
		}
		
		return itemDrop;
	}
	
	/**
	  * init this game.
	  */
	public static void initGame() {
		isGameActive = true;
		GameLogic.getInstance().newGame();
		GameLogic.getInstance().setCurrentHero( GameLogic.getInstance().getHeros().get(heroOrder) );

	}
	
	/**
	  * start each stage in this game and update GUI.
	  */
	public static void startStageGame() {
		System.out.println( "distance NOW : "+GameLogic.getInstance().getDistance() );
		setMonsterTurn(false);
		
		if(isBossStage) {
			GameLogic.getInstance().generateBossStage();
		}else {
			
			GameLogic.getInstance().generateMonsters();

		}
		GameLogic.getInstance().initPointer();
		GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatUnit();
		GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatDisplay();
		
		isStageClear = false;
		isStageFail = false;
		heroOrder = 0;
		GameLogic.getInstance().setCurrentHero( GameLogic.getInstance().getHeros().get(heroOrder) );
		
	}
	
	/**
	  * update state game after skill is clicked.
	  */
	public static void heroAction() {
		
		updateStateGame();
		if(isStageClear) {
			if(isBossStage) {
				System.out.println("YOU WIN!!!");
				
				try {
					GameLogic.getInstance().getCombatController().switchtoGameClear();
				} catch (IOException e) {
					System.out.println("game clear error");
					e.printStackTrace();
				}
				heroOrder = 0;
				isBossStage = false;
				isGameActive = false;
				return;
			}
			
			//GameLogic.getInstance();
			GameLogic.collectItem();
			GameLogic.getInstance().getCombatController().getItemGridPane().updateState();
			GameLogic.getInstance().getCombatController().dice.setEnable(true);

			return;
		}
		
		heroOrder++;
		
		Unit heroUnit;
		while(heroOrder<MAX_PARTY) {
			heroUnit = GameLogic.getInstance().getHeros().get(heroOrder);
			if(heroUnit.isAlive()) {
				break;
			}
			else {
				heroOrder++;
			}
		}
		if(heroOrder >= MAX_PARTY) {
			heroOrder = 0;
			monsterTurn();
//			while(heroOrder<MAX_PARTY) {
//				heroUnit = GameLogic.getInstance().getHeros().get(heroOrder);
//				System.out.println(heroUnit);
//				if(heroUnit.isAlive()) {
//					break;
//				}
//				else {
//					heroOrder++;
//				}
//			}
//			System.out.println("hero "+heroOrder);
			return;
		}

		GameLogic.getInstance().setCurrentHero( GameLogic.getInstance().getHeros().get(heroOrder) );

	}
	
	/**
	  * monster auto use skill.
	  */
	public static void monsterTurn() {

		Thread monsterDelayThread = new Thread() {
			public void run () {
				setMonsterTurn(true);
				ArrayList<Unit> monsters = GameLogic.getInstance().getMonsters();
				Unit monster;
				
				for(int i = 0;i<monsters.size();i++) {

					monster = GameLogic.getInstance().getUnitByPosition(i,monsters);
					if( monster == null || !monster.isAlive() ) {
						continue;
					}
					try {
						Thread.sleep(600);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					monster.useSkill(null);
					GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatDisplay();
					GameLogic.getInstance().updateTargetPointer();
					GameLogic.getInstance().getCombatController().getCombatDisplay().updatePointer();
					updateStateGame();
					
					if(isStageFail) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {

							e1.printStackTrace();
						}
						Platform.runLater( ()->{
							try {
								GameLogic.getInstance().getCombatController().switchtoGameOver();
							} catch (IOException e) {
								System.out.println("game over error");
								e.printStackTrace();
							}
						});
						heroOrder = 0;
						isGameActive = false;
						return;
					}
				}
				setMonsterTurn(false);
				Unit heroUnit;
				while(heroOrder<MAX_PARTY) {
					heroUnit = GameLogic.getInstance().getHeros().get(heroOrder);
					System.out.println(heroUnit);
					if(heroUnit.isAlive()) {
						break;
					}
					else {
						heroOrder++;
					}
				}
				GameLogic.getInstance().countdownGame();
				GameLogic.getInstance().getCombatController().getCombatDisplay().updateCombatDisplay();
				GameLogic.getInstance().setCurrentHero( GameLogic.getInstance().getHeros().get(heroOrder) );
				GameLogic.getInstance().getCombatController().getSkillPane().updateState();
			}
		};
		monsterDelayThread.start();

	}
	/**
	  * update state game
	  */
	public static void updateStateGame() {
		isStageClear = GameLogic.getInstance().stageClear();
		isStageFail = GameLogic.getInstance().stageFail();
	}
	
	/**
	  * generate item drop and show inventory.
	  */
	public static void collectItem() {
		GameLogic.getInstance().generateItemDrop();

		GameLogic.getInstance().showInventory();
	
	}

}
	
