package entity.base;

import java.util.ArrayList;

public abstract class Skill implements Countdownable {
	private int ratio;
	private String name;
	private String text;
	private int cd;
	private ArrayList<Buff> buffsTarget;
	private ArrayList<Buff> buffsSelf;
	private int inCombatCd;
	
	
	public Skill(String name,String text,int ratio,int cd) {
		this.setName(name);
		this.setText(text);
		this.setRatio(ratio);
		this.setCd(cd);
		this.buffsSelf = new ArrayList<Buff>();
		this.buffsTarget = new ArrayList<Buff>();
	}
	
	public Skill(Skill s) {
		this.setName(s.getName());
		this.setText(s.getText());
		this.setRatio(s.getRatio());
		this.setCd(s.getCd());
	}
	
	public abstract boolean skillActive(Unit u);
	
	public void countDown() {
		this.setInCombatCd(inCombatCd - 1);
	}
	
	
	public boolean readySkill() {
		return this.inCombatCd == 0;
	}
	
	
	// getter setter
	

	public int getRatio() {
		return ratio;
	}

	public ArrayList<Buff> getBuffsTarget() {
		return buffsTarget;
	}

	public void addBuffsTarget(Buff buff) {
		this.buffsTarget.add(buff);
	}

	public ArrayList<Buff> getBuffsSelf() {
		return buffsSelf;
	}

	public void addBuffsSelf(Buff buff) {
		this.buffsSelf.add(buff);
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

	public String toString() {
		return this.getName() + " : " + this.getInCombatCd() + "/" + this.getCd();
		
	}

	
	
	
}
