package com.cesco.customsettings;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import utils.CMDProcessor;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class UVTable extends Activity  {


	public static final String PREFS_NAME = "UVprefs";
	public SharedPreferences prefs;
	public String str;
	public ListView listView1;
	public String s1;
	public String[] split1;
	public String[] names;
	public String[] values;
	public ViewGroup parentGroup;
	public LinearLayout ll;
	public String val;
	public String s3 = null;
	public int step = 5;
	public boolean checked;
	public MenuItem boot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			prefs = PreferenceManager.getDefaultSharedPreferences(this);
			checked = prefs.getBoolean("boot", false);
			if(checked == true) {
				boot.setIcon(R.drawable.chd);
				boot.setChecked(checked);
			}
			step = prefs.getInt("step", 5);
		File file = new File("/sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table");

		if(file.exists()) {
			setContentView(R.layout.uvlay);
			ll = (LinearLayout) findViewById(R.id.linearTop);
			parentGroup = (ViewGroup)ll;
			this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			prefs = PreferenceManager.getDefaultSharedPreferences(this);

			File file1 = new File("/sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table"); 

			FileInputStream fin = null;
			try {
				fin = new FileInputStream(file1);
				byte fileContent[] = new byte[(int)file1.length()];
				fin.read(fileContent);
				s1 = new String(fileContent);
			}
			catch (FileNotFoundException e) {
				System.out.println("File not found" + e);
			}
			catch (IOException ioe) {
				System.out.println("Exception while reading file " + ioe);
			}
			finally {
				try {
					if (fin != null) {
						fin.close();
					}
				}
				catch (IOException ioe) {
					System.out.println("Error while closing stream: " + ioe);
				}
			} 
			split1 = s1.split(" ");
			ArrayList<String> Tokens = new ArrayList<String>();

			try {
				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream("/sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table");
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {
					strLine = strLine.trim();

					if ((strLine.length()!=0)) {
						String[] names = strLine.split("\\s+");
						Tokens.add(names[0]);
					}


				}

				for (String s : Tokens) {
					//System.out.println(s);
					//Log.d("NNNNNNNNNNNNNNNNNN", s);
				}

				// Close the input stream
				in.close();
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			names = new String[Tokens.size()-1];
			names = Tokens.toArray(names);


			ArrayList<String> value = new ArrayList<String>();

			try {
				// Open the file that is the first
				// command line parameter
				FileInputStream fstream = new FileInputStream("/sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table");
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				// Read File Line By Line
				while ((strLine = br.readLine()) != null) {
					strLine = strLine.trim();

					if ((strLine.length()!=0)) {
						String[] val = strLine.split("\\s+");
						value.add(val[1]);
					}


				}

				for (String s : value) {
					//System.out.println(s);
					//Log.d("NNNNNNNNNNNNNNNNNN", s);
				}

				// Close the input stream
				in.close();
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
			values = new String[value.size()-1];
			values = value.toArray(values);

			LineNumberReader lnr = null;
			try {
				lnr = new LineNumberReader(new FileReader(new File("/sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				lnr.skip(Long.MAX_VALUE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int num = lnr.getLineNumber();
			Log.d("LINES", ""+num);
			int i = 0;
			for (String s : names) {
				LayoutInflater inflater1 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View view1 = inflater1.inflate(R.layout.uvrow, null, true);
				TextView text0 = (TextView) view1.findViewById(R.id.text0);
				ImageButton back0 = (ImageButton) view1.findViewById(R.id.back0);
				final EditText edit0 = (EditText) view1.findViewById(R.id.edit0);
				ImageButton fwd0 = (ImageButton) view1.findViewById(R.id.fwd0);
				text0.setText(s);
				parentGroup.addView(view1);
				edit0.setText(values[i]);
				i=i+1;
				edit0.addTextChangedListener(new TextWatcher() {

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						Log.d("CHANGED", "=========="+edit0.getText().toString());

						StringBuffer result = new StringBuffer();
						for (int i = 0; i < values.length; i++) {
							result.append( values[i] );
							result.append(", ");


						}
						String sa = result.toString();
						String s2 = sa.replaceAll(", $", "");
						s2 = s2.replaceAll(val, edit0.getText().toString());
						values = s2.split(", ");
						s3 = s2;
						Log.d("NEWSTRING", s3);

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub
						val = edit0.getText().toString();
					}

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub

					}

				});

				back0.setOnClickListener( new OnClickListener() {
					@Override
					public void onClick(View v) {
						int val = Integer.parseInt(edit0.getText().toString());
						edit0.setText(String.valueOf(val-step));
					}
				});
				fwd0.setOnClickListener( new OnClickListener() {
					@Override
					public void onClick(View v) {
						int val = Integer.parseInt(edit0.getText().toString());
						edit0.setText(String.valueOf(val+step));
					}
				});

			}
		}
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		boot =  menu.findItem(R.id.boot);
		return true;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.uvmenu, menu);
		return true;
	} 

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{

		switch (item.getItemId()) {
		case R.id.apply:
			Log.d("FINAL", "busybox echo '"+s3+"' > /sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table");
			CMDProcessor.runSuCommand("busybox echo '"+s3+"' > /sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table");
			break;
		case R.id.step:

			AlertDialog.Builder setSteps = new AlertDialog.Builder(this);
			LayoutInflater inflater = this.getLayoutInflater();
			View V = inflater.inflate(R.layout.uvsteps, null);
			setSteps.setView(V);
			final SeekBar sb = (SeekBar) V.findViewById(R.id.sb);
			final TextView tv = (TextView) V.findViewById(R.id.tv);
			sb.setMax(100);
			sb.setProgress(prefs.getInt("step", 5));
			tv.setText(prefs.getInt("step", 5)+"mV");
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
					tv.setText(sb.getProgress()+ "mV");
				}

			});
			setSteps
			.setTitle("Set UV Steps")
			.setPositiveButton("Done", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("step", step);
					editor.commit();
				}
			})
			.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});      
			AlertDialog save = setSteps.create();
			save.show();

			break;
		case R.id.boot:
			if (checked == true){
				boot.setIcon(R.drawable.unchd);
				CMDProcessor.runSuCommand("busybox mount -o remount,rw /system");
				CMDProcessor.runSuCommand("busybox rm -f /system/etc/init.d/98bootuv");
				CMDProcessor.runSuCommand("busybox mount -o remount,ro /system");
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean("boot", false);
				editor.commit();
				checked = false;
				
			}
			else {
				boot.setIcon(R.drawable.chd);
				if (s3==null) {
					changevolt();
					boot.setIcon(R.drawable.unchd);
				}
				else {
					String bootuv = "echo "+s3+" > /sys/devices/system/cpu/cpu0/cpufreq/UV_mV_table";
					CMDProcessor.runSuCommand("busybox mount -o remount,rw /system");
					CMDProcessor.runSuCommand("busybox echo '#!/system/bin/sh' > system/etc/init.d/98bootuv");
					CMDProcessor.runSuCommand("busybox echo 'sleep 60' >> system/etc/init.d/98bootuv");
					CMDProcessor.runSuCommand("busybox echo '#User UV' >> system/etc/init.d/98bootuv");
					CMDProcessor.runSuCommand("busybox echo '" + bootuv + "' >> system/etc/init.d/98bootuv");
					CMDProcessor.runSuCommand("busybox chmod 777 /system/etc/init.d/98bootuv");
					CMDProcessor.runSuCommand("busybox mount -o remount,ro /system");
					SharedPreferences.Editor editor = prefs.edit();
					editor.putBoolean("boot", true);
					editor.commit();
					checked = true;
					
				}
			}
			break;
		}
		return true;
	}

	private void changevolt(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder.setTitle("Warning!");
		// set dialog message
		alertDialogBuilder
		.setMessage("Please change values before hit this button! \nThis is necessary to make the app store the values!")
		.setCancelable(false)
		.setNeutralButton("Got It!",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();	
	}
}


