package com.tim.locationreminder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Application;

import com.tim.locationreminder.manager.RemindManager;
import com.tim.locationreminder.manager.SettingsManager;
import com.tim.locationreminder.util.IOUtil;
import com.tim.locationreminder.util.Log;

public class LocationReminderApplication extends Application {
	
	private static final String BAIDU_MAP_KEY = "08F3C69C84F7F093D1BA44D84851F2352AE321D0";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.loggagble(true);
		checkAndCopyDefaultRing();
		SettingsManager.init(this);
		RemindManager.init(this);
	}
	
	private void checkAndCopyDefaultRing_Old() {
		final File file = new File(Constants.DEFAULT_REMIND_RING_PATH);
		InputStream srcInStream = null;
		ByteArrayOutputStream bos = null;
		if(!file.exists()) {
			try {
				srcInStream = getAssets().open("default_remind_ring.mp3");
				bos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len = srcInStream.read(buffer)) > -1) {
					bos.write(buffer, 0, len);
				}
				bos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				
			}
//			final File src = getAssets().
		}
	}
	
	private void checkAndCopyDefaultRing() {
		final File file = new File(Constants.DEFAULT_REMIND_RING_PATH);
		if(!file.exists()) {
			InputStream srcInStream = null;
			try {
				srcInStream = getAssets().open("default_remind_ring.mp3");
				IOUtil.saveInputStreamAsFile(srcInStream, file);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtil.closeIOStream(srcInStream);
				file.setReadable(true, false);
			}
		}
		else {
			file.setReadable(true, false);
		}
	}
	
	public void doTeminate() {
		System.exit(0);
	}
}
