package com.bh.battle;

import com.bh.battle.gfx.Screen;

public class Program {
	public static void main(String[] args) {
		Game game = new Game();
		Screen.makeJFrame(game);
		game.start();
	}
}
