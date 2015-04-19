package b;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class TravelTrouble {

	static class Edge {
		int node;
		int weight;

		public Edge(int n, int w) {
			this.node = n;
			this.weight = w;
		}
	}

	/*
	 * Used Disjkstra algorithm, followed Wikipedia pseudo code.
	 * http://en.wikipedia.org/wiki/Dijkstra's_algorithm#Pseudocode
	 */
	
	private String getShortestPath(int[][] adjacencyMatrix) {
		if (adjacencyMatrix.length == 1) {
			return "1";
		}
		// starting with 1 as start point
		int start = 0;
		int target = adjacencyMatrix.length - 1;
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(
				(adjacencyMatrix.length - 1), new Comparator<Edge>() {
					@Override
					public int compare(Edge e1, Edge e2) {
						return e1.weight - e2.weight;
					}
				});
		int[] previous = new int[adjacencyMatrix.length];
		boolean[] visited = new boolean[adjacencyMatrix.length];
		// initialize PQ, visited array, and previous array
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			pq.add(new Edge(i, adjacencyMatrix[start][i]));
			visited[i] = false;
			if (i == 0) {
				previous[i] = 0;
			} else {
				previous[i] = -1;
			}
		}
		// while PQ is not empty
		while (!pq.isEmpty()) {
			Edge e = pq.poll();
			visited[e.node] = true;
			// short circuit if target hit
			if (e.node == target) {
				break;
			}
			List<Edge> newlist = new ArrayList<Edge>();
			// loop through remaining PQ to see if new shortest path is found
			// if found use the new weight to create the new edge
			for (Edge edge : pq) {
				if (!visited[edge.node]) {
					int alt = e.weight + adjacencyMatrix[e.node][edge.node];
					// if new path is shortest update else remains same
					if (alt <= edge.weight) {
						// trace the path
						previous[edge.node] = e.node;
						newlist.add(new Edge(edge.node, alt));
					} else {
						newlist.add(new Edge(edge.node, edge.weight));
					}
				}

			}
			// update the PQ
			pq.clear();
			pq.addAll(newlist);
		}
		// processing of result
		StringBuffer sb = new StringBuffer();
		Stack<String> st = new Stack<String>();
		do {
			st.push(target + 1 + "");
			target = previous[target];
		} while (target != -1 && target != 0);
		st.push("1");
		for (int i = st.size() - 1; i >= 0; i--) {
			sb.append(st.get(i) + " ");
		}
		return sb.toString().trim();
	}

	public static void main(String[] args) {
		TravelTrouble tt = new TravelTrouble();
		List<int[][]> adjList = new ArrayList<int[][]>();
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			int numberOfVertices = scanner.nextInt();
			scanner.nextLine();
			int[][] adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
			for (int j = 0; j < numberOfVertices; j++) {
				String[] points = scanner.nextLine().split(" ");
				// parse string and create adjacency matrix
				for (int k = 0; k < points.length; k++) {
					int w = Integer.parseInt(points[k]);
					adjacencyMatrix[j][k] = w;
				}
			}
			adjList.add(adjacencyMatrix);
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
		}
		// pretty print
		int count = 1;
		for (int[][] adj : adjList) {
			System.out.println("Case #" + count + ":");
			System.out.println(tt.getShortestPath(adj));
			count++;
		}
	}
}
