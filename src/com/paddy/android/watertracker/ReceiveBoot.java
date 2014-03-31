package com.paddy.android.watertracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class ReceiveBoot {
	
	public void onReceive(Context context, Intent intent) {
		
		int hours = 1;
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, HandlerNotifications.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		am.cancel(pi);
		
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
				SystemClock.elapsedRealtime() + hours*60*30*1000, 
				hours*60*30*1000, pi);
			
		
	}
}
