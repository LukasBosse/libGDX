package eon.ebs.sounds;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import java.io.IOException;

public class SoundSystem implements OnCompletionListener {
	
	private static MediaPlayer 			mediaPlayer = new MediaPlayer();
	private static AssetFileDescriptor 	afd;
	private static int 					leftVol = 50;
	private static int					rightVol = 50;

	public SoundSystem() {
		mediaPlayer.setOnCompletionListener(this);
	}
	
	/**
	 * Initalisierung und Deklarierung des AssetFileDescriptor und der SoundStorage.
	 * @param context
	 * @param path
	 */
	
	public static void initalSound(Context context, String path) {
		try {
			afd = context.getAssets().openFd(path);
			loadAudioSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Abspielen des Sounds
	 */
	
	public static void startMusic() {
		try {
			mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			mediaPlayer.prepare();
			mediaPlayer.setVolume(leftVol, rightVol);
			mediaPlayer.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Beenden der Soundssequenz
	 */
	
	public static void stopMusic() {
		mediaPlayer.stop();
		mediaPlayer.reset(); 
	}
	

	public static void setVol(Point sound) {
		leftVol = sound.x;
		rightVol = sound.y;
	}
	
	/**
	 * Laden der gespeicherten Audioeinstellungen
	 */
		
	private static void loadAudioSettings() {
		//setVol(soundStorage.getVol());
	}
	
	/**
	 * Bei Ablauf der Audiosequenz, beenden des Abspielvorgangs
	 */

	@Override
	public void onCompletion(MediaPlayer mp) {
		stopMusic();
	}
	
}
