package com.AA.Activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.AA.R;
import com.AA.Other.Article;
import com.AA.Recievers.AlarmReceiver;
import com.AA.Services.RssService;

/***
 * This is the main activity of the app...it is what is launched 
 * when the user starts the application
 * 
 * @author Tyler Robinson 
 * 
 * (Everyone else who edit this file should add their name)
 */
public class AAMain extends ListActivity {
	private final int OPEN = 0;
	private final int SHARE = 1;
	private final int MARK = 2;

	//***GUI Member Variables go here***
	ImageButton ib_refresh;
	//***End GUI Member Variables***

	ArticleAdapter adapter;
	SharedPreferences settings;

	BroadcastReceiver finishReceiver;

	ArrayList < Article > articles;

	View selectedView;

	/***
	 * Called when the activity is created and put into memory.
	 * 
	 * This is where all GUI elements should be set up and any
	 * other member variables that is used throughout the class
	 */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		articles = new ArrayList < Article > ();

		//Creates access to the application settings and marks the access code as
		//closed off to just our app
		settings = this.getSharedPreferences("settings", 0);
		AlarmReceiver.stopAlarm(this);

		//This sets up our adapter to use and tells our activity to use it to fill itself
		adapter = new ArticleAdapter(this);
		this.setListAdapter(adapter);

		//***GUI Elements Set up here***
		ib_refresh = (ImageButton) findViewById(R.id.ib_refresh);
		//***End GUI Set up***

	/**Catches when the service has finished downloading the RSS**/
		finishReceiver = new BroadcastReceiver() {
			@Override public void onReceive(Context context,
						  Intent intent) {
				//TODO - Pull Bundle from intent and take the Article data from it
				refresh();
		}};

		//Registers the Receiver with this activity
		this.registerReceiver(finishReceiver,
					  new IntentFilter("RSS Finish"));

