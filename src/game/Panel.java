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

import graphs.AdjListGraph;
import graphs.UndirectedEdge;
import graphs.exceptions.GraphException;

public class Panel extends JPanel implements Runnable, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 500;
	private static int HEIGHT = 500;
	private Player player;
	private List<UndirectedEdge<Integer>> lines;
	private AdjListGraph<Integer> grid;
	private boolean running;
	private Thread thread;
	private int lastMouseX;
	private int lastMouseY;
	private boolean inPlayer;
	
	public Panel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		player = new Player();
		grid = new AdjListGraph<Integer>();
		lines = new ArrayList<UndirectedEdge<Integer>>();
		GenerateGraph();
		addMouseListener(this);
		addMouseMotionListener(this);
		start();
	}
	
	//Gera o grafo da grade;
	private void GenerateGraph(){
		for(int i=0;i<2000;i+=100) {
			for(int j=0;j<20;j++) {
				try {
					grid.addNode(i+j);
				} catch (GraphException e) {
					e.printStackTrace();
				}
				if(j > 0)
					try {
						grid.addEdge(new UndirectedEdge<Integer>(i+j, i+j-1));
					} catch (GraphException e) {
						e.printStackTrace();
					}
				if(i > 0)
					try {
						grid.addEdge(new UndirectedEdge<Integer>(i+j, i+j-100));
					} catch (GraphException e) {
						e.printStackTrace();
					}
			}
		}
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
		
		// Desenha o Jogador
		player.draw(g);

		// Desenha as linhas de caminho
		if(!lines.isEmpty() && !inPlayer)
			for(UndirectedEdge<Integer> e : lines) 
				drawLine(g, e.getV(),e.getW());
		
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
		player.setGridX((m.getX()-1)/25);
		player.setGridY((m.getY()-1)/25);
		inPlayer = true;
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
	
	private void drawLine(Graphics2D g, int v, int w) {
		int v1 = gridToCoord(v/100) + 12;
		int v2 = gridToCoord(v%100) + 12;
		int w1 = gridToCoord(w/100) + 12;
		int w2 = gridToCoord(w%100) + 12;
		g.setColor(Color.RED);
		g.drawLine(v1 , v2, w1, w2);
	}
	
	private void encontraCaminho() {
		AdjListGraph<Integer> tmp = new AdjListGraph<Integer>();
		try {
			tmp = grid.bfs(100*player.getGridX()+player.getGridY(), 100*lastMouseX+lastMouseY);
		} catch (GraphException e) {
			e.printStackTrace();
		}
		lines = tmp.getEdges();
		//System.out.printf("Reset\n");
		//for(UndirectedEdge<Integer> e : lines) 
			//System.out.printf("%d - %d\n", e.getV(),e.getW());
	}
	
	private int gridToCoord(int v) {
		return v * 25;
	}
	private int coordToGrid(int v) {
		return (v-1)/25;
	}
}
