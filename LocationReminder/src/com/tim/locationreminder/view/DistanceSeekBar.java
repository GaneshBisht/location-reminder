package com.tim.locationreminder.view;

import com.tim.locationreminder.R;
import com.tim.locationreminder.interfaces.DistanceSeekBarListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class DistanceSeekBar extends LinearLayout {
	
	private final int SEEK_BAR_MAX_VALUE = 10000;
	private final int SEEK_BAR_INIT_VALUE = 500;
	private final int DISTANCE_STEP = 500;
	
	private OhEditText mDistance;
	private SeekBar mSeekBar;
	private OhTextView mDoneButton;
	
	private DistanceSeekBarListener mListener;

	public DistanceSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickable(true);
		setOrientation(VERTICAL);
		setBackgroundColor(context.getResources().getColor(R.color.map_bottom_menu_background));
		LayoutInflater.from(context).inflate(R.layout.view_distance_seekbar, this);
		initComponent();
		hide(false);
	}
	
	private void initComponent() {
		mDistance = (OhEditText) findViewById(R.id.view_distance_seekbar_distance);
		mSeekBar = (SeekBar) findViewById(R.id.view_distance_seekbar);
		mDoneButton = (OhTextView) findViewById(R.id.view_distance_seekbar_done);

		mSeekBar.setMax(SEEK_BAR_MAX_VALUE - SEEK_BAR_INIT_VALUE);
		
		setupListeners();
		
		int distance = SEEK_BAR_INIT_VALUE;	//TODO 從配置文件中讀取上次設置的值，注意要減去SEEK_BAR_INIT_VALUE
		int seekbarProgress = distance - SEEK_BAR_INIT_VALUE;
		mSeekBar.setProgress(seekbarProgress < 0 ? 0 : seekbarProgress);	
		mDistance.setText(String.valueOf(distance));
	}
	
	private void setupListeners() {
		mDoneButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//TODO 把設置的target點和distance值存起來.
				hide(true);
				if(mListener != null) {
					int distance = 0;
					try {
						distance = Integer.parseInt(mDistance.getText().toString());
					} catch(NumberFormatException e) {
						
					}
					mListener.onClickDone(distance);
				}
			}
		});
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				progress = ((int) Math.round(progress / DISTANCE_STEP)) * DISTANCE_STEP;
				mDistance.setText(String.valueOf(progress + SEEK_BAR_INIT_VALUE));
			}
		});
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
	
	public void setDisntaceSeekBarListener(DistanceSeekBarListener l) {
		mListener = l;
	}
}
