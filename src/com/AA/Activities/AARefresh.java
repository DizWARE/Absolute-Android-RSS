/*Copyright 2010 University Of Utah Android Development Group
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.AA.Activities;

import com.AA.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
		setContentView(R.layout.main);

		//Remove(hypothetically) that refresh button from the layout
		this.findViewById(R.id.ib_refresh).setVisibility(View.INVISIBLE);
		((TextView)this.findViewById(android.R.id.empty)).setText("");

		//Sets custom font for app title.
		TextView tv=(TextView)findViewById(R.id.AATitle);
		Typeface face=Typeface.createFromAsset(getAssets(), "fonts/WREXHAM_.TTF");
		tv.setTypeface(face);

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
		super.onStop();
	}

	/***
	 * Called when the activity is cleaned out of memory.
	 * 
	 * Clean up all member variables here
	 */
	@Override protected void onDestroy() {
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
		super.onPause();
	}

	/***
	 * Called when the activity comes back into the foreground
	 * 
	 * Restore your data here(to give the user a seamless experience)
	 */
	@Override protected void onResume() {
		super.onResume();
	}
}
