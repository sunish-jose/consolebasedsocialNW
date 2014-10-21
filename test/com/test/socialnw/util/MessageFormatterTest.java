package com.test.socialnw.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.test.socialnw.model.Message;
import com.test.socialnw.model.TimeLine;
import com.test.socialnw.model.User;

import static org.junit.Assert.*;

public class MessageFormatterTest {
	
	private MessageFormatter formatter;
	private List<Message> messageList=null;
	
	private User user=null;
	private Message message=null;
	private static final String USER_MESSAGE_TXT = "Hi There this is a test message by user";
	
	
	@Before
	public void setup() {
		user = new User("Test User");
		message= new Message(USER_MESSAGE_TXT, new Date());
		formatter = new MessageFormatter();
		TimeLine timeline = new TimeLine();
		timeline.addTimelineMessages(message);
		timeline.setUser(user);
		message.setTimeline(timeline);
		messageList = new ArrayList<Message>();
		messageList.add(message);
	}
	
	@Test
	public void testFormat() {
		String formattedText = formatter.format(messageList);
		String expectedText=user.getName()+" - "+messageList.get(0).getMessageText();
		assertTrue(formattedText.startsWith(expectedText));
	}
	
	@Test
	public void testFormatMessageTime() {
		message = new Message(USER_MESSAGE_TXT, new Date());
		formatter = new MessageFormatter();
		String timeElaspsed = formatter.formatMessageTime(message);
		assertNotNull(timeElaspsed);
		
		assertNotNull(formatter.formatMessageTime(null));
		
	}

}
