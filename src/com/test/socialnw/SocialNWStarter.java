package com.test.socialnw;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 	Console based social networking sample application.
 * SocialNWStarter is the main class which performs
 * the basic operations of posting, & reading messages, following another user and
 * displaying the user's wall, the posts will be displayed in the
 * reverse chronological order with latest at the top and oldest at the
 * bottom (applicable for reading and wall)
 *  
 *  @author sunish jose (sunish.jose@ymail.com) 
 */

public class SocialNWStarter {

	//place holder for posted messages
	private Map<String, List<Tweet>> tweetMap = new HashMap<String, List<Tweet>>();

	//place holder for the users to follow
	private Map<String, List<String>> followersMap = new HashMap<String, List<String>>();
	
	private static final CharSequence WALL = "wall";
	
	private static final CharSequence FOLLOWS = "follows";
	
	private static final CharSequence USER_MSG_SEPARATOR="->";

	public static final void main(String... aArgs) {
		System.out.println("Console based social network application - Follow the instructions below");
		System.out.println("1. To post your tweet: <user name> -> <your message>");
		System.out.println("2. To read your tweet: <user name>");
		System.out.println("3. To follow another user: <user name> follows <another user>");
		System.out.println("4. To display your wall: <user name> wall");
		System.out.println("5. To quit the application type quit");
		SocialNWStarter starter = new SocialNWStarter();
		while (true) {
			Console console = System.console();
			String message = console.readLine("");
			if (message!=null && !message.isEmpty() && message.trim().equalsIgnoreCase("quit")) {
				System.out.println("Quitting the application");
				break;
			}
			starter.processInputMessage(message.trim());
		}
	}
	
	/**
	 * Process the input message. This method decide whether it is post, read, follows or wall request
	 * It filters out the user names and post message, it display the 'wal'l, it also displays the 'read'
	 * and it also invoke the 'follows'
	 * Even if the user enters first middle & last names the program will accept correctly
	 * ex. Bob Michael Miller -> What a nice day 
	 * @param message
	 */
	public void processInputMessage(String message) {
		if(message!=null && !message.isEmpty()) {
				//for reading
				if(!message.contains(WALL) && !message.contains(FOLLOWS) && !message.contains(USER_MSG_SEPARATOR)) {
					//replace the extra white space in between first middle & last name
					String fullName = message.replaceAll("^ +| +$|( )+", "$1");
					if(validateFullName(fullName)) {
						List<Tweet> tweets = getTweets(fullName, false);
						displayTweets(tweets, false);						
					} else {
						System.out.println("Invalid Name");
					}
				} else if(message.contains(WALL)) {
					String fullName = message.substring(0, message.indexOf(WALL.toString())-1).trim();
					fullName = fullName.replaceAll("^ +| +$|( )+", "$1");
					if(validateFullName(fullName)) {
						List<Tweet> tweetMsgs = getTweets(fullName, true);
						displayTweets(tweetMsgs, true);
					} else {
						System.out.println("Invalid Name");
					}
				} else if(message.contains(FOLLOWS)) {
					String fullNameLead = message.substring(0, message.indexOf(FOLLOWS.toString())-1).trim();
					fullNameLead = fullNameLead.replaceAll("^ +| +$|( )+", "$1");
					String fullNameFollower =  message.substring(message.indexOf(FOLLOWS.toString())+FOLLOWS.length(), message.length()).trim();
					fullNameFollower = fullNameFollower.replaceAll("^ +| +$|( )+", "$1");
					if(validateFullName(fullNameLead) && validateFullName(fullNameFollower)) {
						addFollowersForTheUser(fullNameLead, fullNameFollower);
					} else {
						System.out.println("Invalid name(s)");
					}
				} 
				//checks the message contains -> then it is for posting
				else if(message.contains(USER_MSG_SEPARATOR)) {
					String fullName = message.substring(0, message.indexOf("->")).trim();
					fullName = fullName.replaceAll("^ +| +$|( )+", "$1");
					if(validateFullName(fullName)) {
						addTweet(fullName, message.substring(message.indexOf("->") + 2, message.length()).trim());
					} else {
						System.out.println("Invalid name");
					}
				}
		}
	}
	
	/**
	 * Validate full name. Assumption - Full name can have first name middle name & last name
	 * All the characters should be alphabetic, no numerals or any special characters
	 * @param fullName
	 * @return
	 */
	public boolean validateFullName(String fullName){
		boolean valid = false;
		if(fullName!=null && !fullName.isEmpty()) {
			String[] names = fullName.split("\\s+");
			if (names.length > 3) {
				valid = false;
			} else {
				for (String name : names) {
					if (!name.trim().matches("[a-zA-Z]{1,30}")) {
						valid = false;
						break;
					}
					valid=true;
				}
			}
		}
		return valid;
	}

	/**
	 * Add messages of a particular user with full name as the
	 * key and , list of messages as value, if the user already exist and
	 * got messages, append the new message into messagelist
	 * 
	 * @param userName
	 * @param message
	 */
	public void addTweet(String userName, String message) {
		
		if(userName!=null && userName.trim().length()>0 && 
				message!=null && message.trim().length()>0){
			Date now = new Date();
			Tweet tweet = new Tweet(userName, message, now);
			List<Tweet> tweetList = null;
			if (tweetMap.containsKey(userName)) {
				tweetList = tweetMap.get(userName);
				tweetList.add(tweet);
				tweetMap.put(userName, tweetList);
			} else {
				tweetList = new ArrayList<Tweet>();
				tweetList.add(tweet);
				tweetMap.put(userName, tweetList);
			}
		}
	}

