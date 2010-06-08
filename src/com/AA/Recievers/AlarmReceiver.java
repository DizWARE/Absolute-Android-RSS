package com.AA.Recievers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.AA.Services.RssService;

/***
 * This Receiver is ticked when an alarm message is sent out by the
 * System. It will call the RssService when this happens
 * 
 * @author Tyler Robinson 
 * 
 * (Everyone else who edit this file should add their name)
 */
public class AlarmReceiver extends BroadcastReceiver 
{

	/***
	 * Called when the receiver catches the message defined by the
	 * intent-filter. 
	 * 
	 * @param context - Context that started this Receiver(don't know what a context is? Look it up...its important)
	 * @param intent - Message that started this Receiver
	 */
	@Override
	public void onReceive(Context context, Intent intent) 
	{
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
	public static void startAlarm(Context context, long delay)
	{
		AlarmManager alarmManager = 
			(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC, 
				System.currentTimeMillis() + (delay * 3600000), 
				delay * 3600000, getPendingIntent(context));
	}
	
	/***
	 * Prematurely stops the alarm message that is pending in the system queue
	 * 
	 * @param context - Context that will cancel the message in the system queue
	 * @param pendingIntent - Message that is to be removed from the system queue
	 * (must be exactly the same as what started the alarm message)
	 */
	public static void stopAlarm(Context context)
	{
		AlarmManager alarmManager = 
			(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(getPendingIntent(context));
	}
	
	/***
	 * Creates a pending intent for our RSS Service
	 * @param context - Context that will be managing this alarm
	 * @return - The pending intent that will be sent to the alarm manager
	 */
	public static PendingIntent getPendingIntent(Context context)
	{
		Intent service = new Intent();
		service.setClass(context, RssService.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 9999, service, 0);
		return pendingIntent;
	}

}
