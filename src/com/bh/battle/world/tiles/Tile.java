package com.bh.battle.world.tiles;

import com.bh.battle.astar.AStarTile;
import com.bh.battle.gfx.ImageManager;
import com.bh.battle.world.World;

public class Tile extends AStarTile {
	public static enum TileType {
		VOID(0, -1, 0, false, false);
		
		private int id;
		private int special;
		private int textureId;
		private boolean solid;
		private boolean connects;
		TileType(int id, int textureId, int special, boolean solid, boolean connects) {
			this.id = id;
			this.special = special;
			this.textureId = textureId;
			this.solid = solid;
			this.connects = connects;
		}
		
		public int getId() {
			return id;
		}
		
		public int getSpecial() {
			return special;
		}
		
		public int getTextureId() {
			return textureId;
		}
		
		public boolean isSolid() {
			return solid;
		}
		
		public boolean connects() {
			return connects;
		}
		
		public static TileType getTypeById(int id) {
			for(TileType t : TileType.values()) {
				if(t.getId() == id) {
					return t;
				}
			}
			return null;
		}
	}
	
	public static Tile makeTile(TileType type) {
		if(type.connects()) {
			return new ConnectedTile(type);
		} else {
			return new Tile(type);
		}
	}
	
	protected int id;
	protected int special;
	protected int textureId;
	protected int data1;
	protected int data2;
	
	public Tile(TileType type) {
		super(type.isSolid());
		setAs(type);
	}
	
	public Tile(int id) {
		this(TileType.getTypeById(id));
	}
	
	public void setAs(TileType type) {
		this.solid = type.isSolid();
		this.id = type.getId();
		this.special = type.getSpecial();
		this.textureId = type.getTextureId();
		this.data1 = 0;
		this.data2 = 0;
	}
	
	public void render(int x, int y, World world) {
		if(textureId != -1)
			ImageManager.renderFromTileMap("tileMap", x * SIZE, y * SIZE, textureId, SIZE, 0);
	}
	
	public int getData1() {
		return data1;
	}
	public void setData1(int data1) {
		this.data1 = data1;
	}
	public int getData2() {
		return data2;
	}
	public void setData2(int data2) {
		this.data2 = data2;
	}
	public int getId() {
		return id;
	}
	public int getSpecial() {
		return special;
	}
	public int getTextureId() {
		return textureId;
	}

}
