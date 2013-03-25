package com.tim.locationreminder;

public class Constants {

	/*-- SharePreferences Keys --*/
	public static final String SP_REMIND_RING_PATH = "SP_REMIND_RING_PATH";
	public static final String SP_MY_LAST_LOCATION_LONG = "SP_MY_LAST_LOCATION_LONG";
	public static final String SP_MY_LAST_LOCATION_LATI = "SP_MY_LAST_LOCATION_LANT";
	/*-- End SharePreferences Keys --*/
	
	/*-- Defaults --*/
	public static final String DEFAULT_REMIND_RING_PATH = "/data/data/" + R.class.getPackage().getName() + "/default_remind_ring.mp3";
	
}
