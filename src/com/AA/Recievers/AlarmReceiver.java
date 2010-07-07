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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.AA.Services.RssService;

/***
 * This Receiver is ticked when an alarm message is sent out by the
 * System. It will call the RssService when this happens
 */
public class AlarmReceiver extends BroadcastReceiver {
	private static final int ALARM_ID = 9999;

	/***
	 * Called when the receiver catches the message defined by the
	 * intent-filter. 
	 * 
	 * @param context - Context that started this Receiver(don't know what a context is? Look it up...its important)
	 * @param intent - Message that started this Receiver
	 */
	@Override public void onReceive(Context context, Intent intent) {
		startAlarm(context);
		Intent service = new Intent();
		service.putExtra("background", true);
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
	public static void startAlarm(Context context) {
		long delay = context.getSharedPreferences("settings", 0).getLong("freq", 2);

		AlarmManager alarmManager =
			(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC,
					  System.currentTimeMillis() +
					  (delay * 3600000), 
					  //(delay * 30000), TODO - FIX THIS(Debug code)
					  getPendingIntent(context));
	}

	/***
	 * Prematurely stops the alarm message that is pending in the system queue
	 * 
	 * @param context - Context that will cancel the message in the system queue
	 * @param pendingIntent - Message that is to be removed from the system queue
	 * (must be exactly the same as what started the alarm message)
	 */
	public static void stopAlarm(Context context) {
		AlarmManager alarmManager =
			(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(getPendingIntent(context));
	}

	/***
	 * Creates a pending intent for our RSS Service
	 * @param context - Context that will be managing this alarm
	 * @return - The pending intent that will be sent to the alarm manager
	 */
	public static PendingIntent getPendingIntent(Context context) {
		Intent service = new Intent();
		service.setClass(context, AlarmReceiver.class);
		PendingIntent pendingIntent =
			PendingIntent.getBroadcast(context, ALARM_ID, service, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}

}
