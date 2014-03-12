package com.paddy.android.watertracker;

import java.util.Date;
import android.text.format.DateFormat;
import android.text.format.Time;


public class TimeManager {
	public final String TAG = "TTD";
	public int hour;
	public int minute;
	
	public CharSequence currentDate() {
		Date now = new Date();
		CharSequence date = DateFormat.format("EEEE,  MMM d, yyyy", now.getTime());
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
