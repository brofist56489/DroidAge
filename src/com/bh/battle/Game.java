package com.bh.battle;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bh.battle.astar.AStarList;
import com.bh.battle.astar.AStarPathFinder;
import com.bh.battle.gfx.Light;
import com.bh.battle.gfx.Screen;
import com.bh.battle.input.KeyHandler;
import com.bh.battle.input.MouseHandler;
import com.bh.battle.world.World;
import com.bh.battle.world.tiles.Tile.TileType;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = -5120981594296451337L;

	private static Game instance;
	private static Logger logger;
	private static MouseHandler mouse;
	private static KeyHandler keyboard;
	private static Random random;

	private static int tickCount = 0;

	private static Thread mainThread;
	private static boolean running = false;
	
	private static World world;
	
	private void init() {
		instance = this;
		
		logger = Logger.getLogger(Game.class.getName());
		mouse = new MouseHandler();
		keyboard = new KeyHandler();
		random = new Random();
		
		Screen.addLight(new Light(160, 120, 100, 255));
		
		world = new World(10, 10);
		AStarPathFinder pathFinder = new AStarPathFinder(world, new Point(0, 0), new Point(8, 7));
		AStarList path = pathFinder.findPath();
		for(Point p : path) {
			world.setTile(p.x, p.y, TileType.PATH);
		}
	}
	
	public void run() {
		init();

		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 30.0;
		double unprocessed = 0.0;
		long now;
		int ticks = 0, frames = 0;
		long lastTimer = System.currentTimeMillis();
		boolean shouldRender = false;
		boolean limitFPS = !Constants.LIMIT_FPS;

		while (running) {
			now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			shouldRender = limitFPS;

			while (unprocessed >= 1) {
				tick();
				ticks++;
				shouldRender = true;
				unprocessed--;
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				logger.log(Level.INFO, ticks + " ticks, " + frames + " frames");
				ticks = frames = 0;
			}
		}
	}
	
	public void tick() {
		tickCount++;
		world.tick();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		Screen.clear(0xffffff, 128);
		
		world.render();
		
		Screen.finalizeLighting();
		Graphics g = bs.getDrawGraphics();
		g.drawImage(Screen.getImage(), 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public void start() {
		if(running)
			return;
		running = true;
		
		mainThread = new Thread(this, "MAIN_GAME");
		mainThread.start();
	}
	
	public void stop() {
		running = false;
	}
	
	public static Game getInstance() {
		return instance;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static MouseHandler getMouse() {
		return mouse;
	}

	public static KeyHandler getKeyboard() {
		return keyboard;
	}

	public static Random getRandom() {
		return random;
	}

	public static int getTickCount() {
		return tickCount;
	}
	
	public static World getWorld() {
		return world;
	}
}
