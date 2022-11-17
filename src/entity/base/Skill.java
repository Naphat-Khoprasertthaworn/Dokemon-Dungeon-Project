package entity.base;

import java.util.ArrayList;

public abstract class Skill {
	private int ratio;
	private String name;
	private String text;
	private int cd;
	private ArrayList<Buff> buffs;
	
	public Skill(String name,String text,int ratio,int cd) {
		this.setName(name);
		this.setText(text);
		this.setRatio(ratio);
		this.setCd(cd);
		
	}
	
	
	public  abstract void skillActive(ArrayList<Unit> units, Unit targetUnit);
	
	
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
	
	
	
}
