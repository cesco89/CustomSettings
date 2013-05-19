package com.cesco.customsettings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.main);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen screen, Preference pref) {
		Class<?> cls = null;
		String title = pref.getTitle().toString();
		if (title.equals("CPU Settings")) {
			cls = layOnePrefs.class;	
		} else if (title.equals("UV Table")) {
			cls = UVTable.class;
		} else if (title.equals("GPU Settings")) {
			cls = layTwoPrefs.class;
		} else if (title.equals("Miscellaneous settings")) {
			cls = layThreePrefs.class;
		} else if (title.equals("Usage Stats")) {
			cls = statsFragment.class;
		} else if (title.equals("Reboot Options")) {
			cls = layBootPrefs.class;
		} else if (title.equals("Install APK/Zip")) {
			cls = layInstaller.class;
		} else if (title.equals("About this app")) {
			cls = layAbout.class;
		} else if (title.equals("MinFree Settings")) {
			cls = MinfreeFragment.class;
		} 
		Intent intent = new Intent(this, cls);
		startActivity(intent);
		return true;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
