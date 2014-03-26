package com.paddy.android.watertracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WeekActivity extends Activity {
	
	public List<String> glassText = new ArrayList<String>();
	public List<Integer> glassNumber = new ArrayList<Integer>();
	
	public Integer[] buttonArray = {
			R.id.one, R.id.two,
			R.id.three, R.id.four,
			R.id.five, R.id.six,
			R.id.seven, R.id.eight,
			R.id.nine
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week);
		
	}
	
	protected void onResume(Bundle savedInstanceState) {
		super.onResume();
		
	}
	
	public void todayActivity(View v) {
		Intent i = new Intent(this, TodayDrunkActivity.class);
		startActivity(i);
	}
	
	public void getData() {
		SharedPreferences sharedPref = getSharedPreferences("TotalGlasses", Context.MODE_PRIVATE);
	
		if (sharedPref != null) {
			
			Map<String, ?> all = sharedPref.getAll();
			
			if (all.size() < 9) {
				int dataSize = all.size();
				int addThese = 9 - dataSize;
				for (int i = 0; i < addThese; i ++) {
//					all.add(Map<"2014.01.01", 0>);
				}
			}
			
			List<Map.Entry<String, ?>> sortedGlasses = new ArrayList<Map.Entry<String, ?>>(all.entrySet());		
			Collections.sort(sortedGlasses, new Comparator<Map.Entry<String, ?>>() {
	
				public int compare(Map.Entry<String, ?> a, Map.Entry<String, ?> b) {
				
					 return a.getKey().compareTo(b.getKey());
					 }
			});
			
			
			
			if (sortedGlasses.size() > 9) {
				int dataSize = sortedGlasses.size();
				int removeThese = dataSize - 9;
				for (int i = 0; i < removeThese; i ++)
					sortedGlasses.remove(i);
			}
			
			String glassesString = new String();
			glassesString = sortedGlasses.toString();
			
			String[] glassesStringSplit = new String[20];
			try {
				glassesStringSplit = glassesString.split(" ");
			} catch (PatternSyntaxException ex) {
				Log.i("there was an error", "on split");
			}
			 
			for (int i = 1; i < glassesStringSplit.length;i+=2) {
				String glassString = glassesStringSplit[i].replace(",","").replace("=", " ").replace("]", "");
				glassText.add(glassString);	
			}
		} else {
			buttonDrawables(0);
		}
		
	}
	
	public void getGlassNumber() {
		String[] glassGetNumber = new String[9];
		String getGlasses = new String();
		getGlasses = glassText.toString();
		glassGetNumber = getGlasses.split(" ");
		
		for (int i = 1; i < glassGetNumber.length;i+=2) {
			String numberString = glassGetNumber[i].replace(",", "").replace("]", "");
			int numberInt = Integer.parseInt(numberString);
			glassNumber.add(numberInt);	
		}		
	}
	
	public void setText() {
		for (int i = 0; i < 9; i++) {
			Button buttonOne = (Button) findViewById(buttonArray[i]);
	        buttonOne.setText(glassText.get(i));
		}
	}
	
	public Integer buttonDrawables(int number) {
		switch(number) {
		case 0: return R.drawable.water;
		case 1: return R.drawable.waterdrunk1;
		case 2: return R.drawable.waterdrunk2;
		case 3: return R.drawable.waterdrunk3;
		case 4: return R.drawable.waterdrunk4;
		case 5: return R.drawable.waterdrunk5;
		case 6: return R.drawable.waterdrunk6;
		case 7: return R.drawable.waterdrunk7;
		case 8: return R.drawable.waterdrunk8;
		case 9: return R.drawable.waterdrunk; 
		}
	 return R.drawable.water;
	}
	
	public void setDrawables() {
		for (int i = 0; i < 9; i++) {
			Button button = (Button) findViewById(buttonArray[i]);	
			int number = glassNumber.get(i);
			button.setBackgroundResource(buttonDrawables(number));
		}
	}
}
