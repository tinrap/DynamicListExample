/**
 * 
 */
package com.dynamiclist.model;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author 
 * @since 18 May 2014
 * Description: Json parser class used to parse issue and comment JSON into respective objects
 */
public class JsonParser {

	/**
	 * Empty Constructor
	 */
	public JsonParser() {
		
	}
	
	@SuppressLint("SimpleDateFormat")
	public static ArrayList<Issue> parseIssues(String json){
		ArrayList<Issue> issues = new ArrayList<Issue>();
		
		Issue issue;
		JSONObject issueObject;
		int size;
		try {
			JSONArray issueArray = new JSONArray(json);
			size = issueArray.length();
			String dateString;
			DateFormat formater;
			Date date;
			
			for(int count = 0; count < size; count++)
			{
				issueObject = issueArray.getJSONObject(count);				
				issue = new Issue();
				
				issue.setTitle(issueObject.getString("title"));
				issue.setCommentsUrl(issueObject.getString("comments_url"));
				issue.setDescription(issueObject.getString("body"));
				dateString = issueObject.getString("updated_at");
				
				dateString = dateString.replace("T", " ");
				dateString = dateString.replace("z", "");
				formater = new SimpleDateFormat("yyyy-mm-dd h:m:s");
				date = formater.parse(dateString);
				issue.setDateUpdated(date);
								
				issues.add(issue);
			}
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return issues;
	}
	
	public static ArrayList<Comment> parseComments(String json){
		ArrayList<Comment> comments = new ArrayList<Comment>();
		
		Comment comment;
		JSONObject issueObject;
		int size;
		try {
			JSONArray commentsArray = new JSONArray(json);
			size = commentsArray.length();
			JSONObject userObj;
			
			for(int count = 0; count < size; count++)
			{
				issueObject = commentsArray.getJSONObject(count);				
				comment = new Comment();
				
				userObj = (JSONObject) issueObject.get("user");
				comment.setUser(userObj.getString("login"));
						
				comment.setDescription(issueObject.getString("body"));
				comments.add(comment);
			}
				
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		return comments;
	}

}
