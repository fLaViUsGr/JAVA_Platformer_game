package EntitiesPackage;

import static UtilsPackage.Utils.EnemyActions.*;
import static UtilsPackage.Utils.GRAVITY;

import java.awt.geom.Rectangle2D;

import MainPackage.GameElements;
import static UtilsPackage.HelpMethods.*;
import static UtilsPackage.Utils.Directions.*;

public abstract class Enemy extends Entity {
	protected int enemyType;
	protected int animationSpeed = 15;
	protected boolean firstUpdate = true; 
	protected int walkDirection = LEFT;
	protected int tileY;
	protected float attackRange = GameElements.TILE_SIZE;
	protected boolean alive = true;
	protected boolean attackChecked;

	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = getMaxHealth(enemyType);
		currentHealth = maxHealth;
		walk_speed = 1.5f * GameElements.SCALE;
	}
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!isEntityOnFloor(hitBox, lvlData))
		{
			inAir = true;
		}
		firstUpdate = false;
	}
	
	protected void updateinAir(int[][] lvlData) {
		if(canMoveThere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData))
		{
			hitBox.y += airSpeed;
			airSpeed += GRAVITY;
		}
		else
		{
			inAir = false;
			hitBox.y = getEntityYPositionUnderRoofOrAboveFloor(hitBox, airSpeed);
			tileY = (int)(hitBox.y / GameElements.TILE_SIZE);
		}
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int)(player.getHitBox().y / GameElements.TILE_SIZE);
		if(playerTileY == tileY)
		{
			if(isPlayerInWalkingRange(player))
			{
				if(areObstaclesInTheWay(lvlData, hitBox, player.hitBox, tileY))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean isPlayerInWalkingRange(Player player) {
		int absoluteValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absoluteValue <= attackRange * 5;
	}
	
	protected boolean isPlayerInAttackingRange(Player player) {
		int absoluteValue = (int) Math.abs(player.hitBox.x - hitBox.x);
		return absoluteValue <= attackRange;
	}

	protected void enemyMoving(int[][] lvlData) {
		float xSpeed = 0;
		if(walkDirection == LEFT) 
		{
			xSpeed = -walk_speed;
		}
		else
		{
			xSpeed = walk_speed;
		}
		if(canMoveThere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
		{
			if(isFloor(hitBox, xSpeed, lvlData))
			{
				hitBox.x += xSpeed;
				return;
			}
		}
		changeWalkDirection();
	}
	
	protected void runTowardsPlayer(Player player) {
		if(player.hitBox.x > hitBox.x)
		{
			walkDirection = RIGHT;
		}
		else
		{
			walkDirection = LEFT;
		}
	}
	
	protected void stateChange(int enemyState) {
		this.state = enemyState;
		animation_tick = 0;
		animation_index = 0;
	}
	
	protected void updateAnimationTick() {
		animation_tick++;
		if(animation_tick >= animationSpeed)
		{
			animation_tick = 0;
			animation_index++;
			if(animation_index >= enemySpriteCount(enemyType,state))
			{
				animation_index = 0;
				
				switch(state)
				{
					case ATTACK, GET_HIT -> state = IDLE;
					case DEAD -> alive = false;
				}
			}
		}
	}
	
	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.hitBox))
		{
			player.changeHealth(-getEnemyDamage(enemyType));
		}
		attackChecked = true;
	}
	
	protected void takeDamage(int damage) {
		currentHealth -= damage;
		if(currentHealth <= 0)
		{
			stateChange(DEAD);
		}
		else
		{
			stateChange(GET_HIT);
		}
	}
	
	protected void changeWalkDirection() {
		if(walkDirection == LEFT)
		{
			walkDirection = RIGHT;
		}
		else
		{
			walkDirection = LEFT;
		}
		
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void resetEnemy() {
		hitBox.x = x;
		hitBox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		stateChange(IDLE);
		alive = true;
		airSpeed = 0;
	}
	
}
