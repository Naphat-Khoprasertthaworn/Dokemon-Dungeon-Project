package entity.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import component.UnitCard;
import javafx.scene.Node;
import logic.GameLogic;

/**
 * Unit class of the game
 */
public class Unit {
	/**
	  * attack of unit
	  */
	private int attack;
	/**
	  * maxhealth of unit
	  */
	private int maxHealth;
	/**
	  * name of unit
	  */
	private String name;
	/**
	  * text of unit
	  */
	private String text;
	/**
	  * position of unit
	  */
	private int position;
	/**
	  * defense of unit
	  */
	private int defense;
	/**
	  * isalive field to check alive
	  */
	private boolean isAlive;
	/**
	  * image path of unit
	  */
	private String imagePath;
	/**
	  * health of unit
	  */
	private int health;
	/**
	  * Arraylist of buffs
	  */
	private ArrayList<Buff> buffs;
	/**
	  * Arraylist of skills
	  */
	private ArrayList<Skill> skills;
	/**
	  * buffattack of unit
	  */
	private int buffAttack;
	/**
	  * buffdefense of unit
	  */
	private int buffDefense;
	/**
	  * Comparator for skill
	  */
	private Comparator<Skill> compSkill;
	
	/**
	  * field check isTargeted
	  */
	private boolean isTargeted;
	
	/**
	  * Getter of isTargeted
	  */
	public boolean isTargeted() {
		return isTargeted;
	}

	/**
	  * Setter of isTargeted
	  * @param isTargeted value to set 
	  */
	public void setTargeted(boolean isTargeted) {
		this.isTargeted = isTargeted;
	}

	/**
	  * Constructor of Unit class
	  * @param name name 
	  * @param text text
	  * @param attack attack 
	  * @param defense defense 
	  * @param position position
	  * @param maxHealth max health
	  * @param imagePath image path
	  */
	public Unit( String name, String text, int attack, int defense , int position,int maxHealth,String imagePath ) {
		this.setName(name);
		this.setText(text);
		this.setAttack(attack);
		this.setMaxHealth(maxHealth);
		this.setPosition(position);
		this.setDefense(defense);
		this.skills = new ArrayList<Skill>();
		this.buffs = new ArrayList<Buff>();
		this.setImagePath(imagePath);
		this.compSkill = (Skill s1,Skill s2)->{
			if(s1.getCd() > s2.getCd()) {
				return 1;
			}
			return -1;
		};
		
		this.reset();
		
		
	}

	/**
	  * Countdown buff and skill
	  */
	public void countdownAll() {
		this.countdownBuffs();
		this.countdownSkills();
	}
	
	/**
	  * Getter of isAlive
	  */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	  * Setter of isAlive
	  * @param isAlive value to set
	  */
	public void setAlive(boolean isAlive) {
		if(!isAlive) {
			this.getBuffs().clear();
		}
		this.isAlive = isAlive;
	}
	
	/**
	  * Method make unit taken damage 
	  * @param dmg Damage unit will be taken
	  * @return damage that unit taken
	  */
	public int takeDamage(int dmg) {
		if(!this.isAlive()) {
			return 0;
		}
		if( this instanceof Monster ) {
		    for( Node unitCard :GameLogic.getInstance().getCombatController().getCombatDisplay().getMonsterCardBox().getChildren() ) {
		    	if ( ((UnitCard)unitCard).getUnit() == this ) {
		    		((UnitCard)unitCard).gotDamagedAnimation();
		    		break;
		    	}
		    }
			
		}else {
			for( Node unitCard :GameLogic.getInstance().getCombatController().getCombatDisplay().getHeroesCardBox().getChildren() ) {
		    	if ( ((UnitCard)unitCard).getUnit() == this ) {
		    		((UnitCard)unitCard).gotDamagedAnimation();
		    		break;
		    	}
		    }
		}
		
		dmg = dmg - this.getTotalDefense();
		if(dmg<=0) {
			return 0;
		}
		this.setHealth(this.getHealth()-dmg);
		return dmg;
	}
	
	/**
	  * heal unit
	  * @param heal amount of heal to unit 
	  */
	public void receiveHeal(int heal) {
		//System.out.println("heal"+heal);
		//System.out.println(this);
		if(!isAlive()) {
			return;
		}
		
		if(heal<=0) {
			return;
		}
		this.setHealth(this.getHealth()+ heal);
		//System.out.println("after heal"+this);
	}
	
	/**
	  * reset unit 
	  */
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
	
