package com.bh.battle.astar;

import java.awt.Point;

public class AStarNode {
	public final int h;
	public float g;
	public float f;
	public final int x;
	public final int y;
	public AStarNode parentNode;
	
	public AStarNode(int h, int x, int y) {
		this.h = h;
		this.x = x;
		this.y = y;
		this.g = -1;
		this.f = -1;
		parentNode = null;
	}
	
	public void calcF() {
		f = g + h;
	}
	
	public Point asPoint() {
		return new Point(x, y);
	}
}
