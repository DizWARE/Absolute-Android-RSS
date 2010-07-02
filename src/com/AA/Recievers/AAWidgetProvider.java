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
package com.AA.Recievers;

import java.util.ArrayList;
import java.util.Collections;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.AA.R;
import com.AA.Activities.AAMain;
import com.AA.Other.Article;
import com.AA.Services.RssService;

/***
 * Receiver for the widget; Handles updates for the widget
 *
 */
public class AAWidgetProvider extends AppWidgetProvider 
{
	/***
	 * Receiver for anything that shoots off an intent "RSSFinish"
	 * or an intent that is meant to update the widget
	 */
	@Override public void onReceive(Context context, Intent intent) {
		//If we have finished fetching new data;
		//Otherwise launch our service to fetch the data
		if(intent.getAction().equals("RSSFinish"))	{
			updateArticles(context, intent);
		}
		else if(intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_ENABLED)) {
			updateWidget(context, "Fetching Data...", "Please wait while I get latest news.");
			Intent service = new Intent();
			service.putExtra("background", true);
			service.setClass(context, RssService.class);
			context.startService(service);			
			return;
		}
		
		super.onReceive(context, intent);
	}	
	
	/***
	 * Updates the widget with the newest article
	 * 
	 * @param context - Context that gives us access to some system operations
	 * @param intent - The intent that launched this receiver
	 */
	private void updateArticles(Context context, Intent intent)
	{
		Bundle articleBundle = intent.getBundleExtra("articles");
		ArrayList<String> titles = articleBundle.getStringArrayList("titles");
		ArrayList<Article> articles = new ArrayList<Article>();
	
		if(titles == null)
			titles = new ArrayList<String>();

		for(String title : titles)
			articles.add((Article)articleBundle.getSerializable(title));
		
		Collections.sort(articles);	
		if(!articles.isEmpty())
			updateWidget(context, articles.get(0).getTitle(), articles.get(0).getDescription());
		else
			updateWidget(context, "Uh Oh", "Something happened. No data received");
	}
	
	/***
	 * Sends out an update to the widget with the given title and description
	 *
	 * @param context - Context that gives us access to some system operations
	 * @param title - Text that will go in the title block
	 * @param description - Text that goes in the description block
	 */
	private void updateWidget(Context context, String title, String description)
	{
		//Gets the "Remote View" of our widget layout. Internally inflates the 
		//views inside it and updates pieces that we request
		RemoteViews rv_main = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
		rv_main.setTextViewText(R.id.tv_title, title);
		rv_main.setTextViewText(R.id.tv_description, description);
		
		//Tells our widget to open the activity when the widget is clicked
		Intent activity = new Intent();
		activity.setClass(context, AAMain.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activity, 0);
		rv_main.setOnClickPendingIntent(R.id.iv_widget_bottom, pendingIntent);
		rv_main.setOnClickPendingIntent(R.id.iv_widget_top, pendingIntent);
		
		AppWidgetManager.getInstance(context).updateAppWidget(
				new ComponentName(context, AAWidgetProvider.class), rv_main);
	
	}
}
