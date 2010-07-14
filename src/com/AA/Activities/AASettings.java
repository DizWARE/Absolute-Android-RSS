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
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.AA.R;
import com.AA.Other.ColorPickerDialog;
import com.AA.Other.DisplayTypes;

/***
 * This activity starts when the user presses "Settings" in the 
 * Context Menu  of the main app
 * 
 * Has Refresh options and color options for our article boxes
 */
public class AASettings extends ListActivity implements ColorPickerDialog.OnColorChangedListener {

	SharedPreferences settings;
	private String currentKey = "";

	//***GUI Member Variables(There will probably be a lot)***
	SeekBar sb_freq;
	TextView tv_freq;

	ColorAdapter adapter;
	//***End GUI Member Variables***

	/***
	 * Called when the activity is created and put into memory.
	 * 
	 * This is where all GUI elements should be set up and any
	 * other member variables that is used throughout the class
	 */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_main);

		//Sets custom font for app title.
		TextView tv=(TextView)findViewById(R.id.AATitle);
		Typeface face=Typeface.createFromAsset(getAssets(), "fonts/WREXHAM_.TTF");
		tv.setTypeface(face);

		settings = this.getSharedPreferences("settings", 0);

		adapter = new ColorAdapter(this);
		this.setListAdapter(adapter);

		//Sets the click listener so that we can do things when user selects an
			//option
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDialog(position);
			}
		});

		//Set up the functionality for the slider bar
		sb_freq = (SeekBar)this.findViewById(R.id.sb_freq);
		tv_freq = (TextView)this.findViewById(R.id.tv_freq);

		sb_freq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {			
			@Override public void onStopTrackingTouch(SeekBar seekBar) {}
			@Override public void onStartTrackingTouch(SeekBar seekBar) {}

			@Override public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tv_freq.setText((progress + 1) + " hour(s)");
			}
		});

		sb_freq.setProgress((int)settings.getLong("freq", 2)-1);
	}

	/***
	 * Stores the currently selected frequency when user leaves the activity
	 */
	@Override protected void onPause() {
		Editor edit = settings.edit();
		edit.putLong("freq", sb_freq.getProgress()+1);
		edit.commit();
		super.onPause();
	}

	/***
	 * Gets the default color based on the position in the list
	 * @param position
	 * @return
	 */
	private int getDefaultColor(int position) {
		if(position == 0 || position ==3)
			return Color.WHITE;	

		return Color.BLACK;	
	}

	/***
	 * Handles the deprecated version of onCreate for pre-2.2 versions of Android.
	 *
	 * Simply returns the dialog that onCreateDialog(int,Bundle) would give with a 
	 * default Bundle.
	 */
	@Override protected Dialog onCreateDialog(int id) {
		return onCreateDialog(id, new Bundle());
	}

	/**
	 * Creates a color chooser dialog based on the DisplayType that was selected
	 */
	@Override public Dialog onCreateDialog(int id, Bundle args) {
		DisplayTypes[] types = DisplayTypes.values();
		int color = settings.getInt(types[id].toString(),this.getDefaultColor(id));
		Dialog dialog = new ColorPickerDialog(this, this, color);
		return dialog;
	}

	/***
	 * For 1.x devices. Launcher for 2.0 default calls
	 */
	@Override protected void onPrepareDialog(int id, Dialog dialog) {
		onPrepareDialog(id,dialog,new Bundle());
	}

	/***
	 * Grabs the current key for the dialog that we are launching(so that we can
	 * save the data) and stores it.
	 */
	@Override protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		DisplayTypes[] types = DisplayTypes.values();
		currentKey = types[id].toString();
	}

	/***
	 * Saves the selected color into our settings preferences
	 */
	@Override public void colorChanged(int color) {
		Editor edit = settings.edit();
		edit.putInt(currentKey, color);
		edit.commit();
		
		adapter.notifyDataSetChanged();
	}

	/***
	 * Adapter that loads up our color options
	 */
	private class ColorAdapter extends ArrayAdapter < String > {
		/***
		 * Sets up our adapter
		 * 
		 * @param context - Context that we will be working with
		 */
		public ColorAdapter(Context context) {
			super(context, R.layout.settings_main, R.id.tv_colorType);

			//Creates a list of items we want to show up in the list
			this.add("Unread Article Background");
			this.add("Read Article Background");
			this.add("Unread Article Text Color");
			this.add("Read Article Text Color");
		}

		/***
		 * Creates and returns the current view in the list
		 */
		@Override public View getView(int position, View convertView, ViewGroup parent) {
			//Creates a layout inflater and our settings provider
			LayoutInflater inflater =
				AASettings.this.getLayoutInflater();
			SharedPreferences settings = AASettings.this.settings;

			View row;

			//Parses the layout we want into a View, so that we can access each
			//individual piece if we haven't already(in which we just use convertView)
			if (convertView == null)
				row = inflater.inflate(R.layout.color_row, null);
			else
				row = convertView;

			//Gets the GUI items
			TextView tv_colorType = (TextView)row.findViewById(R.id.tv_colorType);
			ImageView iv_colorDisplay = (ImageView)row.findViewById(R.id.iv_colorDiplay);

			//Gets our enum values so that we have instant access to the settings names
			DisplayTypes[] displayTypes = DisplayTypes.values();
			
			ColorDrawable color = new ColorDrawable(settings.getInt(displayTypes[position].toString(), 
					getDefaultColor(position)));
			iv_colorDisplay.setBackgroundResource(R.drawable.topbar);
			iv_colorDisplay.setImageDrawable(color);

			tv_colorType.setText(this.getItem(position));

			return row;
		}		
	}
}
