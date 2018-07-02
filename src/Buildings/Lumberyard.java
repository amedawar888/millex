package Buildings;

public class Lumberyard extends Building {
	
	private int lumberProduced;
	
	public Lumberyard(int f) {
		lumberProduced = f;
	}

	public int getLumberProduced() {
		return lumberProduced;
	}
	
}
