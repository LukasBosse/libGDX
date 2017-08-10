package eon.ebs.entities;


public class PowerPlant extends eon.ebs.entities.Tile {

	private final static int WIDTH = 390;
	private final static int HEIGHT = 200;
	private final static int TILEID = 85;

	public PowerPlant(int x, int y) {
		super(x, y, WIDTH, HEIGHT, TILEID);
	}

}
