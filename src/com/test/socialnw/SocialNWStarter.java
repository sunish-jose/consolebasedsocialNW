package com.test.socialnw;

import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialNWStarter {

	private static String quit = "";
	private Map<String, List<Tweet>> tweetMap = new HashMap<String, List<Tweet>>();
	private Map<String, List<String>> followersMap = new HashMap<String, List<String>>();

	public static final void main(String... aArgs) {
		SocialNWStarter starter = new SocialNWStarter();
		while (!quit.equalsIgnoreCase("quit")) {
			Console console = System.console();
			String message = console.readLine("");
			quit = message.trim();
			String[] contents = message.split("\\s+");
			if (contents.length == 1) {
				String msgs = starter.getTweetsForTheUser(contents[0]);
				starter.displayTweets(msgs);
			} else if (contents[1].equals("->")) {
				starter.addTweet(contents[0], message.substring(message.indexOf("->")+1, message.length()).trim());
			} else if (contents[1].equals("follows")) {
				System.out.println("following");
			} else if (contents[1].equals("wall")) {
				System.out.println("wall");
			}
		}
	}

	public void addTweet(String userName, String message) {
		Date now = new Date();
		Tweet tweet = new Tweet(message, now);
		List<Tweet> tweetList = null;
		if (tweetMap.containsKey(userName)) {
			tweetList=tweetMap.get(userName);
			tweetList.add(tweet);
			tweetMap.put(userName, tweetList);
		} else {
			tweetList=new ArrayList<Tweet>();
			tweetList.add(tweet);
			tweetMap.put(userName, tweetList);
		}
	}

	public String getTweetsForTheUser(String userName) {
		StringBuilder tweetMsg = new StringBuilder();
		List<Tweet> tweets = tweetMap.get(userName);
		if (tweets != null && !tweets.isEmpty()) {
			for (Tweet tweet : tweets) {
				tweetMsg.append(tweet.getMessage() + "\n");
			}
		}
		return tweetMsg.toString();
	}

	public void displayTweets(String tweetMessages) {
		if (null != tweetMessages && tweetMessages.length() > 0) {
			System.out.println(tweetMessages);
		} else {
			System.out.println("No Tweets");
		}
	}
	
	public void addFollowersForTheUser(String userName, String followingUser){
		List<String> followersList = null;
		if(followersMap.containsKey(userName)) {
			followersList=followersMap.get(userName);
			followersList.add(followingUser);
			followersMap.put(userName, followersList);
		}else {
			followersList=new ArrayList<String>();
			followersList.add(followingUser);
			followersMap.put(userName, followersList);
		}
		
	}
}
