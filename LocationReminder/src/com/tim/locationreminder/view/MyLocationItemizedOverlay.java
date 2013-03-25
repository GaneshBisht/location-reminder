package com.tim.locationreminder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.MyLocationOverlay;
import com.tim.locationreminder.interfaces.OnLocationChangedListener;

public class MyLocationItemizedOverlay extends MyLocationOverlay {
	
	private OnLocationChangedListener mLocationListener;
	
	private boolean mCustomPoint = true;
	
	public MyLocationItemizedOverlay(Context context, MapView mapView) {
		super(context, mapView);
	}

	@Override
	public void onLocationChanged(Location location) {
		if(mLocationListener != null){
			mLocationListener.onLocationChanged(location);
		}
		if(mCustomPoint) {
			super.onLocationChanged(location);
		}
	}
	
	public void updateLocation(GeoPoint location) {
		if(location != null) {
			Location myLocation = new Location(LocationManager.NETWORK_PROVIDER);
			myLocation.setLatitude(location.getLatitudeE6() / 1E6);
			myLocation.setLongitude(location.getLongitudeE6() / 1E6);
			mCustomPoint = true;
			onLocationChanged(myLocation);
//			mCustomPoint = false;
		}
	}
	
	public void setOnLocationChangedListener(OnLocationChangedListener l) {
		mLocationListener = l;
	}
	

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		//TODO 在以後繪製自己定義的圖標，比如帶動畫的藍點？
		super.draw(canvas, mapView, shadow);
	}
}
