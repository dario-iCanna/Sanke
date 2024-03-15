package Imputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Main.Game;
import Main.GamePanel;

public class KeyboardImputs implements KeyListener{
	
	private GamePanel gamePanel;
	private boolean canPress = true;

	public KeyboardImputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	@Override
	public void keyTyped(KeyEvent e) {
				
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			if(gamePanel.getYDir() != 1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(1);}
			break;
		case KeyEvent.VK_A:
			if(gamePanel.getXDir() != 1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(2);}
			break;
		case KeyEvent.VK_S:
			if(gamePanel.getYDir() != -1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(3);}
			break;
		case KeyEvent.VK_D:
			if(gamePanel.getXDir() != -1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(4);}
			break;
		case KeyEvent.VK_UP:
			if(gamePanel.getYDir() != 1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(1);}
			break;
		case KeyEvent.VK_LEFT:
			if(gamePanel.getXDir() != 1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(2);}
			break;
		case KeyEvent.VK_DOWN:
			if(gamePanel.getYDir() != -1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(3);}
			break;
		case KeyEvent.VK_RIGHT:
			if(gamePanel.getXDir() != -1.0f && gamePanel.GameStart && gamePanel.getIndex() == 0) {gamePanel.SetIndex(4);}
			break;
		case KeyEvent.VK_SPACE:
			if(canPress) {
				gamePanel.xDir = 1.0f;
				gamePanel.yDir = 0.f;
				gamePanel.GameStart = true;
				canPress = false;
			}
			break;
		case KeyEvent.VK_R:
			if(gamePanel.GameStart == false) {
				gamePanel.RestartGame();
				canPress = true;
			}

			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
