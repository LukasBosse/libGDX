package eon.ebs.entities;

import com.badlogic.gdx.math.Rectangle;

public class Tile {

	private int x;
	private int y;
	private int width;
	private int height;
	private int tileID;
	private Rectangle bounding;
	
	public Tile(int x, int y, int width, int height, int tileID) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tileID = tileID;
		this.bounding = new Rectangle(x,y,width,height);
	}

	public int getTileID() { return tileID; }

	public void setTileID(int tileID) { this.tileID = tileID; }

	public Rectangle getBounding() {
		return bounding;
	}

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
}
