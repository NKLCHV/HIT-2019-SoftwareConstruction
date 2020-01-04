/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.*;

import javafx.css.Match;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of every
	 *         tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {
		int length = tweets.size();
		Instant start = tweets.get(0).getTimestamp();
		Instant end = tweets.get(0).getTimestamp();
		for (int i = 0; i < length; i++) {
			if (tweets.get(i).getTimestamp().isBefore(start))
				start = tweets.get(i).getTimestamp();
			if (tweets.get(i).getTimestamp().isAfter(end))
				end = tweets.get(i).getTimestamp();
		}
		Timespan result = new Timespan(start, end);
		return result;
	}

	/**
	 * Get usernames mentioned in a list of tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return the set of usernames who are mentioned in the text of the tweets. A
	 *         username-mention is "@" followed by a Twitter username (as defined by
	 *         Tweet.getAuthor()'s spec). The username-mention cannot be immediately
	 *         preceded or followed by any character valid in a Twitter username.
	 *         For this reason, an email address like bitdiddle@mit.edu does NOT
	 *         contain a mention of the username mit. Twitter usernames are
	 *         case-insensitive, and the returned set may include a username at most
	 *         once.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		int length = tweets.size();
		Set<String> result = new HashSet<>();
		String temp = null,username=null;
		Pattern pattern = Pattern.compile("@([\\w,-]+)");

		for (int i = 0; i < length; i++) {
			temp = tweets.get(i).getText();
			Matcher matcher = pattern.matcher(temp.toLowerCase());
			while(matcher.find()) {
				int start = matcher.start();
				if(start>0) {
					Character per = temp.charAt(start-1);
					if(per != '-' && per != '_' && Character.isDigit(per) && Character.isLetter(per))
						username = matcher.group(1);						
				}
				else {
					username = matcher.group(1);
				}
				if(username != null)
					result.add(username);
			}
		}
		return result;
	}
	
	public static Set<String> getMentionedHashTages(List<Tweet> tweets) {
		int length = tweets.size();
		Set<String> result = new HashSet<>();
		String temp = null,username=null;
		Pattern pattern = Pattern.compile("#([\\w,-]+)");

		for (int i = 0; i < length; i++) {
			temp = tweets.get(i).getText();
			Matcher matcher = pattern.matcher(temp.toLowerCase());
			while(matcher.find()) {
				int start = matcher.start();
				if(start>0) {
					Character per = temp.charAt(start-1);
					if(per != '-' && per != '_' && Character.isDigit(per) && Character.isLetter(per))
						username = matcher.group(1);						
				}
				else {
					username = matcher.group(1);
				}
				if(username != null)
					result.add(username);
			}
		}
		return result;
	}

}
