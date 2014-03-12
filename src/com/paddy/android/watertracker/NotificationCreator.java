package com.paddy.android.watertracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class NotificationCreator extends Service {
	
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		       createNotification();
		       return START_NOT_STICKY; 
		   }

	public NotificationCompat.Builder createNotification() {
		
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
		 
			notificationBuilder.setSmallIcon(R.drawable.water);
			notificationBuilder.setContentTitle("Water Tracker");
			notificationBuilder.setContentText("Time to drink!");
			notificationBuilder.setAutoCancel(true);
			
			Intent intent = new Intent(this, TodayActivity.class);
			Log.i("createNotification", "I get to line 5");
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(TodayActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		resultPendingIntent.cancel();
		
		notificationBuilder.setContentIntent(resultPendingIntent);
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, notificationBuilder.build());
		Log.i("createNotification", "I get to line 8");
		return notificationBuilder;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}	
}
