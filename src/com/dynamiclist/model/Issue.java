/**
 * 
 */
package com.dynamiclist.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author 
 * @since 18 May 2014
 * Description: a Github issue object. Object contains information on a github issue
 *
 */
public class Issue implements Comparable<Issue>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3383084588008670405L;
	private Date dateUpdated;
	private String title;
	private String description;
	private String commentsUrl;
	private ArrayList<Comment> comments;
	
	/**
	 * 
	 */
	public Issue() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the dateUpdated
	 */
	public Date getDateUpdated() {
		return dateUpdated;
	}

	/**
	 * @param dateUpdated the dateUpdated to set
	 */
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the commentsUrl
	 */
	public String getCommentsUrl() {
		return commentsUrl;
	}

	/**
	 * @param commentsUrl the commentsUrl to set
	 */
	public void setCommentsUrl(String commentsUrl) {
		this.commentsUrl = commentsUrl;
	}

	/**
	 * @return the comments
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public int compareTo(Issue another) {
		return another.getDateUpdated().compareTo(dateUpdated);
	}
}
