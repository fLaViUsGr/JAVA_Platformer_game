package GameStatesPackage;

import java.awt.event.MouseEvent;

import MainPackage.GameElements;
import UIPackage.MenuButton;

public class State {

	protected GameElements game_elements;
	
	public State(GameElements game_elements) {
		this.game_elements = game_elements;
	}
	
	public GameElements getGameElements() {
		return game_elements;
	}
	
	public boolean isIn(MouseEvent e, MenuButton menu_button) {
		return menu_button.getButtonHitbox().contains(e.getX(), e.getY());
	}
}
