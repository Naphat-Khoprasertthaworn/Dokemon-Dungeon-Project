package item.type;

import buff.type.Vulnetability;
import entity.base.Unit;

public class VulnetabilityPotion extends DebuffPotion {
	
	private Vulnetability vulnetabilityBuff;
	//private final String imagePath = "vulnetabilityPotion.png";

	public VulnetabilityPotion(int amount,int turn,int ratio) {
		super("Vulnetability Potion", "give vulnetability to a monster", amount,"vulnetabilityPotion.png");
		vulnetabilityBuff = new Vulnetability(turn, ratio);
		
	}

	public VulnetabilityPotion(VulnetabilityPotion dp) {
		this(dp.getAmount(), dp.getBuff().getTurn(), dp.getBuff().getRatio());
	}
	
	public Vulnetability getBuff() {
		return vulnetabilityBuff;
	}

	@Override
	public void activeEffect(Unit u) {
		u.addBuff(vulnetabilityBuff);
		
	}


}
