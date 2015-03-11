package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	private static int foodX;// 食物出现x地方
	private static int foodY;// 食物出现y地方
	private static JLabel[] images = new JLabel[rows * columns];

	public static void main(String args[]) {
		Snake snake = new Snake();
		int next = 197;
		while (true) {
			try {
				Thread.sleep(1000);
				images[next++].setIcon(new ImageIcon("image/black.jpg", ""));
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
	}

	public void init() {
		JPanel p = new JPanel(new GridLayout(rows, columns, 1, 1));
		setLayout(new BorderLayout());
		for (int i = 0; i < rows; i++) {
			for (int y = 0; y < columns; y++) {
				ImageIcon imageIcon;
				if (i == 0 || i == rows - 1 || y == 0 || y == columns - 1) {
					imageIcon = new ImageIcon("image/red.jpg", "");
				} else {
					imageIcon = new ImageIcon("image/white.jpg", "");
				}
				images[columns * i + y] = new JLabel(imageIcon);
				p.add(images[columns * i + y]);
			}
		}
		getContentPane().add(p, BorderLayout.CENTER);
	}
}