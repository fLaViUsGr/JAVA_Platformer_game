package EntitiesPackage;

import static UtilsPackage.Utils.CharacterActions.*;
import static UtilsPackage.Utils.EnemyActions.getEnemyDamage;
import static UtilsPackage.Utils.EnemyActions.CURSE1;
import static UtilsPackage.Utils.EnemyActions.CURSE2;
import static UtilsPackage.Utils.GRAVITY;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import GameStatesPackage.Playing;
import MainPackage.GameElements;
import UtilsPackage.SaveLoad;
import static UtilsPackage.HelpMethods.*;

public class Player extends Entity{

	private int animation_speed = 15;
	private boolean movement = false;
	private boolean attacking1 = false;
	private boolean attacking2 = false;
	private BufferedImage[][] animations = new BufferedImage[12][8];
	private boolean left, right, jump;
	private int[][] lvlData;
	private float xDrawOffSet = 38 * GameElements.SCALE;
	private float yDrawOffSet = 24 * GameElements.SCALE;
	
	//gravity and jump
	private float jumpSpeed = -3.1f * GameElements.SCALE;
	private float fallSpeedAfterColision = 0.5f * GameElements.SCALE;
	
	//health bar
	private BufferedImage healthBarImage;
	
	private int healthBarWidth = (int)(400 * GameElements.SCALE);
	private int healthBarHeight = (int)(30 * GameElements.SCALE);
	private int healthBarX = (int)(10 * GameElements.SCALE);
	private int healthBarY = (int)(10 * GameElements.SCALE);
	
	private int smallHealthbarWidth = (int)(363 * GameElements.SCALE);
	private int smallHealthbarHeight = (int)(4 * GameElements.SCALE);
	private int smallHealthbarX = (int)(30 * GameElements.SCALE);
	private int smallHealthbarY = (int)(12 * GameElements.SCALE);
	
	private int healthWidth = smallHealthbarWidth;
	
	//attack box
	
	private int flipX = 0;
	private int flipW = 1;
	
	private boolean attackChecked;
	private Playing playing;
	
