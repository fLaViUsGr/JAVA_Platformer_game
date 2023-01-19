package UtilsPackage;

import static UtilsPackage.Utils.EnemyActions.CRABBY;
import static UtilsPackage.Utils.ObjectConstants.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import EntitiesPackage.Crabby;
import MainPackage.GameElements;
import ObjectsPackage.Potion;

public class HelpMethods {
	
	public static boolean canMoveThere(float x, float y, float width, float height, int[][] lvlData) {
		
		if(!isSolid(x,y,lvlData))
		{
			if(!isSolid(x + width, y + height, lvlData))
			{
				if(!isSolid(x + width, y, lvlData))
				{
					if(!isSolid(x, y + height, lvlData))
					{
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	private static boolean isSolid(float x, float y, int[][] lvlData) {
		int maxLvlWidth = lvlData[0].length * GameElements.TILE_SIZE;
		int maxLvlHeight = lvlData.length * GameElements.TILE_SIZE;
		if(x < 0 || x >= maxLvlWidth)
		{
			return true;
		}
		
		if(y < 0 || y >= maxLvlHeight)
		{
			return true;
		}
		
		float xIndex = x / GameElements.TILE_SIZE;
		float yIndex = y / GameElements.TILE_SIZE;
		
		return isSolidBetween((int)xIndex, (int)yIndex, lvlData);
	}
	
	public static boolean isSolidBetween(int xTile, int yTile, int[][] lvlData) {
		int val = lvlData[(int)yTile][(int)xTile];
		
		if(val >= 33 || val < 0 || (val != 0 && val != 17 && val != 26))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static float getEntityXPositionNextToWall(Rectangle2D.Float hitBox, float xSpeed) {
		int currentTile = (int)(hitBox.x / GameElements.TILE_SIZE);
		if(xSpeed > 0)
		{
			//right
			int tileXPosition = currentTile * GameElements.TILE_SIZE;
			int xOffset = (int)(GameElements.TILE_SIZE - hitBox.width);
			return tileXPosition + xOffset - 1;
		}
		else
		{
			//left
			return currentTile * GameElements.TILE_SIZE;
		}
	}
	
	public static float getEntityYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
		int currentTile = (int)(hitBox.y / GameElements.TILE_SIZE);
		if(airSpeed > 0)
		{
			//falling or touching floor
			int tileYPosition = currentTile * GameElements.TILE_SIZE;
			int yOffset = (int)(GameElements.TILE_SIZE - hitBox.height);
			return tileYPosition + yOffset - 1;
		}
		else
		{
			//jumping
			return currentTile * GameElements.TILE_SIZE;
		}
	}
	
	public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlData) {
		if(!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlData))
		{
			if(!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlData))
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isFloor(Rectangle2D.Float hitBox, float xSpeed, int[][] lvlData) {
		if(xSpeed > 0)
		{
			return isSolid(hitBox.x + xSpeed + hitBox.width, hitBox.y + hitBox.height + 1, lvlData);
		}
		else
		{
			return isSolid(hitBox.x + xSpeed, hitBox.y + hitBox.height + 1, lvlData);
		}
	}
	
	public static boolean areAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
		for(int i = 0; i < xEnd - xStart; i++)
		{
			if(isSolidBetween(xStart + i, y, lvlData))
			{
				return false;
			}
			if(!isSolidBetween(xStart + i, y + 1, lvlData))
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean areObstaclesInTheWay(int[][] lvlData, Rectangle2D.Float first_hitBox, Rectangle2D.Float second_hitBox, int tileY) {
		int firstXTyle = (int)(first_hitBox.x / GameElements.TILE_SIZE);
		int secondXTyle = (int)(second_hitBox.x / GameElements.TILE_SIZE);
		
		if(firstXTyle > secondXTyle)
		{
			return areAllTilesWalkable(secondXTyle, firstXTyle, tileY, lvlData);
		}
		else
		{
			return areAllTilesWalkable(firstXTyle, secondXTyle, tileY, lvlData);
		}
	}
	
	public static int[][] getLevelData(BufferedImage img) {

		int[][] level_data = new int[img.getHeight()][img.getWidth()];
		for(int j = 0; j < img.getHeight(); j++)
		{
			for(int i = 0; i < img.getWidth(); i++)
			{
				Color color = new Color(img.getRGB(i, j));
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				if(r >= 33)
				{
					r = 0;
				}
				if(r != 255 && g != 255 && b != 255) 
				{
					level_data[j][i] = r;
				}
			}
		}
		return level_data;
	}
	
	public static ArrayList<Crabby> getCrabs(BufferedImage img) {
		
		ArrayList<Crabby> list = new ArrayList<>();
		for(int j = 0; j < img.getHeight(); j++)
		{
			for(int i = 0; i < img.getWidth(); i++)
			{
				Color color = new Color(img.getRGB(i, j));
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				if(r != 255 && g == CRABBY && b != 255)
				{
					list.add(new Crabby(i * GameElements.TILE_SIZE, j * GameElements.TILE_SIZE));
				}
			}
		}
		return list;
	}
	
	public static Point getPlayerSpawn(BufferedImage img) {
		for(int j = 0; j < img.getHeight(); j++)
		{
			for(int i = 0; i < img.getWidth(); i++)
			{
				Color color = new Color(img.getRGB(i, j));
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				if(r == 255 && g == 255 && b == 255)
				{
					return new Point(i * GameElements.TILE_SIZE, j * GameElements.TILE_SIZE);
				}
			}
		}
		return new Point(1 * GameElements.TILE_SIZE, 1 * GameElements.TILE_SIZE);
	}
	
	public static ArrayList<Potion> getPotions(BufferedImage img) {
		
		ArrayList<Potion> list = new ArrayList<>();
		for(int j = 0; j < img.getHeight(); j++)
		{
			for(int i = 0; i < img.getWidth(); i++)
			{
				Color color = new Color(img.getRGB(i, j));
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				if(r != 255 && g != 255 && b == RED_POTION)
				{
					list.add(new Potion(i * GameElements.TILE_SIZE, j * GameElements.TILE_SIZE, RED_POTION));
				}
			}
		}
		return list;
	}
}
