package P3;

import java.util.*;

public class FriendshipGraph {
	public int[][] graph;
	public List<Person> name = new ArrayList<>();
	private int n = 0;
	
	/*
	 * add person
	 * @param person
	 * 		the name you input;
	 * @return void
	 */
	public void addVertex(Person personx) {
		if(this.name.contains(personx)) {
			System.out.println("EXIST PERSON!");
			System.exit(0);
		}			
		this.name.add(this.n, personx);
		this.n = this.n + 1;
		this.graph = new int[n][n];
	}

	/*
	 * add edge between people.
	 * @param personx persony 
	 * 		the two person which has a relationship;
	 * @return void;
	 */
	public void addEdge(Person personx, Person persony) {
		int cordx = this.name.indexOf(personx);
		int cordy = this.name.indexOf(persony);
		this.graph[cordx][cordy] = 1;
	}

	/*
	 * initialize the matrix
	 * @param void
	 * @return void
	 */
	private void initialize() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (this.graph[i][j] == 0)
					this.graph[i][j] = 999999;
			}
		}
	}
	
	private int[][] floyd() {
		initialize();
		int result[][] = new int[n][n];
		int temp[][] = new int[n][n];
		int i, j, k;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				temp[i][j] = this.graph[i][j];
				result[i][j] = -1;
			}
		}
		for (k = 0; k < n; k++) {
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++) {
					if (temp[i][j] > temp[i][k] + temp[k][j])
						temp[i][j] = temp[i][k] + temp[k][j];
					result[i][j] = k;
				}
			}
		}
		return temp;
	}
	
	/*
	 * calculate the distance
	 * @param personx persony
	 * 		the two person you want to calculate distance
	 * @return the distance
	 */
	public int getdistance(Person personx, Person persony) {
		int[][] result = new int[n][n];
		result = floyd();
		int cordx = name.indexOf(personx), cordy = name.indexOf(persony);
		if(cordx==cordy)
			return 0;
		int finalresult = result[cordx][cordy];
		if (finalresult != 999999)
			return finalresult;
		else
			return -1;
	}
	

}
