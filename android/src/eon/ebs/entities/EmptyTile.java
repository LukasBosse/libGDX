package eon.ebs.entities;

public class EmptyTile extends Tile {

    private final static int WIDTH = 210;
    private final static int HEIGHT = 100;
    private final static int TILEID = 88;

    public EmptyTile(int x, int y) {
        super(x, y, WIDTH, HEIGHT, TILEID);
    }

}
