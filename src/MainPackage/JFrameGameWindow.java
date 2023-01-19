package MainPackage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class JFrameGameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JFrame jframe;

	public JFrameGameWindow(JPanelGameScreen game_screen){
		jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(game_screen);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setVisible(true);
		jframe.setLocationRelativeTo(null);
		jframe.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				game_screen.getGameElements().lostWindowsFocus();
				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
