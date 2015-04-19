package b;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Networking {
	// Map to store parent and rank
	HashMap<String, String> parent;
	HashMap<String, Integer> rank;

	// Class for Edge
	static class Edge implements Comparable<Edge> {
		String vertex1;
		String vertex2;
		int weight;

		public Edge(String v1, String v2, int w) {
			this.vertex1 = v1;
			this.vertex2 = v2;
			this.weight = w;
		}

		@Override
		public String toString() {
			return vertex1 + " " + vertex2;

		}

		@Override
		public int compareTo(Edge o) {
			int firstV1 = Integer.parseInt(vertex1);
			int otherV1 = Integer.parseInt(o.vertex1);
			if(firstV1 == otherV1){
				return Integer.parseInt(vertex2) - Integer.parseInt(o.vertex2);
			}
			return firstV1 - otherV1;
		}
	}

	// Graph holding all edges and vertices
	static class Graph {
		List<String> vertices;
		List<Edge> edges;

		public Graph(List<String> v, List<Edge> e) {
			this.vertices = v;
			this.edges = e;
		}
	}

	// union function for set
	public void union(String root1, String root2) {
		if (rank.get(root1) > rank.get(root2)) {
			parent.put(root2, root1);
		} else {
			if (rank.get(root1) == rank.get(root2)) {
				int newRank = rank.get(root2) + 1;
				rank.put(root2, newRank);
			}
			parent.put(root1, root2);
		}
	}

	// find function for set
	public String find(String vertex) {
		if (vertex.equals(parent.get(vertex))) {
			return vertex;
		} else {
			return find(parent.get(vertex));
		}
	}

	// initialize each vertex
	public void makeSet(String vertex) {
		parent.put(vertex, vertex);
		rank.put(vertex, 0);
	}

	// kruskal algorithm implementation
	public List<List<Edge>> getShortestPath(List<Graph> graphList) {
		List<List<Edge>> result = new ArrayList<>();
		// for every test case
		for (Graph graph : graphList) {
			parent = new HashMap<String, String>();
			rank = new HashMap<String, Integer>();
			List<Edge> mst = new ArrayList<Edge>();

			// Initialize the set
			for (String vertex : graph.vertices) {
				makeSet(vertex);
			}
			// sort the edges with lowest weight first
			Collections.sort(graph.edges, new Comparator<Edge>() {
				@Override
				public int compare(Edge o1, Edge o2) {
					return o1.weight - o2.weight;
				}
			});
			// for each edge check if nodes are disjoint
			for (Edge e : graph.edges) {
				String root1 = find(e.vertex1);
				String root2 = find(e.vertex2);
				// if disjoint add to spanning tree
				// perform the union
				if (!root1.equals(root2)) {
					mst.add(e);
					union(root1, root2);
				}
			}
			result.add(mst);
		}
		return result;
	}

	public static void main(String[] args) {
		Networking network = new Networking();
		List<Graph> graphList = new ArrayList<Graph>();
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		List<String> vertices;
		List<Edge> edges;
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			vertices = new ArrayList<String>();
			edges = new ArrayList<Edge>();
			int numberOfVertices = scanner.nextInt();
			scanner.nextLine();
			for (int j = 0; j < numberOfVertices; j++) {
				String line = scanner.nextLine();
				String[] points = line.split(" ");
				vertices.add(j + 1 + "");
				// parse string and create edges
				// get only one half of matrix since other half is same
				for (int k = j + 1; k < points.length; k++) {
					int w = Integer.parseInt(points[k]);
					edges.add(new Edge(j + 1 + "", k + 1 + "", w));
				}
			}
			graphList.add(new Graph(vertices, edges));
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
		}
		// calculate the spanning tree using Kruskal
		int count = 1;
		// for every graphs generate MST
		List<List<Edge>> result = network.getShortestPath(graphList);
		// pretty print
		for (List<Edge> list : result ) {
			System.out.println("Case #" + count + ":");
			Collections.sort(list);
			for (Edge edge : list) {
				System.out.println(edge);
			}
			if (count < result.size()) {
				System.out.println();
				count++;
			}

		}
	}
}
