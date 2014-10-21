package com.test.socialnw.model;

import java.util.Date;

public class Message {
	
	private String messageText;
	
	private Date messageDate;
	
	private TimeLine timeline;
	
	public Message(String messageText, Date messageDate) {
		this.messageText=messageText;
		this.messageDate=messageDate;
	}
	
	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public TimeLine getTimeline() {
		return timeline;
	}

	public void setTimeline(TimeLine timeline) {
		this.timeline = timeline;
	}
	
}
