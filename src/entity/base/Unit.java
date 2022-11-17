package entity.base;

import java.util.ArrayList;

public abstract class Unit {
	
	private int attack;
	private int maxHealth;
	private String name;
	private String text;
	private int position;
	
	private int health;
	private ArrayList<Buff> buffs;
	private boolean attackToken;
	
	
	public Unit( String name, String text, int attack , int position,int maxHealth ) {
		this.setName(name);
		this.setText(text);
		this.setAttack(attack);
		this.setMaxHealth(maxHealth);
		this.setPosition(position);
		
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


	public boolean isAttackToken() {
		return attackToken;
	}


	public void setAttackToken(boolean attackToken) {
		this.attackToken = attackToken;
	}


	public ArrayList<Buff> getBuffs() {
		return buffs;
	}


	public void setBuffs(ArrayList<Buff> buffs) {
		this.buffs = buffs;
	}
	
	public void addBuff(Buff buff) {
		this.buffs.add(buff);
	}
	
	public void countdownBuffs() {
		for(Buff b:this.buffs) {
			b.countDown();
			if(b.getTurn()==0) {
				this.buffs.remove(b);
			}
		}
	}
	

}
