package com.test.socialnw.util;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.test.socialnw.model.Message;

public class MessageFormatter implements IFormatter{
	private static final String NEW_LINE="\n";

	public  String format(List<Message> messageList) {
		if(messageList==null || messageList.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int count=0;
		for(Message msg : messageList) {
			sb.append(msg.getTimeline().getUser().getName()+" - "+msg.getMessageText()+formatMessageTime(msg));
			if(count<messageList.size()-1) {
				sb.append(NEW_LINE);
			}
			count++;
		}
		return sb.toString();
	}
	
	public  String formatMessageTime(Message message) {
		String delayByNow = "";
		if(message==null || message.getMessageDate()==null) {
			return "";
		}
		long duration = new Date().getTime() - message.getMessageDate().getTime();
		long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
		if (diffInHours >= 24) {
			long day = diffInHours / 24;
			delayByNow = day + (day == 1 ? " day" : " days") + " ago";
		} else if (diffInHours > 0) {
			delayByNow = diffInHours + (diffInHours == 1 ? " hour" : " hours")
					+ " ago";
		} else if (diffInMinutes > 0) {
			delayByNow = diffInMinutes
					+ (diffInMinutes == 1 ? " minute" : " minutes") + " ago";
		} else if (diffInSeconds > 0) {
			delayByNow = diffInSeconds
					+ (diffInSeconds == 1 ? " second" : " seconds") + " ago";
		}
		return " (" + delayByNow + ")";
	}	



}
