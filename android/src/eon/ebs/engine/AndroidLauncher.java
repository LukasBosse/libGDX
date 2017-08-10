package eon.ebs.engine;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.R;
import eon.ebs.dialogs.PlayerBox;
import eon.ebs.entities.Player;
import eon.ebs.layouts.GridViewAdapter;
import eon.ebs.layouts.ImageItem;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements OnClickListener {
	
	private MainLoop				mainLoop;
	private Player                  player = new Player("John Doe");

	private Button 					btn_tools;
	private Button					btn_grabPointer;
	private Button					btn_saveChange;
	private Button					btn_undoChange;
	private ImageView               btn_userInfo;
	private TextView				level;
	private ProgressBar				levelProgress;
	
	private GridView 				gridView;
	private GridViewAdapter 		gridAdapter;
	
	private int						mouseModus = 1;
	private int						selectedItem = 0;
				
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		mainLoop = new MainLoop(this);
		setContentView(R.layout.userinterface);
		
		GridLayout rel = (GridLayout) findViewById(R.id.gameBg);
		View view = initializeForView(mainLoop, config);
		rel.addView(view);
		
		configGUI();
		
	}
	
	private void configGUI() {

		btn_tools = (Button) findViewById(R.id.btn_tools);
		btn_grabPointer = (Button) findViewById(R.id.btn_grabPointer);
		btn_saveChange = (Button) findViewById(R.id.btn_saveChange);
		btn_undoChange = (Button) findViewById(R.id.btn_undoChange);
        btn_userInfo = (ImageView) findViewById(R.id.imgViewPersonData);

		levelProgress = (ProgressBar) findViewById(R.id.progressLevelBar);
		level = (TextView) findViewById(R.id.levelText);
		
		btn_saveChange.setVisibility(View.INVISIBLE);
		btn_undoChange.setVisibility(View.INVISIBLE);
		
		gridView = (GridView) findViewById(R.id.gridView1);
        gridAdapter = new GridViewAdapter(this, R.layout.grid, getData());
        gridView.setAdapter(gridAdapter);
       
        level.setText(String.valueOf(levelProgress.getProgress()));
		      
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				pushPosition(position);
			}
		});
               
		btn_tools.setOnClickListener(this);
		btn_grabPointer.setOnClickListener(this);
		btn_saveChange.setOnClickListener(this);
		btn_undoChange.setOnClickListener(this);
        btn_userInfo.setOnClickListener(this);

	}

	private void pushPosition(int selectedItem) {
		showAcceptanceButtons();
		this.selectedItem = selectedItem;
		this.mainLoop.setSelectedItem(this.selectedItem);
	}
	
	private void pushModus() {
		mainLoop.getGrid().setGrid(mouseModus);
	}
	
	private ArrayList<ImageItem> getData() {
       final ArrayList<ImageItem> imageItems = new ArrayList<>();
       TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
       for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, ""));
        }
        imgs.recycle();
        return imageItems;
	 }
						
	/** Einblenden der Toolbox */

	private void showMenu() {
		if(mainLoop.isEditing()) return;
	    gridView.setVisibility(View.VISIBLE);
		gridView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomin));
		btn_tools.setText("<");
	}

	/** Ausblenden der Toolbox */

	private void hideMenu() {
		gridView.setVisibility(View.INVISIBLE);
		gridView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomout));
		btn_tools.setText(">");
	}

	private void showAcceptanceButtons() {
		btn_saveChange.setVisibility(View.VISIBLE);
		btn_undoChange.setVisibility(View.VISIBLE);
	}

	private void hideAcceptanceButtons() {
		btn_saveChange.setVisibility(View.INVISIBLE);
		btn_undoChange.setVisibility(View.INVISIBLE);
	}

	private void clearSelection() {
		selectedItem = 0;
		pushModus();
	}

	private void showPointer() {
        btn_grabPointer.setBackground(getResources().getDrawable(R.drawable.mousepointer));
    }

    private void showGrab() {
        btn_grabPointer.setBackground(getResources().getDrawable(R.drawable.mousegrab));
    }
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.btn_tools: {
			    if(gridView.getVisibility() == View.VISIBLE) {
                    hideMenu();
                } else {
			        showMenu();
                }
				break;
			}
			case R.id.btn_grabPointer: {
				if(mouseModus == 0) { // Pick-Modus
                    hideMenu();
					showPointer();
					mouseModus = 1;
				} else { //Move-Modus
                    showMenu();
                    showGrab();
					mouseModus = 0;
				}
				pushModus();
				break;
			}
			case R.id.btn_saveChange: {
				clearSelection();
				hideAcceptanceButtons();
                hideMenu();
                showPointer();
				mainLoop.setEditing(false);
                mouseModus = 1;
                pushModus();
                break;
			}
			case R.id.btn_undoChange: {
				clearSelection();
				hideAcceptanceButtons();
                hideMenu();
                showPointer();
				mainLoop.setEditing(false);
                mouseModus = 1;
                pushModus();
                break;
			}
			case R.id.imgView_Budget: {
	//			Intent mainPage = new Intent(Game.this, BudgetBox.class);
	//			startActivity(mainPage);
				break;
			}
			case R.id.imgViewPersonData: {
				Intent mainPage = new Intent(AndroidLauncher.this, PlayerBox.class);
				mainPage.putExtra("PLAYERINFO", player);
				startActivity(mainPage);
				break;
			}
		}
	}
	
}
