package item.type;

import buff.type.Enhance;
import entity.base.Unit;

public class EnhancePotion extends BuffPotion{
	
	private Enhance enhanceBuff;
	//private final String imagePath = "enhancePotion.png";
	
	public Enhance getBuff() {
		return enhanceBuff;
	}

	public EnhancePotion(int amount,int turn,int ratio) {
		super("Enhance Potion", "give enhance buff to a hero", amount,"image/enhancePotion.png");
		enhanceBuff = new Enhance(turn, ratio);
	}
	
	public EnhancePotion(EnhancePotion dp) {
		this(dp.getAmount(), dp.getBuff().getTurn(), dp.getBuff().getRatio());
	}
	
	@Override
	public void activeEffect(Unit u) {
		u.addBuff(this.enhanceBuff);
	}

}
