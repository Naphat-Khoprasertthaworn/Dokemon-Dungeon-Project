package logic;

import java.util.ArrayList;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Vulnetability;
import entity.base.Buff;
import entity.base.Unit;
import item.type.BuffPotion;
import skill.type.AttackSkill;
import skill.type.DefenceSkill;

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
		if( this.getDistance() >= MAX_DISTANCE ) {
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
	
	public void resetHeros() {
		for(Unit unit:this.getHeros()) {
			unit.reset();
		}
	}
	
	public boolean stageClear() {
		for(Unit unit :this.getMonsters()) {
			if(unit.isAlive()) {
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
		if(!this.heros.get( this.getTargetedHero()).isAlive()) {
			this.setTargetedHero( this.getFrontLineUnit(heros).getPosition() );
		}
		if(!this.monsters.get( this.getTargetedMonster()).isAlive()) {
			this.setTargetedMonster( this.getFrontLineUnit(monsters).getPosition() );
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
		
		AttackSkill autoAttackWarrior = new AttackSkill("Auto attack","can't target",100,1) {
			@Override
			public void skillEffect(ArrayList<Unit> units,int targetUnit, Unit owner) {
				getFrontLineUnit(units).takeDamage( (this.getRatio()/100) * owner.getTotalAttack() );
			}
		};
		
		AttackSkill warriorSkill1 = new AttackSkill("atk & buff self","can't target",150,3) {
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				getFrontLineUnit(units).takeDamage( (this.getRatio()/100) * owner.getTotalAttack() );
				owner.addBuff( new DamageReduction(3, 20) );
			}
		};
		
		AttackSkill warriorSkill2 = new AttackSkill("AOE atk","can't target",70,5) {
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				for(Unit unit:units) {
					unit.takeDamage( this.getRatio()*(owner.getTotalAttack()+owner.getTotalDefense())/100 );
				}
				owner.addBuff( new Exhaust(2,50) );
			}
		};
		
		AttackSkill autoAttackMedic = new AttackSkill("Auto attack","can't target and give exhaust to attacked unit",100,1) {
			@Override
			public void skillEffect(ArrayList<Unit> units,int targetUnit, Unit owner) {
				getFrontLineUnit(units).takeDamage( (this.getRatio()/100) * owner.getTotalAttack() );
				getFrontLineUnit(units).addBuff( new Exhaust(2 , 30) );
			}
		};
		DefenceSkill medicSkill1 = new DefenceSkill("heal","single heal",100,3) {
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				System.out.println(units.get(targetUnit));
				units.get(targetUnit).receiveHeal( (this.getRatio()*owner.getTotalAttack())/100 );
				
			}
		};
		
		DefenceSkill medicSkill2 = new DefenceSkill("AOE buff","AOE buff",100,6) {
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				for(Unit unit:units) {
					unit.addBuff(new DamageReduction(1, 100));
					unit.addBuff(new Enhance(2, 100));
				}
			}
		};
		
		AttackSkill autoAttackArcher = new AttackSkill("Auto attack","Give vulnetability to target",100,1) {
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				units.get(targetUnit).addBuff(new Vulnetability(2, 20));
				units.get(targetUnit).takeDamage( (this.getRatio()/100)*owner.getTotalAttack() );
			}
		};
		
		AttackSkill archerSkill1 = new AttackSkill("DPS skill","enhance self and vulnetability target",200,2) {
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				owner.addBuff( new Enhance(3, 20) );
				units.get(targetUnit).takeDamage( (this.getRatio()/100)*owner.getTotalAttack() );
				owner.addBuff( new Vulnetability(2, 30) );
			}
		};
		
		AttackSkill archerSkill2 = new AttackSkill("super debuff","give all enimies vulnetability and exhaust",100,4) {
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				for(Unit unit:units) {
					unit.takeDamage((this.getRatio()/100)*owner.getTotalAttack());
					unit.addBuff( new Vulnetability(2, 30) );
					unit.addBuff( new Exhaust(1, 30) );
				}
			}
		};
		warriorUnit.addSkills(autoAttackWarrior);
		warriorUnit.addSkills(warriorSkill1);
		warriorUnit.addSkills(warriorSkill2);
		
		archerUnit.addSkills(autoAttackArcher);
		archerUnit.addSkills(archerSkill1);
		archerUnit.addSkills(archerSkill2);
		
		medicUnit.addSkills(autoAttackMedic);
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
		
		AttackSkill autoAttackGolem = new AttackSkill("Auto attack","attack front line hero",100,2) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				getFrontLineUnit(units).takeDamage( (this.getRatio()*owner.getTotalAttack() )/100 );
			}
		};
		
		AttackSkill golemSkill1 = new AttackSkill("Eathquake","AOE attack",100,5) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				for(Unit u:units) {
					u.takeDamage( (this.getRatio()*owner.getTotalAttack())/100 );
				}
			}
		};
		
		AttackSkill autoAttackSlime = new AttackSkill("Auto attack" , "heal self and give front line exhaust" , 100,1) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				Unit unit = getFrontLineUnit(units);
				unit.addBuff(new Exhaust(1, 10));
				int dmg = unit.takeDamage( (this.getRatio() * owner.getTotalAttack())/100 );
				owner.receiveHeal(dmg);
			}
		};
		
		AttackSkill slimeSkill1 = new AttackSkill("normal debuff","give debuff to front line",100,3) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				Unit u = getFrontLineUnit(units);
				u.addBuff( new Exhaust(3, 20) );
				u.addBuff( new Vulnetability(2, 20) );
				u.takeDamage( (this.getRatio() * owner.getTotalAttack())/100 );
			}
		};
				
				
		AttackSkill autoAttackOni = new AttackSkill("Auto attack" , "target back line first" , 100,2) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				getBackLineUnit(units).takeDamage( (this.getRatio()*owner.getTotalAttack())/100 );
				
			}
		};
		
		AttackSkill oniSkill1 = new AttackSkill("heavy attack","attack front line",120,5) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				Unit u = getFrontLineUnit(units);
				u.addBuff( new Vulnetability(2, 50));
				u.takeDamage((this.getRatio()*owner.getTotalAttack())/100 );
			}
		};
		
		AttackSkill autoAttackBloodHawk = new AttackSkill("Auto attack" , "target back line first" , 100,1) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				Unit u = getBackLineUnit(units);
				u.takeDamage( (this.getRatio()*owner.getTotalAttack())/100 );
				if(u.isAlive()) {
					u.addBuff(new Vulnetability(4, 10));
				}
			}
		};
		
		AttackSkill bloodHawkSkill1 = new AttackSkill("super dangerous vulnetability" , "attack vulnetability incresing" , 100,2) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				Unit u = getBackLineUnit(units);
				for(Buff b: u.getBuffs()) {
					if(b instanceof Vulnetability) {
						u.takeDamage( ((this.getRatio()+50)*owner.getTotalAttack())/100 );
						return;
					}
				}
				u.addBuff(new Vulnetability(4, 10));
				u.takeDamage( (this.getRatio()*owner.getTotalAttack())/100 );
			}
		};
		
		DefenceSkill autoAttackGnome = new DefenceSkill("heal lowest hp monster","single heal",50,2) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				getLowestHealthUnit(units).receiveHeal( (this.getRatio()*owner.getTotalAttack())/100 );
				
			}
		};
		
		DefenceSkill gnomeSkill1 = new DefenceSkill("Heal monster","AOE heal",30,4) {
			
			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				for(Unit unit:units) {
					unit.receiveHeal( this.getRatio()*owner.getTotalAttack() );
				}
			}
		};
		
		golemUnit.addSkills(autoAttackGolem);
		golemUnit.addSkills(golemSkill1);
		
		slimeUnit.addSkills(autoAttackSlime);
		slimeUnit.addSkills(slimeSkill1);
		
		oniUnit.addSkills(autoAttackOni);
		oniUnit.addSkills(oniSkill1);
		
		bloodHawkUnit.addSkills(autoAttackBloodHawk);
		bloodHawkUnit.addSkills(bloodHawkSkill1);
		
		gnomeUnit.addSkills(autoAttackGnome);
		gnomeUnit.addSkills(gnomeSkill1);
		
		this.poolMonsters.add(golemUnit);
		this.poolMonsters.add(slimeUnit);
		this.poolMonsters.add(oniUnit);
		this.poolMonsters.add(bloodHawkUnit);
		this.poolMonsters.add(gnomeUnit);
		
	}
	
	//######## INITIAL STAGE ########
	public void generateMonsters() {
		this.monsters = new ArrayList<Unit>();
		
		Unit monster1 = new Unit("wolf", "do nothing", 50, 30,0,100);
		Unit monster2 = new Unit("wolf", "do nothing", 50, 30,1,100);
		Unit monster3 = new Unit("wolf", "do nothing", 50, 30,2,100);
		this.monsters.add(monster1);
		this.monsters.add(monster2);
		this.monsters.add(monster3);
		
		
		AttackSkill monsterAuto1 = new AttackSkill("bite","bite rang mak mak",70,1) {

			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				getFrontLineUnit(units).takeDamage( (this.getRatio()*owner.getTotalAttack())/100 ) ;
			}
			
		};
		
		AttackSkill monsterAuto2 = new AttackSkill("bite","bite rang mak mak",70,1) {

			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				getFrontLineUnit(units).takeDamage( (this.getRatio()*owner.getTotalAttack())/100 ) ;
			}
			
		};
		
		AttackSkill monsterAuto3 = new AttackSkill("bite","bite rang mak mak",70,1) {

			@Override
			public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
				getFrontLineUnit(units).takeDamage( (this.getRatio()*owner.getTotalAttack())/100 ) ;
			}
			
		};
		
		monster1.addSkills(monsterAuto1);
		monster2.addSkills(monsterAuto2);
		monster3.addSkills(monsterAuto3);
		
		
	}
}
	
