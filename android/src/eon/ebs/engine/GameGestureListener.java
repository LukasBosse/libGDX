package eon.ebs.engine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GameGestureListener implements GestureListener {

	private MainLoop main;
	private OrthographicCamera cam;
    private float initialScale = 2f;
    private static final float ZOOM_MIN = (float) 1f;
    private static final float ZOOM_MAX = (float) 2.8f;
	
    public GameGestureListener(MainLoop main, OrthographicCamera cam) {
    	this.main = main;
    	this.cam = cam;
    	this.cam.zoom = initialScale;
    }
    
   	@Override
   	public boolean touchDown(float x, float y, int pointer, int button) {
        initialScale = cam.zoom;
	   	return false;
   	}
	   	
	@Override
	public boolean tap(float x, float y, int count, int button) {	
		return false;
	}
		
	@Override
	public boolean longPress(float x, float y) {
			
		return false;
	}
		
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
			
		return false;
	}
		
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}
		
	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
			
		return false;
	}
		
   	@Override
   	public boolean zoom (float originalDistance, float currentDistance){
   	   float ratio = originalDistance / currentDistance;
   	   if(main.getMouseMode() == 0) return false;
   	   cam.zoom = initialScale * ratio;
       checkLimits();
	   return false;
   	}

   	private void checkLimits() {
   		if(cam.zoom >= ZOOM_MAX) cam.zoom = ZOOM_MAX;
   		if(cam.zoom <= ZOOM_MIN) cam.zoom = ZOOM_MIN;
	}

   	@Override
	public void pinchStop () {
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
}