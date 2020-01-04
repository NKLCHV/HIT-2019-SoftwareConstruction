package P2;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import P1.graph.*;

public class FriendshipGraph {
	private Graph<Person> graph = Graph.empty();
	
	private void checkRep() {
		Set<Person> mySet = graph.vertices();
		for (Person string : mySet) {
			assert Collections.frequency(mySet, string) == 1;
		}
	}
	
	public Graph<Person> getGraph() {
		return graph;
	}

	public void addvertex(Person vertex) {
		Graph<Person> mycopy = graph;
		if (!mycopy.add(vertex)) {
			System.out.println("EXIST PERSON!");
			return;
		}
		graph = mycopy;
	}

	public void addEdge(Person xperson, Person yperson) {
		Graph<Person> mycopy = graph;
		if (!mycopy.vertices().contains(xperson) || !mycopy.vertices().contains(yperson)) {
			System.out.println("PERSON NOT EXIST!");//检查是否存在两个person
			return;
		}
		mycopy.set(xperson, yperson, 1);
		graph = mycopy;
	}

	public int getDistance(Person xperson, Person yperson) {
		int result = 0;
		Graph<Person> mycopy = graph;//检查是否存在两个person
		if (!mycopy.vertices().contains(xperson) || !mycopy.vertices().contains(yperson)) {
			System.out.println("PERSON NOT EXIST!");
			return 0;
		} else {
			result = BFS(mycopy, xperson, yperson);
		}
		return result;

	}

	private int BFS(Graph<Person> mygraph, Person origin, Person target) {
		int result = 0;
		Map<Person, Boolean> visited = new HashMap<>();
		Set<Person> tempkeyset = mygraph.vertices();
		for(Person personit : tempkeyset) {//标记是否经过
			visited.put(personit, false);
		}
		Queue<Person> tempqueue = new LinkedList<>();
		Queue<Integer> countqueue = new LinkedList<>();
		tempqueue.offer(origin);
		visited.replace(origin, false, true);
		countqueue.offer(1);
		while (!tempqueue.isEmpty()) {
			Person person2 = tempqueue.peek();
			Set<Person> person2targets = mygraph.targets(person2).keySet();
			for(Person personit2 : person2targets) {
				if (!visited.get(personit2)) {
					tempqueue.add(personit2);
					countqueue.add(countqueue.peek() + 1);
				}
				if (personit2.equals(target)) {
					result = countqueue.peek();
					return result;
				}
			}
			tempqueue.poll();
			countqueue.poll();
		}
		return -1;
	}
	
}
