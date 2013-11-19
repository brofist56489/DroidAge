package com.bh.battle.astar;

public abstract class AStarTile {
	public static final int SIZE = 16;

	public boolean solid;
	public AStarTile(boolean s) {
		solid = s;
	}
}
