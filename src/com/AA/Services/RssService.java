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
package com.AA.Services;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;

import com.AA.Other.Article;
import com.AA.Other.RSSParse;

/***
 * This service sends a request from the RSS feed, receives the data
 * that is sent back, and saves the data to the file system.
 * 
 * @author Tyler Robinson 
 * 
 * (Everyone else who edit this file should add their name)
 */
public class RssService extends Service {
	
	SharedPreferences settings;
	
	/***
	 * Unnecessary method. Used if you are binding a service.
	 * 
	 * Learned that this is used to prove to the system that things have
	 * permissions that were previous granted. Probably some other things
	 * would use this, but its mostly to ID the service.
	 */
	@Override public IBinder onBind(Intent intent) {
		return null;
	}
	
	/***
	 * Called when the service is first put into memory. This is not
	 * always called when the StartService() is called. 
	 * 
	 * This should only be used to prepare member variables that are
	 * needed throughout the service.
	 */ @Override
	 public void onCreate() {
		settings = this.getSharedPreferences("settings", 0);
		super.onCreate();
	}

	/***
	 * Called every time StartService() is called.
	 * 
	 * All service work should be done here
	 */
	@Override public void onStart(Intent intent, int startId) {		
		fetchData(intent.getBooleanExtra("background", true));		
		super.onStart(intent, startId);
	}
	
	/***
	 * Required for all devices 2.0 and above. 
	 * Basically is the exact same thing as onStart(). Can't find any reason 
	 * for the change but onStart is now depricated
	 */
	@Override public int onStartCommand(Intent intent, int flags, int startId) {
		fetchData(intent.getBooleanExtra("background", true));
		return super.onStartCommand(intent, flags, startId);
	}

	/***
	 * Called when the service is cleared out of the phone's memory
	 * 
	 * Clean up all member variables here
	 */
	@Override public void onDestroy() {
		super.onDestroy();
	}

	/***
	 * Requests the data from the RSS feed and handles what is received
	 */
	public void fetchData(boolean inBackground) {
		//Get the list of articles
		ArrayList<Article> articleList = (ArrayList<Article>) RSSParse.getArticles(inBackground, this);
		if(articleList == null)
			return;
		
		if(!inBackground)
			readData(articleList);
		
		ArrayList<String> titles = new ArrayList<String>();
		
		//Store the articles into the bundles
		Bundle articleBundle = new Bundle();
		for(Article a : articleList)
		{
			titles.add(a.getTitle());
			articleBundle.putSerializable(a.getTitle(), a);
		}
		
		//Broadcast the article bundle to the main app
		articleBundle.putStringArrayList("titles", titles);
		Intent broadcast = new Intent("RSS Finish");
		broadcast.putExtra("articles", articleBundle);		
		this.sendBroadcast(broadcast);		
		
		//Update widget if it can't read broadcast
		if(!inBackground)
			writeData(articleList);
	}

	/***
	 * Writes the received data to the application settings so that data
	 * restoration is easier
	 * 
	 * @param articleList - List of all the articles that have been aggregated from the stream
	 */
	public void writeData(ArrayList<Article> articleList) {
		String titles = "";
		Editor e = settings.edit();
		
		//For ever article, add its name to a big string and store its 
			//read status
		for(Article article : articleList)
		{
			titles += article.getTitle().replace("/", "::") + "/";		
			e.putBoolean(article.getTitle(), article.isRead());
		}		
		
		//and store it into the settings
		e.putString("articleTitles", titles);
		e.commit();
	}
	
	/***
	 * Reads in old data. Used for restoring settings per article
	 * 
	 * @param articleList - List of all the articles that have been aggregated from the stream
	 */
	public void readData(ArrayList<Article> articleList) {
		String[] articles = settings.getString("articleTitles", "").split("/");		
		Editor e = settings.edit();
		
		//For every item in the restored list; Check to see if that item
			//has had some action(been read) restore it. If the item isn't 
			//in the fetched data; remove it from our sett9jgs
		for(int i = 0; i < articles.length; i++)
		{
			Article article = new Article("",articles[i].replace("::", "/"),"","");
			if(articleList.contains(article) && settings.getBoolean(articles[i], false))
				articleList.get(articleList.indexOf(article)).markRead();
			else if(!articleList.contains(article))
				e.remove(article.getTitle());
		}
		
		e.commit();
	}

}
