package skill.type;

import java.util.ArrayList;

import buff.type.DamageReduction;
import buff.type.Enhance;
import buff.type.Exhaust;
import buff.type.Regeneration;
import buff.type.Vulnetability;
import entity.base.Buff;
import entity.base.Unit;

public class MultiTargetDefenceSkill extends DefenceSkill {

	public MultiTargetDefenceSkill(String name, String text, int ratio, int cd,String imagePath) {
		super(name, text, ratio, cd,imagePath);
	}

	@Override
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
				}else if(b instanceof Vulnetability) {
					unit.addBuff(new Vulnetability((Vulnetability)b));
				}else {
					
				}
			
			}
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
			}else if(b instanceof Vulnetability) {
				owner.addBuff(new Vulnetability((Vulnetability)b));
			}else {
				
			}
		}
		
	}

}
