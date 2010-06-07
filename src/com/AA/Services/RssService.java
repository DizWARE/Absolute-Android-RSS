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
