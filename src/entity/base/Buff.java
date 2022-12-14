package entity.base;
/**
 * Base buff class
 */
public class Buff implements Countdownable {
	/**
	  * Remaining turn
	  */
	private int turn;
	/**
	  * Ratio for buff
	  */
	private int ratio;
	/**
	  * Buff name
	  */
	private String name;
	/**
	  * Image path for this buff
	  */
	private String imagePath;
	/**
	  * Buff constructor
	  * @param turn turn
	  * @param ratio ratio
	  * 
	  */
	public Buff(int turn,int ratio) {
		this.setTurn(turn);
		this.setRatio(ratio);
	}
	/**
	  * Copy constructor
	  * @param b buff to copy
	  */
	public Buff(Buff b) {
		this(b.getTurn(),b.getRatio());
	}
	
	/**
	  * Getter for turn
	  * @return turn
	  */
	public int getTurn() {
		return turn;
	}
	/**
	  * Getter for ratio
	  * @return ratio
	  */
	public int getRatio() {
		return ratio;
	}
	/**
	  * Setter for ratio
	  * @param ratio ratio to set
	  */
	public void setRatio(int ratio) {
		if(ratio < 0) {
			this.ratio = 0;
			return;
		}
		this.ratio = ratio;
	}
	
	/**
	  * Setter for turn
	  * @param turn turn to set
	  */
	public void setTurn(int turn) {
		if(this.turn < 0) {
			this.turn = 0;
			return;
		}
		this.turn = turn;
	}
	
	/**
	  * Count down turn for buff
	  */
	public void countDown() {
		this.setTurn(this.getTurn() - 1);
	}
	
	/**
	  * Getter of name
	  * @return name
	  */
	public String getName() {
		return name;
	}
	/**
	  * Setter of name
	  * @param name name to set
	  */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	  * Equals method for buff
	  * @param o other object to check equal
	  */
	public boolean equals(Object o) {
		//System.out.println( "buff equal work" );
		if (o == this) {
			return true;
		}
		if( !(o.getClass().equals(this.getClass())) ) {
			return false;
		}
		
		Buff bo = (Buff)o;
		//System.out.println( "buff equal work" );
		return (bo.getRatio() == this.getRatio()) && (bo.getTurn() == this.getTurn());
		
	}
	
	/**
	 * Activate buff 
	  * @param unit give buff to unit
	  */
	public void activeBuff(Unit unit) {
		
	};
	
	/**
	  * Disable buff
	  * @param unit disable buff this unit
	  */
	public void removeSelf(Unit unit) {
		
	}
	/**
	  * Getter for image path
	  * @return image path
	  */
	public String getImagePath() {
		return imagePath;
	}

	/**
	  * Setter for image path
	  * @param imagePath image path to set
	  */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	};
	
}
