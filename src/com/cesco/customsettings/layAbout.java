package com.cesco.customsettings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class layAbout extends PreferenceActivity implements OnPreferenceClickListener {

	public SharedPreferences prefs;
	public Preference donate;
	public Preference androidiani;
	public Preference xda;
	public Preference gplus;
	public Preference twitter;


	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefabout);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		donate = (Preference) findPreference("donate");
		donate.setOnPreferenceClickListener(this);

		androidiani = (Preference) findPreference("androidiani");
		androidiani.setOnPreferenceClickListener(this);

		xda = (Preference) findPreference("xda");
		xda.setOnPreferenceClickListener(this);
		
		gplus = (Preference) findPreference("gplus");
		gplus.setOnPreferenceClickListener(this);
		
		twitter = (Preference) findPreference("twitter");
		twitter.setOnPreferenceClickListener(this);

	}


	@Override
	public boolean onPreferenceClick(Preference pref) {
		// TODO Auto-generated method stub
		if(pref == donate) {
			Uri uri = Uri.parse("http://forum.xda-developers.com/donatetome.php?u=3283046");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);	
		}
		if(pref == androidiani) {
			Uri uri = Uri.parse("http://www.androidiani.com/forum/-one-x-modding/285933-app-4-1-aosp-sense-customsettings-v2-0-2-0-cpu-gpu-s2w-uv-profiles-all-kernels.html");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
		if(pref == xda) {
			Uri uri = Uri.parse("http://forum.xda-developers.com/showthread.php?t=2212253");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
		if(pref == gplus) {
			Uri uri = Uri.parse("https://plus.google.com/113012341277613226011/posts");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
		if(pref == twitter) {
			Uri uri = Uri.parse("https://twitter.com/xcesco89");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
		return false;
	}
}
