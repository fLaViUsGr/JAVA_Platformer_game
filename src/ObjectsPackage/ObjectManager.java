package ObjectsPackage;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GameStatesPackage.Playing;
import LevelsPackage.Level;
import UtilsPackage.SaveLoad;

import static UtilsPackage.Utils.ObjectConstants.*;

public class ObjectManager {
	
	private Playing playing;
	private BufferedImage[][] potionImgs;
	private ArrayList<Potion>potions;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}
	
	public void checkObjectTouched(Rectangle2D.Float hitBox) {
		for(Potion p : potions)
		{
			if(p.isActive())
			{
				if(hitBox.intersects(p.getHitBox()))
				{
					p.setActive(false);
					applyEffectToPlayer(p);
				}
			}
		}
	}
	
	public void applyEffectToPlayer(Potion p) {
		if(p.getObjectType() == RED_POTION)
		{
			playing.getPlayer().changeHealth(RED_POTION_VALUE);
		}
	}

	private void loadImgs() {
		BufferedImage potionSprite = SaveLoad.getSpriteSet(SaveLoad.POTIONS_SPRITE);
		potionImgs = new BufferedImage[1][6];
		for(int j = 0; j < potionImgs.length; j++)
		{
			for(int i = 0; i < potionImgs[j].length; i++)
			{
				potionImgs[j][i] = potionSprite.getSubimage(24 * i, 24 * j, 24, 24); 
			}
		}
	}
	
	public void loadObjects(Level newLevel) {
		potions = newLevel.getPotions();	
	}
	
	
	public void update() {
		for(Potion p : potions)
		{
			if(p.isActive())
			{
				p.update();
			}
		}
	}
	
	public void draw(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		drawPotions(graphics, xLvlOffset, yLvlOffset);
	}

	private void drawPotions(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		for(Potion p : potions)
		{
			if(p.isActive())
			{
				graphics.drawImage(potionImgs[0][p.getAnimationIndex()],
						(int)(p.getHitBox().x - p.getxDrawOffset()) - xLvlOffset, 
						(int)(p.getHitBox().y - p.getyDrawOffset()) - yLvlOffset, 
						POTION_WIDTH, 
						POTION_HEIGHT, 
						null);
				//p.drawHitBox(graphics, xLvlOffset, yLvlOffset);
			}
		}
	}

	public void resetAllObjects() {
		for(Potion p : potions)
		{
			p.resetObjects();
		}
	}
}
