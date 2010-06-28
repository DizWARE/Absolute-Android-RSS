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

import android.app.Dialog;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.AA.R;
import com.AA.Other.ColorPickerDialog;


/***
 * This activity starts when the user presses "Settings" in the 
 * Context Menu  of the main app
 * 
 */
public class AAColors extends ListActivity implements ColorPickerDialog.OnColorChangedListener  {
	
	private static final int COLOR_UNREAD = 0;
	private static final int COLOR_READ = 1;
	private static final int COLOR_TEXT = 2;
	
//	private static final String BRIGHTNESS_PREFERENCE_KEY = "brightness";
//	private static final String COLOR_PREFERENCE_KEY = "color";
	private static final String READ_COLOR_KEY = "colorRead";
	private static final String UNREAD_COLOR_KEY = "colorUnread";
	private static final String FONT_COLOR_KEY = "colorFont";
	
	private SharedPreferences settings;
	private String currentKey = "";

	//***GUI Member Variables(There will probably be a lot)***

	//***End GUI Member Variables***

	ArrayAdapter<String> adapter;
	/***
	 * Called when the activity is created and put into memory.
	 * 
	 * This is where all GUI elements should be set up and any
	 * other member variables that is used throughout the class
	 */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.getResources().getStringArray(R.array.ColorOptions));
		this.setListAdapter(adapter);
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		settings = this.getSharedPreferences("settings", 0);

		//Sets custom font for app title.
		TextView tv=(TextView)findViewById(R.id.AATitle);
		Typeface face=Typeface.createFromAsset(getAssets(), "fonts/WREXHAM_.TTF");
		tv.setTypeface(face);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDialog(position);
			}
			
		});

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
	 * d
	 * Restore your data here(to give the user a seamless experience)
	 */
	@Override protected void onResume() {
		super.onResume();
	}
	
	/***
	 * Handles the deprecated version of onCreate for pre-2.2 versions of Android.
	 *
	 * Simply returns the dialog that onCreateDialog(int,Bundle) would give with a 
	 * default Bundle.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		return onCreateDialog(id, new Bundle());
	}

	/**
	 * 
	 */
	@Override public Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog = null;
		int color;
		switch(id) {
		case COLOR_UNREAD:
			color = settings.getInt("colorUnread", Color.BLACK);
			dialog = new ColorPickerDialog(this, this, color);
			currentKey = UNREAD_COLOR_KEY;
			break;
		case COLOR_READ:
			color = settings.getInt("colorRead", Color.WHITE);
			dialog = new ColorPickerDialog(this, this, color);
			currentKey = READ_COLOR_KEY;
			break;	
		case COLOR_TEXT:
			color = settings.getInt("colorFont", Color.BLUE);
			dialog = new ColorPickerDialog(this, this, color);
			currentKey = FONT_COLOR_KEY;
			break;
		default:
			dialog = null;
		}
		return dialog;
	}

	/***
	 * Saves the selected color into our settings preferences
	 */
	@Override public void colorChanged(int color) {
		Editor edit = settings.edit();
		edit.putInt(currentKey, color);
		edit.commit();
	}
	
}
