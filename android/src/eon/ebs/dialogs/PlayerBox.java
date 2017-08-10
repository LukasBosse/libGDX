package eon.ebs.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import com.mygdx.game.R;
import eon.ebs.entities.Player;
import android.view.View.OnClickListener;

public class PlayerBox extends Activity implements OnClickListener {

    private Player      player;

    private TextView    playerName;
    private TextView    playerLevel;
    private ProgressBar playerEP;
    private RatingBar   playerRating;
    private Button      btn_close;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature (Window.FEATURE_NO_TITLE);
        setContentView(R.layout.player_dialog);

        player = (Player) getIntent().getSerializableExtra("PLAYERINFO");
        playerName = (TextView) findViewById(R.id.txt_username);
        playerLevel = (TextView) findViewById(R.id.txt_level);
        playerEP = (ProgressBar) findViewById(R.id.prog_ep);
        playerRating = (RatingBar) findViewById(R.id.rate_Rating);
        btn_close = (Button) findViewById(R.id.btn_closeDialog);

        btn_close.setOnClickListener(this);
        loadPlayerData();

    }

    private void loadPlayerData() {
        if(player != null) {
            playerName.setText(player.getName());
            playerLevel.setText("Level: " + player.getLevel());
            playerEP.setProgress(player.getEp());
            playerRating.setRating(player.getStars());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_closeDialog: {
                finish();
                break;
            }
        }
    }
}
