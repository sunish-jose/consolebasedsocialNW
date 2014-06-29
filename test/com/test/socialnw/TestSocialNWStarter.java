package com.test.socialnw;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * TestSocialNWStarter.java, test case for the class SocialNWStarter.
 * Tests most of the methods in the class SocialNWStarter.java using junit
 * @author sunish jose
 *
 */
public class TestSocialNWStarter {
	private SocialNWStarter starter;
	String userNancy = "user_nancy";
	String userCharle = "Charle";
	
	String tweetMsgNancy = "Test tweet message by Nancy";
	String tweetMsg1Charle = "Test tweet message 1 by Charle";
	String tweetMsg2Charle = "Test tweet message 2 by Charle";

	@Before
	public void setup() {
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
	public void testGetTweetsForNullInputs() {
		
		starter.addTweet(userNancy, null);
		assertNotNull(starter.getTweets(userNancy, false));
		
		assertEquals(0, starter.getTweets(userNancy, false).size());
		
		starter.addTweet(null, null);
		
		assertNotNull(starter.getTweets(null, false));
		
		assertEquals(0, starter.getTweets(userNancy, true).size());
	}
	
	@Test
	public void testValidateFullName() {
		boolean valid = starter.validateFullName("FirstName");
		assertTrue(valid);
		
		valid = starter.validateFullName("FirstName LastName");
		assertTrue(valid);
		
		valid = starter.validateFullName("FristName MiddleName LastName");
		assertTrue(valid);
		
		valid = starter.validateFullName("");
		assertFalse(valid);
		
		valid = starter.validateFullName(null);
		assertFalse(valid);
		
		valid=starter.validateFullName("FirstName MiddleName LastName FourthName");
		assertFalse(valid);
		
		valid=starter.validateFullName("1stName");
		assertFalse(valid);
		
		valid=starter.validateFullName("FirstName ->");
		assertFalse(valid);
	}

}
