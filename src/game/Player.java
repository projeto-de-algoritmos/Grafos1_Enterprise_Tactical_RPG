package game;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	private int x;
	private int gridX;
	
	private int y;
	private int gridY;
	
	
	public Player() {
		gridX = 5;
		gridY = 5;
		x = gridX * 50 + 6;
		y = gridY * 50 + 6;
	}
	
	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
		x = gridX * 25 + 6;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
		y = gridY * 25 + 6;
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 14, 14);
	}
	
	public void update() {
		
	}
	
}
