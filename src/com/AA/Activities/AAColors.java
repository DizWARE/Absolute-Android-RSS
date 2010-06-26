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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
public class AAColors extends ListActivity {
	
	static final int COLOR_UNREAD = 0;
	static final int COLOR_READ = 1;
	static final int COLOR_TEXT = 2;
	
	SharedPreferences colors;

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
				showDialog(position, new Bundle());
				
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
	 * 
	 * Restore your data here(to give the user a seamless experience)
	 */
	@Override protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	/**
	 * 
	 */
	@Override public Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog = null;
		switch(id) {
		case COLOR_UNREAD:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                finish();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			dialog = builder.create();
			break;
		case COLOR_READ:
			dialog = new Dialog(getApplicationContext());
			dialog.setContentView(R.layout.color_picker);
			dialog.setTitle("Pick a color");
			
			TextView text = (TextView) dialog.findViewById(R.id.CP_text);
			text.setText("Hello, this is a custom dialog!");
			ImageView image = (ImageView) dialog.findViewById(R.id.CP_image);
			image.setImageResource(R.drawable.aa_logosmall);
			break;
		case COLOR_TEXT:
			dialog = new ColorPickerDialog(getApplicationContext(), null, 0);
			break;
		default:
			dialog = null;
		}
		return dialog;
	}
	
}
