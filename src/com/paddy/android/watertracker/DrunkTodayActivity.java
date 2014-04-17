package com.paddy.android.watertracker;

import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class DrunkTodayActivity extends Activity{	
	public Integer[] checked = new Integer[9];
	private int glassesCount = 0;
	private int glassesToDrink = 9;
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
		setContentView(R.layout.activity_main);
		setCheckedGlasses();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setAlarm();
	}
	
	protected void onPause() {
		super.onPause();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		for (int i = 0; i < checked.length;i++) {
			Log.i("checked onDestroy", "" +checked[i]);
		}
	}
	
	public void weekActivity(View v) {
		Intent i = new Intent(this, WeekActivity.class);
		startActivity(i);
	}
	
	public void setCheckedGlasses() {
		List<Integer> checkedGlasses = GlassCountHelper.getTodaysCheckedGlasses(this);
		Log.i("checkedGlasses array", "" +checkedGlasses);
		
		for (int k = 0; k < buttonArray.length; k++) {
			((ToggleButton) findViewById(buttonArray[k])).setChecked(false);
			for (int i = 0; i < checkedGlasses.size(); i++) {
				if (checkedGlasses.get(i) == k) {
					glassesCount++;
					glassesToDrink--;
					checked[i] = k;
					((ToggleButton) findViewById(buttonArray[k])).setChecked(true);
				} 
			}
		}
	}
	
	public void onToggleClicked(View v) {
		boolean on = ((ToggleButton) v).isChecked();
		
		if (on) {
			glassesCount ++;
			glassesToDrink --;
			displayTracking(glassesCount);
			for (int k = 0; k < buttonArray.length; k++) { 
				if (v == findViewById(buttonArray[k])) {
					checked[k] = k;
				}
			}
			
		} else {
			glassesCount --;
			glassesToDrink ++;
			displayTracking(glassesCount);
			for (int k = 0; k < buttonArray.length; k++) { 
				if (v == findViewById(buttonArray[k])) { 
					checked[k] = 0;
				}
			}	
		}
		GlassCountHelper.setTodaysCheckedGlasses(this, Arrays.asList(checked[0], checked[1], checked[2], 
				 checked[3], checked[4], checked[5], 
				 checked[6], checked[7], checked[8]));
	}	
	
	public void displayTracking (int glassesCount) {
		if (glassesCount == 1) {
            Toast.makeText(DrunkTodayActivity.this,  "Way to go! " + glassesCount + " Glass of water drunk today. " + glassesToDrink + " Left today. You should drink a glass of water every hour to reach the target",  Toast.LENGTH_SHORT).show();  
        
       } else if (glassesCount > 1 && glassesCount < 9){
    	   Toast.makeText(DrunkTodayActivity.this,  "Way to go! " + glassesCount + " Glasses of water drunk today. "+ glassesToDrink + " Left today. You should drink a glass of water every hour to reach the target",  Toast.LENGTH_SHORT).show();  
       } else if (glassesCount == 0 ) {
    	   Toast.makeText(DrunkTodayActivity.this,  "Better get drinking, you're on 0." ,  Toast.LENGTH_SHORT).show();
       } else if (glassesCount == 9){
    	   Toast.makeText(DrunkTodayActivity.this,  "Way to go! You have reached the target!" ,  Toast.LENGTH_SHORT).show();
       }
	}
	
	public void setAlarm() {
		int hours = 1;
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent i = new Intent(this, HandlerNotifications.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		am.cancel(pi);
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
				SystemClock.elapsedRealtime() + hours*60*60*1000, 
				hours*60*60*1000, pi);
	}
		
	
}
	
	