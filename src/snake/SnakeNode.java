package snake;

public class SnakeNode {
	private int x;
	private int y;
	private SnakeNode next;
	private SnakeNode ahead;

	public SnakeNode() {
	}

	public SnakeNode(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

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

	public SnakeNode getAhead() {
		return ahead;
	}

	public void setAhead(SnakeNode ahead) {
		this.ahead = ahead;
	}

}
