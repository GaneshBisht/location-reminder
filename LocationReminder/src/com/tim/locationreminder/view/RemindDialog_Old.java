package com.tim.locationreminder.view;

import com.tim.locationreminder.R;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager.LayoutParams;

public class RemindDialog_Old extends Dialog {

	public RemindDialog_Old(Context context) {
		super(context, R.style.remind_dialog);
		setCanceledOnTouchOutside(false);
		setCancelable(false);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		setContentView(R.layout.dialog_remind);
		initComponent();
	}
	
	private void initComponent() {
		
	}
	

}
