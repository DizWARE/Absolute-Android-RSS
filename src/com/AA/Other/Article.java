package com.AA.Other;

public class Article 
{
	private String description;
	private String title;
	private String date;
	private boolean read;
	
	/***
	 * Creates a new unread article
	 */
	public Article() 
	{
		read = false;
	}
	
	public Article(String description, String title, String date)
	{
		this.description = description;
		this.date = date;
		this.title = title;
	}
	
	/***
	 * Gets the date of the article
	 * @return - Article date
	 */
	public String getDate() 
	{
		return date;
	}
	
	/***
	 * Gets the description of the article
	 * @return - Article description
	 */
	public String getDescription() 
	{
		return description;
	}
	
	/***
	 * Gets the title of the article
	 * @return - Article description
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/***
	 * Checks if the article has been read
	 * @return - returns if the article has been read or not
	 */
	public boolean isRead() 
	{
		return read;
	}
	
	/***
	 * Sets the article as read
	 */
	public void markRead() 
	{
		this.read = true;
	}
	
	/***
	 * Sets the article date
	 * @param date - Article date
	 */
	public void setDate(String date) 
	{
		this.date = date;
	}
	
	/***
	 * Sets the article description
	 * @param description - Article description
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	/***
	 * Sets the article title
	 * @param title - Article title
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	/***
	 * Builds an article from XML
	 * 
	 * Should parse through the tags to get the info that will be used
	 * to describe the article
	 * @param xml - Xml Snippet that we are reading from
	 */
	public static Article buildFromXml(StringBuffer xml)
	{
		return null;
	}
	
	
}
