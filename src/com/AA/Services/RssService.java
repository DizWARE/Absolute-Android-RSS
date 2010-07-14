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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.AA.Other.Article;
import com.AA.Other.RSSParse;

/***
 * This service sends a request from the RSS feed, receives the data
 * that is sent back, and saves the data to the file system.
 */
public class RssService extends Service {

	boolean isBackground;
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
	 *
	 * @return - Returns how the system should handle the service. 
	 * If we are calling the service through the alarm, we would be just 
	 * fine to be shutdown if the system decides it needs more memory, so return the
	 * START_NOT_STICKY constant
	 * If we are calling it from the application, we are expected to finish
	 * our work before this service is killed so return the START_STICKY constant
	 */
	@Override public int onStartCommand(Intent intent, int flags, int startId) {
		
		isBackground = intent.getBooleanExtra("background", true);
		Thread thread = new Thread(new Runnable() 
			{ @Override public void run() { fetchData(isBackground); } });
		thread.start();
		if(isBackground)
			return START_NOT_STICKY;
		else
			return START_STICKY;
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
			articleList = readData(this);

		//Read old data if it is the main activity; Otherwise set a timer to launch
		//this service again
		if(!inBackground)
			readData(this, articleList);

		ArrayList<String> titles = new ArrayList<String>();

		//Store the articles into the bundles
		Bundle articleBundle = new Bundle();
		for(Article a : articleList){
			titles.add(a.getTitle());
			articleBundle.putSerializable(a.getTitle(), a);
		}

		//Broadcast the article bundle to the main app
		articleBundle.putStringArrayList("titles", titles);
		Intent broadcast = new Intent("RSSFinish");
		broadcast.putExtra("articles", articleBundle);
		this.sendBroadcast(broadcast);
		
		this.stopSelf();
	}

	/***
	 * Writes the received data to the application settings so that data
	 * restoration is easier
	 *
	 * @param articleList - List of all the articles that have been aggregated from the stream
	 */
	public static void writeData(Context context,
			List<Article> articleList) {
		/* Try to write our data to the file system
		 *
		 * CATCH STATEMENTS
		 * -There is a chance that an IOException might be thrown. According to
		 *  the exception, if the file doesn't exist this will happen, but according
		 *  to the method openFileOutput, the file will be created if it doesn't exist
		 *  I would presume that this exception would also be thrown if, for some reason
		 *  the file system is unaccessible.
		 *
		 *  Note: "articles" has no extension. If we want one, it should be 
		 *  easy to add, but due to the fact that it is private, I don't think
		 *  it matters
		 */
		try {
			FileOutputStream fileStream = context.openFileOutput("articles", 
					Context.MODE_PRIVATE);
			ObjectOutputStream writer = new ObjectOutputStream(fileStream);
			writer.writeObject(articleList);
			writer.close();
			fileStream.close();
		} catch(IOException e) {
			Log.e("AARSS","Problem saving file.",e);
			return;
		}
	}

	/***
	 * Reads in old data. Used for restoring settings per article
	 * 
	 * @param articleList - List of all the articles that have been aggregated from the stream
	 */
	public static void readData(Context context, List<Article> articleList) {
		List<Article> oldList = readData(context);

		if(oldList == null)
			return;

		//Restore the state for the current articles
		for(Article article : oldList)
			if(articleList.contains(article) && article.isRead())
				articleList.get(articleList.indexOf(article)).markRead();
	}

	/***
	 * Reads in data from the saved file.
	 * 
	 * @param context - Context that gives us some system access
	 * @return - Returns the list of items that were saved; If there was problem
	 * when gathering this data, an empty list is returned
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Article> readData(Context context) {
		ArrayList<Article> oldList = new ArrayList<Article>();

		try {
			FileInputStream fileStream = context.openFileInput("articles");
			ObjectInputStream reader = new ObjectInputStream(fileStream);

			//Unprotected copy from object to List<Articles>(warning comes from this)
			oldList = (ArrayList<Article>)reader.readObject();

			//Close our streams
			reader.close();
			fileStream.close();

			return oldList;
		} catch(java.io.FileNotFoundException e){
			return new ArrayList<Article>(); //If the file doesn't exist then nothing is saved. We are done.
		} catch(IOException e) {
			Log.e("AARSS","Problem loading the file. Does it exists?",e);
			return new ArrayList<Article>();
		} catch (ClassNotFoundException e) {
			Log.e("AARSS","Problem converting data from file.",e);
			return new ArrayList<Article>();
		}
	}
}
