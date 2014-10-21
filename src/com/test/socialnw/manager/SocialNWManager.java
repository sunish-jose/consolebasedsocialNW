package com.test.socialnw.manager;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.test.socialnw.model.Message;
import com.test.socialnw.model.TimeLine;
import com.test.socialnw.model.User;
import com.test.socialnw.util.IFormatter;

public class SocialNWManager {
	
	private IFormatter formatter;
	
	public void postToTimeline(User user, Message msg) {
		user.getTimeline().addTimelineMessages(msg);
	}
	
	public List<Message> getTimeLineMessages(User user) {
		if(user==null) {
			return Collections.<Message>emptyList();
		}
		TimeLine timeline = user.getTimeline();
		List<Message> userMsgList=null;
		List<Message> consolidatedList = null;
		if(timeline!=null) {
			userMsgList = timeline.getTimelineMessages();
			consolidatedList = new ArrayList<Message>();
			if(!userMsgList.isEmpty()) {
				consolidatedList.addAll(userMsgList);
			}
			Set<User> followers = user.getFollowes();
			List<Message> followersMsgList = new ArrayList<Message>();
			if(followers!=null && !followers.isEmpty()) {
				for(User follower : followers) {
					followersMsgList.addAll(follower.getTimeline().getTimelineMessages());
				}
			}
			if(followersMsgList!=null && !followersMsgList.isEmpty()) {
					consolidatedList.addAll(followersMsgList);
			}
		}
		return consolidatedList==null?Collections.<Message>emptyList():consolidatedList;
	}
	
	public List<Message> sortTimelineMessages(List<Message> msgList, boolean sortDecending) {
		if(sortDecending) {
			Collections.sort(msgList, new DecendingComparator());
		} else {
			Collections.sort(msgList, new AscendingComparator());
		}
		return msgList;
	}
	
	public String generateTimelineText(List<Message> msgs) {
		if(msgs!=null && !msgs.isEmpty()) {
			return formatter.format(msgs);
		}
		return "";
	}
	
	
	public IFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(IFormatter formatter) {
		this.formatter = formatter;
	}
	
	
	
	/**
	 * MessageComparator is to sort the posts in the reverse chronological order
	 *
	 */
	public class DecendingComparator implements Comparator<Message> {
		
		@Override
		public int compare(Message msg1, Message msg2) {
			return msg2.getMessageDate().compareTo(msg1.getMessageDate());
		}
	}
	
	/**
	 * MessageComparator is to sort the posts in the chronological order
	 *
	 */
	public class AscendingComparator implements Comparator<Message> {
		
		@Override
		public int compare(Message msg1, Message msg2) {
			return msg1.getMessageDate().compareTo(msg2.getMessageDate());
		}
	}



}
