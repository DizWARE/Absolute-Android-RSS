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

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.AA.R;
import com.AA.Other.Article;

/***
 * This activity starts when the user presses "Settings" in the 
 * Context Menu  of the main app
 * 
 */
public class AAColors extends ListActivity {
	SharedPreferences colors;

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
//		this.setListAdapter(adapter);

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
	
	private class ColorOptionAdapter extends ArrayAdapter<Object> {
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
		public ColorOptionAdapter(Context context) {
			super(context, R.layout.article_layout,
				  R.id.iv_title);
		}
		
		/***
		 * Adds a list of items into the list view
		 * 
		 * @param articles - that are being added to our list view of articles
		 */
		public void addList(List < Article > articles) {
			  for (Article article:articles)
				this.add(article);
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
		public View getView(int position, View convertView,
					ViewGroup parent) {
			//Creates a layout inflater using the main activity's context
			LayoutInflater inflater =
				AAColors.this.getLayoutInflater();
// XXX			SharedPreferences settings = AAMain.this.settings;

			View row;

			//Parses the layout we want into a View, so that we can access each
			//individual piece if we haven't already(in which we just use convertView)
			if (convertView == null)
				row =
					inflater.inflate(R.layout.
							 article_layout, null);
			else
				row = convertView;

			//Grabs our TextViews from our article row layout for edit
			TextView tv_title =
				(TextView) row.findViewById(R.id.tv_title);
			TextView tv_date =
				(TextView) row.findViewById(R.id.tv_date);
			TextView tv_description =
				(TextView) row.findViewById(R.id.
							tv_description);

			//Gets the article that will be displayed at this position
// XXX			Article article = this.getItem(position);

			//Puts our data into each of the TextViews for the user's view pleasure
//			tv_title.setText(article.getTitle());
//			tv_date.setText(article.getDate());
//			tv_description.setText(article.getDescription());

			//Grabs our background color for the read/unread from the settings and sets
			//the row background to reflect that					
			int bgColor;
			int textColor;

/*			if (article.isRead())
				bgColor = settings.getInt("colorRead", Color.WHITE);
			else
				bgColor = settings.getInt("colorUnread", Color.BLACK);
			row.setBackgroundColor(bgColor);

			//Produces a complementary color of the background color and sets
			//it to the foreground color; this way the user never hides the text
			int r = (~Color.red(bgColor)) & 0xff;
			int g = (~Color.green(bgColor)) & 0xff;
			int b = (~Color.blue(bgColor)) & 0xff;
			textColor = Color.rgb(r, g, b);

			tv_title.setTextColor(textColor);
			tv_description.setTextColor(textColor);
			tv_date.setTextColor(textColor);

			//Allows for long pressing a row item
			AAMain.this.registerForContextMenu(row);

			//Stores the article within the view(for access elsewhere)
			row.setTag(article); */

			/**Click listener for the row**/
			row.setOnClickListener(new OnClickListener() {
			/***
			 * Open the file browser when user clicks the article
			 */
			@Override public void onClick(View v){
//				AAMain.this.openBrowser((Article) v.getTag());
//				ArticleAdapter.this.
				notifyDataSetChanged();}
			});

			return row;
		}
	}
}
