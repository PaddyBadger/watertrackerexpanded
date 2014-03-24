package com.paddy.android.watertracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

public class DrinkBootReceiver {
	public static final String TAG = "BR";
	
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sharedPref = context.getSharedPreferences("GlassesData", Context.MODE_PRIVATE);
		int hours = sharedPref.getInt("interval", 1);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, DrinkNotificationHandler.class);
		PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
		am.cancel(pi);
		Log.i(TAG, "Alarm called");
		if (hours > 0) {
			am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
					SystemClock.elapsedRealtime() + hours*60*30*1000, 
					hours*60*30*1000, pi);
			Log.i(TAG, "Alarm called in if");
		}
	}
}
