package UIPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import UtilsPackage.SaveLoad;
import static UtilsPackage.Utils.UI.URMPauseBottons.*;

public class URMButtons extends PauseButtons {
	private BufferedImage[] URM_buttons;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;

	public URMButtons(int x, int y, int width, int height, int rowIndex) {
		super(x, y, width, height);
		this.rowIndex = rowIndex;
		loadURMButtons();
	}
	
	private void loadURMButtons() {
		BufferedImage temp = SaveLoad.getSpriteSet(SaveLoad.PAUSE_OTHER_BUTTONS);
		URM_buttons = new BufferedImage[3];
		for(int i = 0; i < URM_buttons.length; i++)
		{
			URM_buttons[i] = temp.getSubimage(i * URM_SIZE_DEFAULT, rowIndex * URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
		}
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
	
	public void draw(Graphics graphics) {
		graphics.drawImage(URM_buttons[index], x, y, URM_SIZE, URM_SIZE, null);
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
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
	
	

}
