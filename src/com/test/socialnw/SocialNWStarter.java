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
			if (message.trim().equalsIgnoreCase("quit")) {
				System.out.println("Quitting the application");
				break;
			}
			String[] contents = message.split("\\s+");
			if (contents.length == 1) {
				List<Tweet> tweetMsg = starter.getTweets(contents[0], false);
				starter.displayTweets(tweetMsg, false);
			} else if (contents[1].equals("->")) {
				starter.addTweet(contents[0], message.substring(	message.indexOf("->") + 2, message.length()).trim());
			} else if (contents[1].equals("follows")) {
				starter.addFollowersForTheUser(contents[0], contents[2]);
			} else if (contents[1].equals("wall")) {
				List<Tweet> tweetMsgs = starter.getTweets(contents[0], true);
				starter.displayTweets(tweetMsgs, true);
			}
		}
	}

	/**
	 * Add messages of a particular user with userName as the
	 * key and , list of messages as value, if the user already exist and
	 * got messages append to the new message into messagelist
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
	 * Get the messages for display on the wall or to read based on the parameter
	 * walll, if the user is following another user then display the another users
	 * message on the users wall. Messages will be sorted reverse chronologically
	 * 
	 * @param userName
	 * @param wall
	 * @return
	 */
	public List<Tweet> getTweets(String userName, boolean wall) {
		List<Tweet> combinedTweets =null;
		if(userName!=null && !userName.isEmpty()){
			List<Tweet> tweets = tweetMap.get(userName);
			if(tweets!=null && !tweets.isEmpty()){
				combinedTweets = new ArrayList<Tweet>();
				combinedTweets.addAll(tweets);
				if (wall && hasFollowers(userName)) {
					List<Tweet> followersTweets = getFollowersTweets(getFollowersList(userName));
					combinedTweets.addAll(followersTweets);
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
			if (null != tweetMsg && tweetMsg.length() > 0) {
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
		return " (" + delayByNow + " )";
	}

	/**
	 * TweetComparator is to sort the posts in the reverse chronological order
	 * @author sunish jose
	 *
	 */
	public class TweetComparator implements Comparator<Tweet> {
		@Override
		public int compare(Tweet tweet1, Tweet tweet2) {
			return tweet2.getDate().compareTo(tweet1.getDate());
		}
	}
}
