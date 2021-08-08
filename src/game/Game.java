package game;

import javax.swing.JFrame;

public class Game {
	
	private boolean running;
	
	private JFrame frame;
	private Panel panel;
	
	public Game() {
		this.setRunning(true);
		frame = new JFrame();
		panel = new Panel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Enterprise Tactical RPG");
		frame.pack();
		frame.setSize(501, 531);
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.setVisible(true);
	}

	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
}
