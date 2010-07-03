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

import java.io.Serializable;
import java.util.Calendar;

/***
 * Describes an article that we pull out of the RSS feed
 */
public class Article implements Serializable, Comparable<Article> {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -3009386435305319933L;

	private String description;
	private String title;	
	private String url;

	private boolean read;

	private Calendar articleDate;

	/***
	 * Creates a new unread article
	 */
	public Article() {
		read = false;
		articleDate = Calendar.getInstance();
	}
	/***
	 * Creates a article with the given description, title, and date
	 *
	 * @param description - Article description
	 * @param title - Article title
	 * @param url - Article URL
	 * @param date - Article date; String format should be like this:
	 *
	 * <Day name:3 letters>, DD <Month name: 3 letters> YYYY HH:MM:SS +0000
	 */
	public Article(String description, String title,
				   String date, String url) {
		this();
		this.description = removeEscapeChar(description);
		this.title = removeEscapeChar(title);
		this.url = url;
		this.articleDate = DateFunctions.makeDate(date);
	}

	/***
	 * Gets the formatted date of when the article was published
	 * 
	 * For example, if the article was posted earlier that day, this would
	 * return the number of hours in which the article was posted, like this:
	 * 
	 * "About x hours ago"
	 * 
	 * There are other intelligent date reductions as well
	 * 
	 * @return - Article date
	 */
	public String getDate() {		
		return DateFunctions.convertToString(articleDate);
	}

	/***
	 * Gets the description of the article
	 * @return - Article description
	 */
	public String getDescription() {
		return description;
	}

	/***
	 * Gets the title of the article
	 * @return - Article description
	 */
	public String getTitle() {
		return title;
	}

	/***
	 * Checks if the article has been read
	 * @return - returns if the article has been read or not
	 */
	public boolean isRead() {
		return read;
	}

	/***
	 * Toggles whether this article has been read or not
	 */
	public void toggleRead() {
		this.read = !this.read;
	}

	/***
	 * Marks the article as just read
	 */
	public void markRead() {
		this.read = true;
	}

	/***
	 * Sets the article date; String format required looks like this:
	 * 
	 * <Day name:3 letters>, DD <Month name: 3 letters> YYYY HH:MM:SS +0000
	 * 
	 * @param date - Article date
	 */
	public void setDate(String date) {
		articleDate = DateFunctions.makeDate(date);
	}

	/***
	 * Sets the article description
	 * @param description - Article description
	 */
	public void setDescription(String description) {
		this.description = removeEscapeChar(description);
	}

	/***
	 * Sets the article title
	 * @param title - Article title
	 */
	public void setTitle(String title) {
		this.title = removeEscapeChar(title);
	}

	/***
	 * Removes all escape characters from a string
	 * @param string - Any string that may contain the &# combo for escape characters
	 * @return - A new string with the unicode replacements
	 */
	private String removeEscapeChar(String string)	{
		int lastIndex = 0;
		while(string.contains("&#")) {
			//Get the escape character index
			int startIndex = string.indexOf("&#", lastIndex);
			int endIndex = string.indexOf(";", startIndex);

			//and rip the sucker out of the string
			String escapeChar = string.substring(startIndex, endIndex);

			//Get the unicode representation and replace all occurrences in the string
			String replacementChar = HTMLConverter.convertEscapeChar(escapeChar);
			string = string.replaceAll(escapeChar + ";", replacementChar);			
			lastIndex = endIndex;
		}
		return string;
	}

	/***
	 * Gets the URL of the article
	 * @return - Article URL
	 */
	public String getUrl() {
		return url;
	}

	/***
	 * Sets the article URL
	 * @param url - Article URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/***
	 * If the name is equal, its reasonable to say that the articles are equal
	 */
	@Override
	public boolean equals(Object o) {
		return this.title.equals(((Article)o).title);
	}

	/***
	 * Defines the Natural order of an article...Should be based off of the
	 * date
	 *
	 * @param other - The other article to compare to
	 */
	@Override public int compareTo(Article other) {
		return -this.articleDate.compareTo(other.articleDate);
	}
}
