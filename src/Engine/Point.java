package Engine;

public class Point {
	
	private int x, y;
	private int multiClick;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		multiClick = 1;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getMultiClick() {
		return multiClick;
	}

	public void incrementMultiClick() {
		multiClick++;
	}
	
}
