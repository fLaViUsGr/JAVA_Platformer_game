package UIPackage;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import GameStatesPackage.GameState;
import GameStatesPackage.Playing;
import MainPackage.GameElements;
import UtilsPackage.SaveLoad;
import static UtilsPackage.Utils.UI.SoundPauseButtons.*;
import static UtilsPackage.Utils.UI.URMPauseBottons.*;
import static UtilsPackage.Utils.UI.VolumeSpliderPauseButton.*;


public class PauseOverlay {
	
	private BufferedImage pauseBox;
	private int bgX, bgY, bgW, bgH;
	private SoundButtons sfx;
	private URMButtons menu_button, resume_button, restart_button;
	private Playing playing;
	private VolumeSliderButton volume_button;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createPauseSoundButton();
		createPauseURMButton();
		createVolumeSliderButton();
	}
	
	private void createVolumeSliderButton() {
		int vX = (int) ((bgX + 150 - VSLIDER_WIDTH / 2) * GameElements.SCALE);
		int vY = (int) (290 * GameElements.SCALE);
		volume_button = new VolumeSliderButton(vX, vY, VSLIDER_WIDTH, VBUTTON_HEIGHT);
	}

	private void createPauseURMButton() {
		int restartX = (int)((bgX + 150 - URM_SIZE / 2) * GameElements.SCALE);
		int menuX = (int)((restartX - 70) * GameElements.SCALE);
		int resumeX = (int)((restartX + 70)  * GameElements.SCALE);
		int buttonsY = (int)(400 * GameElements.SCALE);
		
		menu_button = new URMButtons(menuX, buttonsY, URM_SIZE, URM_SIZE, 2);
		resume_button = new URMButtons(resumeX, buttonsY, URM_SIZE, URM_SIZE, 0);
		restart_button = new URMButtons(restartX, buttonsY, URM_SIZE, URM_SIZE, 1);
	}

	private void createPauseSoundButton() {
		int sfxX = (int)((bgX + 150 - SOUND_SIZE / 2) * GameElements.SCALE);
		int sfxY = (int)((bgY + 150) * GameElements.SCALE);
		sfx = new SoundButtons(sfxX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	private void loadBackground() {
		pauseBox = SaveLoad.getSpriteSet(SaveLoad.PAUSE_BOX);
		bgW = (int) (300 * GameElements.SCALE);
		bgH = (int) (300 * GameElements.SCALE);
		bgX = (int) (GameElements.GAME_WIDTH / 2 * GameElements.SCALE - bgW / 2);
		bgY = (int) (GameElements.GAME_HEIGHT / 2 * GameElements.SCALE - bgH / 2);
	}

	public void update() {
		sfx.update();
		menu_button.update();
		resume_button.update();
		restart_button.update();
		volume_button.update();
	}
	
	public void draw(Graphics graphics) {
		graphics.drawImage(pauseBox, bgX, bgY, bgW, bgH, null);
		
		sfx.draw(graphics);
		
		menu_button.draw(graphics);
		resume_button.draw(graphics);
		restart_button.draw(graphics);
		
		volume_button.draw(graphics);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(volume_button.isMousePressed())
		{
			volume_button.changeX(e.getX());
		}
	}

	public void mousePressed(MouseEvent e) {
		if(isIn(e, sfx))
		{
			sfx.setMousePressed(true);
		}
		else if(isIn(e, menu_button))
		{
			menu_button.setMousePressed(true);
		}
		else if(isIn(e, resume_button))
		{
			resume_button.setMousePressed(true);
		}
		else if(isIn(e, restart_button))
		{
			restart_button.setMousePressed(true);
		}
		else if(isIn(e, volume_button))
		{
			volume_button.setMousePressed(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(isIn(e, sfx))
		{
			if(sfx.isMousePressed())
			{
				sfx.setMuted(!sfx.isMuted());
			}
		}
		else if(isIn(e, menu_button))
		{
			if(menu_button.isMousePressed())
			{
				playing.resetAll();
				GameState.game_state = GameState.MENU;
				playing.resumeGame();
			}
		}
		else if(isIn(e, restart_button))
		{
			if(restart_button.isMousePressed())
			{
				playing.resetAll();
				playing.resumeGame();
			}
		}
		else if(isIn(e, resume_button))
		{
			if(resume_button.isMousePressed())
			{
				playing.resumeGame();
			}
		}
		
		menu_button.resetBools();
		restart_button.resetBools();
		resume_button.resetBools();
		sfx.resetBools();
		volume_button.resetBools();
	}

	public void mouseMoved(MouseEvent e) {
		sfx.setMouseOver(false);
		menu_button.setMouseOver(false);
		restart_button.setMouseOver(false);
		resume_button.setMouseOver(false);
		volume_button.setMouseOver(false);
		
		if(isIn(e, sfx))
		{
			sfx.setMouseOver(true);
		}
		else if(isIn(e, menu_button))
		{
			menu_button.setMouseOver(true);
		}
		else if(isIn(e, restart_button))
		{
			restart_button.setMouseOver(true);
		}
		else if(isIn(e, resume_button))
		{
			resume_button.setMouseOver(true);
		}
		else if(isIn(e, volume_button))
		{
			volume_button.setMouseOver(true);
		}
		
	}
	
	private boolean isIn(MouseEvent e, PauseButtons button) {
		return button.getButton_hitbox().contains(e.getX(), e.getY());
	}
	
}
