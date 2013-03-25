package com.tim.locationreminder.manager;

import com.tim.locationreminder.util.Log;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class RemindManager {
	
	private static RemindManager instance = null;
	
	private Context mContext;
	private MediaPlayer mMediaPlayer;
	
	private Vibrator mVibrator;
	
	public static void init(Context context) {
		if(instance == null) {
			instance = new RemindManager(context);
		}
	}
	
	public static void destroy() {
		if(instance != null) {
			instance.release();
			instance = null;
		}
	}
	
	public static RemindManager getInstance() {
		return instance;
	}
	
	private RemindManager(Context context) {
		mContext = context;
		mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		initPlayer();
	}
	
	private void initPlayer(){
		if(mMediaPlayer != null){
			return;
		}
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.reset();
		try {
			Log.e("getPath ~~~~", "path: " + SettingsManager.getInstance().getRemindRingPath());
			mMediaPlayer.setDataSource(SettingsManager.getInstance().getRemindRingPath());
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.prepare();
		} catch (Exception e) {
			try {
				Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
				mMediaPlayer.setDataSource(mContext, uri);
				final AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
				if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
					mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
					mMediaPlayer.prepare();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			e.printStackTrace();
		}
	}
	
	public void startRemind() {
		try {
			mVibrator.vibrate(new long[] {800, 500, 800, 500}, 0);
			mMediaPlayer.setLooping(true);
			if(!mMediaPlayer.isPlaying()) {
				mMediaPlayer.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopRemind() {
		try {
			mVibrator.cancel();
			mMediaPlayer.pause();
//			mMediaPlayer.seekTo(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void release() {
		stopRemind();
		mContext = null;
	}
	
}
