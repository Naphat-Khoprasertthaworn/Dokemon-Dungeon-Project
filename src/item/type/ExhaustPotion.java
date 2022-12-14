package item.type;

import buff.type.Exhaust;
import entity.base.Unit;


/**
 * One class of debuff potion
 */ 
public class ExhaustPotion extends DebuffPotion {

    /**
     * Exhaust buff
     */ 
	private Exhaust exhaustBuff;
	//private final String imagePath = "exhaustPotion.png";
	
    /**
     * Constructor of exhaust buff
     * @param amount amount
     * @param turn turn
     * @param ratio ratio
     */ 
	public ExhaustPotion( int amount,int turn,int ratio) {
		super("Exhaust Potion", "give exhaust to a monster", amount,"image/exhaustPotion.png");
		exhaustBuff = new Exhaust(turn, ratio);
	}
	
    /**
     * Copy constructor
     * @param dp dp
     */ 
	public ExhaustPotion(ExhaustPotion dp) {
		this(dp.getAmount(), dp.getBuff().getTurn(), dp.getBuff().getRatio());
	}

    /**
     * Active buff
     * @param u unit to active
     */ 
	@Override
	public void activeEffect(Unit u) {
		u.addBuff(exhaustBuff);
		
	}

    /**
     * Getter of exhaust buff
     * @return exhaust buff
     */ 
	public Exhaust getBuff() {
		return exhaustBuff;
	}

}
