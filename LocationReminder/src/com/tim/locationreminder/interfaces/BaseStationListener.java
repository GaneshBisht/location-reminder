package com.tim.locationreminder.interfaces;

import com.amap.mapapi.core.GeoPoint;


public interface BaseStationListener {

	void onGetLocationByBS(GeoPoint myLocation);
	
}
