package snake;

public class SnakeNode {
	private int x;
	private int y;
	private SnakeNode next;
	private int direction;//方向

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public SnakeNode getNext() {
		return next;
	}

	public void setNext(SnakeNode next) {
		this.next = next;
	}

}
