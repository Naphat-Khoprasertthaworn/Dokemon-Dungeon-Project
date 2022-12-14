package item.type;

import buff.type.Enhance;
import entity.base.Unit;

/**
 * One class of buff potion
 */ 
public class EnhancePotion extends BuffPotion{
	
    /**
     * Enhance buff
     */ 
	private Enhance enhanceBuff;
	//private final String imagePath = "enhancePotion.png";
	
    /**
     * Getter of Enhance buff
     * @return enhance buff
     */ 
	public Enhance getBuff() {
		return enhanceBuff;
	}

    /**
     * Constructor of Enhance potion
     * @param amount amount
     * @param turn turn
     * @param ratio ratio
     */ 
	public EnhancePotion(int amount,int turn,int ratio) {
		super("Enhance Potion", "give enhance buff to a hero", amount,"image/enhancePotion.png");
		enhanceBuff = new Enhance(turn, ratio);
	}
	
    /**
     * Coppy constructor
     * @param dp dp
     */ 
	public EnhancePotion(EnhancePotion dp) {
		this(dp.getAmount(), dp.getBuff().getTurn(), dp.getBuff().getRatio());
	}
	
    /**
     * Active buff
     * @param u unit to active
     */ 
	@Override
	public void activeEffect(Unit u) {
		u.addBuff(this.enhanceBuff);
	}

}
