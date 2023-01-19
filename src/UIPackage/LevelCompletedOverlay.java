package UIPackage;

import static UtilsPackage.Utils.UI.URMPauseBottons.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import GameStatesPackage.GameState;
import GameStatesPackage.Playing;
import MainPackage.GameElements;
import UtilsPackage.SaveLoad;

public class LevelCompletedOverlay {

	private Playing playing;
	private URMButtons menu, next;
	private BufferedImage img;
	private int bgX, bgY, bgW, bgH;
	
	public LevelCompletedOverlay(Playing playing) {
		this.playing = playing;
		initiateImage();
		initiateButtons();
	}

	private void initiateButtons() {
		int menuX = (int)((bgX + 50) * GameElements.SCALE);
		int nextX = (int)((bgW + bgX - URM_SIZE - 50)  * GameElements.SCALE);
		int buttonsY = (int)((bgY + bgH / 2) * GameElements.SCALE);
		
		menu = new URMButtons(menuX, buttonsY, URM_SIZE, URM_SIZE, 2);
		next = new URMButtons(nextX, buttonsY, URM_SIZE, URM_SIZE, 0);
	}

	private void initiateImage() {
		img = SaveLoad.getSpriteSet(SaveLoad.COMPLETED_LEVEL_IMAGE);
		bgW = (int)(600 * GameElements.SCALE);
		bgH = (int)(200 * GameElements.SCALE);
		bgX = GameElements.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int)(75 * GameElements.SCALE);
	}
	
	private boolean isIn(URMButtons b, MouseEvent e) {
		return b.getButton_hitbox().contains(e.getX(), e.getY());
	}
	
	public void draw(Graphics graphics) {
		graphics.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(graphics);
		menu.draw(graphics);
	}
	
	public void update() {
		next.update();
		menu.update();
	}
	
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		
		if(isIn(menu, e))
		{
			menu.setMouseOver(true);
		}
		else if(isIn(next, e))
		{
			next.setMouseOver(true);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isIn(menu, e))
		{
			if(menu.isMousePressed())
			{
				playing.resetAll();
				GameState.game_state = GameState.MENU;
			}
		}
		else if(isIn(next, e))
		{
			if(next.isMousePressed())
			{
				playing.loadNextLevel();
			}
		}
		menu.resetBools();
		next.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if(isIn(menu, e))
		{
			menu.setMousePressed(true);
		}
		else if(isIn(next, e))
		{
			next.setMousePressed(true);
		}
	}
	
}
