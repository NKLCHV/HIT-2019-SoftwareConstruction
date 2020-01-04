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
public class ConcreteEdgesGraph<L> implements Graph<L> {

	// Abstraction function:
	// AF(a set of vertices + a list of edges) = a graph
	// Representation invariant:
	// Vertices doesn't contain the same.
	// Edges doesn't contain the same.
	// The source and the target of an edge must exist in vertices.

	// Safety from rep exposure:
	// Vertices and edges are private.
	// Most of the methods' returned values are immutable.(add, set,
	// remove,toString)
	// Vertices method use defensive copy to reduce rep exposure.
	// Sources and targets use a new map to return.
	// The type of vertex is L, which is immutable due to spec.

	private final Set<L> vertices = new HashSet<>();
	private final List<Edge<L>> edges = new ArrayList<>();

	private void checkRep() {
		Set<L> vcopy = vertices;
		List<Edge<L>> ecopy = edges;
		for (L vtemp : vcopy) {
			assert Collections.frequency(vcopy, vtemp) == 1;
		}
		for (Edge<L> etemp : ecopy) {
			assert Collections.frequency(ecopy, etemp) == 1;
			assert vcopy.contains(etemp.getSource()) && vcopy.contains(etemp.getTarget());
		}
	}

	@Override
	public boolean add(L vertex) {
		if (vertices.contains(vertex)) {
			checkRep();
			return false;
		} else {
			vertices.add(vertex);
			checkRep();
			return true;
		}
	}

	@Override
	public int set(L source, L target, int weight) {
		if (weight < 0)
			return -1;
		Edge<L> temp = null;
		int returnvalue = 0;
		Iterator<Edge<L>> it = edges.iterator();
		while (it.hasNext()) {
			temp = it.next();
			if (source.equals(temp.getSource()) && target.equals(temp.getTarget())) {
				returnvalue = temp.getWeight();
				if (weight >= 0)
					it.remove();
			}
		}
		if (weight == 0)
			return returnvalue;

		Edge<L> result = new Edge<>(source, target, weight);
		if (weight > 0)
			edges.add(result);
		checkRep();
		return returnvalue;
	}

	@Override
	public boolean remove(L vertex) {
		if (!vertices.contains(vertex))
			return false;
		else {
			Iterator<Edge<L>> itedge = edges.iterator();
			while (itedge.hasNext()) {
				Edge<L> temp = itedge.next();
				if (temp.getSource().equals(vertex) || temp.getTarget().equals(vertex)) {
					itedge.remove();
				}
			}
			vertices.remove(vertex);
			checkRep();
			return true;
		}
	}

	@Override
	public Set<L> vertices() {
		Set<L> result = new HashSet<>();
		result.addAll(vertices);
		return result;
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> result = new HashMap<>();
		List<Edge<L>> edgescopy = new ArrayList<>();
		edgescopy.addAll(edges);
		for (Edge<L> temp : edgescopy) {
			if (temp.getTarget().equals(target)) {
				result.put(temp.getSource(), temp.getWeight());
			}
		}
		return result;
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> result = new HashMap<>();
		List<Edge<L>> edgescopy = new ArrayList<>();
		edgescopy.addAll(edges);
		for (Edge<L> temp : edgescopy) {
			if (temp.getSource().equals(source)) {
				result.put(temp.getTarget(), temp.getWeight());
			}
		}
		checkRep();
		return result;
	}

	// TODO toString()
	public String toString() {
		String result = "";
		for (Edge<L> temp : edges)
			result = result + temp.toString();
		checkRep();
		return result;
	}

	/*
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((edges == null) ? 0 : edges.hashCode()); result =
	 * prime * result + ((vertices == null) ? 0 : vertices.hashCode()); return
	 * result; }
	 */

}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {
	private L source;
	private L target;
	private int weight;

	public L getSource() {
		return source;
	}

	private void setSource(L source) {
		this.source = source;
	}

	public L getTarget() {
		return target;
	}

	public int getWeight() {
		return weight;
	}

	private void setWeight(int weight) {
		this.weight = weight;
	}

	private void setTarget(L target) {
		this.target = target;
	}

	private void checkRep() {
		assert this.weight > 0;
	}

	public String toString() {
		String result = this.source + "->" + this.target + "\tweight is" + this.weight + "\n";
		return result;
	}

	public Edge(L source, L target, int weight) {
		setSource(source);
		setTarget(target);
		setWeight(weight);
		checkRep();
	}

}
