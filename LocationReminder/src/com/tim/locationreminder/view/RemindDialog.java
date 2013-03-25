package com.tim.locationreminder.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.tim.locationreminder.R;
import com.tim.locationreminder.manager.RemindManager;

public class RemindDialog extends AlertDialog {

	public RemindDialog(Context context) {
		super(context);
		setCancelable(false);
		setMessage(context.getString(R.string.remind_dialog_msg));
		setButton(Dialog.BUTTON_POSITIVE, context.getString(R.string.remind_dialog_ok), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		});
	}

	
	@Override
	public void show() {
		if(isShowing()) {
			return ;
		}
		super.show();
		final RemindManager remindManager = RemindManager.getInstance();
		if(remindManager != null) {
			remindManager.startRemind();
		}
//		new Handler().postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
////				AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
////				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
////				audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
//				
//			}
//		}, 800);
	}

	@Override
	public void dismiss() {
		final RemindManager remindManager = RemindManager.getInstance();
		if(remindManager != null) {
			remindManager.stopRemind();
		}
		super.dismiss();
	}
}
