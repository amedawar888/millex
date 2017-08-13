package Buildings;

public class Farm extends Building {
	
	private int foodProduced;
	
	public Farm(int f) {
		foodProduced = f;
	}

	public int getFoodProduced() {
		return foodProduced;
	}
	
}
