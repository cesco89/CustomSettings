package com.cesco.customsettings;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class layWidget extends AppWidgetProvider {

	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		// Create an Intent to launch ExampleActivity
		Intent intent = new Intent(context, rebootDialog.class);
		intent.setAction("ACTION_WIDGET_RECEIVER1");
		Intent intent1 = new Intent(context, recoveryDialog.class);
		intent.setAction("ACTION_WIDGET_RECEIVER2");
		Intent intent2 = new Intent(context, bootDialog.class);
		intent.setAction("ACTION_WIDGET_RECEIVER3");
		Intent intent3 = new Intent(context, HBdialog.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, 0);
		PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
		PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0, intent3, 0);

		// Get the layout for the App Widget and attach an on-click listener
		// to the button
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.laywidget);
		views.setOnClickPendingIntent(R.id.button1, pendingIntent);
		views.setOnClickPendingIntent(R.id.button2, pendingIntent1);
		views.setOnClickPendingIntent(R.id.button3, pendingIntent2);
		views.setOnClickPendingIntent(R.id.button4, pendingIntent3);

		// Tell the AppWidgetManager to perform an update on the current app widget
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}
}

