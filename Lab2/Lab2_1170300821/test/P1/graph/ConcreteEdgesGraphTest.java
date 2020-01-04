/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteEdgesGraph();
	}

	/*
	 * Testing ConcreteEdgesGraph...
	 */

	@Test
	public void testtoString() {
		String vertex1 = "vertex1";
		String vertex2 = "vertex2";
		String vertex3 = "vertex3";
		String vertex4 = "vertex4";
		Graph<String> mygraph = emptyInstance();
		assertTrue(mygraph.add(vertex1));
		assertTrue(mygraph.add(vertex2));
		assertTrue(mygraph.add(vertex3));
		assertTrue(mygraph.add(vertex4));
		assertEquals(0, mygraph.set(vertex1, vertex2, 3));
		assertEquals(0, mygraph.set(vertex1, vertex3, 2));
		assertEquals(0, mygraph.set(vertex2, vertex3, 5));
		assertEquals(0, mygraph.set(vertex3, vertex2, 1));
		assertEquals(0, mygraph.set(vertex3, vertex4, 6));
		assertEquals(0, mygraph.set(vertex4, vertex1, 4));
		assertEquals(0, mygraph.set(vertex4, vertex2, 3));
		assertTrue(mygraph.remove(vertex4));
		assertFalse(mygraph.vertices().contains(vertex4));
		assertFalse(mygraph.remove("vertex5"));
		assertEquals(
				"vertex1->vertex2	weight is3\nvertex1->vertex3	weight is2\nvertex2->vertex3	weight is5\nvertex3->vertex2	weight is1\n",
				mygraph.toString());
	}
	/*
	 * Testing Edge...
	 */

	// Testing strategy for Edge
	// test whether it is legal for edge
	@Test
	public void testedge() {
		Edge ds = new Edge("v1", "v2", 15);
		assertEquals("v1", ds.getSource());
		assertEquals("v2", ds.getTarget());
		assertEquals(15, ds.getWeight());
	}

}
