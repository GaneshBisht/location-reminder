package com.tim.locationreminder.view;

import android.content.Context;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.tim.locationreminder.R;
import com.tim.locationreminder.util.Log;

public class MyLocationItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private final String TAG = "MyLocationItemizedOverlay";
	
	private OverlayItem mLocationOverlayItem;
	
	public MyLocationItemizedOverlay(Context context) {
		super(context.getResources().getDrawable(R.drawable.ico_position_blue));
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
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		Log.d(TAG, "onTap >> " + p);
		updateMyLocation(p);
		mapView.refresh();
		return super.onTap(p, mapView);
	}

	public void updateMyLocation(GeoPoint myPoint) {
		if(myPoint != null) {
			mLocationOverlayItem = new OverlayItem(myPoint, null, null);
			boundCenter(mLocationOverlayItem);
			populate();
		}
	}

}
