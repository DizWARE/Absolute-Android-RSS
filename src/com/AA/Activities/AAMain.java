/*
 * Copyright 2010 University Of Utah Android Development Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.AA.Activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.AA.R;
import com.AA.Other.Article;

/***
 * This is the main activity of the app...it is what is launched 
 * when the user starts the application
 * 
 */
public class AAMain extends ListActivity {
	//***GUI Member Variables go here***
	ImageButton ib_refresh;
	//***End GUI Member Variables***
	
	
	 /***
	  * Called when the activity is created and put into memory.
	  * 
	  * This is where all GUI elements should be set up and any
	  * other member variables that is used throughout the class
	  */
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	
		//***GUI Elements Set up here***
		ib_refresh = (ImageButton)findViewById(R.id.ib_refresh);	
		//***End GUI Set up***
		
		//***Action Listeners set up here***
		ib_refresh.setOnClickListener(new OnClickListener() 
		{			
			/***
			 * Handles when the user clicks the refresh button
			 * @param v - view that was clicked
			 */
			@Override
			public void onClick(View v) 
			{
				refresh();
			}
		});
		  //***End Action Listener set up***
	}
	
	/***
	 * Called when the activity starts. Not 100% necessary since 
	 * this usually happens after onCreate or onResume, but may be
	 * required for some state handling depending on the situation
	 */
	@Override
	protected void onStart() {
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
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	/***
	 * Called when the activity is cleaned out of memory.
	 * 
	 * Clean up all member variables here
	 */
	@Override
	protected void onDestroy() {
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
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	/***
	 * Called when the activity comes back into the foreground
	 * 
	 * Restore your data here(to give the user a seamless experience)
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	/***
	 * Refreshes the article list. Should start the RSS service
	 * and then refreshes the data in the main activity list using
	 * the ArticleAdapter
	 */
	private void refresh(){
		//TODO - Implement me :)
	}
	
	/***
	 * Creates the ContextMenu that shows up when the user presses MENU
	 * 
	 * Should display "Settings" when the user presses MENU
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	/***
	 * Handles when the user selects an option in the ContextMenu
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}
	
	/***
	 * I don't know if we need this, but this will do something when
	 * the user closes the ContextMenu(by pressing MENU again)
	 */
	@Override
	public void onContextMenuClosed(Menu menu){
		// TODO Auto-generated method stub
		super.onContextMenuClosed(menu);
	}
	
	/***
	 * This adapter will take the article data and format each
	 * row of a list. This data includes the title, date, and the
	 * article description
	 * 
	 */
	private class ArticleAdapter extends ArrayAdapter<Article>{

		/***
		 * Constructor - An array adapter has several different constructors.
		 * This one required both a list of articles and a layout resource for
		 * each row.
		 * 
		 * @param context - Context that will be using this adapter
		 * @param resource - Layout resource that will define the design of each row
		 * @param textViewResourceId - Usually used for simple text view lists...not really needed since we have the row layout
		 * @param objects - List of articles that we will display in the list
		 */
		public ArticleAdapter(Context context, int resource,
				int textViewResourceId, List<Article> objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}
		
		/***
		 * Called when the row is in the users current view. Rows should be prepared here
		 * 
		 * It is necessary to inflate the row that was given in the constructor, before you
		 * are able to change individual pieces of each row...I have code for this if we need it.
		 * 
		 * @param position - Current position in the list that is being prepared to be displayed
		 * @param convertView - Old view that needs to be converted...we won't use this.
		 * @param parent - parent that this view gets attached to
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return super.getView(position, convertView, parent);
		}		
	}
}
