package MainPackage;

import java.awt.Graphics;

import GameStatesPackage.GameState;
import GameStatesPackage.Menu;
import GameStatesPackage.Playing;
import UtilsPackage.SaveLoad;

public class GameElements implements Runnable{
	private JPanelGameScreen game_screen;
	private JFrameGameWindow game_window;
	private Thread game_thread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 120;
	
	public final static int TILE_DEFAULT_SIZE = 48;
	public final static float SCALE = 1.0f;
	public final static int TILES_WIDTH = 26;
	public final static int TILES_HEIGHT = 14;
	public final static int TILE_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILE_SIZE * TILES_WIDTH;
	public final static int GAME_HEIGHT = TILE_SIZE * TILES_HEIGHT;
	
	private Playing playing;
	private Menu menu;
	
	public GameElements() {
		initializeClasses();
		game_screen = new JPanelGameScreen(this);
		game_window = new JFrameGameWindow(game_screen);
		game_screen.setFocusable(true);
		game_screen.requestFocus();
		startLoop();
	}

	private void initializeClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}

	private void startLoop() {
		game_thread = new Thread(this);
		game_thread.start();
	}
	
	private void update() {

		switch(GameState.game_state)
		{
		
			case MENU:
				menu.update();
				break;
			case PLAYING:
				playing.update();
				break;
			case OPTIONS:
			case QUIT:
			default:
				System.exit(0);
				break;
		
		}
	}
	
	public void render(Graphics graphics) {
		
		switch(GameState.game_state)
		{
			case MENU:
				menu.draw(graphics);
				break;
			case PLAYING:
				playing.draw(graphics);
				break;
			default:
				break;
		
		}
		
	}
	
	@Override
	public void run() {
		double time_per_frame = 1000000000.0 / FPS_SET;
		double time_per_update = 1000000000.0 / UPS_SET;
	
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		
		long lastFrameCheck = System.currentTimeMillis();
		
		double deltaUpdate = 0;
		double deltaFrame = 0;
		
		while(true)
		{
			long currentTime = System.nanoTime();
			
			deltaUpdate += (currentTime - previousTime) / time_per_update;
			deltaFrame += (currentTime - previousTime) / time_per_frame;
			previousTime = currentTime;
			
			if(deltaUpdate >= 1)
			{
				update();
				updates++;
				deltaUpdate--;
			}
			
			if(deltaFrame >= 1)
			{
				game_screen.repaint();
				frames++;
				deltaFrame--;
			}
			
			if(System.currentTimeMillis() - lastFrameCheck >= 1000)
			{
				lastFrameCheck = System.currentTimeMillis();
				frames = 0;
				updates = 0;
			}
		}
	}
	
	public void lostWindowsFocus() {
		if(GameState.game_state == GameState.PLAYING)
		{
			playing.getPlayer().resetBooleans();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
}
