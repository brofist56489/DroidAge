package com.bh.battle.astar;

public abstract class AStarLevel {
	public int width;
	public int height;
	
	public abstract AStarTile getTile(int x, int y);
}
