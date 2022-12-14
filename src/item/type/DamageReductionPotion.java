package item.type;

import buff.type.DamageReduction;
import entity.base.Unit;
/**
 * One of buff potion class 
 */ 
public class DamageReductionPotion extends BuffPotion{
    /**
     * Damage reduction buff
     */ 
	private DamageReduction dmgRedBuff;
	//private final String imagePath = "damageReductionPotion.png";
	
    /**
     * Getter of damage reduction buff
     * @return damage reduction buff
     */ 
	public DamageReduction getBuff() {
		return dmgRedBuff;
	}

    /**
     * Constructor of damage reduction potion
     * @param amount amount
     * @param turn turn 
     * @param ratio ratio
     */ 
	public DamageReductionPotion(int amount,int turn,int ratio) {
		super("Damage Reduction Potion", "give dmg reduction to hero", amount,"image/damageReductionPotion.png");
		dmgRedBuff = new DamageReduction(turn, ratio);
	}
	
    /**
     * Copy constructor
     * @param dp dp
     */ 
	public DamageReductionPotion(DamageReductionPotion dp) {
		this(dp.getAmount(), dp.getBuff().getTurn(), dp.getBuff().getRatio());
	}

    /**
     * Active buff to unit
     * @param u unit to active
     */ 
	@Override
	public void activeEffect(Unit u) {
		u.addBuff(dmgRedBuff);
	}
	
}
