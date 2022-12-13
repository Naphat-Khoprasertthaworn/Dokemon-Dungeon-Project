package entity.base;

public class Buff implements Countdownable {
	private int turn;
	private int ratio;
	private String name;
	private String imagePath;
	public Buff(int turn,int ratio) {
		this.setTurn(turn);
		this.setRatio(ratio);
	}
	
	public Buff(Buff b) {
		this(b.getTurn(),b.getRatio());
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
	
	public boolean equals(Object o) {
		//System.out.println( "buff eaual work" );
		if (o == this) {
			return true;
		}
		if( !(o.getClass().equals(this.getClass())) ) {
			return false;
		}
		
		Buff bo = (Buff)o;
		//System.out.println( "buff eaual work" );
		return (bo.getRatio() == this.getRatio()) && (bo.getTurn() == this.getTurn());
		
	}
	
	public void activeBuff(Unit unit) {
		
	};
	
	public void removeSelf(Unit unit) {
		
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	};
	
}
