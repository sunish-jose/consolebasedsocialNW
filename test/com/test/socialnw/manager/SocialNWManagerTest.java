package com.test.socialnw.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.test.socialnw.model.Message;
import com.test.socialnw.model.TimeLine;
import com.test.socialnw.model.User;
import com.test.socialnw.util.MessageFormatter;


import static org.junit.Assert.*;

public class SocialNWManagerTest {
	
	private SocialNWManager manager;
	private Message message;
	private Message follower1Message;
	private Message follower2Message;
	private Message noFollowerMessage;
	
	private Message sortUser1Msg;
	private Message sortUser2Msg;
	private Message sortUser3Msg;
	
	private String name_user1="user1";
	private String name_follower1="follower1";
	private String name_follower2="follower2";
	
	private User user;
	private User follower1;
	private User follower2;
	private User noFollowersUser;
	private User noMessageUserWithFollowers;
	
	private TimeLine timelineUser;
	private TimeLine timelineFollower1;
	private TimeLine timelineFollower2;
	private TimeLine noFollowerUserTimeline;
	private TimeLine noMessageUserTimeline;
	
	private TimeLine timelineSortUser1;
	private TimeLine timelineSortUser2;
	private TimeLine timelineSortUser3;
	
	private User sortUser1;
	private User sortUser2;
	private User sortUser3;
	
	private static final String USER_MESSAGE_TXT = "Hi There this is a test message by user";
	private static final String FOLLOWER1_MESSAGE_TXT = "Hi there this is a test message by follower1";
	private static final String FOLLOWER2_MESSAGE_TXT = "Hi there this is a test message by follower2";
	private static final String NO_FOLLOWER_MESSAGE_TXT="Hi I am a no follower user";
	
	private static final String SORT_USER1_NAME="Sort User1";
	private static final String SORT_USER2_NAME="Sort User2";
	private static final String SORT_USER3_NAME="Sort User2";
	
	@Before
	public void setup() {
		manager = new SocialNWManager();
		
		follower1=new User(name_follower1);
		follower1Message=new Message(FOLLOWER1_MESSAGE_TXT, new Date());
		timelineFollower1=new TimeLine();
		
		timelineFollower1.setUser(follower1);
		follower1Message.setTimeline(timelineFollower1);
		timelineFollower1.addTimelineMessages(follower1Message);
		follower1.setTimeline(timelineFollower1);
		
		follower2=new User(name_follower2);
		follower2Message=new Message(FOLLOWER2_MESSAGE_TXT, new Date());
		timelineFollower2=new TimeLine();
		
		timelineFollower2.setUser(follower2);
		follower2Message.setTimeline(timelineFollower2);
		timelineFollower2.addTimelineMessages(follower2Message);
		follower2.setTimeline(timelineFollower2);

		user = new User(name_user1);
		message = new Message(USER_MESSAGE_TXT, new Date());
		timelineUser=new TimeLine();
		
		timelineUser.setUser(user);
		timelineUser.addTimelineMessages(message);
		message.setTimeline(timelineUser);
		user.setTimeline(timelineUser);
		
		user.addFollowers(follower1);
		user.addFollowers(follower2);
		
		noFollowersUser = new User("No Follower User");
		noFollowerUserTimeline=new TimeLine();
		noFollowerMessage = new Message(NO_FOLLOWER_MESSAGE_TXT, new Date());
		noFollowerUserTimeline=new TimeLine();
		noFollowerUserTimeline.addTimelineMessages(noFollowerMessage);
		noFollowersUser.setTimeline(noFollowerUserTimeline);
		
		noMessageUserWithFollowers = new User("No Message User");
		noMessageUserTimeline=new TimeLine();
		noMessageUserTimeline.addTimelineMessages(null);
		noMessageUserWithFollowers.setTimeline(noMessageUserTimeline);
		noMessageUserWithFollowers.addFollowers(follower1);
		noMessageUserWithFollowers.addFollowers(follower2);
		
		
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DATE, -1);
		Date date1=new Date(cal1.getTime().getTime());
		
