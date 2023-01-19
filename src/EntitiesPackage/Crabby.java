package EntitiesPackage;

import static UtilsPackage.Utils.Directions.*;
import static UtilsPackage.Utils.EnemyActions.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import MainPackage.GameElements;

public class Crabby extends Enemy {
	
	private int attackBoxOffsetX;

	public Crabby(float x, float y) {
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initiateHitBox(22, 20);
		initiateAttackBox();
	}
	
	private void initiateAttackBox() {
		attackBox = new Rectangle2D.Float(x,y,(int)(82 * GameElements.SCALE),(int)(19 * GameElements.SCALE));
		attackBoxOffsetX = (int)(GameElements.SCALE * 30);
	}

	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	private void updateAttackBox() {
		attackBox.x = hitBox.x - attackBoxOffsetX;
		attackBox.y = hitBox.y;
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate)
		{
			firstUpdateCheck(lvlData);
		}
		if(inAir)
		{
			updateinAir(lvlData);
		}
		else
		{
			switch(state)
			{
				case IDLE:
					stateChange(RUN);
					break;
				case RUN:
					if(canSeePlayer(lvlData, player))
					{
						runTowardsPlayer(player);
						if(isPlayerInAttackingRange(player))
						{
							stateChange(ATTACK);
						}
					}
					enemyMoving(lvlData);
					break;
				case ATTACK:
					if(animation_index == 0)
					{
						attackChecked = false;
					}
					if(animation_index == 3 && !attackChecked)
					{
						checkPlayerHit(attackBox, player);
					}
					break;
				case GET_HIT:
					break;
			}
		}
	}
	
	public int flipX() {
		if(walkDirection == RIGHT)
		{
			return width;
		}
		else
		{
			return 0;
		}
	}
	
	public int flipW() {
		if(walkDirection == RIGHT)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}
}
