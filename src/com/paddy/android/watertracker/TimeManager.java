package com.paddy.android.watertracker;

import java.util.Date;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;


public class TimeManager {
	public final String TAG = "TTD";
	public int hour;
	public int minute;
	
	public CharSequence currentDate() {
		Date now = new Date();
		CharSequence date = DateFormat.format("yyyy.MM.d EEEE", now.getTime());
		
		Log.i("the date format is", "this" + date);
		return date;
	}
	
	public int currentTime() {
		Time now = new Time();
		now.setToNow();
		hour = now.hour;
		return hour;
	}
	
	public int timeTillEOD() {
		int hoursToEOD = (23 - currentTime());
		return hoursToEOD;
	}
}
