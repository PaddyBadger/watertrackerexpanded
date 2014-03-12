package com.paddy.android.watertracker;

import com.paddy.android.watertracker.NavigationFragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.TableRow;

public class TodayActivity extends Activity implements OnClickListener{
	public static final String TAG = "MA";
	public int glassesCount;
	public int glassesToDrink = 9;
	public int DEFAULT = 0;
	public ToggleButton[] buttons;
	public TimeManager timeNow = new TimeManager();
	public ResetGlasses reset = new ResetGlasses();
	public Button week;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		NavigationFragment navigation = new NavigationFragment();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.navigation1, navigation, "NavigationFragment");
		transaction.commit();
	
	}
	
	protected void onResume() {
		super.onResume();
		setContentView(R.layout.activity_main);
		
		resetGlasses();
		
		setGlasses();
		setAlarm();	
	}
	
	protected void onPause() {
		super.onPause();
		
		int glassesToday = glassesCount;
		CharSequence dateToday = timeNow.currentDate();
		String today = dateToday.toString();
		
		SharedPreferences sharedPref = getSharedPreferences("TotalGlasses", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt(today, glassesToday);
		editor.commit();
		
		glassesCount = 0;
	}
	
	@Override
	public void onClick(View v) {
		Intent i = new Intent(TodayActivity.this, WeekActivity.class);
		TodayActivity.this.startActivity(i);
		
	}
	
	public void onToggleClicked(View v) {
			
			boolean on = ((ToggleButton) v).isChecked();
				if (on) {
					recordGlassesPlus(v);
					
				} else {
					recordGlassesMinus(v);
				}
		}
	
	public void setGlasses() {
		SharedPreferences sharedPref = getSharedPreferences("GlassesData", Context.MODE_PRIVATE);
		TableLayout tl = (TableLayout)findViewById(R.id.gridView);
		int numRows = tl.getChildCount();
		int q = 0;
		
		buttons = new ToggleButton[9];
		
		for (int j=0; j < numRows; j++) {
			int numButtonsInThisRow = ((TableRow) tl.getChildAt(j)).getChildCount();
			
			for (int k=0; k < numButtonsInThisRow; k++) {
				
				buttons[q] = (((ToggleButton)((TableRow) tl.getChildAt(j)).getChildAt(k)));
				q++;	
			}
		}
		for (int i=0; i < q; i++) {
			if (sharedPref.getBoolean(Integer.toString(buttons[i].getId()), false)) {
				buttons[i].setChecked(true);	
				glassesCount += 1;
				glassesToDrink = glassesToDrink - glassesCount;
				
			}
		}
	}
		
	public int rateOfDrinking() {
		int timeToGo = timeNow.timeTillEOD();
		if (glassesToDrink > 0) {
			int hoursRounded = (timeToGo / glassesToDrink);
			
			if (hoursRounded < 1) {
				hoursRounded = 1;
			}
			SharedPreferences sharedPref = getSharedPreferences("SpeedData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt("interval", hoursRounded);
			editor.commit();
			return hoursRounded;
			
		}
		return 0;
	}
	
	public void displayTracking (int glassesCount) {
		if (glassesCount == 1) {
            Toast.makeText(TodayActivity.this,  "Way to go! " + glassesCount + " Glass of water drunk today. " + glassesToDrink + " Left today. You should drink a glass of water every " 
             + rateOfDrinking() + " hours to reach the target",  Toast.LENGTH_SHORT).show();  
        
       } else if (glassesCount > 1 && glassesCount < 9){
    	   Toast.makeText(TodayActivity.this,  "Way to go! " + glassesCount + " Glasses of water drunk today. "+ glassesToDrink + " Left today. You should drink a glass of water every " 
             + rateOfDrinking() + " hours to reach the target",  Toast.LENGTH_SHORT).show();  
       } else if (glassesCount == 0 ) {
    	   Toast.makeText(TodayActivity.this,  "Better get drinking, you're on 0." ,  Toast.LENGTH_SHORT).show();
       } else {
    	   Toast.makeText(TodayActivity.this,  "Way to go! You have reached the target!" ,  Toast.LENGTH_SHORT).show();
       }
	}
	
	public void recordGlassesPlus(View v) {
		if (glassesCount < 10 && glassesCount >= 0) {
			glassesCount += 1;	
			glassesToDrink = (9 - glassesCount);
			SharedPreferences sharedPref = getSharedPreferences("GlassesData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(Integer.toString(v.getId()), true);
			editor.commit();
			
			displayTracking(glassesCount);
		}
	}
	
	public void recordGlassesMinus(View v) {
		if (glassesCount < 10 && glassesCount >= 0) {
			glassesCount -= 1;	
			glassesToDrink = (9 - glassesCount);
			SharedPreferences sharedPref = getSharedPreferences("GlassesData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(Integer.toString(v.getId()), false);
			editor.commit();
			
			displayTracking(glassesCount);
		}
	}
	
	public void setAlarm() {
		SharedPreferences sharedPref = getSharedPreferences("TotalGlasses", Context.MODE_PRIVATE);
		int hours = sharedPref.getInt("interval", 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent i = new Intent(this, NotificationHandler.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		am.cancel(pi);
		
		if (hours > 0) {
			am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
					SystemClock.elapsedRealtime() + hours*60*60*1000, 
					hours*60*60*1000, pi);
		}
	}
	
	public void resetGlasses() {
		CharSequence dateToday = timeNow.currentDate();
		String today = dateToday.toString();
		
		SharedPreferences sharedPref = getSharedPreferences("TotalGlasses", Context.MODE_PRIVATE);
		
		if (!sharedPref.contains(today)) {
			zeroGlasses();
		} else {
			
		}
	}
		
	public void zeroGlasses() {
			SharedPreferences sharedPref = getSharedPreferences("GlassesData", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.clear();
			editor.commit();
	}
}
