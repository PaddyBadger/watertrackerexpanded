package com.paddy.android.watertracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class WeekActivity extends Activity {
	public String day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week);
		
		getData();
		
	}
	
	public void todayActivity(View v) {
		Intent i = new Intent(this, TodayActivity.class);
		startActivity(i);
	}
	
	public void getData() {
		SharedPreferences sharedPref = getSharedPreferences("TotalGlasses", Context.MODE_PRIVATE);
		Map<String, ?> all = sharedPref.getAll();
		
		List<Map.Entry<String, ?>> sortedGlasses = new ArrayList<Map.Entry<String, ?>>(all.entrySet());
				
		Collections.sort(sortedGlasses, new Comparator<Map.Entry<String, ?>>() {

			public int compare(Map.Entry<String, ?> a, Map.Entry<String, ?> b) {
				 return a.getKey().compareTo(b.getKey());
			}
		});
		Log.i("sortedGlasses", "is" +sortedGlasses);
		
	}
}
