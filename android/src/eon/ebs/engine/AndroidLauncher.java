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
import eon.ebs.dialogs.BudgetBox;
import eon.ebs.dialogs.PlayerBox;
import eon.ebs.entities.Player;
import eon.ebs.layouts.GridViewAdapter;
import eon.ebs.layouts.ImageItem;

import java.util.ArrayList;

public class AndroidLauncher extends AndroidApplication implements OnClickListener {

	/*****************************************************
	 * EnergyWorld - AndroidLauncher					 *
	 * Main class for settings and interaction with GUI. *
	 * @author Lukas Bosse								 *
	 * @version 1.0.0									 *
	 * @date 11/08/17									 *
	 ****************************************************/

	private MainLoop				mainLoop;
	private Player                  player;

	private Button 					btn_tools;
	private Button					btn_grabPointer;
	private Button					btn_saveChange;
	private Button					btn_undoChange;
	private ImageView               btn_userInfo;
	private ImageView				btn_budget;
	private ImageView				btn_location;
	private TextView				level;
	private ProgressBar				levelProgress;

	private GridView 				gridView;
	private GridViewAdapter 		gridAdapter;
	
	private int						mouseMode = 1;
	private int						selectedItem = 0;

    /** OnCreate method for setting up application configuration, selected views and gui
     *  @param savedInstanceState **/

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		mainLoop = new MainLoop(this);
        player = mainLoop.getPlayer();
		setContentView(R.layout.userinterface);
		GridLayout rel = (GridLayout) findViewById(R.id.gameBg);
		View view = initializeForView(mainLoop, config);
		rel.addView(view);
		
		configGUI();
		
	}

	/** Declare and initalize gui objects from layout 'userinterface' **/

	private void configGUI() {

		btn_tools = (Button) findViewById(R.id.btn_tools);
		btn_grabPointer = (Button) findViewById(R.id.btn_grabPointer);
		btn_saveChange = (Button) findViewById(R.id.btn_saveChange);
		btn_undoChange = (Button) findViewById(R.id.btn_undoChange);
        btn_userInfo = (ImageView) findViewById(R.id.imgViewPersonData);
		btn_location = (ImageView) findViewById(R.id.imgViewLocation);
		btn_budget = (ImageView) findViewById(R.id.imgView_Budget);

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
		btn_location.setOnClickListener(this);
		btn_budget.setOnClickListener(this);

	}

	/** Push position of selected toolbox item to mainloop class
     *  @param selectedItem **/

	private void pushPosition(int selectedItem) {
		showAcceptanceButtons();
		this.selectedItem = selectedItem;
		this.mainLoop.setSelectedItem(this.selectedItem);
	}

	/** Push mouse mode to mainloop class **/

	private void pushModus() {
		mainLoop.getGrid().setGrid(mouseMode);
	}

	/** Set image as icon for toolbox list entity
     *  @return imageItems **/

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
						
	/** Fade in - Toolbox **/

	private void showMenu() {
		if(mainLoop.isEditing()) return;
	    gridView.setVisibility(View.VISIBLE);
		gridView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomin));
		btn_tools.setText("<");
	}

	/** Fade out - Toolbox **/

	private void hideMenu() {
		gridView.setVisibility(View.INVISIBLE);
		gridView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomout));
		btn_tools.setText(">");
	}

	/** Set visibility of 'Acceptance Button' to true **/

	private void showAcceptanceButtons() {
		btn_saveChange.setVisibility(View.VISIBLE);
		btn_undoChange.setVisibility(View.VISIBLE);
	}

	/** Set visibility of 'Acceptance Button' to false **/

	private void hideAcceptanceButtons() {
		btn_saveChange.setVisibility(View.INVISIBLE);
		btn_undoChange.setVisibility(View.INVISIBLE);
	}

	/** Reset selected item and push mouse mode to mainloop class **/

	private void clearSelection() {
		selectedItem = 0;
		pushModus();
	}

	/** Set background of mode button to pointer **/

	private void showPointer() {
        btn_grabPointer.setBackground(getResources().getDrawable(R.drawable.mousepointer));
    }

    /** Set background of mode button to grab **/

    private void showGrab() {
        btn_grabPointer.setBackground(getResources().getDrawable(R.drawable.mousegrab));
    }

    /** OnClick-Listener **/

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
				if(mouseMode == 0) {
                    hideMenu();
					showPointer();
					mouseMode = 1;
				} else {
                    showMenu();
                    showGrab();
					mouseMode = 0;
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
				mouseMode = 1;
                pushModus();
                break;
			}
			case R.id.btn_undoChange: {
				clearSelection();
				hideAcceptanceButtons();
                hideMenu();
                showPointer();
				mainLoop.setEditing(false);
				mouseMode = 1;
                pushModus();
                break;
			}
			case R.id.imgView_Budget: {
				Intent mainPage = new Intent(AndroidLauncher.this, BudgetBox.class);
				mainPage.putExtra("PLAYERINFO", player);
				startActivity(mainPage);
				break;
			}
			case R.id.imgViewPersonData: {
				Intent mainPage = new Intent(AndroidLauncher.this, PlayerBox.class);
				mainPage.putExtra("PLAYERINFO", player);
				startActivity(mainPage);
				break;
			}
			case R.id.imgViewLocation: {
                Intent mainPage = new Intent(AndroidLauncher.this, Europa.class);
                startActivity(mainPage);
				break;
			}
		}
	}

	/** Application lifecycle **/

    @Override
    public void onResume() {
    	super.onResume();
	}

    @Override
    public void onPause() {
        super.onPause();
    }

}
