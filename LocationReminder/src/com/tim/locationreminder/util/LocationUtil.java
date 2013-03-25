package com.tim.locationreminder.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.amap.mapapi.core.GeoPoint;

public class LocationUtil {
	
	private static final String TAG = "LocationUtil";
	
	
	public static GeoPoint getMyLocationByBS4(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operatorName = tm.getNetworkOperatorName();
		GsmCellLocation cellLocation = (GsmCellLocation) tm.getCellLocation();
		Log.d(TAG, String.format("OP name: %1$s, %2$d , %3$d", operatorName, cellLocation.getCid(), cellLocation.getLac()));
		return null;
	}
	
	public static GeoPoint getMyLocationByBS(Context context) {
		GeoPoint myLocation = null;
		CdmaCellLocation cellLocation = new CdmaCellLocation();
		GsmCellLocation gsmLocation = new GsmCellLocation();
		double latitude = cellLocation.getBaseStationLatitude() / 14400.0;
		double longitude = cellLocation.getBaseStationLongitude() / 14400.0;
		double longitude2 = cellLocation.getBaseStationLongitude() / 14400.0;
		Log.d(TAG, String.format("Response: %1$f , %2$f", latitude, longitude));
		Log.d(TAG, String.format("GSM Response: %1$f , %2$f", latitude, longitude));
//		myLocation = new GeoPoint(latitude, longitude);
		return myLocation;
	}
	
	public static GeoPoint getMyLocationByBS2(Context context) {
		GeoPoint myLocation = null;
		
		/** 调用API获取基站信息 */
	    TelephonyManager mTelNet = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    GsmCellLocation location = (GsmCellLocation) mTelNet.getCellLocation();
	    if (location == null) {
	        return null;
	    }
	 
	    String operator = mTelNet.getNetworkOperator();
	    int mcc = Integer.parseInt(operator.substring(0, 3));
	    int mnc = Integer.parseInt(operator.substring(3));
	    int cid = location.getCid();
	    int lac = location.getLac();
		
		/** 采用Android默认的HttpClient */
	    HttpClient client = new DefaultHttpClient();
	    /** 采用POST方法 */
	    HttpPost post = new HttpPost("http://www.google.com/loc/json");
	    try {
	        /** 构造POST的JSON数据 */
	        JSONObject holder = new JSONObject();
	        holder.put("version", "1.1.0");
	        holder.put("host", "maps.google.com");
	        holder.put("address_language", "zh_CN");
	        holder.put("request_address", true);
	        holder.put("radio_type", "gsm");
	        holder.put("carrier", "HTC");
	 
	        JSONObject tower = new JSONObject();
	        tower.put("mobile_country_code", mcc);
	        tower.put("mobile_network_code", mnc);
	        tower.put("cell_id", cid);
	        tower.put("location_area_code", lac);
	 
	        JSONArray towerarray = new JSONArray();
	        towerarray.put(tower);
	        holder.put("cell_towers", towerarray);
	 
	        StringEntity query = new StringEntity(holder.toString());
	        post.setEntity(query);
	 
	        /** 发出POST数据并获取返回数据 */
	        HttpResponse response = client.execute(post);
	        HttpEntity entity = response.getEntity();
	        BufferedReader buffReader = new BufferedReader(new InputStreamReader(entity.getContent()));
	        StringBuffer strBuff = new StringBuffer();
	        String result = null;
	        while ((result = buffReader.readLine()) != null) {
	            strBuff.append(result);
	        }
	 
	        Log.d(TAG, "Response: " + strBuff.toString());
	        
	        /** 解析返回的JSON数据获得经纬度 */
	        JSONObject json = new JSONObject(strBuff.toString());
	        JSONObject subjosn = new JSONObject(json.getString("location"));
	 
	        String latitude = subjosn.getString("latitude");
	        String longitude = subjosn.getString("longitude");
	         
	        Log.i("Result Location", String.format("Result Location: lat = %1$s, lon = %2$s", latitude, longitude));
	         
	    } catch (Exception e) {
	        Log.e(e.getMessage(), e.toString());
	    } finally{
	        post.abort();
	        client = null;
	    }
		return myLocation;
	}

}
