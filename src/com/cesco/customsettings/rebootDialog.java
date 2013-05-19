package com.cesco.customsettings;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PowerManager;

public class rebootDialog extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final PowerManager pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder.setTitle("Reboot");
		// set dialog message
		alertDialogBuilder
		.setMessage("do you really want to reboot ?")
		.setCancelable(false)
		.setPositiveButton("Reboot", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				pm.reboot("null");
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
