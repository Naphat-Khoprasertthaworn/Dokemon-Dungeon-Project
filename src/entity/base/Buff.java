package entity.base;

public abstract class Buff implements Countdownable {
	private int turn;
	private int ratio;
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
	
	public abstract void activeBuff(Unit unit);

	public abstract void removeSelf(Unit unit);
	
	
}