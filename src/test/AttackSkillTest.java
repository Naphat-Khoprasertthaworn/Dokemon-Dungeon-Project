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
import skill.type.AttackSkill;

public class AttackSkillTest {
	
	
	//Unit hero1 = new Unit("tank","i am tank",20,30,1,100);
	AttackSkill autoattack = new AttackSkill("auto attack","normal attack",100,1) {
		@Override
		public void skillActive(ArrayList<Unit> units, Unit targetUnit,Unit owner) {
			
			targetUnit.takeDamage(  (this.getRatio()/100)* owner.getTotalAttack() );
		}
	};
	
	AttackSkill buffAtkSkill = new AttackSkill("buff","enhance",150,3) {

		@Override
		public void skillActive(ArrayList<Unit> units, Unit targetUnit, Unit owner) {
			owner.addBuff(new Enhance(3,30));
			System.out.println((this.getRatio()/100)* owner.getTotalAttack());
			for(Unit u:units) {
				u.takeDamage( (this.getRatio()/100)* owner.getTotalAttack() );
			}
			
		}
		
	};
	
	Unit hero1;
	Unit monster1;
	Unit monster2;
	ArrayList<Unit> monsters;
	//hero1.addSkills(auto);
	
	
	@BeforeEach
	void setUp() {
		hero1 = new Unit("tank","i am tank",20,30,1,100);
		monster1 = new Unit("tank","i am tank",20,0,1,30);
		monster2 = new Unit("tank","i am tank",20,30,1,20);
		//System.out.println("test2");
		monsters = new ArrayList<Unit>();
		
		monsters.add(monster1);
		monsters.add(monster2);
		
		monster1.addSkills(autoattack);
		
		hero1.addSkills(autoattack);
		hero1.addSkills(buffAtkSkill);
		//System.out.println("i am done");
		
	}
	
	
	@Test
	void useAutoSkill() {
		//fail("Not yet implemented");
		//System.out.println("test1");
		//hero1.addSkills(autoattack);
		//System.out.println(hero1.getSkills().get(0).getName());
		assertEquals("auto attack",hero1.getSkills().get(0).getName());
		//System.out.println(monster1.getHealth());
		assertEquals(true,hero1.useSkill(0, null, monster1));
		//System.out.println(monster1.getHealth());
		assertEquals(10,monster1.getHealth());
		
		
		
	}
	
	@Test
	void useBuffAtkSkill() {
		assertEquals(true,hero1.useSkill(1, monsters, monster1));
		assertEquals(4,monster1.getHealth());
		assertEquals(20,monster2.getHealth());
		
	}
	
	@Test
	void testCoundownBuff() {
		ArrayList<Unit> m = new ArrayList<Unit>();
		DamageReduction b = new DamageReduction(3,30);
		hero1.addBuff(b);
		assertEquals(true,hero1.useSkill(1, m, monster1));
		for(int i = 0;i<hero1.getBuffs().size();i++) {
			System.out.println(hero1.getBuffs().get(i).getName());
		}
		
		
		assertEquals(3,hero1.getBuffs().get(0).getTurn());
		hero1.countdownBuffs();
		assertEquals(2,hero1.getBuffs().get(0).getTurn());
		hero1.countdownBuffs();
		assertEquals(1,hero1.getBuffs().get(0).getTurn());
		hero1.countdownBuffs();
		//System.out.println(hero1.getBuffs().get(0));
		
		assertEquals(true,hero1.getBuffs().isEmpty());
		
		assertEquals(true,hero1.useSkill(0, monsters, monster1));
		assertEquals(10,monster1.getHealth());
		assertEquals(20,monster2.getHealth());
	}
	
	@Test
	void testReset() {
		hero1.setDefense(0);
		
		assertEquals(true,monster1.useSkill(0, monsters, hero1));
		
		assertEquals(80,hero1.getHealth());
		hero1.reset();
		assertEquals(100,hero1.getHealth());
		
		hero1.setDefense(100);
		
		assertEquals(true,hero1.useSkill(1, monsters, monster1));
		hero1.addBuff(new Vulnetability(3, 30));
		hero1.addBuff(new Exhaust(3, 30));
		
		for(int i = 0;i<hero1.getBuffs().size();i++) {
			System.out.println(hero1.getBuffs().get(i).getName());
		}
		
		
		System.out.println(hero1.getTotalAttack());
		System.out.println(hero1.getTotalDefense());
		
		
	}
	

}

