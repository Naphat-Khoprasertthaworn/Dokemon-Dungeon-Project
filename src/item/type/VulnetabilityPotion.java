package item.type;

import buff.type.Vulnetability;
import entity.base.Unit;

public class VulnetabilityPotion extends DebuffPotion {
	
	private Vulnetability vulnetabilityBuff;
	
	public VulnetabilityPotion(int amount,int turn,int ratio) {
		super("Vulnetability Potion", "give vulnetability to a monster", amount);
		vulnetabilityBuff = new Vulnetability(turn, ratio);
		
	}

	public Vulnetability getBuff() {
		return vulnetabilityBuff;
	}

	@Override
	public void activeEffect(Unit u) {
		u.addBuff(vulnetabilityBuff);
		
	}

}