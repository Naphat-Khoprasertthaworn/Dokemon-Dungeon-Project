package entity.base;

import java.util.ArrayList;

public abstract class Skill implements Countdownable {
	private int ratio;
	private String name;
	private String text;
	private int cd;
	private ArrayList<Buff> buffs;
	//private boolean isAOE;
	
	private int inCombatCd;
	
	
	public Skill(String name,String text,int ratio,int cd) {
		this.setName(name);
		this.setText(text);
		this.setRatio(ratio);
		this.setCd(cd);
		//this.setAOE(isAOE);
		
	}
	
	
	public  abstract void skillActive(ArrayList<Unit> units, Unit targetUnit,Unit owner);
	
	
	//public void useSkill( ArrayList<Unit> units, Unit targetUnit ) {
	//	this.skillActive(units, targetUnit,this);
	//	this.setInCombatCd(this.cd);
	//}
	
	
	public void countDown() {
		this.setInCombatCd(inCombatCd - 1);
	}
	
	
	public boolean readySkill() {
		return this.inCombatCd == 0;
	}
	
	
	// getter setter
	public ArrayList<Buff> getBuffs() {
		return buffs;
	}

	public void addBuffs(Buff buffs) {
		this.buffs.add(buffs);
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
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

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}


	public int getInCombatCd() {
		return inCombatCd;
	}


	public void setInCombatCd(int inCombatCd) {
		if(inCombatCd<0) {
			this.inCombatCd = 0;
			return;
		}
		this.inCombatCd = inCombatCd;
	}


//	public boolean isAOE() {
//		return isAOE;
//	}
//
//
//	public void setAOE(boolean isAOE) {
//		this.isAOE = isAOE;
//	}
	
	
	
}
