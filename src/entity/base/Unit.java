package entity.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import logic.GameLogic;


public class Unit {
	private int attack;
	private int maxHealth;
	private String name;
	private String text;
	private int position;
	private int defense;
	private boolean isAlive;
	
	private int health;
	private ArrayList<Buff> buffs;
	private ArrayList<Skill> skills;
	private int buffAttack;
	private int buffDefense;
	private Comparator<Skill> compSkill;

	public Unit( String name, String text, int attack, int defense , int position,int maxHealth ) {
		this.setName(name);
		this.setText(text);
		this.setAttack(attack);
		this.setMaxHealth(maxHealth);
		this.setPosition(position);
		this.setDefense(defense);
		this.skills = new ArrayList<Skill>();
		this.buffs = new ArrayList<Buff>();
		
		this.compSkill = (Skill s1,Skill s2)->{
			if(s1.getCd() > s2.getCd()) {
				return 1;
			}
			return -1;
		};
		
		this.reset();
		
		
	}

	
	public void countdownAll() {
		this.countdownBuffs();
		this.countdownSkills();
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		if(isAlive == false) {
			this.getBuffs().clear();
		}
		this.isAlive = isAlive;
	}

	public int takeDamage(int dmg) {
		dmg = dmg - this.getTotalDefense();
		if(dmg<=0) {
			return 0;
		}
		this.setHealth(this.getHealth()-dmg);
		return dmg;
	}
	
	public void receiveHeal(int heal) {
		if(heal<=0) {
			return;
		}
		this.setHealth(this.getHealth()+ heal);
	}
	
	public void reset() {
		this.setAlive(true);
		this.setHealth( this.getMaxHealth() );
		this.setBuffAttack(0);
		this.setBuffDefense(0);
		this.getBuffs().clear();
		for(Skill s:this.getSkills()) {
			s.setInCombatCd(0);
		}
	}
	
	public boolean useSkill( int order ) {
		if( this.skills.get(order).readySkill()==false ) {
			return false;
		}
		if(this.skills.get(order)==null) {
			return false;
		}
		System.out.println("############# "+ this.getName()+" use skill " + this.skills.get(order).getName());
		return this.skills.get(order).skillActive(this);
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}


	public void addSkills(Skill skill) {
		this.skills.add(skill);
		Collections.sort(this.getSkills(),this.compSkill);
	}
	
	public void countdownSkills() {
		for(Skill s:this.getSkills()) {
			s.countDown();
		}
	}
	
	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getMaxHealth() {
		return maxHealth;
	}


	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		if(health < 0) {
			this.setAlive(false);
			this.health = 0;
		}else if(health > this.maxHealth) {
			this.health = this.maxHealth;
		}else {
			this.health = health;
		}
		
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPosition() { //0 = Leader ,1 = DPS, 2 = support
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}


	public ArrayList<Buff> getBuffs() {
		return buffs;
	}


	public void setBuffs(ArrayList<Buff> buffs) {
		this.buffs = buffs;
	}
	
	public void addBuff(Buff buff) {
		if(!this.isAlive()) {
			return;
		}
		this.buffs.add(buff);
		buff.activeBuff(this);
	}
	
	public void countdownBuffs() {
		if(this.buffs.isEmpty()) {
			return;
		}

		int i = 0;
		while(true) {
			this.buffs.get(i).countDown();
			if(this.buffs.get(i).getTurn()==0) {
				this.buffs.get(i).removeSelf(this);
				this.buffs.remove(this.buffs.get(i));
			}
			else {
				i++;
			}
			if(i==this.buffs.size()) {
				break;
			}
			
		}

		
	}

	public int getBuffAttack() {
		return buffAttack;
	}

	public void setBuffAttack(int buffAttack) {
		this.buffAttack = buffAttack;
	}
	
	public int getTotalAttack() {
		return this.getAttack() + this.getBuffAttack();
	}
	
	public int getTotalDefense() {
		return this.getDefense()+this.getBuffDefense();
	}
	
	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		if(defense < 0) {
			this.defense = 0;
			return;
		}
		this.defense = defense;
	}

	public int getBuffDefense() {
		return buffDefense;
	}

	public void setBuffDefense(int buffDefense) {
		this.buffDefense = buffDefense;
	}

	public String toString() {
		return "😆 "+ this.getName() + "\n💥 Atk : " + this.getTotalAttack() + " / " + this.getAttack() + "\n❤️ Health: " + this.getHealth() +" / "+ this.getMaxHealth() + "\n🔰 Def : " + this.getTotalDefense() + " / " + this.getDefense();
	}
	

}
