package com.cesco.customsettings;

import utils.CMDProcessor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class layBootPrefs extends PreferenceActivity implements OnPreferenceClickListener {

	public SharedPreferences prefs;
	public Preference normal;
	public Preference recovery;
	public Preference bootloader;
	public Preference hotreboot;
	public PowerManager pm;


	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefboot);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		normal = (Preference) findPreference("normal");
		normal.setOnPreferenceClickListener(this);

		recovery = (Preference) findPreference("recovery");
		recovery.setOnPreferenceClickListener(this);

		bootloader = (Preference) findPreference("bootloader");
		bootloader.setOnPreferenceClickListener(this);
		
		hotreboot = (Preference) findPreference("hotreboot");
		hotreboot.setOnPreferenceClickListener(this);

		pm = (PowerManager)this.getSystemService(Context.POWER_SERVICE);

	}


	@Override
	public boolean onPreferenceClick(Preference pref) {
		// TODO Auto-generated method stub
		if(pref == normal) {
			reboot("null");
		}
		if(pref == recovery) {
			reboot("recovery");
		}
		if(pref == bootloader) {
			reboot("bootloader");
		}
		if(pref == hotreboot) {
			Hreboot();
		}
		return false;
	}

	public void reboot (final String reason) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder.setTitle("Reboot " +reason);
		// set dialog message
		alertDialogBuilder
		.setMessage("do you really want to reboot ?")
		.setCancelable(false)
		.setPositiveButton("Reboot", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				pm.reboot(reason);
			}
		})
		.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();	
	}
	
	public void Hreboot() {
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
				layBootPrefs.this.finish();
			}
		})
		.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		
	}
}
