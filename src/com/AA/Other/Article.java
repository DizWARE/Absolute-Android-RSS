package com.AA.Other;

import java.io.Serializable;
import java.util.Calendar;

/***
 * Describes an article that we pull out of the RSS feed
 * 
 * @author Tyler Robinson
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
		this.description = description;		
		this.title = title;
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
		this.description = description;
	}

	/***
	 * Sets the article title
	 * @param title - Article title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * Defines the Natural order of an article...Should be based off of the
	 * date
	 * 
	 * @param other - The other article to compare to
	 */
	@Override public int compareTo(Article other) {
		return -this.articleDate.compareTo(other.articleDate);
	}
}
