package UIPackage;

import java.awt.Rectangle;

public class PauseButtons {
	protected int x, y, width, height;
	protected Rectangle button_hitbox;
	
	public PauseButtons(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		initiateButtonHitbox();
	}

	private void initiateButtonHitbox() {
		button_hitbox = new Rectangle(x, y, width, height);
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getButton_hitbox() {
		return button_hitbox;
	}
	public void setButton_hitbox(Rectangle button_hitbox) {
		this.button_hitbox = button_hitbox;
	}
	
	
}
