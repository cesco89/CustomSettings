package com.cesco.customsettings;


import utils.CMDProcessor;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.TwoStatePreference;
import android.util.Log;
import android.widget.Toast;

public class layTwoPrefs extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {

	public SharedPreferences prefs;
	public ListPreference maxgpu;
	public ListPreference mingpu;
	public CheckBoxPreference gpuboot;
	public String maxValue, minValue, boot;
	public TwoStatePreference applygpu;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preftwo);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		maxgpu = (ListPreference) findPreference("maxgpu");
		maxgpu.setOnPreferenceChangeListener(this);
		mingpu = (ListPreference) findPreference("mingpu");
		mingpu.setOnPreferenceChangeListener(this);
		gpuboot = (CheckBoxPreference) findPreference("gpuboot");
		gpuboot.setOnPreferenceClickListener(this);
		applygpu = (TwoStatePreference) findPreference("applygpu");
		applygpu.setOnPreferenceClickListener(this);
		
		String mValue = prefs.getString("maxgpu", "Select max frequency");
		maxgpu.setSummary(mValue);
		String mIValue = prefs.getString("mingpu", "Select min frequency");
		mingpu.setSummary(mIValue);
		
		maxValue = prefs.getString("maxgpu", "416");
		minValue = prefs.getString("mingpu", "247");

	}


	@Override
	public boolean onPreferenceChange(Preference pref, Object newvalue) {
		// TODO Auto-generated method stub
		if(pref == maxgpu) {
			maxValue = (String)newvalue;
			Log.d("AAAAAAAAA", "================"+maxValue+"============");
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("maxgpu", maxValue);
			editor.commit();
			maxgpu.setSummary(maxValue);

		}
		if(pref == mingpu) {
			minValue = (String)newvalue;
			Log.d("AAAAAAAAA", "================"+minValue+"============");
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("mingpu", minValue);
			editor.commit();
			mingpu.setSummary(minValue);
		}

		return false;
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean onPreferenceClick(Preference pref) {
		// TODO Auto-generated method stub
		if (pref == applygpu) {
			boolean value = applygpu.isChecked();
			if(value == true) {
				if(maxValue.equals("416")) {
					CMDProcessor.runSuCommand("busybox echo '" + maxValue + " " +maxValue + " "+maxValue + " "+maxValue + " " + maxValue + " " + "400 304 " + minValue +"'" +" > sys/devices/system/cpu/cpu0/cpufreq/gpu_oc ");
					Toast.makeText(this, "frequencies applied:" + "\n Max: "  + maxValue + "\n Min: " + minValue, Toast.LENGTH_LONG).show();

				}
				else {
					CMDProcessor.runSuCommand("busybox echo '" + maxValue + " " +maxValue + " "+maxValue + " "+maxValue + " "+ "484 400 304 " + minValue +"'" +" > sys/devices/system/cpu/cpu0/cpufreq/gpu_oc ");
					Toast.makeText(this, "frequencies applied:" + "\n Max: "  + maxValue + "\n Min: " + minValue, Toast.LENGTH_SHORT).show();
				};
			}
			if(value == false) {
				CMDProcessor.runSuCommand("busybox echo '416 416 416 416 416 400 304 247' > sys/devices/system/cpu/cpu0/cpufreq/gpu_oc ");
				Toast.makeText(this, "stock values applied:" + "\n Max: 416\n Min: 247", Toast.LENGTH_SHORT).show();
			}
		}

		if(pref == gpuboot) {
			boolean value = gpuboot.isChecked();
			if(value == true) {
				if(maxValue.equals("416")) {
					boot = "echo " + maxValue + " " +maxValue + " "+maxValue + " "+maxValue + " " + maxValue + " " + "400 304 " + minValue + " " + "> sys/devices/system/cpu/cpu0/cpufreq/gpu_oc";
				}
				else {
					boot = "echo " +  maxValue + " " +maxValue + " "+maxValue + " "+maxValue + " "+ "484 400 304 " + minValue + " " + "> sys/devices/system/cpu/cpu0/cpufreq/gpu_oc";
				}
				CMDProcessor.runSuCommand("busybox mount -o remount,rw /system");
				CMDProcessor.runSuCommand("busybox echo '#!/system/bin/sh' > system/etc/init.d/99bootgpu");
				CMDProcessor.runSuCommand("busybox echo 'sleep 60' >> system/etc/init.d/99bootgpu");
				CMDProcessor.runSuCommand("busybox echo '" + boot + "' >> system/etc/init.d/99bootgpu");
				CMDProcessor.runSuCommand("busybox chmod 755 /system/etc/init.d/99bootgpu");
				CMDProcessor.runSuCommand("busybox mount -o remount,ro /system");
			}
			if (value == false) {
				CMDProcessor.runSuCommand("busybox mount -o remount,rw /system");
				CMDProcessor.runSuCommand("busybox rm -f /system/etc/init.d/99bootgpu");
				CMDProcessor.runSuCommand("busybox mount -o remount,ro /system");
			}
		}

		return false;
	}
}
