package logic;

import java.util.ArrayList;

import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.EnumSelector;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Regeneration;
import buff.type.Vulnetability;
import entity.base.Buff;
import entity.base.Unit;
import item.type.BuffPotion;
import skill.type.AttackSkill;
import skill.type.DefenceSkill;
import skill.type.MultiTargetAttackSkill;
import skill.type.MultiTargetDefenceSkill;
import skill.type.SingleTargetAttackSkill;
import skill.type.SingleTargetDefenceSkill;

public class GameLogic {
	private static GameLogic instance = null;
	private ArrayList<Unit> heros;
	private ArrayList<Unit> monsters;
	private ArrayList<Unit> poolMonsters;
	private int targetedHero;
	private int targetedMonster;
	private int distance;
	static final int MAX_DISTANCE = 20;
	static final int MAX_PARTY = 3;
	private Inventory inventory;
	
	
	//######## GAME LOGIC ########
	private GameLogic() {
		this.newGame();
	}
	
	public static GameLogic getInstance() {
		if(instance == null) {
			instance = new GameLogic();
		}
		return instance;
	}
	//######## DISTANCE & DICE ########
	public int rollDice() {
		int i = (int) ((Math.random()*5) +1);
		//this.setDistance(this.getDistance() + i);
		return i;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public boolean setDistance(int distance) {
		
		if( distance >= MAX_DISTANCE ) {
			this.distance = MAX_DISTANCE;
			return true;
		}
		this.distance = distance;
		return false;
	}
	
	//######## TURN & STAGE HANDLER ########
	
	public void countdownGame() {
		for(Unit unit:this.getHeros()) {
			unit.countdownAll();
		}
		for(Unit unit:this.getMonsters()) {
			unit.countdownAll();
		}
	}
	
	public void resetUnits() {
		for(Unit unit:this.getHeros()) {
			unit.reset();
		}
		for(Unit unit:this.getMonsters()) {
			unit.reset();
		}
		this.getMonsters().clear();
	}
	
	public void startStage() {
		this.setTargetedHero( this.getFrontLineUnit(heros).getPosition() );
		this.setTargetedMonster( this.getFrontLineUnit(monsters).getPosition() );
	}
	
	public boolean stageClear() {
		for(Unit unit :this.getMonsters()) {
			if(unit.isAlive()) {
				System.out.println(unit);
				return false;
			}
		}
		return true;
	}
	
	public boolean stageFail() {
		for(Unit unit :this.getHeros()) {
			if(unit.isAlive()) {
				return false;
			}
		}
		return true;
	}
	
	public void updateTargetPointer() {
		Unit unit;
		if(!this.heros.get( this.getTargetedHero()).isAlive()) {
			unit = this.getFrontLineUnit(heros);
			if(unit == null) {
				this.setTargetedHero( 0 );
			}else {
				this.setTargetedHero( unit.getPosition() );
			}

		}
		if(!this.monsters.get( this.getTargetedMonster()).isAlive()) {
			unit = this.getFrontLineUnit(monsters);
			if(unit == null) {
				this.setTargetedMonster( 0 );
			}else {
				this.setTargetedMonster( unit.getPosition() );
			}
		}
	}
	
	//######## TARGET POINTER HANDLER ########
	
	public int findTarget(Unit unit,boolean b) {
		if(this.getHeros().contains(unit) == b) {
			return this.getTargetedHero();
		}else if(this.getMonsters().contains(unit) == b) {
			return this.getTargetedMonster();
		}else {
			return -1;
		}
	}
	
	public int getTargetedHero() {
		return targetedHero;
	}

	public void setTargetedHero(int targetedHero) {
		if(this.getHeros().get(targetedHero).isAlive()) {
			this.targetedHero = targetedHero;
		}
	}

	public int getTargetedMonster() {
		return targetedMonster;
	}

	public void setTargetedMonster(int targetedMonster) {
		if(this.getMonsters().get(targetedMonster).isAlive()) {
			this.targetedMonster = targetedMonster;
		}
	}

	//######## SINGLE UNIT HANDLER ########
	
	public Unit getUnitByPosition(int p,ArrayList<Unit> units) {
		for(int i = 0;i<MAX_PARTY;i++) {
			if(units.get(i).getPosition() == p) {
				return units.get(i);
			}
		}
		return null;
	}
	
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
	
	public Unit getLowestHealthUnit( ArrayList<Unit> units ) {
		Unit u = getFrontLineUnit(units);
		for(Unit unit:units) {
			if(unit.isAlive() && unit.getHealth() < u.getHealth() ) {
				u = unit;
			}
		}
		return u;
	}
	
//	public void deathUnit(Unit u) {
//	u.setAlive(false);
//	ArrayList<Unit> party = findParty(u, true);
//	for(Unit unit:party) {
//		if(unit.getPosition() > u.getPosition() ) {
//			unit.setPosition(unit.getPosition()-1);
//		}
//	}
//}
//
//public void reviveUnit(Unit u) {
//	u.setAlive(true);
//	ArrayList<Unit> party = findParty(u, true);
//	for(Unit unit:party) {
//		if(unit.getPosition()>=u.getPosition()) {
//			unit.setPosition(0);
//		}
//	}
//}
	
	//######## PARTY HANDLER ########

	public ArrayList<Unit> getHeros() {
		return heros;
	}

	public void addHeros(Unit heros) {
		this.heros.add(heros);
	}

	public ArrayList<Unit> getMonsters() {
		return monsters;
	}

	public void addMonsters(Unit monsters) {
		this.monsters.add(monsters);
	}
	
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
	public void newGame() {
		this.setDistance(0);
		this.inventory = new Inventory();
		this.gennerateHerosParty();
		
		this.monsters = new ArrayList<Unit>();
		this.generatePoolMonsters();
		
	}
	
	public void gennerateHerosParty() {
		this.heros = new ArrayList<Unit>();
		
		Unit warriorUnit = new Unit("Warrior", "I am warrior.", 50, 30, 0, 100);
		Unit archerUnit = new Unit("Archer", "I am archer.", 50, 10, 1, 50);
		Unit medicUnit = new Unit("Medic", "I am medic", 40, 20, 2, 75);
		heros.add(warriorUnit);
		heros.add(archerUnit);
		heros.add(medicUnit);
		
		SingleTargetAttackSkill warriorAutoAttack = new SingleTargetAttackSkill("Auto attack","can't target", 100,1, false);
		SingleTargetAttackSkill warriorSkill1 = new SingleTargetAttackSkill("atk & self buff","give dmg reduc to self",120,3,false);
		warriorSkill1.addBuffsSelf( new DamageReduction(2, 30) );
		MultiTargetAttackSkill warriorSkill2 = new MultiTargetAttackSkill("AOE atk","can't target",120,5);
		warriorSkill2.addBuffsSelf( new Exhaust(2, 30) );
		
		SingleTargetAttackSkill medicAutoAttack = new SingleTargetAttackSkill("Auto attack","can't target and give exhaust to attacked unit",100,1, false);
		medicAutoAttack.addBuffsTarget(new Exhaust(1 , 30));
		SingleTargetDefenceSkill medicSkill1 = new SingleTargetDefenceSkill("heal","single heal",100,3);
		MultiTargetDefenceSkill medicSkill2 = new MultiTargetDefenceSkill("AOE buff","AOE buff",0,6);
		medicSkill2.addBuffsTarget( new DamageReduction(1, 100) );
		medicSkill2.addBuffsTarget( new Enhance(2, 100) );
		
		SingleTargetAttackSkill archerAutoAttack = new SingleTargetAttackSkill("Auto attack","Give vulnetability to target",100,1, true);
		archerAutoAttack.addBuffsTarget(new Vulnetability(2, 20));
		SingleTargetAttackSkill archerSkill1 = new SingleTargetAttackSkill("DPS skill","enhance self and vulnetability target",200,2, true);
		archerSkill1.addBuffsTarget(new Vulnetability(2, 30));
		archerSkill1.addBuffsSelf( new Enhance(3, 20) );
		MultiTargetAttackSkill archerSkill2 = new MultiTargetAttackSkill("super debuff","give all enimies vulnetability and exhaust",100,4);
		archerSkill2.addBuffsTarget(new Vulnetability(2, 30));
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
	
	public void generatePoolMonsters() {
		this.poolMonsters = new ArrayList<Unit>();
		Unit golemUnit = new Unit("Golem", "I am golem", 50, 30, 0,100);
		Unit slimeUnit = new Unit("Slime", "I am slime", 20, 10, 0, 50);
		Unit oniUnit = new Unit("Oni", "I am Oni", 50 , 30, 0, 60);
		Unit bloodHawkUnit = new Unit("Blood Hawk", "I am blood hawk" ,40, 10, 0, 50);
		Unit gnomeUnit = new Unit("Gnome", "I am Gnome", 20, 10, 0, 50);
		
		
		SingleTargetAttackSkill golemAutoAttack = new SingleTargetAttackSkill("Auto attack","attack front line hero",100,2, false);
		MultiTargetAttackSkill golemSkill1 = new MultiTargetAttackSkill("Eathquake","AOE attack",100,5);

		SingleTargetAttackSkill slimeAutoAttack = new SingleTargetAttackSkill("Auto attack" , "heal self and give front line exhaust" , 100,1, false);
		slimeAutoAttack.addBuffsSelf(new Regeneration(40));

		SingleTargetAttackSkill slimeSkill1 = new SingleTargetAttackSkill("normal debuff","give debuff to front line",100,3, false);
		slimeSkill1.addBuffsTarget(new Exhaust(3, 20));
		slimeSkill1.addBuffsTarget(new Vulnetability(2, 20));
		
		SingleTargetAttackSkill oniAutoAttack = new SingleTargetAttackSkill("Auto attack" , "target back line first" , 100,2, true);
		SingleTargetAttackSkill oniSkill1 = new SingleTargetAttackSkill("heavy attack","attack front line",150,5, false);
		oniSkill1.addBuffsTarget(new Vulnetability(2, 30));
		
		SingleTargetAttackSkill bloodHawkAutoAttack = new SingleTargetAttackSkill("Auto attack" , "target back line first" , 100,1,true);
		bloodHawkAutoAttack.addBuffsTarget(new Vulnetability(4, 10));
		SingleTargetAttackSkill bloodHawkSkill1 = new SingleTargetAttackSkill("super dangerous vulnetability" , "" , 100,2,true);
		bloodHawkSkill1.addBuffsTarget(new Vulnetability(2, 30));

		SingleTargetDefenceSkill gnomeAutoAttack = new SingleTargetDefenceSkill("heal lowest hp monster","single heal",50,2);
		MultiTargetDefenceSkill gnomeSkill1 = new MultiTargetDefenceSkill("Heal monster","AOE heal",30,4);
		
		
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
		
	}
	
	//######## INITIAL STAGE ########
	public void generateMonsters() {
		int rand;
		ArrayList<Integer> nums = new ArrayList<Integer>();
		int i = 0;
		while(true) {
			rand = (int) ((Math.random()*5));
			System.out.println(rand);
			if(nums.contains(rand)) {
				
			}else {
				nums.add(rand);
				Unit monsterUnit = this.poolMonsters.get(rand);
				monsterUnit.setPosition(i);
				this.monsters.add(monsterUnit);
				
				i++;
				if(i==MAX_PARTY) {
					return;
				}
			}
		}

		
	}
}
	
