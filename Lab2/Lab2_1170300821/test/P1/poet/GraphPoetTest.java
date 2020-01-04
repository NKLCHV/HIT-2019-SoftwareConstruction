/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Test;

import P1.graph.Graph;
import P1.poet.GraphPoet;



/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

	// Testing strategy
	// TODO

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void toTest() {
		try {
			final GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
			final String input = "Test the Omni sound";
			System.out.println(input+"\n>>>\n"+nimoy.poem(input));
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGraphpoet() {
		File testfile=new File("test/P1/poet/1.txt");
		try {
			GraphPoet testpoet=new GraphPoet(testfile);
			Graph<String>testgraph=testpoet.getGraph();
			Set<String>testvertices=testgraph.vertices();
			assertEquals(17, testvertices.size());
			assertEquals(1,testgraph.set("i", "love", 1));
			assertEquals(3, testgraph.set("hello,","hello,",2));
			assertEquals(1, testgraph.set("hello,", "goodbye!", 1));
			assertEquals(1, testgraph.set("goodbye!", "hello,", 1));
			assertEquals(2, testgraph.targets("love").keySet().size());
			assertEquals(2, testgraph.sources("the").keySet().size());
		} catch (IOException e) {
			System.out.println("FILE ERROR!");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPoet(){
		File testfile=new File("test/P1/poet/2.txt");
		try {
			GraphPoet testPoet =new GraphPoet(testfile);
			String testinput="Seek to explore new and exciting synergies!";
			String testresult=testPoet.poem(testinput);
			String expectresult="Seek to explore strange new life and exciting synergies!";
			assertEquals(expectresult, testresult);
			String testinput2="Hello, again!";
			String expectresult2="Hello, hello, again!";
			String testresult2=testPoet.poem(testinput2);
			assertEquals(expectresult2,testresult2);
		} catch (IOException e) {
			System.out.println("FILE ERROR!");
			e.printStackTrace();
		}
	}
	@Test
	public void testPoet2(){
		File testfile =new File("test/P1/poet/3.txt");
		try {
			GraphPoet testPoet=new GraphPoet(testfile);
			String testinput="hello, HELLO,",
					testresult=testPoet.poem(testinput),
					expectresult="hello, goodbye! HELLO,";
			assertEquals(expectresult, testresult);
		} catch (IOException e) {
			System.out.println("FILE ERROR!");
			e.printStackTrace();
		}
	}

}