		//***Action Listeners set up here***
		ib_refresh.setOnClickListener(new OnClickListener() {
			/***
			 * Handles when the user clicks the refresh button
			 * @param v - view that was clicked
			 */
			@Override public void onClick(View v) {
				refresh();}
			});
			//***End Action Listener set up***
	}

	/***
	 * Called when the activity starts. Not 100% necessary since 
	 * this usually happens after onCreate or onResume, but may be
	 * required for some state handling depending on the situation
	 */
	@Override protected void onStart() {
		//Starts the service
		Intent service = new Intent();
		service.setClass(this, RssService.class);
		this.startService(service);

		super.onStart();
	}

	/***
	 * Called when the activity stops running in the foreground.
	 * Should clean up anything that maybe unnecessarily hogging memory
	 * while in the background
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
		AlarmReceiver.startAlarm(this,
					 settings.getLong("freq", 2));
		super.onDestroy();
	}


	/***
	 * Called when another activity takes over the foreground.
	 * Also called when the the screen goes off or when the screen
	 * is rotated. 
	 * 
	 * Save any data that may be floating around at the moment, here
	 * ***CORRECTION - Save your data in the onSaveInstanceState(), not here.
	 */
	@Override protected void onPause() {
		//This cancels the receiver(requirement on the Android Dev Guide)
		this.unregisterReceiver(finishReceiver);
		super.onPause();
	}

	/***
	 * Called when the activity comes back into the foreground
	 * 
	 * Restore your data here(to give the user a seamless experience)
	 */
	@Override protected void onResume() {
		//Registers the Receiver with this activity
		this.registerReceiver(finishReceiver,
					  new IntentFilter("RSS Finish"));
		super.onResume();
	}

	/***
	 * Refreshes the article list. Should start the RSS service
	 * and then refreshes the data in the main activity list using
	 * the ArticleAdapter
	 */
	private void refresh() {
		//TODO - Implement me :) And remove the test code	   

		//Adds a fake article to the list of articles and issues a refresh of the GUI list
		articles.add(new Article(
				"This is article #" + articles.size(),
				"Article " + articles.size(), "Uhhh",
				"http://www.google.com"));
		adapter.clear();
		adapter.addList(articles);
	}

	/***
	 * Creates the ContextMenu that shows up when the user presses MENU
	 * 
	 * Should display "Settings" when the user presses MENU
	 */
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Settings");
		return true;
	}

	/***
	 * Starts the settings activity when user presses "Settings"
	 * 
	 * Note - This goes under the assumption that we don't add any new
	 * options. If we do, this will have to be changed to handle that
	 * 
	 * @param menuItem - Item selected from the options menu
	 */
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		Intent activity = new Intent();
		activity.setClass(this, AASettings.class);
		this.startActivity(activity);
		return true;
	}

	/***
	 * Creates a list of options when long pressing an item that has been registered for a
	 * context menu
	 */
	@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
		selectedView = v;
		Article article = (Article) v.getTag();

		//Creates the menu items
		menu.add(ContextMenu.NONE, OPEN, 0, "Open");
		menu.add(ContextMenu.NONE, SHARE, 1, "Share");

		if (article.isRead())
			menu.add(ContextMenu.NONE, MARK, 1,
				 "Mark as unread");
		else
			menu.add(ContextMenu.NONE, MARK, 1,
				 "Mark as read");
	}

	/***
	 * Handles when the user selects an option in the ContextMenu
	 */
	@Override public boolean onContextItemSelected(MenuItem item) {
		Article a = (Article) selectedView.getTag();

		if (item.getItemId() == OPEN)
			openBrowser(a);
		else if (item.getItemId() == SHARE)
			shareDialog(a);
		else if (item.getItemId() == MARK)
			a.toggleRead();

		//Tells the adapter to refresh itself
		adapter.notifyDataSetChanged();
		return super.onContextItemSelected(item);
	}

	/***
	 * Opens the browser at the given URL
	 * @param url - URL that we want opened in the browser
	 */
	private void openBrowser(Article a) {
		Intent browserLaunch = new Intent();

		a.markRead();

		//Sets this intent to launch the default browser app with the given URL
		browserLaunch.setAction(Intent.ACTION_DEFAULT);
		browserLaunch.addCategory(Intent.CATEGORY_BROWSABLE);
		browserLaunch.setData(Uri.parse(a.getUrl()));
		this.startActivity(browserLaunch);
	}

	/***
	 * Opens a dialog of possible places to share the article
	 * 
	 * Should hopefully allow for email, SMS, Facebook, and Twitter
	 * 
	 * @param a - Article to share
	 */
	private void shareDialog(Article a) {
		Intent shareChooser = new Intent(Intent.ACTION_SEND);

		shareChooser.setType("text/plain");

		//Puts a subject in our article and some text from the article
		shareChooser.putExtra(Intent.EXTRA_SUBJECT,
					  "Check this article out");
		shareChooser.putExtra(Intent.EXTRA_TEXT,
					  a.getTitle() +
					  " from Absolutely Android\n" +
					  a.getDescription() + "\n" +
					  "To read more, click this link(or copy it into URL bar): "
					  + a.getUrl());

		startActivity(Intent.
				  createChooser(shareChooser,
						"How do you want to share?"));
	}

	/***
	 * This adapter will take the article data and format each
	 * row of a list. This data includes the title, date, and the
	 * article description
	 * 
	 * @author Tyler Robinson 
	 * 
	 * (Everyone else who edit this file should add their name)
	 */
	private class ArticleAdapter extends ArrayAdapter < Article > {

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
		public ArticleAdapter(Context context) {
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
				AAMain.this.getLayoutInflater();
			SharedPreferences settings = AAMain.this.settings;

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
			Article article = this.getItem(position);

			//Puts our data into each of the TextViews for the user's view pleasure
			tv_title.setText(article.getTitle());
			tv_date.setText(article.getDate());
			tv_description.setText(article.getDescription());

			//Grabs our background color for the read/unread from the settings and sets
			//the row background to reflect that					
			int bgColor;
			int textColor;

			if (article.isRead())
				bgColor =
					settings.getInt("colorRead",
							Color.WHITE);
			else
				bgColor =
					settings.getInt("colorUnread",
							Color.BLACK);
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
			row.setTag(article);


		/**Click listener for the row**/
			row.setOnClickListener(new OnClickListener() {
			/***
				 * Open the file browser when user clicks the article
				 */
						   @Override
						   public void onClick(View v)
						   {
						   AAMain.this.
						   openBrowser((Article) v.
							   getTag());
						   ArticleAdapter.this.
						   notifyDataSetChanged();}
						   });

			return row;
		}
	}
}
