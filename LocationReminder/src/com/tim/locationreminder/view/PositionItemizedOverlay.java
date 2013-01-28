package com.tim.locationreminder.view;

import android.content.Context;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.tim.locationreminder.R;

public class PositionItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private OverlayItem mPositionOverlayItem;
	
	public PositionItemizedOverlay(Context context) {
		super(context.getResources().getDrawable(R.drawable.icon_position_mark));
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mPositionOverlayItem;
	}

	@Override
	public int size() {
		return mPositionOverlayItem == null ? 0 : 1;
	}
	
	public void updatePosition(GeoPoint positionPoint) {
		if(positionPoint != null) {
			mPositionOverlayItem = new OverlayItem(positionPoint, null, null);
			populate();
		}
	}

}
