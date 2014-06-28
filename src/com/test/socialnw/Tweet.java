package com.test.socialnw;

import java.util.Date;

/**
 * Tweet POJO class
 * Holds the sample tweet messages along with the user and tweet time
 * @author sunish jose (sunish.jose@ymail.com)
 *
 */
public class Tweet {

	private String message;
	private Date date;
	private String tweetUser;
	
	
	public Tweet(){}
	public Tweet(String tweetUser, String message, Date now) {
		this.tweetUser = tweetUser;
		this.message=message;
		this.date=now;
	}
	
	public String getTweetUser() {
		return tweetUser;
	}
	public void setTweetUser(String tweetUser) {
		this.tweetUser = tweetUser;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
