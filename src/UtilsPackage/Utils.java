package UtilsPackage;

import MainPackage.GameElements;

public class Utils {
	
	public final static float GRAVITY = 0.04f * GameElements.SCALE;
	
	public static class ObjectConstants {
		public static final int RED_POTION = 0;
		public static final int KEY = 1;
		
		public static final int RED_POTION_VALUE = 50;
		
		public static final int POTION_DEFAULT_WIDTH = 24;
		public static final int POTION_DEFAULT_HEIGHT = 24;
		public static final int POTION_WIDTH = (int)(GameElements.SCALE * POTION_DEFAULT_WIDTH);
		public static final int POTION_HEIGHT = (int)(GameElements.SCALE * POTION_DEFAULT_HEIGHT);
		
		public static int objectSpriteCount(int objectType) {
			switch(objectType) 
			{
				case RED_POTION:
					return 6;
				case KEY:
					return 0;
			}
			return -1;
		}
	}
	
	public static class EnemyActions {
		public static final int CRABBY = 0;
		public static final int CURSE1 = 1;
		public static final int CURSE2 = 2;
		
		public static final int IDLE = 0;
		public static final int RUN = 1;
		public static final int JUMP = 2;
		public static final int FALL = 3;
		public static final int ATTACK = 4;
		public static final int GET_HIT = 5;
		public static final int DEAD = 6;
		
		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;
		
		public static final int CRABBY_WIDTH = (int)(CRABBY_WIDTH_DEFAULT * GameElements.SCALE);
		public static final int CRABBY_HEIGHT = (int)(CRABBY_HEIGHT_DEFAULT * GameElements.SCALE);
		
		public static final int CRRABY_DRAWOFFSET_X = (int)(26 * GameElements.SCALE);
		public static final int CRRABY_DRAWOFFSET_Y = (int)(9 * GameElements.SCALE);
		
		public static int enemySpriteCount(int enemyType, int enemyState) {
			switch(enemyType)
			{
				case CRABBY:
					switch(enemyState)
					{
						case IDLE:
							return 9;
						case RUN:
							return 6;
						case JUMP:
							return 3;
						case FALL:
							return 1;
						case ATTACK:
							return 7;
						case GET_HIT:
							return 4;
						case DEAD:
							return 5;
					}
					return 0;
			}
			return 0;
		}
		
		public static int getMaxHealth(int enemyType) {
			switch(enemyType)
			{
				case CRABBY:
					return 20;
				default:
					return 1;
			}
		}
		
		public static int getEnemyDamage(int enemyType) {
			switch(enemyType)
			{
				case CRABBY:
					return 15;
				case CURSE1:
					return 1;
				case CURSE2:
					return 3;
				default:
					return 0;
			}
		}
	}
	
	public static class UI {
		public static class Buttons {
			public static final int BUTTON_WIDTH_DEFAULT = 58;
			public static final int BUTTON_HEIGHT_DEFAULT = 26;
			public static final int BUTTON_WIDTH = (int)(BUTTON_WIDTH_DEFAULT * GameElements.SCALE) + 50;
			public static final int BUTTON_HEIGHT = (int)(BUTTON_HEIGHT_DEFAULT * GameElements.SCALE) + 15;
		}
		
		public static class SoundPauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 20;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * GameElements.SCALE) + 20;
		}
		
		public static class URMPauseBottons {
			public static final int URM_SIZE_DEFAULT = 20;
			public static final int URM_SIZE = (int)(URM_SIZE_DEFAULT * GameElements.SCALE) + 10;
		}
		
		public static class VolumeSpliderPauseButton {
			public static final int VBUTTON_WIDTH_DEFAULT = 40;
			public static final int VBUTTON_HEIGHT_DEFAULT = 40;
			public static final int VBUTTON_WIDTH = (int)(VBUTTON_WIDTH_DEFAULT * GameElements.SCALE);
			public static final int VBUTTON_HEIGHT = (int)(VBUTTON_HEIGHT_DEFAULT * GameElements.SCALE);
			
			public static final int VSLIDER_WIDTH_DEFAULT = 160;
			public static final int VSLIDER_HEIGHT_DEFAULT = 9;
			public static final int VSLIDER_WIDTH = (int)(VSLIDER_WIDTH_DEFAULT * GameElements.SCALE) + 80;
			public static final int VSLIDER_HEIGHT = (int)(VSLIDER_HEIGHT_DEFAULT * GameElements.SCALE) + 10;
		}
	}
	
	public static class Directions {
		public static final int UP = 0;
		public static final int LEFT = 1;
		public static final int DOWN = 2;
		public static final int RIGHT = 3;
	}
	
	public static class CharacterActions {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK1 = 2;
		public static final int ATTACK2 = 3;
		public static final int JUMPING = 4;
		public static final int FALLING = 5;
		public static final int GET_HIT_PLAYER = 6;
		public static final int DEAD_PLAYER = 7;
		
		public static int spriteCount(int character_action) {
			switch(character_action)
			{
				case IDLE:
					return 4;
				case RUNNING:
					return 8;
				case ATTACK1:
				case ATTACK2:
					return 4;
				case JUMPING:
					return 4;
				case FALLING:
					return 3;
				case GET_HIT_PLAYER:
					return 2;
				case DEAD_PLAYER:
					return 8;
				default:
					return -1;
			}
				
		}
	}
	
}
