package com.test.socialnw;

import java.util.Date;


public class Tweet {

	private String message;
	private Date date;
	
	public Tweet(){}
	public Tweet(String message, Date now) {
		this.message=message;
		this.date=now;
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
