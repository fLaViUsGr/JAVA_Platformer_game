package LevelsPackage;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import EntitiesPackage.Crabby;
import MainPackage.GameElements;
import ObjectsPackage.Potion;
import UtilsPackage.HelpMethods;
import UtilsPackage.SaveLoad;

import static UtilsPackage.HelpMethods.getLevelData;
import static UtilsPackage.HelpMethods.getCrabs;
import static UtilsPackage.HelpMethods.getPlayerSpawn;

public class Level {

	private BufferedImage img;
	private int[][] level_data;
	private ArrayList<Crabby>crabs;
	private ArrayList<Potion>potions;
	
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	
	private int lvlTilesWide_top;
	private int maxTilesOffset_top;
	private int maxLvlOffsetY;
	
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createPotions();
		calculateLevelOffsets();
		calculatePlayerSpawn();
	}
	
	private void createPotions() {
		potions = HelpMethods.getPotions(img);
	}

	private void calculatePlayerSpawn() {
		playerSpawn = getPlayerSpawn(img);
	}

	private void calculateLevelOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - GameElements.TILES_WIDTH;
		maxLvlOffsetX = GameElements.TILE_SIZE * maxTilesOffset;
		
		lvlTilesWide_top = img.getHeight();
		maxTilesOffset_top = lvlTilesWide_top - GameElements.TILES_HEIGHT;
		maxLvlOffsetY = GameElements.TILE_SIZE * maxTilesOffset_top;
	}

	private void createEnemies() {
		crabs = getCrabs(img);
	}

	private void createLevelData() {
		level_data = getLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return level_data[y][x];
	}
	
	public int[][] getLvlData() {
		return level_data;
	}
	
	public int getLevelOffsetX() {
		return maxLvlOffsetX;
	}
	
	public int getLevelOffsetY() {
		return maxLvlOffsetY;
	}
	
	public ArrayList<Crabby> getCrabbies() {
		return crabs;
	}
	
	public Point getPlayerSpawnPoint() {
		return playerSpawn;
	}
	
	public ArrayList<Potion> getPotions() {
		return potions;
	}
}
