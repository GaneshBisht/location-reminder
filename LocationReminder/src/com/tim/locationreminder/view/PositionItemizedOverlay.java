package com.tim.locationreminder.view;

import android.content.Context;
import android.view.MotionEvent;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.core.OverlayItem;
import com.amap.mapapi.map.ItemizedOverlay;
import com.amap.mapapi.map.MapView;
import com.tim.locationreminder.R;

public class PositionItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private final String TAG = "PositionItemizedOverlay";

	private OverlayItem mPositionOverlayItem;
	
	private boolean mTwoPointerTouch;
	private boolean mSelectPositionMode;
	
	private GeoPoint mSelectedLocation;
	
	public PositionItemizedOverlay(Context context) {
		super(boundCenter(context.getResources().getDrawable(R.drawable.icon_position_mark)));
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mPositionOverlayItem;
	}

	@Override
	public int size() {
		return mPositionOverlayItem == null ? 0 : 1;
	}
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView) {
		if(mSelectPositionMode && !mTwoPointerTouch) {
			updatePosition(p);
			mapView.postInvalidate();
		}
		return super.onTap(p, mapView);
	}
	
	public void updatePosition(GeoPoint positionPoint) {
		if(positionPoint != null) {
			mPositionOverlayItem = new OverlayItem(positionPoint, null, null);
			mSelectedLocation = positionPoint;
			populate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			mTwoPointerTouch = event.getPointerCount() >= 2;
		}
		if(!mTwoPointerTouch) {
			mTwoPointerTouch = event.getPointerCount() >= 2;
		}
		return super.onTouchEvent(event, mapView);
	}
	
	public void selectPositionMode(boolean on) {
		mSelectPositionMode = on;
	}
	
	public GeoPoint getSelectedLocation() {
		return mSelectedLocation;
	}
	
	public boolean isSelectPositionMode() {
		return mSelectPositionMode;
	}
}
