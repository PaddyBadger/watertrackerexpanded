package com.paddy.android.watertracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiveBoot extends BroadcastReceiver {
	public static String ACTION_RESP = "Action processed";
	
	public void onReceive(Context context, Intent intent) {
		
		int hours = 1;
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, HandlerNotifications.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		am.cancel(pi);
		
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
				hours*60*60*1000, 
				hours*60*60*1000, pi);
			
		
	}
}
