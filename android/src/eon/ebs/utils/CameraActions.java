package eon.ebs.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;

public class CameraActions {

    //Centers camera in TiledMap bounds

    public static void centerCamera(TiledMap tiledMap, OrthographicCamera camera) {
        TiledMapTileLayer referenceTile = (TiledMapTileLayer) tiledMap.getLayers().get(1);
        Vector3 center = new Vector3(referenceTile.getWidth() * referenceTile.getTileWidth() / 2, 50, 0);
        camera.position.set(center);
    }

    //Convertion from screen coordinats to isometric grid position

    public static Vector3 worldToIso(OrthographicCamera camera, Vector3 point, int tileWidth, int tileHeight) {
        camera.unproject(point);
        point.x /= tileWidth;
        point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
        point.x -= point.y - point.x;
        return point;
    }

}
