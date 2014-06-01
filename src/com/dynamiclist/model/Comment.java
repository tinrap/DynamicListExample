/**
 * 
 */
package com.dynamiclist.model;

import java.io.Serializable;

/**
 * @author 
 * @since 18 May 2014
 * Description: Comment object for a github issue's comments
 */
public class Comment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6911985412814293354L;
	private String user;
	private String description;
	
	/**
	 * 
	 */
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
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

}
