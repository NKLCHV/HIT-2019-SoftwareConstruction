/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist as a key in the map; this is true even if A is followed by other people
 * in the network. Twitter usernames are not case sensitive, so "ernie" is the
 * same as "ERNie". A username should appear at most once as a key in the map or
 * in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets
	 *            a list of tweets providing the evidence, not modified by this
	 *            method.
	 * @return a social network (as defined above) in which Ernie follows Bert if
	 *         and only if there is evidence for it in the given list of tweets. One
	 *         kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of evidence
	 *            may be used at the implementor's discretion. All the Twitter
	 *            usernames in the returned social network must be either authors
	 *            or @-mentions in the list of tweets.
	 */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
		Map<String, Set<String>> result = new HashMap<>();
		int size = tweets.size();
		for (int i = 0; i < size; i++) {
			Tweet tweet = tweets.get(i);
			if (!result.containsKey(tweet.getAuthor())) {
				List<Tweet> specifictweets = Filter.writtenBy(tweets, tweet.getAuthor());
				Set<String> specificusersmentioned = Extract.getMentionedUsers(specifictweets);
				result.put(tweet.getAuthor(), specificusersmentioned);
			}
		}
		Map<String, Set<String>> finalresult = new HashMap<>();
		Set<String> keyset = result.keySet();
		for (String tempkey : keyset) {
			Set<String> tempset = result.get(tempkey);
			if (tempset.isEmpty())
				finalresult.put(tempkey, Collections.EMPTY_SET);
			else {
				Set<String> finale = new TreeSet<>();
				for (String tempstring : tempset) {
					if (result.containsKey(tempstring)) {
						finale.addAll(tempset);
						finale.addAll(result.get(tempstring));
						finalresult.put(tempkey, finale);
					}
				}
			}
		}
		return finalresult;
	}

	/**
	 * Find the people in a social network who have the greatest influence, in the
	 * sense that they have the most followers.
	 * 
	 * @param followsGraph
	 *            a social network (as defined above)
	 * @return a list of all distinct Twitter usernames in followsGraph, in
	 *         descending order of follower count.
	 */
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {
		List<String> influencers = new ArrayList<>();
		Collection<Set<String>> collectionofusernames = followsGraph.values();
		Iterator<Set<String>> collectionit = collectionofusernames.iterator();
		while (collectionit.hasNext()) {
			Set<String> setofusernames = collectionit.next();
			Iterator<String> setit = setofusernames.iterator();
			while (setit.hasNext()) {
				influencers.add(setit.next().toLowerCase());
			}
		}
		List<String> influencers2 = new ArrayList<>();
		for (int i = 0; i < influencers.size(); i++) {
			if (!influencers2.contains(influencers.get(i))) {
				influencers2.add(influencers.get(i));
			}
		}
		int[] count = new int[influencers2.size()];
		for (int i = 0; i < influencers2.size(); i++) {
			count[i] = Collections.frequency(influencers, influencers2.get(i));
		}
		List<String> finalresult = new ArrayList<>();
		int maxindex = 0;
		for (int i = 0; i < influencers2.size(); i++) {
			maxindex = getmaxindex(count);
			finalresult.add(influencers2.get(maxindex));
			count[maxindex] = 0;
		}
		return finalresult;
	}

	public static int getmaxindex(int[] count) {
		int maxindex = 0;
		for (int j = 0; j < count.length; j++) {
			if (count[maxindex] < count[j]) {
				maxindex = j;
			}

		}
		return maxindex;
	}

}
