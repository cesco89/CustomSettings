package com.cesco.customsettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class statsFragment extends Activity  {

	public TextView curfreq;
	public TextView curfreq1;
	public TextView curfreq2;
	public TextView curfreq3;
	public ProgressBar progressBar1;
	public ProgressBar progress2;
	public ProgressBar progress3;
	public ProgressBar progress4;
	public String s1,str1;
	public String p2,strp2;
	public String p3,strp3;
	public String p4,strp4;
	public Timer t;
	public ProgressBar progressBar2;
	public TextView battlevel;
	public TextView battstatus;
	public TextView battvolt;
	public String s2;
	public String str2;
	public String s3;
	public String str3;
	public String s4, str4;
	public String s8, str8;
	public ProgressBar pfreq0;
	public ProgressBar pfreq1;
	public ProgressBar pfreq2;
	public ProgressBar pfreq3;
	public ProgressBar pfreq4;
	public ProgressBar pfreq5;
	public ProgressBar pfreq6;
	public ProgressBar pfreq7;
	public ProgressBar pfreq8;
	public ProgressBar pfreq9;
	public ProgressBar pfreq10;
	public ProgressBar pfreq11;
	public ProgressBar pfreq12;
	public ProgressBar pfreq13;
	public TextView perc0, perc1, perc2, perc3, perc4, perc5,
	perc6, perc7, perc8, perc9, perc10, perc11,
	perc12, perc13;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			setContentView(R.layout.statslayout);
			progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
			progressBar1.setProgress(0);
			progressBar1.setMax(1700000);
			progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
			progressBar2.setProgress(0);
			progressBar2.setMax(100);
			progress2 = (ProgressBar) findViewById(R.id.progress2);
			progress2.setProgress(0);
			progress2.setMax(1700000);
			progress3 = (ProgressBar) findViewById(R.id.progress3);
			progress3.setProgress(0);
			progress3.setMax(1700000);
			progress4 = (ProgressBar) findViewById(R.id.progress4);
			progress4.setProgress(0);
			progress4.setMax(1700000);

			pfreq0 = (ProgressBar) findViewById(R.id.pfreq0);
			pfreq1 = (ProgressBar) findViewById(R.id.pfreq1);
			pfreq2 = (ProgressBar) findViewById(R.id.pfreq2);
			pfreq3 = (ProgressBar) findViewById(R.id.pfreq3);
			pfreq4 = (ProgressBar) findViewById(R.id.pfreq4);
			pfreq5 = (ProgressBar) findViewById(R.id.pfreq5);
			pfreq6 = (ProgressBar) findViewById(R.id.pfreq6);
			pfreq7 = (ProgressBar) findViewById(R.id.pfreq7);
			pfreq8 = (ProgressBar) findViewById(R.id.pfreq8);
			pfreq9 = (ProgressBar) findViewById(R.id.pfreq9);
			pfreq10 = (ProgressBar) findViewById(R.id.pfreq10);
			pfreq11 = (ProgressBar) findViewById(R.id.pfreq11);
			pfreq12 = (ProgressBar) findViewById(R.id.pfreq12);
			pfreq13 = (ProgressBar) findViewById(R.id.pfreq13);

			curfreq = (TextView) findViewById(R.id.curfreq);
			curfreq1 = (TextView) findViewById(R.id.curfreq1);
			curfreq2 = (TextView) findViewById(R.id.curfreq2);
			curfreq3 = (TextView) findViewById(R.id.curfreq3);
			perc0 = (TextView) findViewById(R.id.perc0);
			perc1 = (TextView) findViewById(R.id.perc1);
			perc2 = (TextView) findViewById(R.id.perc2);
			perc3 = (TextView) findViewById(R.id.perc3);
			perc4 = (TextView) findViewById(R.id.perc4);
			perc5 = (TextView) findViewById(R.id.perc5);
			perc6 = (TextView) findViewById(R.id.perc6);
			perc7 = (TextView) findViewById(R.id.perc7);
			perc8 = (TextView) findViewById(R.id.perc8);
			perc9 = (TextView) findViewById(R.id.perc9);
			perc10 = (TextView) findViewById(R.id.perc10);
			perc11 = (TextView) findViewById(R.id.perc11);
			perc12 = (TextView) findViewById(R.id.perc12);
			perc13 = (TextView) findViewById(R.id.perc13);
			battlevel = (TextView) findViewById(R.id.battlevel);
			battstatus = (TextView) findViewById(R.id.battstatus);
			battvolt = (TextView) findViewById(R.id.battvolt);
			
			updater();

		}

		public void updater() {	

			t = new Timer();
			t.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							

							//CPU0 Current freq
							File file1 = new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");  
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
							String[] split1 = s1.split("\\s+");
							str1 = split1[0];
							int value = Integer.parseInt(str1); 
							progressBar1.setProgress(value);
							Log.d("CURRENT", str1); 
							curfreq.setText(value + " Hz");

							//CPU1 Current freq
							File file3 = new File("/sys/devices/system/cpu/cpu1/cpufreq/scaling_cur_freq");
							if (file3.exists()) {
								FileInputStream fin3 = null;
								try {
									fin3 = new FileInputStream(file3);
									byte fileContent[] = new byte[(int)file3.length()];
									fin3.read(fileContent);
									p2 = new String(fileContent);
								}
								catch (FileNotFoundException e) {
									System.out.println("File not found" + e);
								}
								catch (IOException ioe) {
									System.out.println("Exception while reading file " + ioe);
								}
								finally {
									try {
										if (fin3 != null) {
											fin3.close();
										}
									}
									catch (IOException ioe) {
										System.out.println("Error while closing stream: " + ioe);
									}
								} 
								String[] split3 = p2.split("\\s+");
								strp2 = split3[0];
								int value2 = Integer.parseInt(strp2); 
								progress2.setProgress(value2);
								Log.d("CURRENT", strp2); 
								curfreq1.setText(value2 + " Hz");
							}else{
								curfreq1.setText("Offline");
								progress2.setProgress(0);
							}

							//CPU2 Current freq
							File file4 = new File("/sys/devices/system/cpu/cpu2/cpufreq/scaling_cur_freq");
							if (file4.exists()) {
								FileInputStream fin4 = null;
								try {
									fin4 = new FileInputStream(file4);
									byte fileContent[] = new byte[(int)file4.length()];
									fin4.read(fileContent);
									p3 = new String(fileContent);
								}
								catch (FileNotFoundException e) {
									System.out.println("File not found" + e);
								}
								catch (IOException ioe) {
									System.out.println("Exception while reading file " + ioe);
								}
								finally {
									try {
										if (fin4 != null) {
											fin4.close();
										}
									}
									catch (IOException ioe) {
										System.out.println("Error while closing stream: " + ioe);
									}
								} 
								String[] split4 = p3.split("\\s+");
								strp3 = split4[0];
								int value3 = Integer.parseInt(strp3); 
								progress3.setProgress(value3);
								//Log.d("CURRENT", strp2); 
								curfreq2.setText(value3 + " Hz");
							}else{
								curfreq2.setText("Offline");
								progress3.setProgress(0);
							}

							//CPU3 Current freq
							File file5 = new File("/sys/devices/system/cpu/cpu3/cpufreq/scaling_cur_freq");
							if (file5.exists()) {
								FileInputStream fin5 = null;
								try {
									fin5 = new FileInputStream(file5);
									byte fileContent[] = new byte[(int)file5.length()];
									fin5.read(fileContent);
									p4 = new String(fileContent);
								}
								catch (FileNotFoundException e) {
									System.out.println("File not found" + e);
								}
								catch (IOException ioe) {
									System.out.println("Exception while reading file " + ioe);
								}
								finally {
									try {
										if (fin5 != null) {
											fin5.close();
										}
									}
									catch (IOException ioe) {
										System.out.println("Error while closing stream: " + ioe);
									}
								} 
								String[] split5 = p4.split("\\s+");
								strp4 = split5[0];
								int value4 = Integer.parseInt(strp4); 
								progress4.setProgress(value4);
								Log.d("CURRENT", strp4); 
								curfreq3.setText(value4 + " Hz");
							}else{
								curfreq3.setText("Offline");
								progress4.setProgress(0);

							}

							//Battery current capacity
							File file2 = new File("/sys/devices/platform/htc_battery/power_supply/battery/capacity");  
							FileInputStream fin2 = null;
							try {
								fin2 = new FileInputStream(file2);
								byte fileContent[] = new byte[(int)file2.length()];
								fin2.read(fileContent);
								s2 = new String(fileContent);
							}
							catch (FileNotFoundException e) {
								System.out.println("File not found" + e);
							}
							catch (IOException ioe) {
								System.out.println("Exception while reading file " + ioe);
							}
							finally {
								try {
									if (fin2 != null) {
										fin2.close();
									}
								}
								catch (IOException ioe) {
									System.out.println("Error while closing stream: " + ioe);
								}
							} 
							String[] split2 = s2.split("\\s+");
							str2 = split2[0];
							int value2 = Integer.parseInt(str2); 
							progressBar2.setProgress(value2);
							Log.d("CURRENT", str2); 
							battlevel.setText(" " +value2+"%");

							//Battery Health
							File file7 = new File("/sys/devices/platform/htc_battery/power_supply/battery/health");  
							FileInputStream fin7 = null;
							try {
								fin7 = new FileInputStream(file7);
								byte fileContent[] = new byte[(int)file7.length()];
								fin7.read(fileContent);
								s3 = new String(fileContent);
							}
							catch (FileNotFoundException e) {
								System.out.println("File not found" + e);
							}
							catch (IOException ioe) {
								System.out.println("Exception while reading file " + ioe);
							}
							finally {
								try {
									if (fin7 != null) {
										fin7.close();
									}
								}
								catch (IOException ioe) {
									System.out.println("Error while closing stream: " + ioe);
								}
							} 
							String[] split7 = s3.split("\\s+");
							str3 = split7[0]; 
							Log.d("CURRENT", str3); 
							battstatus.setText(" " +str3);


							//Battery Voltage (mV)
							File file6 = new File("/sys/devices/platform/htc_battery/power_supply/battery/batt_vol");  
							FileInputStream fin6 = null;
							try {
								fin6 = new FileInputStream(file6);
								byte fileContent[] = new byte[(int)file6.length()];
								fin6.read(fileContent);
								s4 = new String(fileContent);
							}
							catch (FileNotFoundException e) {
								System.out.println("File not found" + e);
							}
							catch (IOException ioe) {
								System.out.println("Exception while reading file " + ioe);
							}
							finally {
								try {
									if (fin6 != null) {
										fin6.close();
									}
								}
								catch (IOException ioe) {
									System.out.println("Error while closing stream: " + ioe);
								}
							} 
							String[] split6 = s4.split("\\s+");
							str4 = split6[0];
							int value6 = Integer.parseInt(str4); 
							Log.d("CURRENT", str4); 
							battvolt.setText(" " +value6+ " mV");

							//CPU Freq Usage
							File file8 = new File("/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state");  
							FileInputStream fin8 = null;
							try {
								fin8 = new FileInputStream(file8);
								byte fileContent[] = new byte[(int)file8.length()];
								fin8.read(fileContent);
								s8 = new String(fileContent);
							}
							catch (FileNotFoundException e) {
								System.out.println("File not found" + e);
							}
							catch (IOException ioe) {
								System.out.println("Exception while reading file " + ioe);
							}
							finally {
								try {
									if (fin8 != null) {
										fin8.close();
									}
								}
								catch (IOException ioe) {
									System.out.println("Error while closing stream: " + ioe);
								}
							} 
							String[] split8 = s8.split("\\s+");
							// get values from array
							int val0 = Integer.parseInt(split8[1]);
							int val1 = Integer.parseInt(split8[3]);
							int val2 = Integer.parseInt(split8[5]);
							int val3 = Integer.parseInt(split8[7]);
							int val4 = Integer.parseInt(split8[9]);
							int val5 = Integer.parseInt(split8[11]);
							int val6 = Integer.parseInt(split8[13]);
							int val7 = Integer.parseInt(split8[15]);
							int val8 = Integer.parseInt(split8[17]);
							int val9 = Integer.parseInt(split8[19]);
							int val10 = Integer.parseInt(split8[21]);
							int val11 = Integer.parseInt(split8[23]);
							int val12 = Integer.parseInt(split8[25]);
							int val13 = Integer.parseInt(split8[27]);
							//calculate the max progressbar value and convert in hours
							int Tval = (val0+val1+val2+val3+val4+val5+val6+val7+val8+val9+val10+val11+val12+val13)/3600;

							// set max progressbar value
							pfreq0.setMax(Tval);
							pfreq1.setMax(Tval);
							pfreq2.setMax(Tval);
							pfreq3.setMax(Tval);
							pfreq4.setMax(Tval);
							pfreq5.setMax(Tval);
							pfreq6.setMax(Tval);
							pfreq7.setMax(Tval);
							pfreq8.setMax(Tval);
							pfreq9.setMax(Tval);
							pfreq10.setMax(Tval);
							pfreq11.setMax(Tval);
							pfreq12.setMax(Tval);
							pfreq13.setMax(Tval);

							//set single progressbars value in hours
							pfreq0.setProgress((val0/3600));
							pfreq1.setProgress((val1/3600));
							pfreq2.setProgress((val2/3600));
							pfreq3.setProgress((val3/3600));
							pfreq4.setProgress((val4/3600));
							pfreq5.setProgress((val5/3600));
							pfreq6.setProgress((val6/3600));
							pfreq7.setProgress((val7/3600));
							pfreq8.setProgress((val8/3600));
							pfreq9.setProgress((val9/3600));
							pfreq10.setProgress((val10/3600));
							pfreq11.setProgress((val11/3600));
							pfreq12.setProgress((val12/3600));
							pfreq13.setProgress((val13/3600));
							perc0.setText((100*(val0/3600)/Tval)+"%");
							perc1.setText((100*(val1/3600)/Tval)+"%");
							perc2.setText((100*(val2/3600)/Tval)+"%");
							perc3.setText((100*(val3/3600)/Tval)+"%");
							perc4.setText((100*(val4/3600)/Tval)+"%");
							perc5.setText((100*(val5/3600)/Tval)+"%");
							perc6.setText((100*(val6/3600)/Tval)+"%");
							perc7.setText((100*(val7/3600)/Tval)+"%");
							perc8.setText((100*(val8/3600)/Tval)+"%");
							perc9.setText((100*(val9/3600)/Tval)+"%");
							perc10.setText((100*(val10/3600)/Tval)+"%");
							perc11.setText((100*(val11/3600)/Tval)+"%");
							perc12.setText((100*(val12/3600)/Tval)+"%");
							perc13.setText((100*(val13/3600)/Tval)+"%");

						}
					});
				}
			},0,1000);
		}

		@Override
		public void onPause() {
			super.onPause();
			t.cancel();
			t.purge();
		}

		@Override
		public void onStop() {
			super.onStop();
			t.cancel();
			t.purge();
		}
		@Override
		public void onResume() {
			super.onResume();
			updater();
		}
		@Override
		public void onDestroy() {
			super.onDestroy();
			t.cancel();
			t.purge();
		}
	}
