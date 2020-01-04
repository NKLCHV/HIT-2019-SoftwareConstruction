/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();

	// Abstraction function:
	// AF(a List of Vertex)= a graph
	// Representation invariant:
	// Vertices doesn't contain the same.
	// Edges doesn't contain the same.
	// The source and the target of an edge must exist in vertices.

	// Safety from rep exposure:
	// All of the fields are private.
	// Most of the methods' returned values are immutable.(add, set,
	// remove,toString)
	// All methods use defensive copy to reduce rep exposure.
	// Sources and targets use a new map to return.
	// The type of vertex is L, which is immutable due to spec.
	private void checkRep() {
		List<Vertex<L>> mycopy = new ArrayList<>();
		mycopy.addAll(vertices);
		Set<L> labelset = new HashSet<>();
		for (Vertex<L> temp : mycopy)
			assert Collections.frequency(mycopy, temp) == 1;
		for (Vertex<L> temp : mycopy)
			labelset.add(temp.getLabel());
		for (Vertex<L> temp : mycopy) {
			Map<L, Integer> tempmap = temp.getTargetmap();
			Set<L> temptargets = tempmap.keySet();
			for (L finaltemp : temptargets) {
				assert labelset.contains(finaltemp);
				assert Collections.frequency(temptargets, finaltemp) == 1;
			}
		}
	}

	@Override
	public boolean add(L vertex) {
		List<Vertex<L>> myList = new ArrayList<>();
		myList.addAll(vertices);
		Vertex<L> temp = new Vertex<>(vertex);
		for (Vertex<L> itvertex : myList) {
			if (itvertex.getLabel().equals(vertex))
				return false;
		}
		myList.add(temp);
		vertices.clear();
		vertices.addAll(myList);
		checkRep();
		return true;
	}

	@Override
	public int set(L source, L target, int weight) {
		if (weight < 0) {
			checkRep();
			return -1;
		}
		int returnvalue = 0;
		Map<L, Integer> myMap = new HashMap<>();
		for (Vertex<L> temp : vertices) {
			myMap = temp.getTargetmap();
			if (temp.getLabel().equals(source)) {
				if (myMap.containsKey(target)) {
					returnvalue = myMap.get(target);
					if (weight >= 0)
						temp.removeanedge(target);
				}
				if (weight > 0)
					temp.addanedge(target, weight);
			}
		}
		checkRep();
		return returnvalue;
	}

	@Override
	public boolean remove(L vertex) {
		Vertex<L> temp = new Vertex<>(vertex);
		Iterator<Vertex<L>> it = vertices.iterator();
		boolean flag = false;
		while (it.hasNext()) {
			temp = it.next();
			Map<L, Integer> tempmap = temp.getTargetmap();
			if (temp.getLabel().equals(vertex) || tempmap.containsKey(vertex)) {
				if (temp.getLabel().equals(vertex)) {
					it.remove();
				}
				if (tempmap.containsKey(vertex)) {
					temp.removeanedge(vertex);
				}
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public Set<L> vertices() {
		Set<L> result = new HashSet<>();
		for (Vertex<L> temp : vertices)
			result.add(temp.getLabel());
		checkRep();
		return result;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> result = new HashMap<>();
		for (Vertex<L> temp : vertices) {
			if (temp.getTargetmap().containsKey(target)) {
				result.put(temp.getLabel(), temp.getTargetmap().get(target));
			}
		}
		checkRep();
		return result;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> result = new HashMap<>();
		for (Vertex<L> temp : vertices) {
			if (temp.getLabel().equals(source)) {
				result.putAll(temp.getTargetmap());
				checkRep();
				return result;
			}
		}
		checkRep();
		return result;
	}

	// TODO toString()
	public String toString() {
		String result = "";
		for (Vertex<L> temp : vertices) {
			result = result + temp.toString();
		}
		checkRep();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		ConcreteVerticesGraph<L> other = (ConcreteVerticesGraph<L>) obj;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}
}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {

	private L label;
	private final Map<L, Integer> targetmap = new HashMap<>();

	public Map<L, Integer> getTargetmap() {
		Map<L, Integer> mycopy = new HashMap<>();
		mycopy.putAll(this.targetmap);
		return mycopy;
	}

	public Vertex(L vertex) {
		checkRep();
		setLabel(vertex);
	}

	public L getLabel() {
		checkRep();
		return label;
	}

	private void setLabel(L label) {
		this.label = label;
	}

	public String toString() {
		String result = "";
		Set<L> targets = this.targetmap.keySet();
		Iterator<L> it = targets.iterator();
		while (it.hasNext()) {
			L temp = it.next();
			result = result + this.label + "->" + temp + "\tweight is" + this.targetmap.get(temp) + "\n";
		}
		checkRep();
		return result;
	}

	private void checkRep() {
		Map<L, Integer> mycopy = new HashMap<>();
		mycopy.putAll(this.targetmap);
		Set<L> key = mycopy.keySet();
		Iterator<L> keyit = key.iterator();
		while (keyit.hasNext()) {
			L temp = keyit.next();
			assert mycopy.get(temp) > 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((targetmap == null) ? 0 : targetmap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		Vertex<L> other = (Vertex<L>) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (targetmap == null) {
			if (other.targetmap != null)
				return false;
		} else if (!targetmap.equals(other.targetmap))
			return false;
		return true;
	}

	public void addanedge(L target, int weight) {
		this.targetmap.put(target, weight);
		checkRep();
	}

	public void removeanedge(L target) {
		this.targetmap.remove(target);
		checkRep();
	}
}
