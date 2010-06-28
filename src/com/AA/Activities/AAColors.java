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
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	private static final String READ_COLOR_KEY = "read_color";
	private static final String UNREAD_COLOR_KEY = "unread_color_key";
	private SharedPreferences colors;

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
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDialog(position);
//				new ColorPickerDialog(AAColors.this, AAColors.this, 0).show();
//				Toast.makeText(getApplicationContext(), ((TextView)view).getText(), Toast.LENGTH_SHORT).show();
			}
			
		});

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
	 * d
	 * Restore your data here(to give the user a seamless experience)
	 */
	@Override protected void onResume() {
		// TODO Auto-generated method stub
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
			color = PreferenceManager.getDefaultSharedPreferences(AAColors.this)
				.getInt(UNREAD_COLOR_KEY, Color.WHITE);
			dialog = new ColorPickerDialog(this, this, color);
			break;
		case COLOR_READ:
			color = PreferenceManager.getDefaultSharedPreferences(AAColors.this)
				.getInt(READ_COLOR_KEY, Color.WHITE);
			dialog = new ColorPickerDialog(this, this, color);
			break;	
		case COLOR_TEXT:
			color = PreferenceManager.getDefaultSharedPreferences(AAColors.this)
				.getInt(READ_COLOR_KEY, Color.WHITE);
			dialog = new ColorPickerDialog(this, this, color);
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
		
	@Override
	public void colorChanged(int color) {
		PreferenceManager.getDefaultSharedPreferences(this).edit()
			.putInt(READ_COLOR_KEY, color).commit();
	}
	
}
