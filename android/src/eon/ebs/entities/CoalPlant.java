package eon.ebs.entities;

public class CoalPlant extends eon.ebs.entities.Tile {

	private final static int WIDTH = 210;
	private final static int HEIGHT = 100;
	private final static int TILEID = 86;
	
	public CoalPlant(int x, int y) {
		super(x, y, WIDTH, HEIGHT, TILEID);
	}

}
