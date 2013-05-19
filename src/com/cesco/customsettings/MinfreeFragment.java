package com.cesco.customsettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import utils.CMDProcessor;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MinfreeFragment extends Activity {
	public SharedPreferences prefs;
	public ListView lv;
	public File file1;
	public String s1;
	public String[] values;
	public ViewGroup parentGroup;
	public ItemAdapter adapter;
	public String[] names = {
		"Foreground_app",
		"Visible_app",
		"Secondary_server",
		"Hidden_app",
		"Content_provider",
		"Empty_app",
	};
	public int valAtPos;
	public int convVal;
	public String format, finalVal;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			prefs = PreferenceManager.getDefaultSharedPreferences(this);
			setContentView(R.layout.installer);
			lv = (ListView) findViewById(R.id.lv);
			parentGroup = (ViewGroup)lv;
			adapter = new ItemAdapter();
			file1 = new File("/sys/module/lowmemorykiller/parameters/minfree");
			FileInputStream fin1 = null;
			try {
				fin1 = new FileInputStream(file1);
				byte fileContent[] = new byte[(int)file1.length()];
				fin1.read(fileContent);
				s1 = new String(fileContent);
			}
			catch (FileNotFoundException e1) {
				System.out.println("File not found" + e1);
			}
			catch (IOException ioe1) {
				System.out.println("Exception while reading file " + ioe1);
			}
			finally {
				try {
					if (fin1 != null) {
						fin1.close();
					}
				}
				catch (IOException ioe1) {
					System.out.println("Error while closing stream: " + ioe1);
				}
			}
			format = s1.replaceAll(" ","").trim();
			values = format.split(",");
			Log.d("VALUES",""+format);
			
			lv.setAdapter(adapter);
	}
	
	class ItemAdapter extends BaseAdapter {

		private class ViewHolder {
			public TextView Title;
			public TextView tv;
			public SeekBar sb;
		}

		@Override
		public int getCount() {
			return values.length;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = MinfreeFragment.this.getLayoutInflater().inflate(R.layout.mf_row, parent, false);
				holder = new ViewHolder();
				holder.Title = (TextView) view.findViewById(R.id.Title);
				holder.tv = (TextView) view.findViewById(R.id.tv);
				holder.sb = (SeekBar) view.findViewById(R.id.sb);
				holder.sb.setMax(256);
				holder.Title.setText(names[position].toString());
				valAtPos = Integer.parseInt(values[position].toString());
				convVal = ((valAtPos * 4)/ 1024);
				holder.sb.setProgress(convVal);
				holder.tv.setText(convVal+" Mb");
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					holder.tv.setText(seekBar.getProgress()+" Mb");
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					int value = seekBar.getProgress();
					int newValue = ((value * 1024)/4);
					Log.d("NEWVALUE", "=======>"+newValue);
					finalVal = format;
					String[] vals = finalVal.split(",");
					finalVal = finalVal.replaceAll(vals[position], newValue+"");
					format = finalVal;
					Log.d("NEWSTRING", "===>"+finalVal);
					CMDProcessor.runSuCommand("busybox echo '"+format+"' > /sys/module/lowmemorykiller/parameters/minfree");
				}
				
			});

			return view;
		}
	}
	
}
	
