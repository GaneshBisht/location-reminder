package com.tim.locationreminder.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.map.MapActivity;
import com.amap.mapapi.map.MapController;
import com.amap.mapapi.map.MapView;
import com.tim.locationreminder.R;
import com.tim.locationreminder.interfaces.BaseStationListener;
import com.tim.locationreminder.interfaces.BottomMenuListener;
import com.tim.locationreminder.interfaces.DistanceSeekBarListener;
import com.tim.locationreminder.interfaces.OnLocationChangedListener;
import com.tim.locationreminder.manager.LocationManager;
import com.tim.locationreminder.manager.SettingsManager;
import com.tim.locationreminder.view.BottomMenuView;
import com.tim.locationreminder.view.DistanceSeekBar;
import com.tim.locationreminder.view.MyLocationItemizedOverlay;
import com.tim.locationreminder.view.PositionItemizedOverlay;
import com.tim.locationreminder.view.RemindDialog;

public class MainActivity extends MapActivity 
	implements OnLocationChangedListener, BottomMenuListener, DistanceSeekBarListener, BaseStationListener {
	
	private final String TAG = "MainActivity";

	private MapView mMapView;
	private MapController mMapController;
	
//	private MyLocationItemizedOverlay mLocationItemizedOverlay; 	//custom my location overlay.
	private GeoPoint mLocation;
	private boolean mFristLocateMe;
	private MyLocationItemizedOverlay mLocationItemizedOverlay;
	private PositionItemizedOverlay mPositionItemizedOverlay;
	
	private BottomMenuView mBottomMenuView;
	private DistanceSeekBar mDistanceSeekBar;
	
	private RemindDialog mRemindDialog;
	
	private int mSettingsDistance;
	
	private LocationManager mLocationManager;
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFristLocateMe = true;
		setContentView(R.layout.page_main);
		initComponent();
//		mLocationManager = new LocationManager(this);
//		mLocationManager.setBaseStationListener(this);
		mHandler = new Handler();
	}
	
	private void initComponent() {
		mMapView = (MapView) findViewById(R.id.page_main_map);
		mBottomMenuView = (BottomMenuView) findViewById(R.id.page_main_bottom_menu_view);
		mDistanceSeekBar = (DistanceSeekBar) findViewById(R.id.page_main_distance_seekbar);
		mMapController = mMapView.getController();
		
		mMapView.setBuiltInZoomControls(true);
		mMapController.setZoom(12);
		
//		mLocationItemizedOverlay = new MyLocationItemizedOverlay(getApplicationContext());
		
		mLocationItemizedOverlay = new MyLocationItemizedOverlay(this, mMapView);
		mPositionItemizedOverlay = new PositionItemizedOverlay(getApplicationContext());
		mMapView.getOverlays().add(mPositionItemizedOverlay);
		mMapView.getOverlays().add(mLocationItemizedOverlay);
		
		mLocationItemizedOverlay.setOnLocationChangedListener(this);
		mBottomMenuView.setBottomMenuListener(this);
		mDistanceSeekBar.setDisntaceSeekBarListener(this);
		
		mLocationItemizedOverlay.enableCompass();
		mLocationItemizedOverlay.enableMyLocation();
		
		loadMyLastLocation();
	}
	
	private void loadMyLastLocation() {
		final SettingsManager settingsManager = SettingsManager.getInstance();
		if(settingsManager != null) {
			GeoPoint myLastLocation = settingsManager.getMyLastLocation();
			if(myLastLocation != null) {
				mMapController.setCenter(myLastLocation);
			}
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if(location != null) {
			mLocation = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
			if(mFristLocateMe) {
				mFristLocateMe = false;
				if(mMapController != null) {
					mMapController.animateTo(mLocation);
				}
			}
			
			final SettingsManager settingsManager = SettingsManager.getInstance();
			if(settingsManager != null) {
				settingsManager.saveMyLastLocation(mLocation);
			}
			if(mLocationManager != null) {
				mLocationManager.detect(mLocation);
			}
			
			//開始判斷所選位置和當前位置是否在設置範圍內
			if(mSettingsDistance > 0 && mPositionItemizedOverlay != null) {
				if(mPositionItemizedOverlay.isSelectPositionMode()) {
					return ;
				}
				GeoPoint targetPoint = mPositionItemizedOverlay.getSelectedLocation();
				if(targetPoint != null) {
					float distanceFromMyToTarget = MapController.calculateDistance(mLocation, targetPoint);
					if(distanceFromMyToTarget >= 0 && distanceFromMyToTarget <= mSettingsDistance) {
						//當前目標距離在設置範圍內，提醒用戶
						if(mRemindDialog != null && mRemindDialog.isShowing()) {
							//正在提醒，忽略
							return ;
						}
						mRemindDialog = new RemindDialog(this);
						mRemindDialog.show();
						mSettingsDistance = -1;
					}
				}
			}
		}
	}
	
	/**
	 * 從基站獲取到我的位置的回調方法，該方法從另一個線程中回調
	 */
	@Override
	public void onGetLocationByBS(final GeoPoint myLocation) {
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				if(mLocationItemizedOverlay != null) {
					mLocationItemizedOverlay.updateLocation(myLocation);
				}
			}
		});
	}
	
	@Override
	public void onClickOptions(View optionsView) {
		if(optionsView.getId() == R.id.view_bottom_menu_select_target) {
			mBottomMenuView.hide();
			mDistanceSeekBar.show();
			mPositionItemizedOverlay.selectPositionMode(true);
		}
		else if(optionsView.getId() == R.id.view_bottom_menu_more_settings) {
			System.exit(0);
		}
	}
	
	@Override
	public void onClickDone(int distance) {
		mPositionItemizedOverlay.selectPositionMode(false);
		mSettingsDistance = distance;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		mLocationItemizedOverlay.enableCompass();
//		mLocationItemizedOverlay.enableMyLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
//		mLocationItemizedOverlay.disableCompass();
//		mLocationItemizedOverlay.disableMyLocation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(mDistanceSeekBar.isShowing()) {
			return true;
		}
		if(keyCode == KeyEvent.KEYCODE_MENU){
			if(mBottomMenuView.isShowing()) {
				mBottomMenuView.hide();
			}
			else {
				mBottomMenuView.show();
			}
		}
		else if(keyCode == KeyEvent.KEYCODE_BACK) {
			if(mBottomMenuView.isShowing()){
				mBottomMenuView.hide();
			}
			else {
				final Intent intent = new Intent();
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
			}
		}
		return true;
	}
}
