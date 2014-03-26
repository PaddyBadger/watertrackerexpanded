package com.paddy.android.watertracker;

import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class TodayDrunkActivity extends Activity{	
	public int[] checked = new int[9];
	private int glassesCount = 0;
	private int glassesToDrink = 9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_main);		
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
		
		Log.i("GlassesHelper.get ", "" + GlassCountHelper.getTodaysCheckedGlasses(this));
		
		for (int i = 0; i < checkedGlasses.size(); i++ ) {
			glassesCount ++;
			glassesToDrink --;;
			switch (checkedGlasses.get(i)) {
			case 1: ((ToggleButton) findViewById(R.id.one)).setChecked(true); 
			break;
			case 2: ((ToggleButton) findViewById(R.id.two)).setChecked(true);
			break;
			case 3: ((ToggleButton) findViewById(R.id.three)).setChecked(true);
			break;
			case 4: ((ToggleButton) findViewById(R.id.four)).setChecked(true);
			break;
			case 5: ((ToggleButton) findViewById(R.id.five)).setChecked(true);
			break;
			case 6: ((ToggleButton) findViewById(R.id.six)).setChecked(true);
			break;
			case 7: ((ToggleButton) findViewById(R.id.seven)).setChecked(true);
			break;
			case 8: ((ToggleButton) findViewById(R.id.eight)).setChecked(true);
			break;
			case 9: ((ToggleButton) findViewById(R.id.nine)).setChecked(true);
			break;
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
            Toast.makeText(TodayDrunkActivity.this,  "Way to go! " + glassesCount + " Glass of water drunk today. " + glassesToDrink + " Left today. You should drink a glass of water every hour to reach the target",  Toast.LENGTH_SHORT).show();  
        
       } else if (glassesCount > 1 && glassesCount < 9){
    	   Toast.makeText(TodayDrunkActivity.this,  "Way to go! " + glassesCount + " Glasses of water drunk today. "+ glassesToDrink + " Left today. You should drink a glass of water every hour to reach the target",  Toast.LENGTH_SHORT).show();  
       } else if (glassesCount == 0 ) {
    	   Toast.makeText(TodayDrunkActivity.this,  "Better get drinking, you're on 0." ,  Toast.LENGTH_SHORT).show();
       } else if (glassesCount == 9){
    	   Toast.makeText(TodayDrunkActivity.this,  "Way to go! You have reached the target!" ,  Toast.LENGTH_SHORT).show();
       }
	}
	
}
	
	