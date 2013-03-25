package com.tim.locationreminder.manager;

import android.content.Context;

import com.amap.mapapi.core.GeoPoint;
import com.tim.locationreminder.interfaces.BaseStationListener;
import com.tim.locationreminder.util.LocationUtil;
import com.tim.locationreminder.util.Log;

public class LocationManager implements Runnable {
	
	private final String TAG = "LocationManager";
	
	private final int DETECT_SECONDS = 10;
	
	private Context mContext;

	private BaseStationListener mListener;
	private Thread mDetectThread;
	private GeoPoint mLocationFromGPS;
	private GeoPoint mLastLocationFromGPS;
	
	public LocationManager(Context context) {
		mContext = context;
		if(mDetectThread == null) {
			mDetectThread = new Thread(this);
			mDetectThread.start();
		}
	}
	
	public void detect(GeoPoint myLocation) {
		if(myLocation != null) {
			mLocationFromGPS = myLocation;
		}
	}
	
	public void setBaseStationListener(BaseStationListener l) {
		mListener = l;
	}

	@Override
	public void run() {
		int seconds = DETECT_SECONDS;
		while(true) {
			try {
				if(--seconds <= 0) {	//每60秒檢測一次我的位置
					Log.i(TAG, "On detect my location.");
					seconds = DETECT_SECONDS;
					if(mLocationFromGPS == null) {	//如果60秒內都不能從GPS裏獲取位置，則用基站來獲取
						requestLocationFromBS();
					}
					else {
						if(mLastLocationFromGPS == mLocationFromGPS) {	//如果之前有獲取到位置，但是60秒都沒有變化（可能獲取過一次但以後都獲取不到），則用基站來獲取
							requestLocationFromBS();
						}
						mLastLocationFromGPS = mLocationFromGPS;
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void requestLocationFromBS() {
		Log.i(TAG, "== requestLocationFromBS ==");
		GeoPoint locationFromBS = LocationUtil.getMyLocationByBS(mContext);
		if(locationFromBS != null) {
			if(mListener != null) {
				mListener.onGetLocationByBS(locationFromBS);
			}
		}
	}
	
}
