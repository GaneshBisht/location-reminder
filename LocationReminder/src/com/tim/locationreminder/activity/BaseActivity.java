package com.tim.locationreminder.activity;

import com.tim.locationreminder.LocationReminderApplication;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public LocationReminderApplication getLRApplication() {
		return (LocationReminderApplication) super.getApplication();
	}
	
}
