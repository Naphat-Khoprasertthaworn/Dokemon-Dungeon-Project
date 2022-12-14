package entity.base;

import java.util.ArrayList;
/**
 * Skill class
 */
public abstract class Skill implements Countdownable {
	/**
	  * ratio
	  */
	private int ratio;
	/**
	  * name
	  */
	private String name;
	/**
	  * text
	  */
	private String text;
	/**
	  * cooldown
	  */
	private int cd;
	/**
	  * Arraylist of bufftarget
	  */
	private ArrayList<Buff> buffsTarget;
	/**
	  * Arraylist of buffself
	  */
	private ArrayList<Buff> buffsSelf;
	/**
	  * Actual cooldown in combat field
	  */
	private int inCombatCd;
	/**
	  * Image path
	  */
	private String imagePath;
	
	/**
	  * Constructor of Skill class
	  * @param name name of skill
	  * @param text text of skill
	  * @param ratio ratio of skill
	  * @param cd cooldown of skill
	  * @param imagePath image path of skill
	  */
	public Skill(String name,String text,int ratio,int cd,String imagePath) {
		this.setName(name);
		this.setText(text);
		this.setRatio(ratio);
		this.setCd(cd);
		this.buffsSelf = new ArrayList<Buff>();
		this.buffsTarget = new ArrayList<Buff>();
		this.setImagePath(imagePath);
	}
	
	/**
	  * Copy constructor of skill 
	  * @param s skill to copy
	  */
	public Skill(Skill s) {
		this.setName(s.getName());
		this.setText(s.getText());
		this.setRatio(s.getRatio());
		this.setCd(s.getCd());
	}
	
	/**
	  * set skill active or not to unit
	  * @param u Unit to set skill active
	  */
	public abstract boolean skillActive(Unit u);
	
	/**
	  * Countdown skill cooldown
	  */
	public void countDown() {
		this.setInCombatCd(inCombatCd - 1);
	}
	
	/**
	  * Check skill is ready or not
	  * 
	  */
	public boolean readySkill() {
		return this.inCombatCd == 0;
	}
	
	
	// getter setter
	
	/**
	  * Getter of ratio
	  * @return ratio
	  */
	public int getRatio() {
		return ratio;
	}
	/**
	  * Getter of getBuffsTarget
	  * @return buffstarget
	  */
	public ArrayList<Buff> getBuffsTarget() {
		return buffsTarget;
	}
	/**
	  * add buff to buffsTarget
	  * @param buff buff to add
	  */
	public void addBuffsTarget(Buff buff) {
		this.buffsTarget.add(buff);
	}
	/**
	  * Getter of buffsSelf 
	  * @return buffsSelf
	  */
	public ArrayList<Buff> getBuffsSelf() {
		return buffsSelf;
	}
	/**
	  * add buff to buffsSelf
	  * @param buff buff to add
	  */
	public void addBuffsSelf(Buff buff) {
		this.buffsSelf.add(buff);
	}
	/**
	  * Setter of ratio
	  * @param ratio ratio to set
	  */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	/**
	  * Getter of name
	  * @return name
	  */
	public String getName() {
		return name;
	}
	/**
	  * Setter of name
	  * @param name name to set
	  */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	  * Getter of text
	  * @return text
	  */
	public String getText() {
		return text;
	}
	
	/**
	  * Setter of text
	  * @param text text to set
	  */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	  * Getter of Cd
	  * @return cooldown
	  */
	public int getCd() {
		return cd;
	}
	
	/**
	  * Setter of Cd
	  * @param cd cooldown to set
	  * 
	  */
	public void setCd(int cd) {
		this.cd = cd;
	}

	/**
	  * Getter of inCombatCd
	  * @return in combat cooldown
	  */
	public int getInCombatCd() {
		return inCombatCd;
	}

	/**
	  * Setter of inCombatCd
	  * @param inCombatCd cooldown to set
	  */
	public void setInCombatCd(int inCombatCd) {
		if(inCombatCd<0) {
			this.inCombatCd = 0;
			return;
		}
		this.inCombatCd = inCombatCd;
	}
	
	/**
	  * to string method
	  * @return string form of this class
	  */
	public String toString() {
		return this.getName() + " : " + this.getInCombatCd() + "/" + this.getCd();
		
	}
	
	/**
	  * Getter of image path
	  * @return image path
	  */
	public String getImagePath() {
		return imagePath;
	}

	/**
	  * Setter of image path
	  * @param imagePath imagePath to set
	  */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	
	
}
