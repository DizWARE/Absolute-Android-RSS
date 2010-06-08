package com.AA.Other;

import java.io.Serializable;

/***
 * Describes an article that we pull out of the RSS feed
 * 
 * @author Tyler Robinson
 */
public class Article implements Serializable {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = -3009386435305319933L;

	private String description;
	private String title;
	private String date;
	private String url;
	private boolean read;

	/***
	 * Creates a new unread article
	 */
	public Article() {
		read = false;
	}
	/***
	 * Creates a article with the given description, title, and date
	 * 
	 * @param description - Article description
	 * @param title - Article title
	 * @param date - Article date
	 * @param url - Article URL
	 */
	public Article(String description, String title,
				   String date, String url) {
		this();
		this.description = description;
		this.date = date;
		this.title = title;
		this.url = url;
	}

	/***
	 * Gets the date of the article
	 * @return - Article date
	 */
	public String getDate() {
		return date;
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
	 * Marks the article as read
	 */
	public void markRead() {
		this.read = true;
	}

	/***
	 * Sets the article date
	 * @param date - Article date
	 */
	public void setDate(String date) {
		this.date = date;
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
}
