package Main;

public class Game implements Runnable{
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameLoop;
	private final int FPS_SET = 250;
	
	public Game() {

		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		startGameLoop();
		gamePanel.requestFocus();

	}
	
	private void startGameLoop() {
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	@Override
	public void run() {
		
		double timePerFrame = 1000000000.0 / FPS_SET;
		long LastFrame = System.nanoTime();
		long now = System.nanoTime();

		while(true) {
			now = System.nanoTime();
			if(now - LastFrame >= timePerFrame) {
				gamePanel.repaint();
				LastFrame = now;
			}
		}		
	}
}
