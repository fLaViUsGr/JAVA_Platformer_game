package InputsPackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import GameStatesPackage.GameState;
import MainPackage.JPanelGameScreen;

public class MouseInputs implements MouseListener, MouseMotionListener{
	private JPanelGameScreen game_screen;
	
	public MouseInputs(JPanelGameScreen game_screen) {
		this.game_screen = game_screen;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch(GameState.game_state)
		{
			case PLAYING:
				game_screen.getGameElements().getPlaying().mouseDragged(e);
				break;
			default:
				break;
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch(GameState.game_state)
		{
			case MENU:
				game_screen.getGameElements().getMenu().mouseMoved(e);
				break;
			case PLAYING:
				game_screen.getGameElements().getPlaying().mouseMoved(e);
				break;
			default:
				break;
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch(GameState.game_state)
		{
			case MENU:
				game_screen.getGameElements().getMenu().mouseClicked(e);
				break;
			case PLAYING:
				game_screen.getGameElements().getPlaying().mouseClicked(e);
				break;
			default:
				break;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(GameState.game_state)
		{
			case MENU:
				game_screen.getGameElements().getMenu().mousePressed(e);
				break;
			case PLAYING:
				game_screen.getGameElements().getPlaying().mousePressed(e);
				break;
			default:
				break;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(GameState.game_state)
		{
			case MENU:
				game_screen.getGameElements().getMenu().mouseReleased(e);
				break;
			case PLAYING:
				game_screen.getGameElements().getPlaying().mouseReleased(e);
				break;
			default:
				break;
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
