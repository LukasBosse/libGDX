package eon.ebs.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import eon.ebs.engine.MainLoop;

public class Grid {

    private MainLoop mainLoop;

    private final String MAP = "NuclearPlant.tmx";

    private TiledMap tiledMap;
    private TiledMapTileLayer layer;
    private TiledMapTileLayer gridLayer;
    private TiledMapTileLayer groundObjects;
    private TiledMapTileLayer basedObjects;

    public Grid(MainLoop mainLoop) {
        this.mainLoop = mainLoop;
        tiledMap = new TmxMapLoader().load(MAP);
        layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        gridLayer = (TiledMapTileLayer) tiledMap.getLayers().get(2);
        groundObjects = (TiledMapTileLayer) tiledMap.getLayers().get(3);
        basedObjects = (TiledMapTileLayer) tiledMap.getLayers().get(4);
    }

    public String getMAP() { return MAP; }

    public TiledMap getTiledMap() { return tiledMap; }

    public void setTiledMap(TiledMap tiledMap) { this.tiledMap = tiledMap; }

    public TiledMapTileLayer getLayer() { return layer; }

    public void setLayer(TiledMapTileLayer layer) { this.layer = layer; }

    public TiledMapTileLayer getGridLayer() { return gridLayer; }

    public void setGridLayer(TiledMapTileLayer gridLayer) { this.gridLayer = gridLayer; }

    public TiledMapTileLayer getGroundObjects() { return groundObjects; }

    public void setGroundObjects(TiledMapTileLayer groundObjects) { this.groundObjects = groundObjects; }

    public TiledMapTileLayer getBasedObjects() { return basedObjects; }

    public void setBasedObjects(TiledMapTileLayer basedObjects) { this.basedObjects = basedObjects; }

    //Sets the visibility of grid
    public void setGrid(int mouseMode) {
        if(gridLayer.isVisible()) {
            gridLayer.setVisible(false);
        } else {
            gridLayer.setVisible(true);
        }
        mainLoop.setMouseModus(mouseMode);
    }

}
