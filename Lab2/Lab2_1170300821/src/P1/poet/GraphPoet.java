/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

	private final Graph<String> graph = Graph.empty();

	// Abstraction function:
	// AF(a graph)=a poem(a fluent string)
	// Representation invariant:
	// must use legal English words and sentences. and the same as the graph.
	// Safety from rep exposure:
	// graph is private,and there is no mutator for it.

	/**
	 * Create a new poet with the graph from corpus (as described above).
	 * 
	 * @param corpus
	 *            text file from which to derive the poet's affinity graph
	 * @throws IOException
	 *             if the corpus file cannot be found or read
	 */
	public GraphPoet(File corpus) throws IOException {
		try {
			FileReader FileReader = new FileReader(corpus);
			@SuppressWarnings("resource") // 编译器提示加的
			BufferedReader BfReader = new BufferedReader(FileReader);
			String line = new String();
			while ((line = BfReader.readLine()) != null) {
				line = line.toLowerCase();
				String[] splitwords = line.split(" ");
				int size = splitwords.length;// 老办法读取单词
				int i = 0, j = 0;
				while (i < size - 1) {
					boolean flag = true;
					j = i + 1;
					graph.add(splitwords[i]);
					graph.add(splitwords[j]);
					int weight = 1;// 根据例子的规则计算前后单词权重
					while (splitwords[i].equals(splitwords[j])) {
						weight++;
						flag = false;
						j++;
					}
					if (flag) {// 权重为一，前后两个单词不同
						graph.set(splitwords[i], splitwords[j], weight);
						i = j;
					} else {// 权重不为一，单词重复多次
						graph.set(splitwords[i], splitwords[j - 1], weight - 1);
						i = j - 1;
					}
				}
			}

			checkRep();
		} catch (IOException e) {
			System.out.println("FILE ERROR");
		}
	}

	private void checkRep() {
		Set<String> mySet = graph.vertices();
		for (String string : mySet) {// :迭代
			assert Collections.frequency(mySet, string) == 1;
		}
	}

	/**
	 * Generate a poem.
	 * 
	 * @param input
	 *            string from which to create the poem
	 * @return poem (as described above)
	 */
	public String poem(String input) {
		Graph<String> copy = graph;
		String inputlow = input.toLowerCase();
		String[] inputsplit = input.split(" ");
		String[] lowsplit = inputlow.split(" ");
		String result = inputsplit[0];// 提取出原单词和小写单词

		Set<String> words = copy.vertices();
		for (int i = 0; i + 1 < inputsplit.length; i++) {
			String keyword1 = lowsplit[i], keyword2 = lowsplit[i + 1], keyword3 = inputsplit[i + 1];
			if (words.contains(keyword1) && words.contains(keyword2)) {
				int[] weight = new int[100];//找到合适的单词
				String[] bridges = new String[100];
				Map<String, Integer> k1target = copy.targets(keyword1);
				Set<String> k1targetset = k1target.keySet();
				boolean flag = false;
				for (String temp : k1targetset) {//遍历寻找交集
					Map<String, Integer> ttarget = copy.targets(temp);
					Set<String> ttargetset = ttarget.keySet();
					int j = 0;
					for (String expect : ttargetset) {
						if (expect.equals(keyword2)) {//寻找交集
							weight[j] = k1target.get(temp).intValue() + ttarget.get(expect).intValue();
							bridges[j] = temp;
							j = j + 1;
							flag = true;
						}
					}
				}
				if (flag) {//取权值最大
					int bindex = getmaxindex(weight);
					String fbridge = bridges[bindex];
					result = result + " " + fbridge + " " + keyword3;
				} else {
					result = result + " " + keyword3;
				}
			} else {
				result = result + " " + keyword3;
			}
		}
		checkRep();
		return result;
	}

	public int getmaxindex(int[] count) {
		int maxindex = 0;
		for (int j = 0; j < count.length; j++) {
			if (count[maxindex] < count[j]) {
				maxindex = j;
			}

		}
		checkRep();
		return maxindex;
	}

	public Graph<String> getGraph() {
		Graph<String> result = graph;
		checkRep();
		return result;
	}

	@Override
	public String toString() {
		return "GraphPoet [graph=" + graph.toString() + "]";
	}

}
