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
package com.AA.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/***
 * This service sends a request from the RSS feed, receives the data
 * that is sent back, and saves the data to the file system.
 * 
 */
public class RssService extends Service {
	/***
	 * Unnecessary method. Used if you are binding a service
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/***
	 * Called when the service is first put into memory. This is not
	 * always called when the StartService() is called. 
	 * 
	 * This should only be used to prepare member variables that are
	 * needed throughout the service.
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	/***
	 * Called every time StartService() is called.
	 * 
	 * All service work should be done here
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	
	/***
	 * Called when the service is cleared out of the phone's memory
	 * 
	 * Clean up all member variables here
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/***
	 * Requests the data from the RSS feed and handles what is received
	 * 
	 * I don't know if this is all we need or not...Only time will tell.
	 */
	public void fetchData(){
		//TODO Implement me please :)
	}
	
	/***
	 * Writes the received data to the file system.
	 * 
	 * This is another one that might change depending on how we do things
	 */
	public void writeData(){
		//TODO Implement me please :)
	}

}
