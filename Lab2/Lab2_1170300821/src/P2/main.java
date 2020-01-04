package P2;

public class main {
	public static void main(String[] args) {
		Person wang=new Person("wang");
		Person ye =new Person("ye");
		Person gao=new Person("gao");
		Person li =new Person("li");
		FriendshipGraph graph=new FriendshipGraph();
		graph.addvertex(wang);
		graph.addvertex(ye);
		graph.addvertex(gao);
		graph.addvertex(li);
		graph.addEdge(wang, ye);
		graph.addEdge(ye, gao);
		graph.addEdge(gao, ye);
		System.out.println(graph.getDistance(wang, gao));
		System.out.println(graph.getDistance(wang, ye));
		System.out.println(graph.getDistance(gao, li));
	}
}
