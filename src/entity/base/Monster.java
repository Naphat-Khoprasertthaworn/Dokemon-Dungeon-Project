package entity.base;

public class Monster extends Unit {

	public Monster(String name, String text, int attack, int defense, int position, int maxHealth) {
		super(name, text, attack, defense, position, maxHealth);
		
	}
	
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
	
	public boolean useSkill(int order) {
		for(int i=this.getSkills().size()-1;i>=0;i--) {
			if(this.getSkills().get(i)==null) {
				continue;
			}
			else if(this.getSkills().get(i).readySkill()==false) {
				continue;
			}else {
				System.out.println("############# "+ this.getName()+" use skill " + this.getSkills().get(i).getName());
				return this.getSkills().get(i).skillActive(this);
			}
		}
		System.out.println("############# "+ this.getName()+" do nothing");
		return true;
	}
	
}
