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
package com.AA.Other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/***
 * Bunch of functions for handling dates that represent an article
 */
public class DateFunctions {
	/***
	 * Given a month number, the month name is returned
	 * 
	 * I hate this code and proves the need for enumerations, but since
	 * those don't exist in java(or I don't think they do), this is as close
	 * as we can get(unless we defined a dictionary of some sort...but meh)
	 * 
	 * @param month - the number of the month to be converted
	 * @return The name of the given month 
	 */
	public static String getMonth(int month) {
		if(month == 1)
			return "January";
		else if(month == 2)
			return "February";
		else if(month == 3)
			return "March";
		else if(month == 4)
			return "April";
		else if(month == 5)
			return "May";
		else if(month == 6)
			return "June";
		else if(month == 7)
			return "July";
		else if(month == 8)
			return "August";
		else if(month == 9)
			return "September";
		else if(month == 10)
			return "October";
		else if(month == 11)
			return "November";
		else
			return "December";
	}
	
	/***
	 * Creates a Calendar object based off a string with this format
	 * 
	 * <Day name:3 letters>, DD <Month name: 3 letters> YYYY HH:MM:SS +0000
	 * @param date - The string we are to convert to a calendar date
	 * @return - The calendar date object that this string represents
	 */
	public static Calendar makeDate(String date) {
		Date d = null;
		Calendar calendar = Calendar.getInstance();
		try 
		{			
			//Parses the date from the <Day name:3 letters>, DD <Month name: 3 letters> YYYY HH:MM:SS +0000 format
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
			d = format.parse(date);
			calendar.setTime(d);			
		} catch (ParseException e) {
			//If the date is fudged, just set the date to the current date
			//I have no idea what else to do in this case...Any ideas?
			e.printStackTrace();
		}		
		return calendar;
	}
	
	/***
	 * Compares the 2 calendars against the given field for equality
	 * @param field - integer that represents the field to be compared(i.e. Calendar.MONTH)
	 * @param timeA - A calendar object to be compared
	 * @param timeB - A calendar object to be compared
	 * @return true if the fields return the same number, false otherwise
	 */
	public static boolean isDateFieldEqual(int field, Calendar timeA, Calendar timeB) {
		if(timeA.get(field) == timeB.get(field))
			return true;
		
		return false;
	}
	
	/***
	 * Takes the date and creates an intelligent string response about
	 * the difference of the current time and the given time
	 * 
	 * For example, if the article was posted 2 hours before the current time
	 * the response would be "About 2 hours ago" but if the article was posted
	 * a day before the current time, the response would be "Yesterday"
	 * 
	 * Sorry about how nasty this code is...I don't know if there can be
	 * an improvements to it, seeing that there really isn't much flexability
	 * with these Calendar objects
	 * 
	 * @param articleDate - The date of posting
	 * @return An intelligent response of the difference between the current
	 * time and the article date
	 */
	public static String convertToString(Calendar articleDate) {
		Calendar currentTime = Calendar.getInstance();
		
		String printDate = "";
		
		//If the date is the same as when published then return
		//a time difference; If the date is one day more than when it
		//was published, then return "Yesterday"; Otherwise return the
		//formated date: <Month full name> <Day>, <Year>
		if(isDateFieldEqual(Calendar.MONTH,articleDate,currentTime) &&
				isDateFieldEqual(Calendar.DAY_OF_MONTH,articleDate,currentTime) &&
				isDateFieldEqual(Calendar.YEAR,articleDate,currentTime))
		{
			//If the hour is the same...return how many minutes ago the article was published
			//Otherwise return how many hours ago the article was published
			if(isDateFieldEqual(Calendar.HOUR,articleDate,currentTime))
				printDate = "About " + (currentTime.get(Calendar.MINUTE) - 
						articleDate.get(Calendar.MINUTE)) + " mins ago"; 
			else
				printDate = "About " + (currentTime.get(Calendar.HOUR_OF_DAY) - 
						articleDate.get(Calendar.HOUR_OF_DAY)) + " hours ago";
		}
		else if((isDateFieldEqual(Calendar.YEAR,articleDate,currentTime) &&
				currentTime.get(Calendar.DAY_OF_YEAR) - articleDate.get(Calendar.DAY_OF_YEAR) == 1)||
				isCrossingYears(articleDate,currentTime))
			printDate = "Yesterday";
		else	
			printDate = DateFunctions.getMonth(articleDate.get(Calendar.MONTH)+1) + " " +
					articleDate.get(Calendar.DAY_OF_MONTH) + ", " + articleDate.get(Calendar.YEAR);
		
		return printDate;
	}
	
	/***
	 * This class just checks to see the given 2 times are crossing over 
	 * the year mark(Article Date = Dec 31 and Current Date = Jan 1)
	 * 
	 * @param articleDate - Day the article was posted
	 * @param currentDate - The current day
	 * @return - True if articleDate = Dec 31 Year = n and currentDate = Jan 1 Year = n + 1
	 */
	public static boolean isCrossingYears(Calendar articleDate, Calendar currentDate) {
		if(currentDate.get(Calendar.YEAR) - articleDate.get(Calendar.YEAR) != 1)
			return false;
		
		Calendar lastDayOfDec = (Calendar)articleDate.clone();
		Calendar firstDayOfJan = (Calendar)currentDate.clone();
		
		lastDayOfDec.set(Calendar.MONTH,Calendar.DECEMBER);
		lastDayOfDec.set(Calendar.DAY_OF_MONTH, 31);
		
		firstDayOfJan.set(Calendar.MONTH, Calendar.JANUARY);
		firstDayOfJan.set(Calendar.DAY_OF_MONTH, 1);
		
		if(articleDate.equals(lastDayOfDec) && currentDate.equals(firstDayOfJan))
			return true;
 		
		return false;
	}
}
