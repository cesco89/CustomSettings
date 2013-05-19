package com.cesco.customsettings;

import utils.CMDProcessor;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.TwoStatePreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class layThreePrefs extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {

	public SharedPreferences prefs;
	public ListPreference light, buttons;
	public TwoStatePreference btnback, sweepwake, sweepdir;
	public CheckBoxPreference miscboot;
	public TwoStatePreference dtwake;
	public Preference dtTime;
	public String s2wvalue, lValue;
	public String backvalue = "echo 0 > /sys/class/leds/button-backlight/brightness";
	public String bootsweep ="echo 0 > /sys/android_touch/sweep2wake";
	public String dirvalue ="echo 0 > /sys/android_touch/s2w_allow_stroke";
	public String bootdtwake = "echo 0 > /sys/android_touch/s2w_allow_double_tap";
	public String bootdtduration = "echo 500 > /sys/android_touch/s2w_double_tap_threshold";
	public String bootlight = "echo 255 > /sys/class/leds/button-backlight/button_brightness";
	public String bootbtn = "echo 500 > /sys/android_touch/s2w_min_distance";
	public int step=5;
	public int v = 500;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefthree);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		light = (ListPreference) findPreference("light");
		light.setOnPreferenceChangeListener(this);
		buttons = (ListPreference) findPreference("buttons");
		buttons.setOnPreferenceChangeListener(this);
		btnback = (TwoStatePreference) findPreference("btnback");
		btnback.setOnPreferenceClickListener(this);
		sweepwake = (TwoStatePreference) findPreference("sweepwake");
		sweepwake.setOnPreferenceClickListener(this);
		sweepdir = (TwoStatePreference) findPreference("sweepdir");
		sweepdir.setOnPreferenceClickListener(this);
		miscboot = (CheckBoxPreference) findPreference("miscboot");
		miscboot.setOnPreferenceClickListener(this);
		dtwake = (TwoStatePreference) findPreference("dtwake");
		dtwake.setOnPreferenceClickListener(this);
		dtTime = (Preference) findPreference("dtTime");
		dtTime.setOnPreferenceClickListener(this);
		
		String lVal = prefs.getString("light", "Select brightness value. Zero means no backlight");
		light.setSummary(lVal);
		String bVal = prefs.getString("buttons", "Select Sweep2wake min swipe distance");
		buttons.setSummary(bVal);

		
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onPreferenceChange(Preference pref, Object newvalue) {
		// TODO Auto-generated method stub
		if (pref == light) {
			lValue = (String)newvalue;
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("light", lValue);
			editor.commit();
			CMDProcessor.runSuCommand("busybox echo '"+lValue+"' > /sys/class/leds/button-backlight/button_brightness");
			light.setSummary(lValue);
		}
		if (pref == buttons) {
			String value = (String)newvalue;
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("buttons", value);
			editor.commit();
			
			if (value.equals("1 button")) {
				s2wvalue = "325";
			}
			if (value.equals("2 buttons")) {
				s2wvalue = "500";
			}
			if (value.equals("3 buttons")) {
				s2wvalue = "850";
			}
			CMDProcessor.runSuCommand("busybox echo '"+s2wvalue+"' > /sys/android_touch/s2w_min_distance");
			buttons.setSummary(value);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onPreferenceClick(Preference pref) {
		// TODO Auto-generated method stub
		if (pref == btnback) {
			boolean value = btnback.isChecked();
			if (value == true) {
				backvalue = "echo 0 > /sys/class/leds/button-backlight/brightness";
				CMDProcessor.runSuCommand("busybox echo '0' > /sys/class/leds/button-backlight/brightness");
				Toast.makeText(this,"backlight Disabled!" , Toast.LENGTH_SHORT).show();
			}
			if (value == false) {
				backvalue = "#";
				CMDProcessor.runSuCommand("busybox echo '1' > /sys/class/leds/button-backlight/brightness");
				Toast.makeText(this,"backlight Enabled!" , Toast.LENGTH_SHORT).show();
			}
		}
		if(pref == sweepwake) {
			boolean value = sweepwake.isChecked();
			if(value == true) {
				bootsweep = "echo 1 > /sys/android_touch/sweep2wake";
				CMDProcessor.runSuCommand("busybox echo '1' > /sys/android_touch/sweep2wake");
				Toast.makeText(this,"Sweep2Wake Enabled" , Toast.LENGTH_SHORT).show();
			}
			if(value == false) {
				bootsweep = "echo 0 > /sys/android_touch/sweep2wake";
				CMDProcessor.runSuCommand("busybox echo '0' > /sys/android_touch/sweep2wake");
				Toast.makeText(this,"Sweep2Wake Disabled" , Toast.LENGTH_SHORT).show();
			}
		}
		if(pref == sweepdir) {
			boolean value = sweepdir.isChecked();
			if(value == true) {
				dirvalue = "echo 1 > /sys/android_touch/s2w_allow_stroke";
				CMDProcessor.runSuCommand("busybox echo 1 > /sys/android_touch/s2w_allow_stroke");
				Toast.makeText(this,"S2w direction independent!" , Toast.LENGTH_SHORT).show();
			}
			if(value == false) {
				dirvalue = "echo 0 > /sys/android_touch/s2w_allow_stroke";
				CMDProcessor.runSuCommand("busybox echo 0 > /sys/android_touch/s2w_allow_stroke");
				Toast.makeText(this,"S2w direction dependent!" , Toast.LENGTH_SHORT).show();
			}
		}
		if(pref == dtwake){
			boolean value = dtwake.isChecked();
			if(value == true){
				bootdtwake = "echo 1 > /sys/android_touch/s2w_allow_double_tap";
				CMDProcessor.runSuCommand("busybox echo 1 > /sys/android_touch/s2w_allow_double_tap");
				Toast.makeText(this,"DT2W Enabled!" , Toast.LENGTH_SHORT).show();
			}
			if(value == false) {
				bootdtwake = "echo 0 > /sys/android_touch/s2w_allow_double_tap";
				CMDProcessor.runSuCommand("busybox echo 0 > /sys/android_touch/s2w_allow_double_tap");
				Toast.makeText(this,"DT2W Disabled!" , Toast.LENGTH_SHORT).show();
			}
		}
		if(pref == dtTime){
			setDuration();
		}
		if(pref == miscboot) {
			boolean value = miscboot.isChecked();
			if(value == true) {
				//bootlight = "echo "+lValue+" > /sys/class/leds/button-backlight/button_brightness";
				//bootbtn = "echo "+s2wvalue+" > /sys/android_touch/s2w_min_distance";
				CMDProcessor.runSuCommand("busybox mount -o remount,rw /system");
				CMDProcessor.runSuCommand("busybox echo '#!/system/bin/sh' > system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo 'sleep 60' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo '"+bootlight+ "' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo '"+backvalue+ "' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo '"+bootsweep+ "' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo '"+bootbtn+ "' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo '"+dirvalue+ "' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo '"+bootdtwake+ "' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox echo '"+bootdtduration+ "' >> system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox chmod 755 /system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox mount -o remount,ro /system");
			}
			if(value == false) {
				CMDProcessor.runSuCommand("busybox mount -o remount,rw /system");
				CMDProcessor.runSuCommand("busybox rm -f /system/etc/init.d/97bootlight");
				CMDProcessor.runSuCommand("busybox mount -o remount,ro /system");
			}
		}
		return false;
	}
	public void setDuration() {
		AlertDialog.Builder setSteps = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View V = inflater.inflate(R.layout.uvsteps, null);
		setSteps.setView(V);
		final SeekBar sb = (SeekBar) V.findViewById(R.id.sb);
		final TextView tv = (TextView) V.findViewById(R.id.tv);
		sb.setMax(10);
		sb.setProgress(prefs.getInt("duration", 5));
		int val = prefs.getInt("duration", 5);
		val *= 100;
		tv.setText(val+"ms");
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				step = sb.getProgress();

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//tv.setText("-" +sb.getProgress()+ "mV");
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				v = sb.getProgress();
				v *= 100;
				tv.setText(v+ "ms");
			}

		});
		setSteps
		.setTitle("Set DT2W duration")
		.setPositiveButton("Set value", new DialogInterface.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt("duration", step);
				editor.commit();
				bootdtduration = "echo "+v+" > /sys/android_touch/s2w_double_tap_threshold";
				CMDProcessor.runSuCommand("busybox echo "+v+" > /sys/android_touch/s2w_double_tap_threshold");
				Toast.makeText(layThreePrefs.this,"DT2W duration set to: "+v+"ms" , Toast.LENGTH_SHORT).show();
				
				
			}
		})
		.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});      
		AlertDialog save = setSteps.create();
		save.show();
	}
}
