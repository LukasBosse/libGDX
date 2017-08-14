package eon.ebs.engine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import eon.ebs.entities.*;
import eon.ebs.storage.SharedPreferencesModul;
import eon.ebs.utils.CameraActions;

import java.util.LinkedList;
import java.util.List;

public class MainLoop extends ApplicationAdapter implements InputProcessor {

    /*****************************************************
     * EnergyWorld - MainLoop       					 *
     * Main class for rendering and input handling       *
     * @author Lukas Bosse								 *
     * @version 1.0.0									 *
     * @date 11/08/17									 *
     ****************************************************/

	//UI
	private AndroidLauncher             ui;
	//Rendering
	private IsometricTiledMapRenderer   tiledMapRenderer;
	//Camera
	private OrthographicCamera          camera;
	private int                         mouseMode = 1;
	//Map
    private Grid                        grid;
	private float                       tilePixelWidth; // = 100px
	private float                       tilePixelHeight; // = 50px
	//Picking
	private Vector3                     lastPoint = new Vector3(-1,-1,-1);
    private List<Tile>                  tileList = new LinkedList<>();
    private boolean                     editing = false;
    private int                         selectedItem = 0;
    private Tile                        newTile;
    //Local Storage Management
    private SharedPreferencesModul      storage;
    //Entites
    private Player                      player;

	/**
	 * Constructor for MainLoop class.
	 * @param ui
	 */

	public MainLoop(AndroidLauncher ui) {
        this.ui = ui;
        storage = new SharedPreferencesModul(ui);
        manageStorage();
	}

    /**
     * Storage manager checks if game content is available, if not create new one else load.
     */

    private void manageStorage() {
	    if(!storage.isNotFirstStart()) {
            storage.createStart();
        }
        player = storage.getPlayerFromStorage();
    }

    /**
     * Oncreate method for setting up display, grid, renderer and camera.
     */

	@Override
	public void create() {

            float w = Gdx.graphics.getWidth();
            float h = Gdx.graphics.getHeight();

            camera = new OrthographicCamera();
            camera.setToOrtho(false, w, h);
            camera.update();

            grid = new Grid(this);

            tiledMapRenderer = new IsometricTiledMapRenderer(grid.getTiledMap());

            tilePixelWidth = grid.getLayer().getTileWidth();
            tilePixelHeight = grid.getLayer().getTileHeight();

            CameraActions.centerCamera(grid.getTiledMap(), camera);

            GestureDetector gd = new GestureDetector(new GameGestureListener(this, camera));
            InputMultiplexer im = new InputMultiplexer(gd, this);
            Gdx.input.setInputProcessor(im);

	}

    /** Render method **/

	@Override
	public void render() {
	  	Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }

	/** Method place selected ground object **/
	
	private void checkTouch() {
		if(lastPoint != new Vector3(-1,-1,-1) && mouseMode == 0 && selectedItem != 0 && !editing) {
			int x = (int) lastPoint.x;
			int y = (int) lastPoint.y;
			if(grid.getGridLayer().getCell(x, y) != null) {
				if (grid.getGroundObjects().getCell(x, y) != null) {
					if (selectedItem == 1) {
						newTile = new PowerPlant(x, y);
					} else {
						newTile = new CoalPlant(x, y);
					}
					if (!checkIntersection(newTile, x, y)) {
                        editing = true;
                        grid.getGroundObjects().getCell(x, y).setTile(grid.getTiledMap().getTileSets().getTile(newTile.getTileID()));
						tileList.add(newTile);
					}
			}
			}
		}
	}

    /** Checks tile and object intersection **/

	private boolean checkIntersection(Tile newTile, int x, int y) {
		boolean blocked = false;
		for(Tile tile : tileList) {
			if(tile.getBounding().contains(newTile.getBounding())) {
				blocked = true;
			}
		}
		for (y = 0; y < grid.getGroundObjects().getHeight(); y++) {
			for (x = 0; x < grid.getGroundObjects().getWidth(); x++) {
				if (grid.getGroundObjects().getCell(x, y) != null && grid.getGroundObjects().getCell(x, y).getTile().getProperties().containsKey("Blocked")) {
					blocked = true;
				}
			}
		}
		return blocked;
	}

	/**
     * Set selected item
     * @param i
     **/

    protected void setSelectedItem(int i) {
		this.selectedItem = i + 1;
	}

	/**
     * Getter method for Grid object
     * @return grid
     **/

	protected Grid getGrid() {
	    return grid;
    }

    /**
     * Set mouse mode
     * @param mode
     **/

    public void setMouseModus(int mode) {
	    this.mouseMode = mode;
    }

    /**
     * Getter method for mouse mode
     * @return mouseMode
     **/

    protected int getMouseMode() {
	    return mouseMode;
    }

    /**
     * Getter for editing mode
     * @return editing
     **/

    protected boolean isEditing() { return editing; }

    /**
     * Set editing mode
     * @param editing
     **/

    protected void setEditing(boolean editing) { this.editing = editing; }

    /**
     * App Lifecycle
     **/

	@Override
	public void dispose() {
		super.dispose();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void resume() {
		super.resume();
	}
	
	@Override
	public void pause() {
		super.pause();
	}
		
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	/** Input Listener **/

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
	    lastPoint = CameraActions.worldToIso(camera, new Vector3(x,y,0),(int)tilePixelWidth,(int)tilePixelHeight);
		if(lastPoint.x >= 0 && lastPoint.y >= 0) checkTouch();
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		lastPoint = new Vector3(-1,-1,-1);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(mouseMode == 0 && editing) {
            Vector3 point = CameraActions.worldToIso(camera, new Vector3(screenX,screenY,0),(int)tilePixelWidth,(int)tilePixelHeight);
            if(grid.getGroundObjects().getCell(newTile.getX(), newTile.getY()) != null) {
                grid.getGroundObjects().getCell(newTile.getX(), newTile.getY()).setTile(grid.getTiledMap().getTileSets().getTile(new EmptyTile(newTile.getX()
                        , newTile.getY()).getTileID()));
                newTile.setX((int) point.x);
                newTile.setY((int) point.y);
                if (grid.getGroundObjects().getCell(newTile.getX(), newTile.getY()) != null) {
                    grid.getGroundObjects().getCell(newTile.getX(), newTile.getY()).setTile(grid.getTiledMap().getTileSets().getTile(newTile.getTileID()));
                }
            }
		} else {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();
            camera.translate(-x, y);
        }
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	protected Player getPlayer() {
	    return player;
    }
	
}
