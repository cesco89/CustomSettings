package com.cesco.customsettings;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import utils.CMDProcessor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class layInstaller extends Activity implements OnItemClickListener {

	public ArrayList<HashMap<String, String>> fileList = new ArrayList<HashMap<String, String>>();
	public ListView lv;
	public String SD_CARD = "/sdcard/";
	public String item;
	static final String KEY_NAME = "name";
	public String TYPE =".zip";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.installer);
		lv = (ListView) findViewById(R.id.lv);
		lv.setOnItemClickListener(this);
		finder();

	}

	void finder() {
		File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
		ListDir(root);

		File downloads = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download");
		ListDownload(downloads);

		//ListAdapter adapter = new ArrayAdapter<String>(act, R.layout.list_item, fileList);
		ListAdapter adapter = new SimpleAdapter(this, fileList,
				R.layout.list_item,
				new String[] { KEY_NAME}, new int[] {R.id.name});
		lv.setAdapter(adapter);
		Log.d("SDCARD", SD_CARD);

	}

	void ListDir(File f){
		File[] files = f.listFiles();
		//fileList.clear();
		for (File file : files){
			if(file.getName().endsWith(TYPE)){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_NAME, file.getName());
				fileList.add(map); 
			}
		}

	}

	void ListDownload(File f){
		File[] files = f.listFiles();
		//fileList.clear();
		for (File file : files){
			if(file.getName().endsWith(TYPE)){
				HashMap<String, String> map = new HashMap<String, String>();
				map.put(KEY_NAME,"(Download Folder) "+ file.getName());
				fileList.add(map); 
			}
		}
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int pos,
			long id) {
		// TODO Auto-generated method stub
		item = lv.getItemAtPosition(pos).toString();
		if(item.contains(".zip")){
			boolean check = item.contains("(Download Folder)");
			if(check == true) {
				SD_CARD = "/sdcard/Download/";
				item = item.replace("(Download Folder) ", "").replace("{name=", "").replace("}", "");
			}else if(check == false){
				SD_CARD = "/sdcard/";
				item = item.replace("{name=", "").replace("}", "");
			}
			recoveryDialog(item);
		}
		if(item.contains(".apk")){
			boolean check = item.contains("(Download Folder)");
			if(check == true) {
				item = item.replace("(Download Folder) ", "").replace("{name=", "").replace("}", "");
				Log.d("ITEMNAME", item);
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				String path = "file://"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+item;
				Log.d("ITEMNAME", path);
				intent.setDataAndType(Uri.parse(path), "application/vnd.android.package-archive");
				startActivity(intent);
			}
			if(check == false) {
				item = item.replace("{name=", "").replace("}", "");
				Log.d("ITEMNAME", item);
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				String path = "file://"+Environment.getExternalStorageDirectory()+"/"+item;
				Log.d("ITEMNAME", path);
				intent.setDataAndType(Uri.parse(path), "application/vnd.android.package-archive");
				startActivity(intent);
			}
		}


	}

	@SuppressWarnings("deprecation")
	public void recoveryDialog(String name) {
		AlertDialog.Builder recoverydiag = new AlertDialog.Builder(this);
		LayoutInflater inflater = this.getLayoutInflater();
		View V = inflater.inflate(R.layout.recoverydiag, null);
		recoverydiag.setView(V);
		final TextView tv1 = (TextView) V.findViewById(R.id.tv1);
		final CheckBox cb1 = (CheckBox) V.findViewById(R.id.cb1);
		final CheckBox cb2 = (CheckBox) V.findViewById(R.id.cb2);
		tv1.setText("You are about to install:   " + name);
		CMDProcessor.runSuCommand("busybox mount -o remount,rw /cache");
		CMDProcessor.runSuCommand("busybox echo 'install "+SD_CARD+item+"' >  /cache/recovery/openrecoveryscript");
		Log.d("RECFILE", "file created");
		cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton cb, boolean checked) {
				// TODO Auto-generated method stub
				if(checked == true){
					Log.d("CB1", "CHECKED");
					CMDProcessor.runSuCommand("busybox mount -o remount,rw /cache");
					CMDProcessor.runSuCommand("busybox echo 'wipe cache' >>  /cache/recovery/openrecoveryscript");
				}else{
					Log.d("CB1", "UNCHECKED");
				}

			}
		});
		cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton cb, boolean checked) {
				// TODO Auto-generated method stub
				if(checked == true){
					Log.d("CB1", "CHECKED");
					CMDProcessor.runSuCommand("busybox mount -o remount,rw /cache");
					CMDProcessor.runSuCommand("busybox echo 'wipe dalvik' >>  /cache/recovery/openrecoveryscript");
				}else{
					Log.d("CB1", "UNCHECKED");
				}

			}
		});
		recoverydiag
		.setTitle("Install Zip")
		.setPositiveButton("Install", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				PowerManager pm = (PowerManager)layInstaller.this.getSystemService(Context.POWER_SERVICE);
				pm.reboot("recovery");
			}
		})
		.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				CMDProcessor.runSuCommand("busybox mount -o remount,rw /cache");
				CMDProcessor.runSuCommand("busybox rm -f /cache/recovery/openrecoveryscript");
				CMDProcessor.runSuCommand("busybox mount -o remount,ro /cache");
				dialog.cancel();
				Log.d("RECFILE", "file destroied");

			}
		});      
		AlertDialog save = recoverydiag.create();
		save.show();

	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.zip:
			TYPE = ".zip";
			fileList.clear();
			finder();
			break;
		case R.id.apk:
			TYPE = ".apk";
			fileList.clear();
			finder();
		}
		return false;

	}

}
