package Buildings;

public class Lumberyard extends Building {
	
	private int lumberProduced;
	
	public Lumberyard(int lumber) {
		super(3);
		lumberProduced = lumber;
	}

	public int getLumberProduced() {
		return lumberProduced;
	}
	
}
