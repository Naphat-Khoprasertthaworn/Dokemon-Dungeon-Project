package skill.type;

import java.util.ArrayList;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Regeneration;
import buff.type.Vulnerability;
import entity.base.Buff;
import entity.base.Unit;
/**
 * MultiTargetDefenseSkill Class
 */
public class MultiTargetDefenseSkill extends DefenseSkill {
	/**
	  * Constructor of MultiTargetDefenceSkill Class.
	  * @param name name.
	  * @param text text.
	  * @param ratio ratio.
	  * @param cd cooldown.
	  * @param imagePath path of skill image. 
	  */
	public MultiTargetDefenseSkill(String name, String text, int ratio, int cd,String imagePath) {
		super(name, text, ratio, cd,imagePath);
	}

	@Override
	/**
	  * active skill.
	  * @param units arraylist of targeted party.
	  * @param targetUnit targeted unit.
	  * @param owner unit that call this skill.
	  */
	public void skillEffect(ArrayList<Unit> units, Unit targetUnit, Unit owner) {
		for(Unit unit:units) {
			unit.receiveHeal( (this.getRatio()*owner.getTotalAttack())/100 );

			for(Buff b: this.getBuffsTarget()) {
				if (b instanceof DamageReduction) {
					unit.addBuff(new DamageReduction((DamageReduction)b));
				}else if(b instanceof Enhance) {
					unit.addBuff(new Enhance((Enhance)b));
				}else if(b instanceof Exhaust) {
					unit.addBuff(new Exhaust((Exhaust)b));
				}else if(b instanceof Regeneration) {
					unit.addBuff(new Regeneration((Regeneration)b));
				}else if(b instanceof Vulnerability) {
					unit.addBuff(new Vulnerability((Vulnerability)b));
				}else {
					
				}
			
			}
			System.out.println("Target"+unit);
		}
		for(Buff b:this.getBuffsSelf()) {
			if (b instanceof DamageReduction) {
				owner.addBuff(new DamageReduction((DamageReduction)b));
			}else if(b instanceof Enhance) {
				owner.addBuff(new Enhance((Enhance)b));
			}else if(b instanceof Exhaust) {
				owner.addBuff(new Exhaust((Exhaust)b));
			}else if(b instanceof Regeneration) {
				owner.addBuff(new Regeneration((Regeneration)b));
			}else if(b instanceof Vulnerability) {
				owner.addBuff(new Vulnerability((Vulnerability)b));
			}else {
				
			}
		}
		System.out.println("owner"+owner);
		
	}

}
