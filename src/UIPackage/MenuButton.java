package UIPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import GameStatesPackage.GameState;
import UtilsPackage.SaveLoad;
import static UtilsPackage.Utils.UI.Buttons.*;

public class MenuButton {

	private int x, y, rowIndex, index;
	private GameState game_state;
	private BufferedImage[] menu_img;
	private boolean mouseOver, mousePressed;
	private Rectangle button_hitbox;
	
	public MenuButton(int x, int y, int rowIndx, GameState game_state) {
		this.x = x;
		this.y = y;
		this.rowIndex = rowIndx;
		this.game_state = game_state;
		loadMenuImages();
		initiateButtonHitbox();
	}

	private void initiateButtonHitbox() {
		button_hitbox = new Rectangle(x - (BUTTON_WIDTH / 2), y, BUTTON_WIDTH, BUTTON_HEIGHT);
	}

	private void loadMenuImages() {
		menu_img = new BufferedImage[3];
		BufferedImage temp = SaveLoad.getSpriteSet(SaveLoad.MENU_BUTTONS_URM);
		for(int i = 0; i < menu_img.length; i++)
		{
			menu_img[i] = temp.getSubimage(i * BUTTON_WIDTH_DEFAULT, rowIndex * BUTTON_HEIGHT_DEFAULT, BUTTON_WIDTH_DEFAULT, BUTTON_HEIGHT_DEFAULT);
		}
	}
	
	public void draw(Graphics graphics) {
		graphics.drawImage(menu_img[index], x - (BUTTON_WIDTH / 2), y - (BUTTON_HEIGHT / 2), BUTTON_WIDTH, BUTTON_HEIGHT, null);
	}
	
	public void update() {
		index = 0;
		if(mouseOver)
		{
			index = 1;
		}
		if(mousePressed)
		{
			index = 2;
		}
	}

	public boolean isMouseOver() {
		return mouseOver;
	}
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Rectangle getButtonHitbox() {
		return button_hitbox;
	}
	
	public void applyGameState() {
		GameState.game_state = game_state;
	}
	
	public void resetBooleans() {
		mouseOver = false;
		mousePressed = false;
	}
	
	
	
}
