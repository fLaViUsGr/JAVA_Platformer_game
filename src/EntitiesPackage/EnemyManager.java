package EntitiesPackage;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static UtilsPackage.Utils.EnemyActions.*;

import GameStatesPackage.Playing;
import LevelsPackage.Level;
import UtilsPackage.SaveLoad;

public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] crabby_animations;
	private ArrayList<Crabby> crabs = new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImages();
	}

	public void loadEnemies(Level level) {
		crabs = level.getCrabbies();
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyAlive = false;
		for(Crabby c : crabs)
		{
			if(c.isAlive())
			{
				c.update(lvlData, player);
				isAnyAlive = true;
			}
		}
		if(!isAnyAlive)
		{
			playing.setLevelCompleted(true);
		}
	}
	
	public void draw(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		drawCrabs(graphics, xLvlOffset, yLvlOffset);
	}
	
	private void drawCrabs(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		for(Crabby c : crabs)
		{
			if(c.isAlive())
			{
				graphics.drawImage(crabby_animations[c.getEnemyState()][c.getAnimationIndex()], 
						(int)(c.getHitBox().x) - xLvlOffset - CRRABY_DRAWOFFSET_X + c.flipX(), 
						(int)(c.getHitBox().y) - yLvlOffset, 
						CRABBY_WIDTH * c.flipW(), 
						CRABBY_HEIGHT, 
						null);
				//c.drawHitBox(graphics, xLvlOffset, yLvlOffset);
				//c.drawAttackBox(graphics, xLvlOffset, yLvlOffset);
			}
		}
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox, int damageAmount) {
		for(Crabby c : crabs)
		{
			if(c.isAlive())
			{
				if(attackBox.intersects(c.getHitBox()))
				{
					c.takeDamage(damageAmount);
					return; 
				}
			}
		}
	}

	private void loadEnemyImages() {
		crabby_animations = new BufferedImage[7][9];	
		BufferedImage temp = SaveLoad.getSpriteSet(SaveLoad.CRABBY_SPRITE);
		for(int j = 0; j < crabby_animations.length; j++)
		{
			for(int i = 0; i < crabby_animations[j].length; i++)
			{
				crabby_animations[j][i] = temp.getSubimage(i * CRABBY_WIDTH_DEFAULT, j * CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
			}
		}
	}

	public void resetAllEnemies() {
		for(Crabby c : crabs)
		{
			c.resetEnemy();
		}
	}
}
