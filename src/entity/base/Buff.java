package entity.base;

public abstract class Buff implements Countdownable {
	private int turn;
	private int ratio;
	private String name;
	
	public Buff(int turn,int ratio) {
		this.setTurn(turn);
		this.setRatio(ratio);
	}
	
	public int getTurn() {
		return turn;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		if(ratio < 0) {
			this.ratio = 0;
			return;
		}
		this.ratio = ratio;
	}

	public void setTurn(int turn) {
		if(this.turn < 0) {
			this.turn = 0;
			return;
		}
		this.turn = turn;
	}

	public void countDown() {
		this.setTurn(this.getTurn() - 1);
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract void activeBuff(Unit unit);

	public abstract void removeSelf(Unit unit);
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if( !(o.getClass().equals(this.getClass())) ) {
			return false;
		}
		
		Buff bo = (Buff)o;
		return (bo.getRatio() == this.getRatio()) && (bo.getTurn() == this.getTurn());
		
	}
	
	
}
