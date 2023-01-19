package ObjectsPackage;

import MainPackage.GameElements;

public class Potion extends GameObjects {

	public Potion(int x, int y, int objectType) {
		super(x, y, objectType);
		doAnimation = true;
		initiateHitBox(12, 14);
		
		xDrawOffset = (int)(6 * GameElements.SCALE);
		yDrawOffset = (int)(5 * GameElements.SCALE);
		
		hitBox.y += yDrawOffset + (int)(GameElements.SCALE * 36);
		hitBox.x += GameElements.TILE_SIZE / 2 - hitBox.width / 2;
	}
	
	public void update() {
		updateAnimationTick();
	}
	
}
