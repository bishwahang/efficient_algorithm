package b;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class JungleNetwork {
	static class Vertice {
		int id, x, y, power;

		public Vertice(int id, int x, int y, int p) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.power = p;
		}

	}

	static class Edge {
		int weight;
		Vertice v1;
		Vertice v2;

		public Edge(int weight, Vertice v1, Vertice v2) {
			this.weight = weight;
			this.v1 = v1;
			this.v2 = v2;
		}
	}

	static class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge e1, Edge e2) {
			return e1.weight - e2.weight;
		}
	}

	private String deriveMST(ArrayList<Vertice> vertices) {
		// create adjacency matrix
		int[][] adjacencyMatrix = new int[vertices.size()][vertices.size()];
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix.length; j++) {
				// same node distance 0
				if (i == j) {
					adjacencyMatrix[i][j] = 0;
				} else {
					// get distance between two nodes and square to get power
					// consumption
					Vertice n1 = vertices.get(i);
					Vertice n2 = vertices.get(j);
					int power = (int) (Math.pow((n2.x - n1.x), 2) + Math.pow(
							(n2.y - n1.y), 2));
					/*
					 * if power required is enough add it else add more than
					 * maximum that can be generated in our case (-100, 100)
					 * coordinates
					 * 
					 * the trick here is to make the weight of the edge more
					 * than any possible so that the sorting is as expected with
					 * lowest possible first
					 */

					if (power <= Math.min(n1.power, n2.power)) {
						adjacencyMatrix[i][j] = power;
					} else {
						adjacencyMatrix[i][j] = 100000;
					}
				}
			}
		}
		Vertice start = vertices.get(0);
		Comparator<Edge> comparator = new EdgeComparator();
		// PQ initialization with custom comparator
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>((vertices.size() - 1),
				comparator);
		// populate PQ with possible edges from first vertex
		for (Vertice b : vertices) {
			if (b.id == start.id)
				continue;
			int weight = adjacencyMatrix[start.id][b.id];
			Edge e = new Edge(weight, start, b);
			pq.add(e);
		}

		List<Edge> mst = new ArrayList<Edge>();
		while (!pq.isEmpty()) {
			// get the edge with lowest weight
			Edge e = pq.poll();
			mst.add(e);
			List<Edge> newlist = new ArrayList<Edge>();
			// loop thoruh remaining PQ to see if new shortest path is found
			// if found use the new weight to create the new edge
			for (Edge edge : pq) {
				int w = adjacencyMatrix[e.v2.id][edge.v2.id] < edge.weight ? adjacencyMatrix[e.v2.id][edge.v2.id]
						: edge.weight;
				newlist.add(new Edge(w, e.v2, edge.v2));
			}
			pq.clear();
			pq.addAll(newlist);
		}

		int result = 0;
		for (Edge edge : mst) {
			// so if any edge still has the more than maximum weight
			// we consider that node was not possbile to reach
			// via any of edges.
			if (edge.weight == 100000) {
				return "not possible";
			}
			// power consumed by both vertex for one edge
			result += 2 * edge.weight;
		}
		return result + "";

	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		JungleNetwork jn = new JungleNetwork();
		List<ArrayList<Vertice>> verticeList = new ArrayList<ArrayList<Vertice>>();
		List<String> result = new ArrayList<String>();
		ArrayList<Vertice> vertices;
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			int numberOfVertices = scanner.nextInt();
			scanner.nextLine();
			vertices = new ArrayList<Vertice>();
			vertices.add(new Vertice(0, 0, 0, 50000));
			// parse string and create vertices
			for (int j = 1; j <= numberOfVertices; j++) {
				String line = scanner.nextLine();
				String[] points = line.split(" ");
				vertices.add(new Vertice(j, Integer.parseInt(points[0]),
						Integer.parseInt(points[1]), Integer
								.parseInt(points[2])));
			}

			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			verticeList.add(vertices);
		}
		// for every input vertex generate MST
		for (ArrayList<Vertice> v : verticeList) {
			result.add(jn.deriveMST(v));
		}
		int count = 1;
		// pretty print
		for (String res : result) {
			System.out.println("Case #" + count + ": " + res);
			count++;
		}
	}
}
