package P3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FriendshipGraphTest {

	Person wang=new Person("wang");
	Person ye =new Person("ye");
	Person gao=new Person("gao");
	Person li =new Person("li");
	FriendshipGraph graph=new FriendshipGraph();

	@Test
	public void testAddVertex() {
		graph.addVertex(wang);
		graph.addVertex(ye);
		graph.addVertex(gao);
		graph.addVertex(li);
		assertTrue(graph.name.contains(wang));
		assertTrue(graph.name.contains(ye));
		assertTrue(graph.name.contains(gao));
		assertTrue(graph.name.contains(li));
	}

	@Test
	public void testAddEdge() {
		graph.addVertex(wang);
		graph.addVertex(ye);
		graph.addVertex(gao);
		graph.addVertex(li);
		graph.addEdge(wang, ye);
		graph.addEdge(ye, wang);
		graph.addEdge(ye, gao);
		graph.addEdge(gao, ye);
		int[][] test= {{0,1,0,0},{1,0,1,0},{0,1,0,0},{0,0,0,0}};
		assertArrayEquals(test[0], graph.graph[0]);
		assertArrayEquals(test[1], graph.graph[1]);
		assertArrayEquals(test[2], graph.graph[2]);
		assertArrayEquals(test[3], graph.graph[3]);	
		
	}

	@Test
	public void testGetdistance() {
		graph.addVertex(wang);
		graph.addVertex(ye);
		graph.addVertex(gao);
		graph.addVertex(li);
		graph.addEdge(wang, ye);
		graph.addEdge(ye, wang);
		graph.addEdge(ye, gao);
		graph.addEdge(gao, ye);
		assertEquals(2, graph.getdistance(wang, gao));
		assertEquals(-1, graph.getdistance(li, gao));
	}


}
