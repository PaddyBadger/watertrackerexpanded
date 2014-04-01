package com.paddy.android.watertracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.DateFormat;

public final class GlassCountHelper {
	private static final String SHARED_PREFS = "GlassesCountPrefs";
	private static final int NUM_GLASSES = 10;
	private static final long MILLIS_IN_DAY = 1000*60*60*24;
	private GlassCountHelper() {}
	
	public static List<Integer> getTodaysCheckedGlasses(Context context) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		
		List<Integer> checkedIndices = new ArrayList<Integer>();
		
		Date today = new Date();
			for (int i = 1; i < NUM_GLASSES; i++) {
				if (sharedPreferences.getBoolean(getKey(today, i), false)) {
					checkedIndices.add(i);
				}
		}	
		return checkedIndices;
	}
	
	public static Map<Date, Integer> getLastWeeksCheckedGlasses(Context context) {
		return getRecentGlasses(context, 9);
	}
	
	private static Map<Date, Integer> getRecentGlasses(Context context, int numberOfDays) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
	
		long todayMillis = (new Date()).getTime();
		List<Date> datesToCheck = new ArrayList<Date>();
		for (int i = 0; i < numberOfDays; i++) {
			datesToCheck.add(new Date(todayMillis - MILLIS_IN_DAY * i));
		}
		
		Map<Date, Integer> recentGlasses = new HashMap<Date, Integer>();
		for (Date date : datesToCheck) {
			int glassCount = 0;
			for (int i = 0; i < NUM_GLASSES; i++) {
				if (sharedPreferences.getBoolean(getKey(date, i), false)) {
					glassCount += 1;
				}
			}
			recentGlasses.put(date, glassCount);
		}
		return recentGlasses;
	}
	
	public static void setTodaysCheckedGlasses(Context context, Collection<Integer> checkedIndices) {
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		
		Date today = new Date();
		for (int i = 0; i < NUM_GLASSES; i++) {
			boolean isChecked = checkedIndices.contains(i);
			editor.putBoolean(getKey(today, i), isChecked);
		}
		editor.commit();
	}
	
	private static String getKey(Date date, int index) {
		CharSequence datePortion = DateFormat.format("yyyy-MM-dd", date);
		
		return String.format(Locale.US, "%s-%02d", datePortion.toString(), index);
	}
	
	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
	}
}
