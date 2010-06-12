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

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
/**
 * Class that handles parsing the list of current articles from the RSS feed
 */
public class RSSParse{
	/**
	 * The URI where the RSS feed is located.
	 */
	public static final String URI = "http://www.absolutelyandroid.com/feed/";

	/**
	 * Get the list of articles currently contained in the RSS feed.
	 * @param isBackground if the request is being run in the background
	 * @param callingContext current application context
	 * @return List of articles contained in the RSS on success. 
	 *         On failure returns null
	 */
	public static List<Article> getArticles(boolean isBackground,Context callingContext){
		//verify that we can use the network
		if(!isNetworkAvailable(isBackground,callingContext))
			return null;
		//try and get the document
		Document doc = getDocument();
		if(doc == null)
			return null;
		//parse into new articles
		try{
			ArrayList<Article> articles = new ArrayList<Article>();
			NodeList items = doc.getElementsByTagName("item");
			for(int i=0;i<items.getLength();i++){
				//this cast _shoud_ be safe if the data is well formed
				Element el = (Element)items.item(i);
				//these also should be safe provided the data is well formed
				String title = el.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
				String date = el.getElementsByTagName("pubDate").item(0).getFirstChild().getNodeValue();
				String url = el.getElementsByTagName("link").item(0).getFirstChild().getNodeValue();
				String desc = el.getElementsByTagName("description").item(0).getFirstChild().getNodeValue();
				articles.add(new Article(desc,title,date,url));
			}
			return articles;
		}catch(Exception e){
			//any parse errors and we'll log and fail
			Log.e("AARSS","Error Parsing RSS",e);
			return null;
		}

	}

	/**
	 * Check if the network is available for get the RSS feed
	 * @param isBackground if the request is being run in the background
	 * @param callingContext current application context
	 * @return if the network is in a state where a request can be sent
	 */
	private static boolean isNetworkAvailable(boolean isBackground,Context callingContext){
		ConnectivityManager manager = (ConnectivityManager)callingContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		//If the request is in the background and the phone does not want us to do
		//any background data transfer then respect that wish and bail.
		if(isBackground && !manager.getBackgroundDataSetting())
			return false;
		//if the current connection isn't ready for data then bail
		if(manager.getActiveNetworkInfo().getState() != NetworkInfo.State.CONNECTED)
			return false;
		return true;
	}
	/**
	 * Get the XML document for the RSS feed
	 * @return the XML Document for the feed on success, on error returns null
	 */
	private static Document getDocument(){
		Document doc = null;
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
			                          .newDocumentBuilder();
			//I'm not sure how this handles network timeouts... Needs testing
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(URI);
			HttpResponse response =  client.execute(request);
			doc = builder.parse(response.getEntity().getContent());

		}catch(java.io.IOException e){
			//IO Exception generally implies network error, so just fail silently
			return null;
		}catch(SAXException e){
			//SAXException means the xml isn't valid. fail and log an error
			Log.e("AARSS","Parse Exception in RSS feed",e);
			return null;
		}catch(Exception e){
			//this means either a builder exception or a timeout
			return null;
		}
		return doc;
	}
}
