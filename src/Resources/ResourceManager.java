package Resources;

public class ResourceManager {

	private int food = 0;
	private int lumber = 0;
	private int trade = 0;
	
	public int getFood() {
		return food;
	}
	
	public int getLumber() {
		return lumber;
	}
	
	public int getTrade() {
		return trade;
	}
	
	public void addFood(int f) {
		food += f;
	}
	
	public void removeFood(int f) {
		food = Math.max(food-f, 0);
	}
	
}
