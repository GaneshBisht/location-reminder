package com.tim.locationreminder.activity;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.tim.locationreminder.R;
import com.tim.locationreminder.util.Log;
import com.tim.locationreminder.view.MyLocationItemizedOverlay;
import com.tim.locationreminder.view.PositionItemizedOverlay;

public class MainActivity extends BaseActivity implements BDLocationListener {
	
	private final String TAG = "MainActivity";

	private MapView mMapView;
	private MapController mMapController;
	
	private LocationClient mLocationClient;
	private GeoPoint mLocation;
	private MyLocationItemizedOverlay mLocationItemizedOverlay;
	private boolean mFristLocateMe;
	
	private PositionItemizedOverlay mPositionItemizedOverlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFristLocateMe = true;
		setContentView(R.layout.fuck_map);
		initComponent();
	}
	
	private void initComponent() {
		mMapView = (MapView) findViewById(R.id.fuck_mapview);
		mMapController = mMapView.getController();
		
		mMapView.setBuiltInZoomControls(true);
		mMapController.setZoom(12);
		
		mLocationItemizedOverlay = new MyLocationItemizedOverlay(getApplicationContext());
		mPositionItemizedOverlay = new PositionItemizedOverlay(getApplicationContext());
		mMapView.getOverlays().add(mLocationItemizedOverlay);
		mMapView.getOverlays().add(mPositionItemizedOverlay);
		mMapView.refresh();
		
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(this);
	}
	
	@Override
	public void onReceiveLocation(BDLocation loc) {
		if(loc != null) {
			mLocation = new GeoPoint((int) (loc.getLatitude() * 1E6), (int) (loc.getLongitude() * 1E6));
			Log.i(TAG, "Updating my location[ " + mLocation + " ] ");
			mLocationItemizedOverlay.updateMyLocation(mLocation);
			mMapView.refresh();
			if(mFristLocateMe) {
				mFristLocateMe = false;
				mMapController.animateTo(mLocation);
			}
		}
	}

	@Override
	public void onReceivePoi(BDLocation loc) {
		
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		mLocationClient.start();
		mLocationClient.requestLocation();
		super.onResume();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		mLocationClient.stop();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		mLocationClient.unRegisterLocationListener(this);
		super.onDestroy();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}
}
