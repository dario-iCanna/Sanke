package Main;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
	public GameWindow(GamePanel gamePanel) {
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.add(gamePanel);
		super.setTitle("Sanke");
		super.pack();
		super.setResizable(false);
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
}
