package P3;

public class main {
	public static void main(String[] args) {
		Person wang=new Person("wang");
		Person ye =new Person("ye");
		Person gao=new Person("gao");
		Person li =new Person("li");
		FriendshipGraph graph=new FriendshipGraph();
		graph.addVertex(wang);
		graph.addVertex(ye);
		graph.addVertex(gao);
		graph.addVertex(li);
		graph.addEdge(wang, ye);
		//graph.addEdge(ye, wang);
		graph.addEdge(ye, gao);
		graph.addEdge(gao, ye);
		System.out.println(graph.getdistance(wang, gao));
		System.out.println(graph.getdistance(wang, wang));
		System.out.println(graph.getdistance(wang, ye));
		System.out.println(graph.getdistance(gao, li));
	}
}
