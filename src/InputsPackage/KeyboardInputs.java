package InputsPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import GameStatesPackage.GameState;
import MainPackage.JPanelGameScreen;

public class KeyboardInputs implements KeyListener {
	private JPanelGameScreen game_screen;
	
	public KeyboardInputs(JPanelGameScreen game_screen) {
		this.game_screen = game_screen;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameState.game_state)
		{
			case MENU:
				game_screen.getGameElements().getMenu().keyPressed(e);
				break;
			case PLAYING:
				game_screen.getGameElements().getPlaying().keyPressed(e);
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(GameState.game_state)
		{
			case MENU:
				game_screen.getGameElements().getMenu().keyReleased(e);
				break;
			case PLAYING:
				game_screen.getGameElements().getPlaying().keyReleased(e);
				break;
			default:
				break;
		}
	}

}
