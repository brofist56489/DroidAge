package com.bh.battle.astar;

import java.awt.Point;
import java.util.ArrayList;

public class AStarList extends ArrayList<Point> {
	private static final long serialVersionUID = 1L;

	public AStarList() {
		super();
	}
	
	public boolean contains(Point p) {
		for(Point p2 : this) {
			if(p2.x == p.x && p2.y == p.y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(int x, int y) {
		return contains(new Point(x, y));
	}
	
	public void add(int x, int y) {
		add(new Point(x, y));
	}
}
