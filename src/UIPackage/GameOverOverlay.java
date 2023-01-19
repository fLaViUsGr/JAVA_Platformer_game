package UIPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import GameStatesPackage.GameState;
import GameStatesPackage.Playing;
import MainPackage.GameElements;

public class GameOverOverlay {
	private Playing playing;
	
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
	}
	
	public void draw(Graphics graphics) {
		graphics.setColor(new Color(0,0,0,200));
		graphics.fillRect(0, 0, GameElements.GAME_WIDTH, GameElements.GAME_HEIGHT);
		
		graphics.setColor(Color.WHITE);
		graphics.drawString("BRUH", GameElements.GAME_WIDTH / 2, 150);
		graphics.drawString("Press 'esc' to enter Main Menu", GameElements.GAME_WIDTH / 2, 300);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			playing.resetAll();
			GameState.game_state = GameState.MENU;
		}
	}
}
