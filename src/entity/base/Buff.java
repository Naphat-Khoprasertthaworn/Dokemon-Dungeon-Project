package entity.base;

public class Buff {
	private int turn;
	
	
	
	public int getTurn() {
		return turn;
	}



	public void setTurn(int turn) {
		if(this.turn < 0) {
			this.turn = 0;
		}
		this.turn = turn;
	}

	void countDown() {
		this.setTurn(this.getTurn() - 1);
	}
	
}
