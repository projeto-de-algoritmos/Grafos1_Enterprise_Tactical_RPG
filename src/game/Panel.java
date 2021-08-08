package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 500;
	private static int HEIGHT = 500;
	private boolean running;
	private Thread thread;
	
	public Panel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		start();
	}
	
	private void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void paint(Graphics g2) {
		Graphics2D g;
		g = (Graphics2D) g2;
		
		//Draw Grid (Possivelmente alterar para uma classe)
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		int Wsize = WIDTH/20;
		int Hsize = HEIGHT/20;
		for(int i=0;i<=WIDTH; i+= Wsize) {
			g.drawLine(i, 0, i, WIDTH);
		}
		for(int i=0;i<=HEIGHT; i+= Hsize) {
			g.drawLine(0, i, HEIGHT, i);
		}
	}

	@Override
	public void run() {
		while(running) {
			repaint();
		}
	}
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
