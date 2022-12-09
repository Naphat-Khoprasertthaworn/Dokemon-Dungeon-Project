package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Vulnetability;
import entity.base.Buff;
import entity.base.Unit;
import logic.GameLogic;
import skill.type.AttackSkill;
import skill.type.DefenceSkill;

public class AttackSkillTest {
	
	
	Unit warriorUnit = new Unit("Warrior", "I am warrior.", 50, 30, 0, 100);
	Unit archerUnit = new Unit("Archer", "I am archer.", 50, 10, 1, 50);
	Unit medicUnit = new Unit("Medic", "I am medic", 40, 20, 2, 75);
	
	
	
	AttackSkill autoAttackWarrior = new AttackSkill("Auto attack","can't target",100,1) {
		@Override
		public void skillEffect(ArrayList<Unit> units,int targetUnit, Unit owner) {
			System.out.println(GameLogic.getInstance().getFrontLineUnit(units));
			GameLogic.getInstance().getFrontLineUnit(units).takeDamage( (this.getRatio()/100) * owner.getTotalAttack() );
			System.out.println(GameLogic.getInstance().getFrontLineUnit(units).getHealth());
		}
	};
	
	AttackSkill warriorSkill1 = new AttackSkill("attack then buff reduc to self","can't target",150,3) {
		@Override
		public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
			GameLogic.getInstance().getFrontLineUnit(units).takeDamage( (this.getRatio()/100) * owner.getTotalAttack() );
			owner.addBuff( new DamageReduction(3, 20) );
		}
	};
	
	AttackSkill warriorSkill2 = new AttackSkill("AOE attack","can't target",70,5) {
		@Override
		public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
			for(Unit unit:units) {
				unit.takeDamage( (this.getRatio()/100)* (owner.getTotalAttack()+owner.getTotalDefense()) );
			}
			owner.addBuff( new Exhaust(2,50) );
		}
	};
	
	AttackSkill autoAttackMedic = new AttackSkill("Auto attack","can't target and give exhaust to attacked unit",100,1) {
		@Override
		public void skillEffect(ArrayList<Unit> units,int targetUnit, Unit owner) {
			GameLogic.getInstance().getFrontLineUnit(units).takeDamage( (this.getRatio()/100) * owner.getTotalAttack() );
			GameLogic.getInstance().getFrontLineUnit(units).addBuff( new Exhaust(2 , 30) );
		}
	};
	
	DefenceSkill medicSkill1 = new DefenceSkill("heal","single heal",100,3) {
		@Override
		public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
			units.get(targetUnit).receiveHeal( (this.getRatio()/100)*owner.getTotalAttack() );
			
		}
	};
	
	DefenceSkill medicSkill2 = new DefenceSkill("super buff","AOE buff",100,6) {
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
			// TODO Auto-generated method stub
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

	Unit monster1 = new Unit("wolf", "do nothing", 50, 30,0,100);
	Unit monster2 = new Unit("wolf", "do nothing", 50, 30,1,100);
	Unit monster3 = new Unit("wolf", "do nothing", 50, 30,2,100);

	AttackSkill monsterAuto1 = new AttackSkill("bite","bite rang mak mak",70,1) {

		@Override
		public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
			GameLogic.getInstance().getFrontLineUnit(units).takeDamage( (this.getRatio()/100 )*owner.getTotalAttack()) ;
		}
		
	};
	
	AttackSkill monsterAuto2 = new AttackSkill("bite","bite rang mak mak",70,1) {

		@Override
		public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
			GameLogic.getInstance().getFrontLineUnit(units).takeDamage( (this.getRatio()/100 )*owner.getTotalAttack()) ;
		}
		
	};
	
	AttackSkill monsterAuto3 = new AttackSkill("bite","bite rang mak mak",70,1) {

		@Override
		public void skillEffect(ArrayList<Unit> units, int targetUnit, Unit owner) {
			
			GameLogic.getInstance().getFrontLineUnit(units).takeDamage( (this.getRatio()/100 )*owner.getTotalAttack()) ;
		}
		
	};
	
	ArrayList<Unit> heros = new ArrayList<Unit>();
	ArrayList<Unit> monsters = new ArrayList<Unit>();
	
	@BeforeEach
	void setUp() {
		heros = new ArrayList<Unit>();
		monsters = new ArrayList<Unit>();
		heros.add(warriorUnit);
		heros.add(archerUnit);
		heros.add(medicUnit);
		
		warriorUnit.addSkills(autoAttackWarrior);
		warriorUnit.addSkills(warriorSkill1);
		warriorUnit.addSkills(warriorSkill2);
		
		archerUnit.addSkills(autoAttackArcher);
		archerUnit.addSkills(archerSkill1);
		archerUnit.addSkills(archerSkill2);
		
		medicUnit.addSkills(autoAttackMedic);
		medicUnit.addSkills(medicSkill1);
		medicUnit.addSkills(medicSkill2);
		
		monsters.add(monster3);
		monsters.add(monster2);
		monsters.add(monster1);
		
		monster1.addSkills(monsterAuto1);
		monster2.addSkills(monsterAuto2);
		monster3.addSkills(monsterAuto3);
		
		
	}
	
	
	@Test
	void useAutoSkill() {
		
		warriorUnit.getSkills().get(0).skillEffect(monsters,0,warriorUnit);
		assertEquals(0, monsters.get(0).getHealth());
		
		
	}
	
	@Test
	void useBuffAtkSkill() {
		
	}
	
	@Test
	void testCoundownBuff() {
		
	}
	
	@Test
	void testReset() {
		
		
		
	}
	

}

