package com.paddy.android.watertracker;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class HandlerNotifications extends IntentService {
	
	public HandlerNotifications() {
		super("HandlerNotifications");
	}

	public NotificationCompat.Builder createNotification() {
		
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
	
			notificationBuilder.setSmallIcon(R.drawable.droplet);
			notificationBuilder.setContentTitle("Water Tracker");
			notificationBuilder.setContentText("Time to drink!");
			notificationBuilder.setAutoCancel(true);
	
			Intent intent = new Intent(this, DrunkTodayActivity.class);
	
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(DrunkTodayActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
	
		resultPendingIntent.cancel();
	
		notificationBuilder.setContentIntent(resultPendingIntent);
	
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, notificationBuilder.build());
		return notificationBuilder;
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		createNotification();	
		
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(ReceiveBoot.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
		sendBroadcast(broadcastIntent);
		
	}
}
