package snake;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Created by hackcoder on 15-3-11.
 */
public class Snake extends JFrame {
	private static final int rows = 60;
	private static final int columns = 80;

	// 方向
	private static final int UP = 1;
	private static final int RIGHT = 2;
	private static final int DOWN = 3;
	private static final int LEFT = 4;

	private static int DRIECTION_NOW = RIGHT;
	private static boolean isEat = false;

	private static int TAILX;
	private static int TAILY;

	private static SnakeNode snakeHeader = new SnakeNode();
	private static SnakeNode snakeTailer = snakeHeader;
	private static SnakeNode food = new SnakeNode();
	private static JLabel[] images = new JLabel[rows * columns];

	public static void main(String args[]) {
		snakeHeader.setX(new Random().nextInt(rows - 1));
		snakeHeader.setY(new Random().nextInt(columns - 1));
		Snake snake = new Snake();
		food = getFood();
		while (true) {
			try {
				next();
				// 吃到了食物
				if (food.getX() == snakeHeader.getX()
						&& food.getY() == snakeHeader.getY()) {
					addTail();
					isEat = true;
				}
				//吃到食物，重新生成一个食物
				if (isEat) {
					food = getFood();
				}
				// 判断是否结束游戏
				if (judgeEND()) {
					JOptionPane.showMessageDialog(null, "游戏结束！", "游戏结束！",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
				SnakeNode pNow = snakeHeader;
				while (pNow != null) {
					images[columns * pNow.getX() + pNow.getY()]
							.setIcon(new ImageIcon("image/black.jpg", ""));
					pNow = pNow.getNext();
				}
				images[columns * food.getX() + food.getY()]
						.setIcon(new ImageIcon("image/black.jpg", ""));
				Thread.sleep(100);
				// 清理
				pNow = snakeHeader;
				while (pNow != null) {
					images[columns * pNow.getX() + pNow.getY()]
							.setIcon(new ImageIcon("image/white.jpg", ""));
					pNow = pNow.getNext();
				}
				images[columns * food.getX() + food.getY()]
						.setIcon(new ImageIcon("image/white.jpg", ""));

				isEat = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public Snake() {
		init();
		this.setBounds(80, 80, 400, 400);
		this.setVisible(true);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		// 添加全局键盘监听
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventPostProcessor((KeyEventPostProcessor) this
				.getMyKeyEventHandler());
	}

	/**
	 * 初始化地图
	 */
	public void init() {
		JPanel p = new JPanel(new GridLayout(rows, columns, 1, 1));
		setLayout(new BorderLayout());
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				ImageIcon imageIcon;
				if (x == 0 || x == rows - 1 || y == 0 || y == columns - 1) {
					imageIcon = new ImageIcon("image/red.jpg", "");
				} else {
					imageIcon = new ImageIcon("image/white.jpg", "");
				}
				images[columns * x + y] = new JLabel(imageIcon);
				p.add(images[columns * x + y]);
			}
		}
		getContentPane().add(p, BorderLayout.CENTER);

	}

	/**
	 * 键盘监听
	 * 
	 * @return
	 */
	public KeyEventPostProcessor getMyKeyEventHandler() {
		return new KeyEventPostProcessor() {
			public boolean postProcessKeyEvent(KeyEvent e) {
				if (e.getID() != KeyEvent.KEY_PRESSED) {
					return false;
				}
				int keycode = e.getKeyCode();
				if (keycode == KeyEvent.VK_UP) {

					if (snakeHeader.getNext() != null) {
						// 判断方向是否可转
						int x1 = snakeHeader.getX();
						int y1 = snakeHeader.getY();
						int x2 = snakeHeader.getNext().getX();
						int y2 = snakeHeader.getNext().getY();
						if (y1 == y2 && x1 - x2 == 1) {
							return true;
						}
					}
					DRIECTION_NOW = UP;
				} else if (keycode == KeyEvent.VK_RIGHT) {
					if (snakeHeader.getNext() != null) {
						int x1 = snakeHeader.getX();
						int y1 = snakeHeader.getY();
						int x2 = snakeHeader.getNext().getX();
						int y2 = snakeHeader.getNext().getY();
						if (x1 == x2 && y2 - y1 == 1) {
							return true;
						}
					}
					DRIECTION_NOW = RIGHT;
				} else if (keycode == KeyEvent.VK_DOWN) {
					if (snakeHeader.getNext() != null) {
						int x1 = snakeHeader.getX();
						int y1 = snakeHeader.getY();
						int x2 = snakeHeader.getNext().getX();
						int y2 = snakeHeader.getNext().getY();
						if (y1 == y2 && x2 - x1 == 1) {
							return true;
						}
					}
					DRIECTION_NOW = DOWN;
				} else if (keycode == KeyEvent.VK_LEFT) {
					if (snakeHeader.getNext() != null) {
						int x1 = snakeHeader.getX();
						int y1 = snakeHeader.getY();
						int x2 = snakeHeader.getNext().getX();
						int y2 = snakeHeader.getNext().getY();
						if (x1 == x2 && y1 - y2 == 1) {
							return true;
						}
					}
					DRIECTION_NOW = LEFT;
				}
				return true;
			}
		};
	}

	/**
	 * 计算贪吃蛇的方向及位移
	 * 
	 * @param header
	 */
	public static void next() {
		if (snakeHeader == null)
			return;
		TAILX = snakeTailer.getX();
		TAILY = snakeTailer.getY();
		SnakeNode pNow = snakeTailer;
		while (pNow != null) {
			if (pNow == snakeHeader) {
				break;
			}
			pNow.setX(pNow.getAhead().getX());
			pNow.setY(pNow.getAhead().getY());
			pNow = pNow.getAhead();
		}

		if (DRIECTION_NOW == RIGHT) {
			snakeHeader.setY(snakeHeader.getY() + 1);
		} else if (DRIECTION_NOW == LEFT) {
			snakeHeader.setY(snakeHeader.getY() - 1);
		} else if (DRIECTION_NOW == UP) {
			snakeHeader.setX(snakeHeader.getX() - 1);
		} else if (DRIECTION_NOW == DOWN) {
			snakeHeader.setX(snakeHeader.getX() + 1);
		}
	}

	public static void addTail() {
		SnakeNode tail = new SnakeNode(TAILX, TAILY);
		snakeTailer.setNext(tail);
		tail.setAhead(snakeTailer);
		snakeTailer = snakeTailer.getNext();

	}

	public static SnakeNode getFood() {
		SnakeNode food = new SnakeNode();
		boolean flag = true;
		while (true) {
			int x = new Random().nextInt(rows);
			int y = new Random().nextInt(columns);
			if (x == 0 || x == rows - 1 || y == 0 || y == columns - 1) {
				continue;
			}
			SnakeNode pNow = snakeHeader;
			while (pNow != null) {
				if (x == pNow.getX() && y == pNow.getY()) {
					flag = false;
				}
				pNow = pNow.getNext();
			}
			if (flag) {
				food = new SnakeNode(x, y);
				break;
			}
		}
		return food;
	}

	public static boolean judgeEND() {
		//碰墙判断
		if (snakeHeader.getX() == 0 || snakeHeader.getX() == rows - 1
				|| snakeHeader.getY() == 0 || snakeHeader.getY() == columns - 1) {
			return true;
		}
		//碰身体判断
		SnakeNode pNow = snakeHeader.getNext();
		while (pNow != null) {
			if (snakeHeader.getX() == pNow.getX()
					&& snakeHeader.getY() == pNow.getY()) {
				System.out.println("=========碰到身体===========");
				return true;
			}
			pNow = pNow.getNext();
		}
		return false;
	}

}