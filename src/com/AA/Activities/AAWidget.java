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

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.AA.R;
import com.AA.Recievers.AAWidgetProvider;

/***
 * This class is the Widget Settings dialog that pops up when a user drops
 * one of our widgets down on to their homescreen
 */
public class AAWidget extends Activity {
	SharedPreferences settings;

	/*Variables for the GUI*/
	Button btn_ok;
	SeekBar sb_freq;
	TextView tv_freq;
	RadioGroup rg_layoutPicker;
	RadioButton rb_left;
	RadioButton rb_center;

	/***
	 * Creates the activity; This themes up our activity to look like a dialog
	 * Also registers some UI action listeners to the UI elements
	 */
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //Sets the content view, applies a title to the window, and background
		this.setContentView(R.layout.widget_settings);
		this.setTitle("Widget Settings");
        getWindow().setBackgroundDrawableResource(R.drawable.widget_dialog2);

        //Due to some weird changes that happen to the activity when a Bg image is applied;
        //this was required to make the window look like a dialog box again
        Display d = getWindow().getWindowManager().getDefaultDisplay();
        getWindow().setLayout(d.getWidth()*15/16, LayoutParams.WRAP_CONTENT);

        //Tells the system that if the user leaves this dialog without pressing OK, 
        //to consider it canceled
		this.setResult(RESULT_CANCELED);

		settings = this.getSharedPreferences("settings", 0);

		/**UI Junk**/
        btn_ok = (Button)this.findViewById(R.id.btn_ok);
        sb_freq = (SeekBar)this.findViewById(R.id.sb_freq);
        tv_freq = (TextView)this.findViewById(R.id.tv_freq);
        rg_layoutPicker = (RadioGroup)this.findViewById(R.id.rg_layoutPicker);
        rb_left = (RadioButton)this.findViewById(R.id.rb_left);
        rb_center = (RadioButton)this.findViewById(R.id.rb_center);
        /**End of UI Junk**/

        //Applies a listener to the OK button, so that we can handle a accept result
        btn_ok.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				acceptChanges();
			}
		});

        //Applies a listener to handle when the slider bar changes
        sb_freq.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override public void onStopTrackingTouch(SeekBar seekBar) {/**Unused method**/}
			@Override public void onStartTrackingTouch(SeekBar seekBar) {/**Unused method**/}

			/***
			 * When the user changes the position of the slider bar; display 
			 * the current progress in the textview below
			 */
			@Override public void onProgressChanged(SeekBar seekBar,
					int progress, boolean fromUser) {
				tv_freq.setText((progress + 1) + " hour/s");
			}
		});

        //Sets the fequency progress bar to equal the users last progress setting
        //(or 2 if they haven't set it before)
        sb_freq.setProgress((int)settings.getLong("freq", 2)-1);
	}

	/***
	 * Saves all the data that is gathered from the setting dialog
	 */
	private void acceptChanges() {
		Bundle extras = getIntent().getExtras();
		int appWidgetId = 0;
		Editor edit = settings.edit();

		//If our bundle doesn't exist(for whatever reason); notify the user and exit
		if (extras == null) {
			Toast.makeText(this, "Uh oh. Something weird just happened\n" +
					"Try again", Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}

		//Gets the widget id from the calling Intent
	    appWidgetId = extras.getInt(
	            AppWidgetManager.EXTRA_APPWIDGET_ID,
	            AppWidgetManager.INVALID_APPWIDGET_ID);

	    //If the center layout is checked than save the id(otherwise we default to the left-aligned layout)
		if(rb_center.isChecked())
			edit.putInt("" + appWidgetId, R.drawable.widget_layout_top_centered);

		//Add to the widget count and save the update frequency, and close the saves
		edit.putInt("widgetCount", settings.getInt("widgetCount", 0) + 1);
		edit.putLong("freq", sb_freq.getProgress()+1);
		edit.commit();

		//Prepare the activity for a successful close
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		setResult(RESULT_OK, resultValue);

		//Relaunch the service so that we can update the widget when it is applied to
		//the homescreen
		AAWidgetProvider.launchService(this);

		this.finish();
	}


}