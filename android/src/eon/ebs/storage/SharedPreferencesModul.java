package eon.ebs.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import eon.ebs.entities.Player;
import eon.ebs.sounds.SoundSettings;

public class SharedPreferencesModul {

	protected static Context context;
	protected static SharedPreferences sharedPreferences;
	protected static SharedPreferences.Editor editor;
		
	@SuppressLint("CommitPrefEdits")
	public SharedPreferencesModul(Context context) {
		this.context = context;
		sharedPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}

	public Player getPlayerFromStorage() {
		Gson gson = new Gson();
		String json = sharedPreferences.getString("Player", "");
		Player player = gson.fromJson(json, Player.class);
		return player;
	}

	public SoundSettings getSoundSettingsFromStorage() {
		Gson gson = new Gson();
		String json = sharedPreferences.getString("SoundSettings", "");
		SoundSettings soundSettings = gson.fromJson(json, SoundSettings.class);
		return soundSettings;
	}

	public void putPlayerToStorage(Player player) {
		Gson gson = new Gson();
		String json = gson.toJson(player);
		editor.putString("Player", json);
		editor.commit();
	}

	public void putSoundToStorage(SoundSettings soundSettings) {
		Gson gson = new Gson();
		String json = gson.toJson(soundSettings);
		editor.putString("SoundSettings", json);
		editor.commit();
	}

	public void createStart() {
		editor.putInt("Start", 1);
		putPlayerToStorage(new Player("Lukas"));
		putSoundToStorage(new SoundSettings(5,5));
	}

	public boolean isNotFirstStart() {
		return sharedPreferences.contains("Start");
	}

}
