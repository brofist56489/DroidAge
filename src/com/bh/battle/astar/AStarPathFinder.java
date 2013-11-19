package com.bh.battle.astar;

import java.awt.Point;

public class AStarPathFinder {
	
	public static int MOVEMENT_COST = 10;
	
	private Point start;
	private Point target;
	private AStarLevel level;
	private AStarNode[] nodes;
	
	
	public AStarPathFinder(AStarLevel l, Point s, Point t) {
		start = s;
		target = t;
		level = l;
		nodes = new AStarNode[level.width * level.height];
		for(int y = 0; y < level.height; y++) {
			for(int x = 0; x < level.width; x++) {
				nodes[x + y * level.width] = new AStarNode(Math.abs(y - target.y) + Math.abs(x - target.x), x, y);
			}
		}
	}
	
	public AStarList findPath() {
		AStarList openList = new AStarList();
		openList.add(start);
		
		AStarList closedList = new AStarList();
		Point pos = new Point(start.x, start.y);
		
		mainLoop : while(true) {
			if(closedList.contains(pos)) {
				continue;
			}
			openList.remove(pos);
			closedList.add(pos);
			if(pos.equals(start)) {
				nodes[getTileCoord(pos)].g = 0;
			}
			for(int i = 0; i < 4; i++) {
				int chx = (i == 1) ? 1 : (i == 3) ? -1 : 0;
				int chy = (i == 0) ? 1 : (i == 2) ? -1 : 0;
				if(chx != 0 && chy != 0) continue;
				Point chp = new Point(pos.x + chx, pos.y + chy);
				if(level.getTile(chp.x, chp.y) == null) {
					continue;
				}
				if(level.getTile(chp.x, chp.y).solid || closedList.contains(chp)) {
					continue;
				}
				if(chp.equals(target)) {
					nodes[getTileCoord(chp)].parentNode = nodes[getTileCoord(pos)];
					break mainLoop;
				}
				if(openList.contains(chp)) {
					if(nodes[getTileCoord(pos)].g + MOVEMENT_COST < nodes[getTileCoord(chp)].g) {
						nodes[getTileCoord(chp)].parentNode = nodes[getTileCoord(pos)];
						nodes[getTileCoord(chp)].g = nodes[getTileCoord(pos)].g + MOVEMENT_COST;
					}
				} else {
					openList.add(chp);
					if(nodes[getTileCoord(chp)].f == -1) {
						nodes[getTileCoord(chp)].parentNode = nodes[getTileCoord(pos)];
					}
					nodes[getTileCoord(chp)].g = nodes[getTileCoord(pos)].g + MOVEMENT_COST;
				}
				nodes[getTileCoord(chp)].calcF();
			}
			
			Point nm = new Point(0, 0);
			float sm = -1;
			for (Point pos2 : openList) {
				if (sm == -1) {
					sm = nodes[getTileCoord(pos2)].f;
					nm = pos2;
					continue;
				}
				if (nodes[getTileCoord(pos2)].f < sm) {
					sm = nodes[getTileCoord(pos2)].f;
					nm = pos2;
				}
			}
			pos = new Point(nm.x, nm.y);
			
		}
		
		AStarList returnList = new AStarList();
		Point a = target;
		while (!(a.equals(start))) {
			returnList.add(new Point(a.x, a.y));
			a = nodes[getTileCoord(a)].parentNode.asPoint();
		}
		returnList.add(start);
		
		return returnList;
	}
	
	private int getTileCoord(Point p) {
		return p.x + p.y * level.width;
	}
}

/*
def find_path(self):
		open_list = [self.start]
		closed_list = []
		done = False
		pos = self.start
		while not done:
			if pos in closed_list:
				continue
			del open_list[open_list.index(pos)]
			closed_list.append(pos)
			if pos == self.start:
				self.nodes[pos].g = 0
			for chn in [(0, 1), (1, 0), (0, -1), (-1, 0)]:#, (-1, 1), (1, 1), (1, -1), (-1, -1)]:
				chp = (pos[0] + chn[0], pos[1] + chn[1])
				if self.level.get_tile(chp[0], chp[1]) == None:
					# print("Skipped Tile", chp)
					continue
				if self.level.get_tile(chp[0], chp[1]).solid or chp in closed_list:
					# print("Skipped Tile because of solid", chp)
					continue
				if chp == self.target:
					self.nodes[chp].parentNode = self.nodes[pos]
					done = True
					break
				if chp in open_list:
					# print("Found node in open list", chp)
					if self.nodes[pos].g + MOVEMENT_COST < self.nodes[chp].g:
						self.nodes[chp].parentNode = self.nodes[pos]
						self.nodes[chp].g = self.nodes[pos].g + MOVEMENT_COST
						# print("Faster path calulated", chp)
				else:
					# print("Found Empty node", chp)
					open_list.append(chp)
					if self.nodes[chp].f == -1:
						self.nodes[chp].parentNode = self.nodes[pos]
					self.nodes[chp].g = self.nodes[pos].g + MOVEMENT_COST
				self.nodes[chp].calc_f()

			nn = (0, 0)
			sm = -1
			for pos2 in open_list:
				if sm == -1:
					sm = self.nodes[pos2].f
					nn = pos2
					continue
				if self.nodes[pos2].f < sm:
					sm = self.nodes[pos2].f
					nn = pos2
			pos = nn
			
			
			
			a;sdlkfja;lsdkjf;alksdjf;alksdjf;alksjdfasdfasdfasdfasdfasdf
		a = self.target
		ns = []
		try:
			while not a == self.start:
				ns.append((self.nodes[a].x, self.nodes[a].y))
				a = (self.nodes[a].parentNode.x, self.nodes[a].parentNode.y)
			ns.append(self.start)
		except AttributeError as e:
			print("A is ", a)
			print("Node Parent is", self.nodes[a].parentNode)
			print("Known nodes", ns)
			raise e
		return ns
*/
