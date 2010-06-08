package com.AA.Recievers;

import com.AA.Services.RssService;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/***
 * This Receiver is ticked when an alarm message is sent out by the
 * System. It will call the RssService when this happens
 * 
 */
public class AlarmReceiver extends BroadcastReceiver {

	/***
	 * Called when the receiver catches the message defined by the
	 * intent-filter. 
	 * 
	 * @param context - Context that started this Receiver(don't know what a context is? Look it up...its important)
	 * @param intent - Message that started this Receiver
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent();
		service.setClass(context, RssService.class);
		context.startService(service);
	}
	
	/***
	 * Tells the System to ping the given message after the given number of milliseconds
	 * 
	 * @param context - Context that will prepare the message in the system queue
	 * @param pendingIntent - Message that will be ping after the given delay
	 * @param delay - How long, in milliseconds, till the message is sent
	 */
	public static void startAlarm(Context context, PendingIntent pendingIntent, long delay){
		
	}
	
	/***
	 * Prematurely stops the alarm message that is pending in the system queue
	 * 
	 * @param context - Context that will cancel the message in the system queue
	 * @param pendingIntent - Message that is to be removed from the system queue
	 * (must be exactly the same as what started the alarm message)
	 */
	public static void stopAlarm(Context context, PendingIntent pendingIntent){
		
	}

}
