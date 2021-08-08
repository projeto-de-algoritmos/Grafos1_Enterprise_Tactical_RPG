package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 500;
	private static int HEIGHT = 500;
	private Player player;
	private boolean running;
	private Thread thread;
	private int x;
	private int y;
	
	public Panel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		start();
	}
	
	private void start() {
		running = true;
		thread = new Thread(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		player = new Player();
		thread.start();

	}

	public void paint(Graphics g2) {
		Graphics2D g;
		g = (Graphics2D) g2;
		
		//Desenha a Grade (Possivelmente alterar para uma classe)
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
		// Desenha o Jogador
		player.draw(g);
		
		// Coordenadas do Mouse na Grade (Debug)
		g.setColor(Color.RED);
		g.drawString("x:", 2, 11);
		g.drawString("y:", 2, 21);
		g.drawString(String.valueOf((x-1)/25), 15, 11);
		g.drawString(String.valueOf((y-1)/25), 15, 21);
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

	@Override
	public void mouseDragged(MouseEvent m) {
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		x = m.getX();
		y = m.getY();
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// Move o Jogador(Temporário)
		player.setGridX((m.getX()-1)/25);
		player.setGridY((m.getY()-1)/25);
	}

	@Override
	public void mouseEntered(MouseEvent m) {
	}

	@Override
	public void mouseExited(MouseEvent m) {
	}

	@Override
	public void mousePressed(MouseEvent m) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
}