	private int damageAmount;
	
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 200;
		this.currentHealth = maxHealth;
		this.walk_speed = 2.0f * GameElements.SCALE;
		animationsImgSet();
		initiateHitBox(15, 40);
		initiateAttackBox();
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		
		hitBox.x = x;
		hitBox.y = y;
	}
	
	private void initiateAttackBox() {
		attackBox = new Rectangle2D.Float(x,y,(int)(20 * GameElements.SCALE),(int)(40 * GameElements.SCALE));
	}

	public void update() {
		updateHealthBar();
		if(currentHealth <= 0)
		{
			playing.setGameOver(true);
			return;
		}
		
		updateAttackBox();
		updatePosition();
		
		if(movement)
		{
			checkPotionTouched();
		}
		
		if(attacking2)
		{
			damageAmount = 10;
			checkAttack(damageAmount);
		}
		if(attacking1)
		{
			damageAmount = 20;
			checkAttack(damageAmount);
		}
		
		animationsLoop();
		animationsLoopOnlyJump();
		setAnimation();
	}
	
	private void checkPotionTouched() {
		playing.checkPotionTouched(hitBox);
	}

	private void checkAttack(int damageAmount) {
		if(damageAmount == 10)
		{
			if(attackChecked || animation_index != 0)
			{
				return;
			}
			changeHealth(-getEnemyDamage(CURSE1));
			attackChecked = true;
			playing.checkEnemyIfHit(attackBox, damageAmount);
		}
		
		if(damageAmount == 20)
		{
			if(attackChecked || animation_index != 2)
			{
				return;
			}
			attackChecked = true;
			changeHealth(-getEnemyDamage(CURSE2));
			playing.checkEnemyIfHit(attackBox, damageAmount);
		}
		
	}

	private void updateAttackBox() {
		if(right)
		{
			attackBox.x = hitBox.x + hitBox.width + (int)(GameElements.SCALE * 10);
		}
		else if(left)
		{
			attackBox.x = hitBox.x - hitBox.width - (int)(GameElements.SCALE * 10) - 5;
		}
		attackBox.y = hitBox.y + (GameElements.SCALE * 10) - 10;
	}

	private void updateHealthBar() {
		healthWidth = (int)((currentHealth / (float)(maxHealth)) * smallHealthbarWidth);
	}

	public void render(Graphics graphics, int xLvlOffset, int yLvlOffset) {
		graphics.drawImage(animations[state][animation_index], 
				(int)(hitBox.x - xDrawOffSet) - xLvlOffset + flipX, 
				(int)(hitBox.y - yDrawOffSet) - yLvlOffset, 
				width * flipW, 
				height, 
				null);
		drawHealthBar(graphics);
		//drawAttackBox(graphics, xLvlOffset, yLvlOffset);
		//drawHitBox(graphics, xLvlOffset, yLvlOffset);
	}

	private void drawHealthBar(Graphics graphics) {
		graphics.drawImage(healthBarImage, healthBarX, healthBarY, healthBarWidth, healthBarHeight, null);
		graphics.setColor(Color.RED);
		graphics.fillRect(smallHealthbarX + healthBarX, smallHealthbarY + healthBarY, healthWidth, smallHealthbarHeight);
	}

	private void animationsImgSet() {
		
		BufferedImage img = SaveLoad.getSpriteSet(SaveLoad.PLAYER_SPRITE);
		for(int i = 0; i < animations.length; i++)
		{
			for(int j = 0; j < animations[i].length; j++)
			{
				animations[i][j] = img.getSubimage(j * 96, i * 96, 96, 96);
			}
		}
		healthBarImage = SaveLoad.getSpriteSet(SaveLoad.HEALTHBAR);
	}
	
	private void updatePosition() {
		movement = false;
		
		if(jump)
		{
			jump();
		}
		
		if(!inAir)
		{
			if((!left && !right) || (left && right))
			{
				return;
			}
		}
		
		float xSpeed = 0;
		
		if(left)
		{
			xSpeed -= walk_speed;	
			flipX = width - 4;
			flipW = -1;
		}
		
		if(right)
		{
			xSpeed += walk_speed;	
			flipX = 0;
			flipW = 1;
		}
		
		if(!inAir)
		{
			if(!isEntityOnFloor(hitBox, lvlData))
			{
				inAir = true;
			}
		}
		
		if(inAir)
		{
			if(canMoveThere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData))
			{
				hitBox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPosition(xSpeed);
			}
			else
			{
				hitBox.y = getEntityYPositionUnderRoofOrAboveFloor(hitBox, airSpeed);
				if(airSpeed > 0)
				{
					resetInAir();
				}
				else
				{
					airSpeed = fallSpeedAfterColision;
				}
				updateXPosition(xSpeed);
			}
		}
		else
		{
			updateXPosition(xSpeed);
		}
		
		movement = true;
	}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if(!isEntityOnFloor(hitBox,lvlData))
		{
			inAir = true;
		}
	}
	
	private void jump() {
		if(inAir)
		{
			return;
		}
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPosition(float xSpeed) {
		if(canMoveThere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
		{
			hitBox.x += xSpeed;
		}
		else
		{
			hitBox.x = getEntityXPositionNextToWall(hitBox, xSpeed);
		}
	}

	public void changeHealth(int value) {
		currentHealth += value;
		if(currentHealth <= 0)
		{
			currentHealth = 0;
			//gameOver();
		}
		else if(currentHealth >= maxHealth)
		{
			currentHealth = maxHealth;
		}
	}
	
	private void setAnimation() {
		int start_animation = state;
		
			if(movement)
			{
				state = RUNNING;
				animation_speed = 8;
			}
			else
			{
				state = IDLE;
				animation_speed = 30;
			}
			
			if(inAir)
			{
				if(airSpeed < 0)
				{
					state = JUMPING;
					animation_speed = 10;
				}
				else
				{
					state = FALLING;
					animation_speed = 20;
				}
			}
			
			if(attacking1)
			{
				state = ATTACK1;
				animation_speed = 10;
			}
			
			if(attacking2)
			{
				state = ATTACK2;
				animation_speed = 10;
			}
		
		if(start_animation != state)
		{
			animation_tick = 0;
			animation_index = 0;
		}
	}
	
	
	private void animationsLoop() {
		if(state != JUMPING && state != FALLING)
		{
			animation_tick++;
			if(animation_tick >= animation_speed)
			{
				animation_tick = 0;
				animation_index++;
				if(animation_index >= spriteCount(state))
				{
					animation_index = 0;
					attacking1 = false;
					attacking2 = false;
					attackChecked = false;
				}
			}
		}
	}
	
	private void animationsLoopOnlyJump() {
		if(state == JUMPING || state == FALLING)
		{
			animation_tick++;
			if(animation_tick >= animation_speed)
			{
				animation_tick = 0;
				animation_index++;
				if(animation_index >= spriteCount(state))
				{
					animation_index = spriteCount(state) - 1;
				}
				
			}
		}
		
		
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void resetBooleans() {
		left = false;
		right = false;
	}
	
	public void setAttacking1(boolean attacking1) {
		this.attacking1 = attacking1;
	}
	
	public void setAttacking2(boolean attacking2) {
		this.attacking2 = attacking2;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetAll() {
		resetBooleans();
		inAir = false;
		attacking1 = false;
		attacking2 = false;
		movement = false;
		state = IDLE;
		currentHealth = maxHealth;
		
		hitBox.x = x;
		hitBox.y = y;
		
		if(!isEntityOnFloor(hitBox,lvlData))
		{
			inAir = true;
		}
	}
	
}
