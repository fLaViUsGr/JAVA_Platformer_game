package UIPackage;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import UtilsPackage.SaveLoad;
import static UtilsPackage.Utils.UI.VolumeSpliderPauseButton.*;

public class VolumeSliderButton extends PauseButtons {

	private BufferedImage[] slider_buttons;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver, mousePressed;
	private int buttonX, minX, maxX;
	
	public VolumeSliderButton(int x, int y, int width, int height) {
		super(x + width / 2, y, VBUTTON_WIDTH, height);
		button_hitbox.x -= VBUTTON_WIDTH / 2;
		buttonX = x + width / 2;
		this.x = x;
		this.width = width;
		minX = x + VBUTTON_WIDTH / 2;
		maxX = x + width - VBUTTON_WIDTH / 2;;
		loadVolumeSliderButtons();
	}
	
	private void loadVolumeSliderButtons() {
		BufferedImage temp = SaveLoad.getSpriteSet(SaveLoad.PAUSE_SLIDER_BUTTON);
		slider_buttons = new BufferedImage[2];
		for(int i = 0; i < slider_buttons.length; i++)
		{
			slider_buttons[i] = temp.getSubimage(i * VBUTTON_WIDTH_DEFAULT, 0, VBUTTON_WIDTH_DEFAULT, VBUTTON_HEIGHT_DEFAULT);
		}
		slider = temp.getSubimage(2 * VBUTTON_WIDTH_DEFAULT, 0, VSLIDER_WIDTH_DEFAULT, VBUTTON_HEIGHT_DEFAULT);
	}

	public void update() {
		index = 0;
		if(mouseOver)
		{
			index = 1;
		}
		if(mousePressed)
		{
			index = 1;
		}
	}
	
	public void draw(Graphics graphics) {
		graphics.drawImage(slider, x, y, width, height, null);
		graphics.drawImage(slider_buttons[index], buttonX - VBUTTON_WIDTH / 2, y, VBUTTON_WIDTH, height, null);
	}
	
	public void changeX(int x) {
		if(x < minX)
		{
			buttonX = minX;
		}
		else if(x > maxX)
		{
			buttonX = maxX;
		}
		else
		{
			buttonX = x;
		}
		button_hitbox.x = buttonX - VBUTTON_WIDTH / 2;
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
