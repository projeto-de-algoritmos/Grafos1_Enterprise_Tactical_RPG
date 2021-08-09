package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import graphs.GraphMatrix;
import graphs.Position;

public class Panel extends JPanel implements Runnable, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 500;
	private static int HEIGHT = 500;
	private Player player;
	private List<Position> preview;
	private GraphMatrix<Integer> grid;
	private boolean running;
	private Thread thread;
	private int lastMouseX;
	private int lastMouseY;
	private boolean inPlayer;
	
	public Panel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);
		
		player = new Player(5);
		grid = new GraphMatrix<Integer>(20, 20, 0, 1, -1);
		preview = new ArrayList<Position>();
		
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

		// Desenha as linhas de caminho
		drawPreview(g);
		
		// Desenha o Jogador
		player.draw(g);
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
		// Coordenadas atuais do mouse na grade
		int mx = coordToGrid(m.getX());
		int my = coordToGrid(m.getY());
		
		// Atualiza as coordenadas do Mouse
		if (lastMouseX != mx || lastMouseY != my){
			lastMouseX = mx;
			lastMouseY = my;
			if (mx == player.getGridX() && my == player.getGridY()) 
				inPlayer = true;
			else {
				inPlayer = false;
				encontraCaminho();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// Move o Jogador(Temporário)
		if (preview.size() <= player.getMoves()) {
		player.setGridX((m.getX()-1)/25);
		player.setGridY((m.getY()-1)/25);
		inPlayer = true;
		}
	}

	@Override
	public void mouseEntered(MouseEvent m) {
		mouseMoved(m);
	}

	@Override
	public void mouseExited(MouseEvent m) {
		inPlayer = true;
		lastMouseX = player.getGridX();
		lastMouseY = player.getGridY();
	}

	@Override
	public void mousePressed(MouseEvent m) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	
	private void drawPreview(Graphics2D g) {
		int x = -1;
		int y = -1;
		int counter = 0;
		g.setColor(Color.RED);
		if(!preview.isEmpty() && !inPlayer) {
			for(Position e : preview) {
				if(counter >= player.getMoves())
					break;
				if(x != -1 && y != -1)
					g.drawLine(gridToCoord(e.getPosX())+12, gridToCoord(e.getPosY())+12, gridToCoord(x)+12, gridToCoord(y)+12);
				x = e.getPosX();
				y = e.getPosY();
				counter++;
			}
		}
	}
	
	private void encontraCaminho() {
		preview = grid.bfs(new Position(player.getGridX(),player.getGridY()), new Position(lastMouseX,lastMouseY));
		grid.clearMatrix();
	}
	
	private int gridToCoord(int v) {
		return v * 25;
	}
	private int coordToGrid(int v) {
		return (v-1)/25;
	}
}
