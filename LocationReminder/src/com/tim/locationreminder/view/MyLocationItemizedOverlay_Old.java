package com.tim.locationreminder.view;

import android.content.Context;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.OverlayItem;
import com.amap.mapapi.map.ItemizedOverlay;
import com.tim.locationreminder.R;

public class MyLocationItemizedOverlay_Old extends ItemizedOverlay<OverlayItem> {
	
	private final String TAG = "MyLocationItemizedOverlay";
	
	private OverlayItem mLocationOverlayItem;
	
	public MyLocationItemizedOverlay_Old(Context context) {
		super(boundCenter(context.getResources().getDrawable(R.drawable.ico_position_blue)));
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mLocationOverlayItem;
	}

	@Override
	public int size() {
		return mLocationOverlayItem == null ? 0 : 1;
	}
	
	public void updateMyLocation(GeoPoint myPoint) {
		if(myPoint != null) {
			mLocationOverlayItem = new OverlayItem(myPoint, null, null);
			populate();
		}
	}

}