	/**
	 * Get the list of tweets for a particular user, if the user follows another
	 * user get the other user's tweets also. Combine both the lists and sort is
	 * reverse chronological order
	 * 
	 * @param userName - full name of the user
	 * @param wall - true for displaying on wall and false for reading
	 * @return
	 */
	public List<Tweet> getTweets(String userName, boolean wall) {
		List<Tweet> combinedTweets =null;
		List<Tweet> followersTweets = null;
		if(userName!=null && !userName.isEmpty()){
			List<Tweet> tweets = tweetMap.get(userName);
			if(tweets!=null && !tweets.isEmpty()){
				combinedTweets = new ArrayList<Tweet>();
				combinedTweets.addAll(tweets);
				if (wall && hasFollowers(userName)) {
					List<String> followersList = getFollowersList(userName);
					if(followersList!=null && !followersList.isEmpty()) {
						followersTweets = getFollowersTweets(followersList);
					}
					if(followersTweets!=null && !followersTweets.isEmpty()) {
						combinedTweets.addAll(followersTweets);
					}
				}
				if (combinedTweets != null && !combinedTweets.isEmpty()) {
					Collections.sort(combinedTweets, new TweetComparator());
				}
			}
		}
		return combinedTweets==null?Collections.<Tweet>emptyList():combinedTweets;
	}

	/**
	 * print the messages, either use a console or system.out.println
	 * format the message for read and wall based on the parameter wall
	 * 
	 * @param tweetMessages
	 * @param wall
	 */
	public void displayTweets(List<Tweet> tweets, boolean wall) {
		StringBuilder tweetMsg = new StringBuilder();
		if (tweets != null && !tweets.isEmpty()) {
			for (int index = 0; index < tweets.size(); index++) {
				Tweet tweet = tweets.get(index);
				String endingChar = index < tweets.size() - 1 ? "\n" : " ";
				if(wall){
					tweetMsg.append(tweet.getTweetUser() + " - "+ tweet.getMessage() + formatTweetTime(tweet)+ endingChar);
				} else {
					tweetMsg.append(tweet.getMessage() + formatTweetTime(tweet)+ endingChar);
				}
			}
			if (tweetMsg.length() > 0) {
				System.out.println(tweetMsg);
			} else {
				System.out.println("No Tweets");
			}
		} else {
			System.out.println("No Tweets");
		}
	}

	/**
	 * Check the user is following another user
	 * @param userName
	 * @return
	 */
	public boolean hasFollowers(String userName) {
		boolean hasFollowers = false;
		if(userName!=null && !userName.isEmpty()) {
			hasFollowers = followersMap.containsKey(userName);
		}
		return hasFollowers;
	}

/**
 * Get the list of users this particular user follows
 * @param userName
 * @return
 */
	public List<String> getFollowersList(String userName) {
		List<String> followersList = null;
		if(userName!=null && !userName.isEmpty()) {
			followersList = followersMap.get(userName);
		}
		return followersList == null ? Collections.<String> emptyList()
				: followersList;
	}

	/**
	 * Get the list of messages of the users this particular user follows
	 * @param followersList
	 * @return
	 */
	public List<Tweet> getFollowersTweets(List<String> followersList) {
		List<Tweet> followersTweets = new ArrayList<Tweet>();
		for (String userName : followersList) {
			if(tweetMap.get(userName)!=null && !tweetMap.get(userName).isEmpty()){ 
				followersTweets.addAll(tweetMap.get(userName));
			}
		}
		return followersTweets;

	}

	/**
	 * Add the followers of a particular user into followersMap
	 * @param userName
	 * @param followingUser
	 */
	public void addFollowersForTheUser(String userName, String followingUser) {
		List<String> followersList = null;
		if(userName!=null && !userName.isEmpty() && followingUser!=null && !followingUser.isEmpty()) {
			if (followersMap.containsKey(userName)) {
				followersList = followersMap.get(userName);
				if (!followersList.contains(followingUser)) {
					followersList.add(followingUser);
					followersMap.put(userName, followersList);
				}
			} else {
				followersList = new ArrayList<String>();
				followersList.add(followingUser);
				followersMap.put(userName, followersList);
			}
		}

	}

	/**
	 * format the time difference between now and the message time 
	 * and display the time difference on the 'wall' and 'reading'
	 * @param tweet
	 * @return
	 */
	public String formatTweetTime(Tweet tweet) {
		String delayByNow = "";
		long duration = new Date().getTime() - tweet.getDate().getTime();
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

	/**
	 * TweetComparator is to sort the posts in the reverse chronological order
	 *
	 */
	public class TweetComparator implements Comparator<Tweet> {
		@Override
		public int compare(Tweet tweet1, Tweet tweet2) {
			return tweet2.getDate().compareTo(tweet1.getDate());
		}
	}
}
