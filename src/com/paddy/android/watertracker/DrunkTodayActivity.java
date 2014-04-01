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
	public int[] checked = new int[9];
	private int glassesCount = 0;
	private int glassesToDrink = 9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_main);
		setCheckedGlasses();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setCheckedGlasses();
	}
	
	protected void onPause() {
		super.onPause();	
	}
	
	public void weekActivity(View v) {
		Intent i = new Intent(this, WeekActivity.class);
		startActivity(i);
	}
	
	public void setCheckedGlasses() {
		List<Integer> checkedGlasses = GlassCountHelper.getTodaysCheckedGlasses(this);
		Log.i("i get called", ""+checkedGlasses);
		
		if (checkedGlasses.size() == 0) {
			glassesCount = 0;
			glassesToDrink = 9;
			((ToggleButton) findViewById(R.id.one)).setChecked(false);
			((ToggleButton) findViewById(R.id.two)).setChecked(false);
			((ToggleButton) findViewById(R.id.three)).setChecked(false);
			((ToggleButton) findViewById(R.id.four)).setChecked(false);
			((ToggleButton) findViewById(R.id.five)).setChecked(false);
			((ToggleButton) findViewById(R.id.six)).setChecked(false);
			((ToggleButton) findViewById(R.id.seven)).setChecked(false);
			((ToggleButton) findViewById(R.id.eight)).setChecked(false);
			((ToggleButton) findViewById(R.id.nine)).setChecked(false);
			
		} else {
			for (int i = 0; i < checkedGlasses.size(); i++ ) {
				glassesCount ++;
				glassesToDrink --;
				
				switch (checkedGlasses.get(i)) {
				case 1: ((ToggleButton) findViewById(R.id.one)).setChecked(true); 
						checked[0] = 1;
						break;
				case 2: ((ToggleButton) findViewById(R.id.two)).setChecked(true);
						checked[1] = 2;
						break;
				case 3: ((ToggleButton) findViewById(R.id.three)).setChecked(true);
						checked[2] = 3;
						break;
				case 4: ((ToggleButton) findViewById(R.id.four)).setChecked(true);
						checked[3] = 4;
						break;
				case 5: ((ToggleButton) findViewById(R.id.five)).setChecked(true);
						checked[4] = 5;	
						break;
				case 6: ((ToggleButton) findViewById(R.id.six)).setChecked(true);
						checked[5] = 6;
						break;
				case 7: ((ToggleButton) findViewById(R.id.seven)).setChecked(true);
						checked[6] = 7;
						break;
				case 8: ((ToggleButton) findViewById(R.id.eight)).setChecked(true);
						checked[7] = 8;
						break;
				case 9: ((ToggleButton) findViewById(R.id.nine)).setChecked(true);
						checked[8] = 9;
						break;
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
			if (v == findViewById(R.id.one)) {checked[0] = 1; 
			} else if (v == findViewById(R.id.two)) { checked[1] = 2;		
			} else if (v == findViewById(R.id.three)) {checked[2] = 3;
			} else if (v == findViewById(R.id.four)) { checked[3] = 4;
			} else if (v == findViewById(R.id.five)) { checked[4] = 5;	
			} else if (v == findViewById(R.id.six)) { checked[5] = 6;
			} else if (v == findViewById(R.id.seven)) { checked[6] = 7;
			} else if (v == findViewById(R.id.eight)) { checked[7] = 8;
			} else if (v == findViewById(R.id.nine)) { checked[8] = 9;
			}
			
		} else {
			glassesCount --;
			glassesToDrink ++;
			displayTracking(glassesCount);
			if (v == findViewById(R.id.one)) { checked[0] = 0;
			} else if (v == findViewById(R.id.two)) { checked[1] = 0;
			} else if (v == findViewById(R.id.three)) { checked[2] = 0;
			} else if (v == findViewById(R.id.four)) { checked[3] = 0;
			} else if (v == findViewById(R.id.five)) { checked[4] = 0;
			} else if (v == findViewById(R.id.six)) { checked[5] = 0;
			} else if (v == findViewById(R.id.seven)) { checked[6] = 0;
			} else if (v == findViewById(R.id.eight)) { checked[7] = 0;
			} else if (v == findViewById(R.id.nine)) { checked[8] = 0;
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
	
	