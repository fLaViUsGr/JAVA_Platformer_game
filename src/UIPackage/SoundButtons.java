package UIPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import UtilsPackage.SaveLoad;
import static UtilsPackage.Utils.UI.SoundPauseButtons.*;

public class SoundButtons extends PauseButtons {
	
	private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed, muted;
	private int rowIndex, colIndex;
	
	public SoundButtons(int x, int y, int width, int height) {
		super(x, y, width, height);
		loadSoundButton();
	}

	private void loadSoundButton() {
		BufferedImage temp = SaveLoad.getSpriteSet(SaveLoad.PAUSE_SOUND_BUTTON);
		soundImgs = new BufferedImage[2][3];
		for(int j = 0; j < soundImgs.length; j++)
		{
			for(int i = 0; i < soundImgs[j].length; i++)
			{
				soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
			}
		}
	}
	
	public void update() {
		if(muted)
		{
			rowIndex = 1;
		}
		else
		{
			rowIndex = 0;
		}
		
		colIndex = 0;
		
		if(mouseOver)
		{
			colIndex = 1;
		}
		
		if(mousePressed)
		{
			colIndex = 2;
		}
	}
	
	public void draw(Graphics graphics) {
		graphics.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
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

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
}
