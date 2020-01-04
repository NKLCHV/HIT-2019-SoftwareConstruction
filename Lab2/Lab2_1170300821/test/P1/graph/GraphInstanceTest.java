/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

	// Testing strategy
	// TODO

	/**
	 * Overridden by implementation-specific test classes.
	 * 
	 * @return a new empty graph of the particular implementation being tested
	 */
	public abstract Graph<String> emptyInstance();

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testInitialVerticesEmpty() {
		// TODO you may use, change, or remove this test
		assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
	}

	// TODO other tests for instance methods of Graph
	@Test
	public void testInitialadd() {
		String teststring = "vertex1";
		Graph<String> mygraph = emptyInstance();
		boolean temp = mygraph.add(teststring);
		assertTrue(temp);
		assertEquals(1, mygraph.vertices().size());
		assertTrue(mygraph.vertices().contains(teststring));
		assertFalse(mygraph.add(teststring));
	}

	@Test
	public void testInitialset() {
		String sourceString = "vertex1";
		String targetString = "vertex2";
		Graph<String> mygraph = emptyInstance();
		assertTrue(mygraph.add(sourceString));
		assertTrue(mygraph.add(targetString));
		assertEquals(0, mygraph.set(sourceString, targetString, 5));
		assertEquals(5, mygraph.set(sourceString, targetString, 0));
		assertEquals(-1, mygraph.set(sourceString, targetString, -6));
		assertEquals(0, mygraph.set(sourceString, targetString, 5));
		assertEquals(0, mygraph.set(targetString, sourceString, 5));
		assertEquals(5, mygraph.set(sourceString, targetString, 0));
		assertEquals(5, mygraph.set(targetString, sourceString, 0));
	}

	@Test
	public void testremove() {
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
	}

	@Test
	public void testsources() {
		String vertex1 = "vertex1";
		String vertex2 = "vertex2";
		String vertex3 = "vertex3";
		String vertex4 = "vertex4";
		String vertex5 = "vertex5";
		Graph<String> mygraph = emptyInstance();
		assertTrue(mygraph.add(vertex1));
		assertTrue(mygraph.add(vertex2));
		assertTrue(mygraph.add(vertex3));
		assertTrue(mygraph.add(vertex4));
		assertTrue(mygraph.add(vertex5));
		assertEquals(0, mygraph.set(vertex1, vertex2, 3));
		assertEquals(0, mygraph.set(vertex1, vertex3, 2));
		assertEquals(0, mygraph.set(vertex2, vertex3, 5));
		assertEquals(0, mygraph.set(vertex3, vertex2, 1));
		assertEquals(0, mygraph.set(vertex3, vertex4, 6));
		assertEquals(0, mygraph.set(vertex4, vertex1, 4));
		assertEquals(0, mygraph.set(vertex4, vertex2, 3));
		Set<String> test = new HashSet<>();
		Map<String, Integer> testmap = mygraph.sources(vertex2);
		test = testmap.keySet();
		assertTrue(test.contains(vertex1));
		assertTrue(test.contains(vertex3));
		assertTrue(test.contains(vertex4));
		assertFalse(test.contains(vertex5));
		assertEquals(3, testmap.get(vertex1).intValue());
		assertEquals(1, testmap.get(vertex3).intValue());
		assertEquals(3, testmap.get(vertex4).intValue());
	}

	@Test
	public void testtargets() {
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
		Set<String> test = new HashSet<>();
		Map<String, Integer> testmap = mygraph.targets(vertex1);
		test = testmap.keySet();
		assertTrue(test.contains(vertex2));
		assertTrue(test.contains(vertex3));
		assertEquals(2, test.size());
		assertFalse(test.contains(vertex4));
		assertEquals(3, testmap.get(vertex2).intValue());
		assertEquals(2, testmap.get(vertex3).intValue());
	}

}
