package entity.base;

import component.UnitCard;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.Image;
import logic.GameLogic;
/**
 * Monster class
 */
public class Monster extends Unit {
	/**
	  * Object monster
	  */
	private Monster monster;
	
	/**
	  * Constructor of Monster class
	  * @param name name of monster
	  * @param text text of monster 
	  * @param attack attack of monster
	  * @param defense defense of monster 
	  * @param position position of monster
	  * @param maxHealth max health of monster
	  * @param imagePath image path of monster
	  */
	public Monster(String name, String text, int attack, int defense, int position, int maxHealth,String imagePath) {
		super(name, text, attack, defense, position, maxHealth, imagePath);
		this.monster = this;
	}
	
	/**
	  * Reset monster to battlefield
	  */
	public void reset() {
		//System.out.println("use this");
		this.setAlive(true);
		this.setHealth( this.getMaxHealth() );
		this.setBuffAttack(0);
		this.setBuffDefense(0);
		this.getBuffs().clear();
		for(Skill s:this.getSkills()) {
			//System.out.println( s.getCd() );
			s.setInCombatCd(s.getCd());
			//System.out.println( s.getInCombatCd() );
		}
	}
	
	/**
	  * Activate skill method
	  * @param skill skill to use 
	  */
	public boolean useSkill(Skill skill) {
		
		//System.err.println(skill.getName() + " cd " + skill.getInCombatCd() + " " + skill.getCd());
		
		for(int i=monster.getSkills().size()-1;i>=0;i--) {
			if(monster.getSkills().get(i)==null) {
				continue;
			}else if(monster.getSkills().get(i).readySkill()==false) {
				continue;
			}else {
				System.out.println("############# "+ this.getName()+" use skill " + monster.getSkills().get(i).getName());
					
					if( monster instanceof Monster ) {
						for( Node unitCard :GameLogic.getInstance().getCombatController().getCombatDisplay().getMonsterCardBox().getChildren() ) {
							if ( ((UnitCard)unitCard).getUnit() == monster ) {
								//System.out.println("monster atk animation");
								((UnitCard)unitCard).attackAnimation();
								break;
							}
						}					
					}
					return monster.getSkills().get(i).skillActive(monster);
			}
		}
		System.out.println("############# "+ this.getName()+" do nothing");
		return true;
				
				
	}
}
