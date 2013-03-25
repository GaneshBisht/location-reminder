package com.tim.locationreminder.manager;

import com.amap.mapapi.core.GeoPoint;
import com.tim.locationreminder.Constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingsManager {
	
	private final String DEFAULT_SHARE_PREFERENCES_NAME = "LocationReminder.sp";
	
	private static SettingsManager instance = null;
	
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	
	public static void init(Context context) {
		if(instance == null) {
			instance = new SettingsManager(context);
		}
	}
	
	public static void destroy() {
		if(instance != null) {
			instance.release();
			instance = null;
		}
	}
	
	public static SettingsManager getInstance() {
		return instance;
	}
	
	private SettingsManager(Context context) {
		mContext = context;
		mSharedPreferences = context.getSharedPreferences(DEFAULT_SHARE_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
	}
	
	public String getRemindRingPath() {
		return mSharedPreferences.getString(Constants.SP_REMIND_RING_PATH, Constants.DEFAULT_REMIND_RING_PATH);
	}
	
	public boolean saveMyLastLocation(GeoPoint myLocation) {
		boolean successful = false;
		if(myLocation != null) {
			Editor editor = mSharedPreferences.edit();
			editor.putInt(Constants.SP_MY_LAST_LOCATION_LATI, myLocation.getLatitudeE6());
			editor.putInt(Constants.SP_MY_LAST_LOCATION_LONG, myLocation.getLongitudeE6());
			successful = editor.commit();
		}
		return successful;
	}
	
	public GeoPoint getMyLastLocation() {
		GeoPoint myLastLocation = null;
		int latiE6 = mSharedPreferences.getInt(Constants.SP_MY_LAST_LOCATION_LATI, -1);
		int longE6 = mSharedPreferences.getInt(Constants.SP_MY_LAST_LOCATION_LONG, -1);
		if(latiE6 != 1 && longE6 != 1) {
			myLastLocation = new GeoPoint(latiE6, longE6);
		}
		return myLastLocation;
	}
	
	private void release() {
		mContext = null;
		mSharedPreferences = null;
	}

}