	/**
	  * make unit to use skill
	  * @param skill skill to use
	  */
	public boolean useSkill( Skill skill ) {
		if(!this.skills.contains(skill)) {
			return false;
		}
		if( !skill.readySkill() ) {
			return false;
		}
		if( skill==null) {
			return false;
		}
		System.out.println("############# "+ this.getName()+" use skill " + skill.getName());
		if( !(this instanceof Monster) ) {
		    for( Node unitCard :GameLogic.getInstance().getCombatController().getCombatDisplay().getHeroesCardBox().getChildren() ) {
		    	if ( ((UnitCard)unitCard).getUnit() == this ) {
		    		//System.out.println("animation");
		    		((UnitCard)unitCard).attackAnimation();
		    		break;
		    	}
		    }
			
		}
		return skill.skillActive(this);
	}
	
	
	/**
	  * Getter of skills 
	  * @return skills
	  */
	public ArrayList<Skill> getSkills() {
		return skills;
	}

	/**
	  * add skill to skills
	  * @param skill skill to add
	  */
	public void addSkills(Skill skill) {
		this.skills.add(skill);
		Collections.sort(this.getSkills(),this.compSkill);
	}
	
	/**
	  * Countdown skill in skills
	  */
	public void countdownSkills() {
		for(Skill s:this.getSkills()) {
			s.countDown();
		}
	}
	
	/**
	  * Getter of attack
	  * @return attack
	  */
	public int getAttack() {
		return attack;
	}
	
	/**
	  * Setter of attack
	  * @param attack attack to set
	  */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	  * Getter of maxHealth
	  * @return max health
	  * 
	  */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	  * Setter of maxHealth
	  * @param maxHealth max health to set
	  */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	/**
	  * Getter of health
	  * @return health
	  */
	public int getHealth() {
		return health;
	}
	/**
	  * Setter of health
	  * @param health health to set
	  */
	public void setHealth(int health) {
		if(health <= 0) {
			this.setAlive(false);
			this.health = 0;
		}else if(health > this.maxHealth) {
			this.health = this.maxHealth;
		}else {
			this.health = health;
		}
		
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
	  * Getter of position
	  * @return position
	  */
	public int getPosition() {
		return position;
	}

	/**
	  * Setter of position
	  * @param position position to set
	  */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	  * Getter of buffs
	  * @return buffs
	  */
	public ArrayList<Buff> getBuffs() {
		return buffs;
	}

	/**
	  * Setter of buffs
	  * @param buffs buffs to set
	  */
	public void setBuffs(ArrayList<Buff> buffs) {
		this.buffs = buffs;
	}
	
	/**
	  * add buff to buffs
	  * @param buff buff to add
	  */
	public void addBuff(Buff buff) {
		if(!this.isAlive()) {
			return;
		}
		this.buffs.add(buff);
		buff.activeBuff(this);
	}
	
	/**
	  * countdown buff in buffs
	  */
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

	/**
	  * Getter of buffAttack
	  * @return buff attack
	  */
	public int getBuffAttack() {
		return buffAttack;
	}

	/**
	  * Setter of buffAttack
	  * @param buffAttack buff attack to set
	  */
	public void setBuffAttack(int buffAttack) {
		this.buffAttack = buffAttack;
	}
	
	/**
	  * Compute total attack
	  * @return total attack
	  */
	public int getTotalAttack() {
		return this.getAttack() + this.getBuffAttack();
	}
	/**
	  * Comput total defense
	  * @return total defense
	  */
	public int getTotalDefense() {
		return this.getDefense()+this.getBuffDefense();
	}
	/**
	  * Getter of defense
	  * @return defense
	  */
	public int getDefense() {
		return defense;
	}
	/**
	  * Setter of defense
	  * @param defense defense to set
	  */
	public void setDefense(int defense) {
		if(defense < 0) {
			this.defense = 0;
			return;
		}
		this.defense = defense;
	}

	/**
	  * Getter of buffDefense
	  * @return buff defense
	  * 
	  */
	public int getBuffDefense() {
		return buffDefense;
	}
	/**
	  * Setter of buffDefense
	  * @param buffDefense buff defense to set
	  */
	public void setBuffDefense(int buffDefense) {
		this.buffDefense = buffDefense;
	}

	/**
	  * to string method
	  * @return string form of this class
	  */
	public String toString() {
		return " "+ this.getName() + "\n Atk : " + this.getTotalAttack() + " / " + this.getAttack() + "\n Health: " + this.getHealth() +" / "+ this.getMaxHealth() + "\n Def : " + this.getTotalDefense() + " / " + this.getDefense();
	}

	/**
	  * Getter of imagePath
	  * @return image path
	  */
	public String getImagePath() {
		return imagePath;
	}


	/**
	  * Setter of imagePath
	  * @param imagePath image path to set
	  */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	

}
