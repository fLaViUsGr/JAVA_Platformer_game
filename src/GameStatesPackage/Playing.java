package GameStatesPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;

import EntitiesPackage.EnemyManager;
import EntitiesPackage.Player;
import LevelsPackage.LevelManager;
import MainPackage.GameElements;
import ObjectsPackage.ObjectManager;
import UIPackage.GameOverOverlay;
import UIPackage.LevelCompletedOverlay;
import UIPackage.PauseOverlay;
import UtilsPackage.SaveLoad;

public class Playing extends State implements StateMethods {

	private Player player;
	private LevelManager lvlManager;
	private EnemyManager enemyManager;
	private boolean paused = false;
	private PauseOverlay pause_overlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay lvlcompletedoverlay;
	
	private int xLvlOffset;
	private int leftBorder = (int)(0.5 * GameElements.GAME_WIDTH);
	private int rightBorder = (int)(0.5 * GameElements.GAME_WIDTH);
	private int maxLvlOffsetX;
	
	private int yLvlOffset;
	private int bottomBorder = (int)(0.5 * GameElements.GAME_HEIGHT);
	private int topBorder = (int)(0.5 * GameElements.GAME_HEIGHT);
	private int maxLvlOffsetY;
	
	private BufferedImage playing_background_image;
	
	private boolean gameOver;
	
	private boolean lvlcompleted = false;
	
	private ObjectManager objManager;
	
	public Playing(GameElements game_elements) {
		super(game_elements);
		initializeClasses();
		playing_background_image = SaveLoad.getSpriteSet(SaveLoad.MAP_BACKGROUND);
		
		calculateLevelOffsets();
		loadStartLevel();
	}
	
	public void loadNextLevel() {
		resetAll();
		lvlManager.loadNextLevel();
		player.setSpawn(lvlManager.getCurrentLevel().getPlayerSpawnPoint());
	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(lvlManager.getCurrentLevel());
		objManager.loadObjects(lvlManager.getCurrentLevel());
	}

	private void calculateLevelOffsets() {
		maxLvlOffsetX = lvlManager.getCurrentLevel().getLevelOffsetX();
		maxLvlOffsetY = lvlManager.getCurrentLevel().getLevelOffsetY();
	}

	private void initializeClasses() {
		lvlManager = new LevelManager(game_elements);
		enemyManager = new EnemyManager(this);
		player = new Player(100,400,(int)(GameElements.SCALE * 96),(int)(GameElements.SCALE * 96), this);
		player.loadLvlData(lvlManager.getCurrentLevel().getLvlData());
		player.setSpawn(lvlManager.getCurrentLevel().getPlayerSpawnPoint());
		pause_overlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		lvlcompletedoverlay = new LevelCompletedOverlay(this);
		objManager = new ObjectManager(this);
	}

	@Override
	public void update() {
		if(paused)
		{
			pause_overlay.update();
		}
		else if(lvlcompleted)
		{
			lvlcompletedoverlay.update();
		}
		else if(!gameOver)
		{
			lvlManager.update();
			objManager.update();
			enemyManager.update(lvlManager.getCurrentLevel().getLvlData(), player);
			player.update();
			checkCloseToBorder();
			checkCloseToBorder_top();
		}
	}

	private void checkCloseToBorder() {
		int playerX = (int)(player.getHitBox().x);
		int diff = playerX - xLvlOffset;
		
		if(diff > rightBorder)
		{
			xLvlOffset += diff - rightBorder;
		}
		if(diff < leftBorder)
		{
			xLvlOffset += diff - leftBorder;
		}
		if(xLvlOffset > maxLvlOffsetX)
		{
			xLvlOffset = maxLvlOffsetX;
		}
		else if(xLvlOffset < 0)
		{
			xLvlOffset = 0;
		}
	}
	
	private void checkCloseToBorder_top() {
		int playerY = (int)(player.getHitBox().y);
		int diff_top = playerY - yLvlOffset;
		
		if(diff_top > bottomBorder)
		{
			yLvlOffset += diff_top - bottomBorder;
		}
		if(diff_top < topBorder)
		{
			yLvlOffset += diff_top - topBorder;
		}
		if(yLvlOffset > maxLvlOffsetY)
		{
			yLvlOffset = maxLvlOffsetY;
		}
		else if(yLvlOffset < 0)
		{
			yLvlOffset = 0;
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(playing_background_image, 0, 0, GameElements.GAME_WIDTH, GameElements.GAME_HEIGHT, null);
		lvlManager.draw(graphics, xLvlOffset, yLvlOffset);
		player.render(graphics, xLvlOffset, yLvlOffset);
		enemyManager.draw(graphics, xLvlOffset, yLvlOffset);
		objManager.draw(graphics, xLvlOffset, yLvlOffset);
		
		if(paused)
		{
			graphics.setColor(new Color(0,0,0,140));
			graphics.fillRect(0, 0, GameElements.GAME_WIDTH, GameElements.GAME_HEIGHT);
			pause_overlay.draw(graphics);
		}
		else if(gameOver)
		{
			gameOverOverlay.draw(graphics);
		}
		else if(lvlcompleted)
		{
			lvlcompletedoverlay.draw(graphics);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
		{
			if(paused)
			{
				pause_overlay.mouseDragged(e);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!gameOver)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				player.setAttacking2(true);
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				player.setAttacking1(true);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver)
		{
			if(paused)
			{
				pause_overlay.mousePressed(e);
			}
			else if(lvlcompleted)
			{
				lvlcompletedoverlay.mousePressed(e);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver)
		{
			if(paused)
			{
				pause_overlay.mouseReleased(e);
			}
			else if(lvlcompleted)
			{
				lvlcompletedoverlay.mouseReleased(e);
			}
		}
		
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver)
		{
			if(paused)
			{
				pause_overlay.mouseMoved(e);
			}
			else if(lvlcompleted)
			{
				lvlcompletedoverlay.mouseMoved(e);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
		{
			gameOverOverlay.keyPressed(e);
		}
		else
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_A:
					player.setLeft(true);
					break;
				case KeyEvent.VK_D:
					player.setRight(true);
					break;
				case KeyEvent.VK_SPACE:
					player.setJump(true);
					break;
				case KeyEvent.VK_ESCAPE:
					paused = !paused;
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!gameOver)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_A:
					player.setLeft(false);
					break;
				case KeyEvent.VK_D:
					player.setRight(false);
					break;
				case KeyEvent.VK_SPACE:
					player.setJump(false);
					break;
			}
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public void lostWindowsFocus() {
		player.resetBooleans();
	}
	
	public void resumeGame() {
		paused = false;
	}
	
	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlcompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		objManager.resetAllObjects();
	}

	public void checkEnemyIfHit(Rectangle2D.Float attackBox, int damageAmount) {
		enemyManager.checkEnemyHit(attackBox, damageAmount);
	}
	
	public void checkPotionTouched(Rectangle2D.Float hitBox) {
		objManager.checkObjectTouched(hitBox);
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public void setMaxLevelOffsetX(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	
	public void setMaxLevelOffsetY(int lvlOffset) {
		this.maxLvlOffsetY = lvlOffset;
	}

	public void setLevelCompleted(boolean lvlcompleted) {
		this.lvlcompleted = lvlcompleted;
	}
	
	public ObjectManager getObjectManager() {
		return objManager;
	}
}
