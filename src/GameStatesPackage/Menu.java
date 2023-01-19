package GameStatesPackage;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import MainPackage.GameElements;
import UIPackage.MenuButton;
import UtilsPackage.SaveLoad;

public class Menu extends State implements StateMethods {
	
	private BufferedImage background_img;
	private BufferedImage menuBox;
	private int menuX, menuY, menuW, menuH;
	private MenuButton[] menu_buttons = new MenuButton[3];
	
	public Menu(GameElements game_elements) {
		super(game_elements);
		loadButtons();
		loadBackground();
	}
	
	private void loadBackground() {
		background_img = SaveLoad.getSpriteSet(SaveLoad.MENU_BACKGROUND);
		menuBox = SaveLoad.getSpriteSet(SaveLoad.MENU_BOX);
		menuW = (int)(300 * GameElements.SCALE);
		menuH = (int)(300 * GameElements.SCALE);
		menuX = GameElements.GAME_WIDTH / 2 - menuW / 2;
		menuY = GameElements.GAME_HEIGHT / 2 - menuH / 2;
	}

	private void loadButtons() {
		menu_buttons[0] = new MenuButton(GameElements.GAME_WIDTH / 2, GameElements.GAME_HEIGHT / 2 - 10, 0, GameState.PLAYING);
		menu_buttons[1] = new MenuButton(GameElements.GAME_WIDTH / 2, GameElements.GAME_HEIGHT / 2 + 40, 1, GameState.OPTIONS);
		menu_buttons[2] = new MenuButton(GameElements.GAME_WIDTH / 2, GameElements.GAME_HEIGHT / 2 + 90, 2, GameState.QUIT);
	}

	@Override
	public void update() {
		for(MenuButton menu_button : menu_buttons)
		{
			menu_button.update();
		}
		
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(background_img, 0, 0, GameElements.GAME_WIDTH, GameElements.GAME_HEIGHT, null);
		graphics.drawImage(menuBox, menuX, menuY, menuW, menuH, null);
		for(MenuButton menu_button : menu_buttons)
		{
			menu_button.draw(graphics);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton menu_button : menu_buttons)
		{
			if(isIn(e,menu_button))
			{
				menu_button.setMousePressed(true);
				break;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton menu_button : menu_buttons)
		{
			if(isIn(e,menu_button))
			{
				if(menu_button.isMousePressed())
				{
					menu_button.applyGameState();
				}
				break;
			}
		}
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton menu_button : menu_buttons)
		{
			menu_button.resetBooleans();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton menu_button : menu_buttons)
		{
			menu_button.setMouseOver(false);
		}
		for(MenuButton menu_button : menu_buttons)
		{
			if(isIn(e,menu_button))
			{
				menu_button.setMouseOver(true);
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			GameState.game_state = GameState.PLAYING;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}
