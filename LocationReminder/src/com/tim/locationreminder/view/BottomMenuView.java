package com.tim.locationreminder.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.tim.locationreminder.R;
import com.tim.locationreminder.interfaces.BottomMenuListener;

public class BottomMenuView extends RelativeLayout {
	
	private OhTextView mSelectTargetOption;
	private OhTextView mAlertOption;
	private OhTextView mVibrateOption;
	private OhTextView mMoreSettingsOption;
	
	private BottomMenuListener mBottomMenuListener;
	
	private long mLastClickTime;
	
	public BottomMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.view_bottom_menu, this);
		initComponents();
		hide(false);
	}
	
	private void initComponents() {
		mSelectTargetOption = (OhTextView) findViewById(R.id.view_bottom_menu_select_target);
		mAlertOption = (OhTextView) findViewById(R.id.view_bottom_menu_alert);
		mVibrateOption = (OhTextView) findViewById(R.id.view_bottom_menu_vibrate);
		mMoreSettingsOption = (OhTextView) findViewById(R.id.view_bottom_menu_more_settings);
		
		setupListeners();
	}
	
	private void setupListeners() {
		OnClickListener onClickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(System.currentTimeMillis() - mLastClickTime < 1000) {
					return ;
				}
				mLastClickTime = System.currentTimeMillis();
//				if(v.getId() == mSelectTargetOption.getId()) {
//					
//				}
//				else if(v.getId() == mAlertOption.getId()) {
//					
//				}
//				else if(v.getId() == mVibrateOption.getId()) {
//					
//				}
//				else if(v.getId() == mMoreSettingsOption.getId()) {
//					
//				}
				if(mBottomMenuListener != null) {
					mBottomMenuListener.onClickOptions(v);
				}
			}
		};
		
		mSelectTargetOption.setOnClickListener(onClickListener);
		mAlertOption.setOnClickListener(onClickListener);
		mVibrateOption.setOnClickListener(onClickListener);
		mMoreSettingsOption.setOnClickListener(onClickListener);
	}
	
	public void show() {
		show(true);
	}
	
	private void show(boolean animate) {
		if(isShowing()) {
			return ;
		}
		if(animate) {
			startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bottom_in));
		}
		setVisibility(View.VISIBLE);
	}
	
	public void hide() {
		hide(true);
	}
	
	private void hide(boolean animate) {
		if(!isShowing()) {
			return ;
		}
		if(animate){
			startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bottom_out));
		}
		setVisibility(View.INVISIBLE);
	}

	public boolean isShowing() {
		return getVisibility() == View.VISIBLE;
	}
	
	public void setBottomMenuListener(BottomMenuListener l) {
		mBottomMenuListener = l;
	}
}
