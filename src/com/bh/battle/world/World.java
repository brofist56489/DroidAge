package com.bh.battle.world;

import com.bh.battle.Game;
import com.bh.battle.astar.AStarLevel;
import com.bh.battle.world.tiles.ConnectedTile;
import com.bh.battle.world.tiles.Tile;
import com.bh.battle.world.tiles.Tile.TileType;

public class World extends AStarLevel {

	private Tile[][] tiles;
	
	public World(int w, int h) {
		this.width = w;
		this.height = h;
		tiles = new Tile[w][h];
		for(int y=0; y<h; y++) {
			for(int x=0; x<w; x++) {
				tiles[x][y] = Tile.makeTile((Game.getRandom().nextInt(10) < 8) ? TileType.VOID : TileType.SOLID);
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return null;
		}
		return tiles[x][y];
	}
	
	public void setTile(int x, int y, TileType type) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return;
		}
		tiles[x][y] = Tile.makeTile(type);
	}
	
	public void checkConnections() {
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				if(tiles[x][y] instanceof ConnectedTile) {
					((ConnectedTile)tiles[x][y]).checkConnection(x, y);
				}
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void render() {
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				tiles[x][y].render(x, y, this);
			}
		}
	}
}
