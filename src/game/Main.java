package game;

import java.util.concurrent.TimeUnit;

import game.Menu;
import game.Game;

public class Main {
	public Main() {
			
			//Menu
			Menu menu = new Menu();
			while(!menu.isStarted()) {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Remove o menu
			menu.getFrame().dispose();
			
			//Game
			Game game = new Game();
			while(game.isRunning()) {
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
	}
	public static void main(String[] args) {
		new Main();
	}

}
