package ObjectsPackage;

import static UtilsPackage.Utils.ObjectConstants.*;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import MainPackage.GameElements;

public class GameObjects {
	
	protected int x, y, objectType, animationSpeed = 15;
	protected Rectangle2D.Float hitBox;
	protected boolean doAnimation, active = true;
	protected int animation_tick, animation_index;
	protected int xDrawOffset, yDrawOffset;
	
	public GameObjects(int x, int y, int objectType) {
		this.x = x;
		this.y = y;
		this.objectType = objectType;
	}
	
	protected void updateAnimationTick() {
		animation_tick++;
		if(animation_tick >= animationSpeed)
		{
			animation_tick = 0;
			animation_index++;
			if(animation_index >= objectSpriteCount(objectType))
			{
				animation_index = 0;
			}
		}
	}

	public void resetObjects() {
		animation_index = 0;
		animation_tick = 0;
		active = true;
		doAnimation = true;
	}
	
	protected void initiateHitBox(int width, int height) {
		hitBox = new Rectangle2D.Float(x, y,(int)(width * GameElements.SCALE),(int)(height * GameElements.SCALE));
	}
	
	public void drawHitBox(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		graphics.setColor(Color.BLUE);
		graphics.drawRect((int)hitBox.x - xLvlOffset, (int)hitBox.y - yLvlOffset, (int)hitBox.width, (int)hitBox.height);
	}

	public int getObjectType() {
		return objectType;
	}

	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getAnimationIndex() {
		return animation_index;
	}
	
}