		Calendar cal2=Calendar.getInstance();
		cal2.add(Calendar.DATE, -2);
		Date date2 = new Date(cal2.getTime().getTime());
		
		sortUser1 = new User(SORT_USER1_NAME);
		sortUser1Msg = new Message(USER_MESSAGE_TXT, new Date());
		timelineSortUser1=new TimeLine();
		timelineSortUser1.addTimelineMessages(sortUser1Msg);
		timelineSortUser1.setUser(sortUser1);		
		sortUser1Msg.setTimeline(timelineSortUser1);
		sortUser1.setTimeline(timelineSortUser1);
		
		sortUser2=new User(SORT_USER2_NAME);
		sortUser2Msg=new Message(FOLLOWER1_MESSAGE_TXT, date2);
		
		timelineSortUser2=new TimeLine();
		sortUser2Msg.setTimeline(timelineSortUser2);
		timelineSortUser2.addTimelineMessages(sortUser2Msg);
		sortUser2.setTimeline(timelineSortUser2);
		timelineSortUser2.setUser(sortUser2);

		sortUser3=new User(SORT_USER3_NAME);
		sortUser3Msg=new Message(FOLLOWER2_MESSAGE_TXT, date1);
		timelineSortUser3=new TimeLine();
		sortUser3Msg.setTimeline(timelineSortUser3);
		timelineSortUser3.addTimelineMessages(sortUser3Msg);
		sortUser3.setTimeline(timelineSortUser3);
		timelineSortUser3.setUser(sortUser3);		
		
		sortUser1.addFollowers(sortUser2);
		sortUser1.addFollowers(sortUser3);
		
	}

	@After
	public void tearDown() {

	}
	
	@Test
	public void testGetTimeLineMessages() {
		int count1 = manager.getTimeLineMessages(user).size();
		assertEquals(3, count1);
		
		int count2 = manager.getTimeLineMessages(follower1).size();
		assertEquals(1, count2);
		
		int count3 = manager.getTimeLineMessages(new User("Unknown User")).size();
		assertEquals(0, count3);
		
		assertNotNull(manager.getTimeLineMessages(null));
		int count4 = manager.getTimeLineMessages(null).size();
		assertEquals(0, count4);
		
		int count5=manager.getTimeLineMessages(noFollowersUser).size();
		assertEquals(1, count5);
		
		int count6=manager.getTimeLineMessages(noMessageUserWithFollowers).size();
		assertEquals(2, count6);
	}
	
	@Test
	public void testSortTimelineMessages() {
		
		List<Message> sortedMsgList1 = manager.sortTimelineMessages(manager.getTimeLineMessages(sortUser1), true);
		for(int i=0; i<sortedMsgList1.size();i++) {
			if(i<sortedMsgList1.size()-1) {
				Date lateDate=sortedMsgList1.get(i).getMessageDate();
				Date earlyDate=sortedMsgList1.get(i+1).getMessageDate();
				assertTrue(lateDate.after(earlyDate));
			}
		}
		
		List<Message> sortedMsgList2 = manager.sortTimelineMessages(manager.getTimeLineMessages(sortUser1), false);
		for(int i=0; i<sortedMsgList2.size();i++) {
			if(i<sortedMsgList2.size()-1) {
				Date earlyDate=sortedMsgList2.get(i).getMessageDate();
				Date lateDate=sortedMsgList2.get(i+1).getMessageDate();
				assertTrue(earlyDate.before(lateDate));
			}
		}		
	}
	
	@Test
	public void testGenerateTimelineText() {
		List<Message> sortedMsgList = manager.sortTimelineMessages(manager.getTimeLineMessages(user), true);
		assertNotNull(sortedMsgList);
		manager.setFormatter(new MessageFormatter());
		String message = manager.generateTimelineText(sortedMsgList);
		assertNotNull(message);
	}
	
	
	
}