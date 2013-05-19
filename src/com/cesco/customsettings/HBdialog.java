package com.cesco.customsettings;

import utils.CMDProcessor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;

public class HBdialog extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder.setTitle("Hot Reboot");
		// set dialog message
		alertDialogBuilder
		.setMessage("do you really want to Hot Reboot ?")
		.setCancelable(false)
		.setPositiveButton("Reboot", new DialogInterface.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(DialogInterface dialog, int id) {
				CMDProcessor.runSuCommand("busybox killall system_server");
				finish();
			}
		})
		.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				finish();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}
