package Buildings;

public class Farm extends Building {
	
	private int foodProduced;
	
	public Farm(int food) {
		super(1);
		foodProduced = food;
	}

	public int getFoodProduced() {
		return foodProduced;
	}
	
}
