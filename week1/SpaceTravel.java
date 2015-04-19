package b;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author hang
 *
 */
public class SpaceTravel {
	static class Vertice {
		int id, x, y, z;

		public Vertice(int id, int x, int y, int z) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.z = z;
		}

	}

	static class Edge {
		int weight;
		int name;

		public Edge(int weight, int name) {
			this.weight = weight;
			this.name = name;
		}
	}
	// comparator for PQ
	static class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge e1, Edge e2) {
			return e1.weight - e2.weight;
		}
	}

	private String deriveMST(ArrayList<Vertice> vertices) {
		if (vertices.size() == 1)
			return 0 + "";
		int[][] adjacencyMatrix = new int[vertices.size()][vertices.size()];
		// creation of adjacency matrix
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix.length; j++) {
				if (i == j) {
					adjacencyMatrix[i][j] = 0;
				} else {
					Vertice n1 = vertices.get(i);
					Vertice n2 = vertices.get(j);
					int edge = Math.abs(n1.x - n2.x) + Math.abs(n1.y - n2.y)
							+ Math.abs(n1.z - n2.z);
					adjacencyMatrix[i][j] = edge;
				}
			}
		}
		int start = 0;
		Comparator<Edge> comparator = new EdgeComparator();
		// initailize PQ with custom comparator
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>((vertices.size() - 1),
				comparator);
		// populate PQ with possible edges from first vertex
		for (Vertice b : vertices) {
			int weight = adjacencyMatrix[start][b.id];
			Edge e = new Edge(weight, b.id);
			pq.add(e);
		}
		int result = 0;
		// while not all vertex are visited
		while (!pq.isEmpty()) {
			// get the edge with lowest weight
			// and add the weight
			Edge e = pq.poll();
			result += e.weight;
			List<Edge> newlist = new ArrayList<Edge>();
			// loop thoruh remaining PQ to see if new shortest path is found
			// if found use the new weight to create the new edge
			for (Edge edge : pq) {
				int w = adjacencyMatrix[e.name][edge.name] < edge.weight ? adjacencyMatrix[e.name][edge.name]
						: edge.weight;
				int n = edge.name;
				newlist.add(new Edge(w, n));
			}
			// update the PQ
			pq.clear();
			pq.addAll(newlist);
		}
		return result + "";
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		SpaceTravel st = new SpaceTravel();
		List<ArrayList<Vertice>> verticeList = new ArrayList<ArrayList<Vertice>>();
		List<String> result = new ArrayList<String>();
		ArrayList<Vertice> vertices;
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			int numberOfVertices = scanner.nextInt();
			scanner.nextLine();
			vertices = new ArrayList<Vertice>();
			for (int j = 0; j < numberOfVertices; j++) {
				String line = scanner.nextLine();
				String[] points = line.split(" ");
				// parse string and create vertices
				vertices.add(new Vertice(j, Integer.parseInt(points[0]),
						Integer.parseInt(points[1]), Integer
								.parseInt(points[2])));
			}

			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			verticeList.add(vertices);
		}
		// for every input vertexes generate MST
		for (ArrayList<Vertice> v : verticeList) {
			result.add(st.deriveMST(v));
		}
		int count = 1;
		// pretty print
		for (String res : result) {
			System.out.println("Case #" + count + ": " + res);
			count++;
		}
	}
}