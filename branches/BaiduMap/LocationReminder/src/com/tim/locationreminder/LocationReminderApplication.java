package com.tim.locationreminder;

import android.app.Application;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.tim.locationreminder.util.Log;

public class LocationReminderApplication extends Application implements MKGeneralListener {
	
	private static final String BAIDU_MAP_KEY = "08F3C69C84F7F093D1BA44D84851F2352AE321D0";
	
	private BMapManager mBMapManager;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.loggagble(true);
		
		getBMapManager();
	}
	
	public synchronized BMapManager getBMapManager() {
		if(mBMapManager == null) {
			mBMapManager = new BMapManager(this);
			mBMapManager.init(BAIDU_MAP_KEY, this);
		}
		return mBMapManager;
	}
	
	public void doTeminate() {
		if(mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager.stop();
			mBMapManager = null;
		}
		System.exit(0);
	}

	@Override
	public void onGetNetworkState(int iError) {
		
	}

	@Override
	public void onGetPermissionState(int iError) {
		
	}

}
