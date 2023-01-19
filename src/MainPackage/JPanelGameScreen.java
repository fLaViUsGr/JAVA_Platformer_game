package MainPackage;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import InputsPackage.KeyboardInputs;
import InputsPackage.MouseInputs;
import static MainPackage.GameElements.GAME_WIDTH;
import static MainPackage.GameElements.GAME_HEIGHT;

public class JPanelGameScreen extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private MouseInputs mouse_inputs = new MouseInputs(this);
	private GameElements game_elements;
	
	public JPanelGameScreen(GameElements game_elements) {
		this.game_elements = game_elements;
		setScreenSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouse_inputs);
		addMouseMotionListener(mouse_inputs); 
	}
	
	private void setScreenSize() {
		Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
		setPreferredSize(size);
	}
	
	public void updateGame() {
		
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		game_elements.render(graphics);
	}
	
	public GameElements getGameElements() {
		return game_elements;
	}

}
