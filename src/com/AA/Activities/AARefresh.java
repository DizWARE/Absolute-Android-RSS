package com.AA.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/***
 * This activity starts when the user presses "Settings" in the 
 * Context Menu  of the main app
 * 
 * @author Tyler Robinson 
 * 
 * (Everyone else who edit this file should add their name)
 */
public class AARefresh extends Activity {
	SharedPreferences settings;

	//***GUI Member Variables(There will probably be a lot)***

	//***End GUI Member Variables***


	/***
	 * Called when the activity is created and put into memory.
	 * 
	 * This is where all GUI elements should be set up and any
	 * other member variables that is used throughout the class
	 */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		//Usually we would define the layout here...but its possible
		//that we might make a PreferenceActivity instead(which requires
		//a different type of layout, I believe). This will take some research
		//cause I've never used it but sounds like what we may want.

	}
	/***
	 * Called when the activity starts. Not 100% necessary since 
	 * this usually happens after onCreate or onResume, but may be
	 * required for some state handling depending on the situation
	 */ 
	@Override protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	/***
	 * Called when the activity stops running in the foreground.
	 * Should clean up anything that maybe unnecessarily hogging memory
	 * while in the background
	 * 
	 * 
	 */
	@Override protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/***
	 * Called when the activity is cleaned out of memory.
	 * 
	 * Clean up all member variables here
	 */
	@Override protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	/***
	 * Called when another activity takes over the foreground.
	 * Also called when the the screen goes off or when the screen
	 * is rotated. 
	 * 
	 * Save any data that may be floating around at the moment, here
	 */
	@Override protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/***
	 * Called when the activity comes back into the foreground
	 * 
	 * Restore your data here(to give the user a seamless experience)
	 */
	@Override protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
