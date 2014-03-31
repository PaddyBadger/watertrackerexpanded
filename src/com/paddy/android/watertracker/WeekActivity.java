package com.paddy.android.watertracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WeekActivity extends Activity {
	
	private List<String> glassText = new ArrayList<String>();
	private List<Integer> glassNumber = new ArrayList<Integer>();
	String[] glassesStringSplit = new String[40];
	
	private Integer[] buttonArray = {
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
		getData();
		getText();
		getNumbers();
		setText();
		setDrawables();
	}
	
	protected void onResume(Bundle savedInstanceState) {
		super.onResume();	
	}
	
	public void todayActivity(View v) {
		Intent i = new Intent(this, TodayDrunkActivity.class);
		startActivity(i);
	}
	
	private String[] getData() {
		Map<Date,Integer> sortedMap = new TreeMap<Date, Integer>(GlassCountHelper.getLastWeeksCheckedGlasses(this));
		
		String glassesString = new String();
		glassesString = sortedMap.toString();
		
		try {
			glassesStringSplit = glassesString.split("\\W");
		} catch (PatternSyntaxException ex) {
			Log.i("there was an error", "on split");
		}	 	
	return glassesStringSplit;
	}
	
	private void getText() {
		for (int i = 1; i < glassesStringSplit.length;i+=10) {
			String glassString = glassesStringSplit[i];
			glassText.add(glassString);		
		}
	}
	
	private void setText() {
		for (int i = 0; i < 9; i++) {
			Button button = (Button) findViewById(buttonArray[i]);
	        button.setText(glassText.get(i));  
		}
	}
	
	private void getNumbers() {
		for (int k = 9; k < glassesStringSplit.length; k+=10) {
			String glassNumbers = glassesStringSplit[k].replace(",","").replace(" ","");
			int numberGlass = Integer.parseInt(glassNumbers);
			glassNumber.add(numberGlass);
		}
	}
	
	private Integer buttonDrawables(int number) {
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
	
	private void setDrawables() {
		for (int i = 0; i < 9; i++) {
			Button button = (Button) findViewById(buttonArray[i]);	
			int number = glassNumber.get(i);
			button.setBackgroundResource(buttonDrawables(number));
		}
	}
}
