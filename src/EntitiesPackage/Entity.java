package EntitiesPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import MainPackage.GameElements;

public abstract class Entity {
	
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitBox;
	protected int animation_tick, animation_index;
	protected int state;
	protected float airSpeed;
	protected boolean inAir = false;
	protected int maxHealth;
	protected int currentHealth;
	protected Rectangle2D.Float attackBox;
	protected float walk_speed;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void initiateHitBox(int width, int height) {
		hitBox = new Rectangle2D.Float(x, y,(int)(width * GameElements.SCALE),(int)(height * GameElements.SCALE));
	}

	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}
	
	public int getEnemyState() {
		return state;
	}
	
	protected void drawAttackBox(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		graphics.setColor(Color.RED);
		graphics.drawRect((int)attackBox.x - xLvlOffset, (int)attackBox.y - yLvlOffset,(int) attackBox.width, (int)attackBox.height);
	}
	
	protected void drawHitBox(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		graphics.setColor(Color.BLUE);
		graphics.drawRect((int)hitBox.x - xLvlOffset, (int)hitBox.y - yLvlOffset, (int)hitBox.width, (int)hitBox.height);
	}
	
	public int getAnimationIndex() {
		return animation_index;
	}
}
