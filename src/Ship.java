
public class Ship {
	private int length;
	public Ship(int length) {
		this.length = length;
	}
	public int getLenght() {
		return length;
	}
	
	@Override
	public String toString() {
		return Integer.toString(length);
	}
}
