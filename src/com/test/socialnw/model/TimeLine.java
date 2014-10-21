package com.test.socialnw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeLine {
	
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private List<Message> timelineMessages=null;
	
	public List<Message> getTimelineMessages() {
		return timelineMessages==null?Collections.<Message>emptyList():timelineMessages;
	}
	
	public void addTimelineMessages(Message message) {
		if(timelineMessages==null) {
			timelineMessages = new ArrayList<Message>();
		}
		if(message!=null){
			timelineMessages.add(message);
		}
	}

	protected void setTimelineMessages(List<Message> timelineMessages) {
		this.timelineMessages = timelineMessages;
	}
	
}
