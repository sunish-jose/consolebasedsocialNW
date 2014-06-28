package com.test.socialnw;

import org.junit.Test;

import junit.framework.TestCase;

public class TestSocialNWStarter extends TestCase {
	private SocialNWStarter starter;
	String userNancy = "user_nancy";
	String userCharle = "Charle";
	
	String tweetMsgNancy = "Test tweet message by Nancy";
	String tweetMsg1Charle = "Test tweet message 1 by Charle";
	String tweetMsg2Charle = "Test tweet message 2 by Charle";

	public void setUp() {
		starter = new SocialNWStarter();
	}

	public void tearDown() {

	}

	@Test
	public void testAddTweet() {
		starter.addTweet(userNancy, tweetMsgNancy);
		starter.addTweet(userCharle, tweetMsg1Charle);
		starter.addTweet(userCharle, tweetMsg2Charle);
		assertEquals(1, starter.getTweets(userNancy, false).size());
		assertEquals(2, starter.getTweets(userCharle, false).size());
	}
	
	@Test
	public void  testAddFollowers(){
		starter.addFollowersForTheUser(userNancy, userCharle);
		assertTrue(starter.hasFollowers(userNancy));
		assertFalse(starter.hasFollowers(userCharle));
	}
	
	@Test
	public void testAddFollowersForNull() {
		starter.addFollowersForTheUser(userNancy, null);
		assertFalse(starter.hasFollowers(userNancy));
		
		
		starter.addFollowersForTheUser(null, null);
		assertFalse(starter.hasFollowers(null));
	}
	
	public void testGetFollowersList() {
		starter.addFollowersForTheUser(userNancy, userCharle);
		assertEquals(1, starter.getFollowersList(userNancy).size());
		
		assertEquals(userCharle, starter.getFollowersList(userNancy).get(0));
	}
	
	@Test
	public void testGetFollowersListForNull() {
		starter.addFollowersForTheUser(userNancy, null);
		assertNotNull(starter.getFollowersList(null));
		
		
		starter.addFollowersForTheUser(null, null);
		assertNotNull(starter.getFollowersList(null));
	}
	
	@Test
	public void testGetTweets() {
		starter.addTweet(userNancy, tweetMsgNancy);
		starter.addTweet(userCharle, tweetMsg1Charle);
		starter.addTweet(userCharle, tweetMsg2Charle);
		assertEquals(1, starter.getTweets(userNancy, false).size());
		assertEquals(2, starter.getTweets(userCharle, false).size());
		
		starter.addFollowersForTheUser(userNancy, userCharle);
		assertEquals(3, starter.getTweets(userNancy, true).size());
		
		assertEquals(tweetMsgNancy, starter.getTweets(userNancy, false).get(0).getMessage());
	}

	@Test
	public void testAddNullTweets() {
		starter.addTweet(userNancy, null);
		assertNotNull(starter.getTweets(userNancy, false));
		
		assertEquals(0, starter.getTweets(userNancy, false).size());
		
		starter.addTweet(null, null);
		assertNotNull(starter.getTweets(null, false));
		assertEquals(0, starter.getTweets(userNancy, false).size());
	}

}
