package LevelsPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GameStatesPackage.GameState;
import MainPackage.GameElements;
import UtilsPackage.SaveLoad;

public class LevelManager {
	private GameElements game_elements;
	private BufferedImage[] levelSprite;
	private ArrayList<Level>levels;
	private int lvlIndex = 0;
	
	public LevelManager(GameElements game_elements) {
		this.game_elements = game_elements; 
		importLvlSprite();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	
	private void buildAllLevels() {
		BufferedImage[] allLevels = SaveLoad.getAllLevels();
		
		for(BufferedImage img : allLevels)
		{
			levels.add(new Level(img));
		}
 	}

	private void importLvlSprite() {
		BufferedImage img = SaveLoad.getSpriteSet(SaveLoad.MAP_SPRITE); 
		levelSprite = new BufferedImage[33];
		for(int j = 0; j < 3; j++)
		{
			for(int i = 0; i < 11; i++)
			{
				int index = j * 11 + i;
				levelSprite[index] = img.getSubimage(i * 48, j * 48, 48, 48);
			}
		}
	}
	
	public void draw(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		for(int j = 0; j < levels.get(lvlIndex).getLvlData().length; j++)
		{
			for(int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++)
			{
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				graphics.drawImage(levelSprite[index], GameElements.TILE_SIZE * i - xLvlOffset, GameElements.TILE_SIZE * j - yLvlOffset, GameElements.TILE_SIZE, GameElements.TILE_SIZE, null);
			}
		}
	}
	
	public void update() {
		
	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	
	public int getAmountOfLevels() {
		return levels.size();
	}

	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex >= levels.size())
		{
			lvlIndex = 0;
			System.out.print("END!");
			GameState.game_state = GameState.MENU;
		}
		Level newLevel = levels.get(lvlIndex);
		game_elements.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game_elements.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
		game_elements.getPlaying().setMaxLevelOffsetX(newLevel.getLevelOffsetX());
		game_elements.getPlaying().setMaxLevelOffsetY(newLevel.getLevelOffsetY());
		game_elements.getPlaying().getObjectManager().loadObjects(newLevel);
	}
}
