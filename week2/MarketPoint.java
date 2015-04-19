package conpra;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MarketPoint {
	private final int INFINITY = Integer.MAX_VALUE;
	private HashMap<Integer, Integer> stores;
	private Map<Integer, Vertex> graph;

	static class Edge {
		int v1;
		int v2;
		int weight;

		public Edge(int v1, int v2, int w) {
			this.v1 = v1;
			this.v2 = v2;
			this.weight = w;
		}
	}

	static class Vertex implements Comparable<Vertex> {
		public final int id;
		String name;
		public int dist = Integer.MAX_VALUE;
		public boolean visited;
		public final Map<Vertex, Integer> neighbours = new HashMap<>();

		public Vertex(int v1) {
			this.id = v1;
			this.name = Integer.toString(id);
		}

		public int compareTo(Vertex other) {
			return Integer.compare(dist, other.dist);
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Vertex) {
				Vertex toCompare = (Vertex) o;
				return this.name.equals(toCompare.name);
			}
			return false;
		}

	}

	private String getShortestRoute(int numberOfCities, List<Edge> edges,
			HashMap<Integer, Integer> storeMap, int source, int destination) {

		// trivial stuffs
		if (storeMap.size() == 0) {
			return "not possible";
		}
		if (edges.size() == 0) {
			if (source != destination) {
				return "not possible";
			}
			if (storeMap.containsKey(source)) {
				int time = storeMap.get(source);
				String hour = String.format("%01d", (time / 60));
				String min = String.format("%02d", (time % 60));
				return hour + ":" + min;
			}
			return "not possible";
		}
		// end of trivial

		stores = storeMap;

		graph = new HashMap<Integer, Vertex>();

		// find all vertices
		for (Edge e : edges) {
			if (!graph.containsKey(e.v1))
				graph.put(e.v1, new Vertex(e.v1));
			if (!graph.containsKey(e.v2))
				graph.put(e.v2, new Vertex(e.v2));
		}
		// early break
		if (!graph.containsKey(source))
			return "not possible";
		if (!graph.containsKey(destination))
			return "not possbile";

		// neighbouring vertices
		for (Edge e : edges) {
			if (graph.get(e.v1).neighbours.containsKey(e.v2)) {
				if (e.weight < graph.get(e.v1).neighbours.get(e.v2)) {
					graph.get(e.v1).neighbours.put(graph.get(e.v2), e.weight);
				}
			} else {
				graph.get(e.v1).neighbours.put(graph.get(e.v2), e.weight);
			}
			if (graph.get(e.v2).neighbours.containsKey(e.v1)) {
				if (e.weight < graph.get(e.v2).neighbours.get(e.v1)) {
					graph.get(e.v2).neighbours.put(graph.get(e.v1), e.weight);
				}
			} else {
				graph.get(e.v2).neighbours.put(graph.get(e.v1), e.weight);
			}
		}
		// call dijkstra
		runSpanningTree(true, source, destination, stores.size(),
				numberOfCities, edges.size());
		int count = 0;
		// add new node and make a new edge with 0 weight
		graph.put(0, new Vertex(0));
		for (int i : stores.keySet()) {
			if (graph.containsKey(i) && graph.get(i).dist != INFINITY) {
				graph.get(0).neighbours.put(graph.get(i), stores.get(i)
						+ graph.get(i).dist);
				count++;
			}

		}
		// if none found return impossible
		if (count == 0) {
			return "not possible";
		}
		// call dijkstra again
		runSpanningTree(false, 0, destination, stores.size(),
				numberOfCities, edges.size());
		int time = graph.get(destination).dist;
		String hour = String.format("%01d", (time / 60));
		String min = String.format("%02d", (time % 60));
		return hour + ":" + min;
	}

	// get the spanning tree
	private void runSpanningTree(boolean b, int s, int d, int count,
			int numberOfCities, int edgeSize) {
		Vertex source = graph.get(s);
		PriorityQueue<Vertex> q = new PriorityQueue<Vertex>(edgeSize);

		for (Vertex v : graph.values()) {
			if (v.id == source.id) {
				v.dist = 0;
			} else {
				v.dist = INFINITY;
			}
			v.visited = false;
		}

		q.add(source);
		Vertex u, v;
		int counter = 0;
		while (!q.isEmpty()) {
			u = q.poll();
			u.visited = true;
			if (b) {
				if (stores.containsKey(u.id)) {
					counter += 1;
				}
				if (counter >= count)
					break;
			} else {
				if (u.id == d)
					break;
			}

			// look at distances to each neighbour
			for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
				v = a.getKey();
				if (!v.visited) {
					int alt = u.dist + a.getValue();
					if (alt < v.dist) {
						q.remove(v);
						v.dist = alt;
						q.add(v);
					}

				}
			}
		}
	}

	public static void main(String[] args) {
		// initialize variables
		List<Integer> numberOfCities = new ArrayList<Integer>();
		HashMap<Integer, Integer> stores;
		List<HashMap<Integer, Integer>> storesList = new ArrayList<HashMap<Integer, Integer>>();
		List<Edge> edges;
		List<List<Edge>> edgesList = new ArrayList<List<Edge>>();
		List<Integer> destination = new ArrayList<Integer>();
		List<Integer> source = new ArrayList<Integer>();
		// parse the input
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			String[] input = scanner.nextLine().split(" ");
			numberOfCities.add(i, Integer.parseInt(input[0]));
			int numberOfRoads = Integer.parseInt(input[1]);
			int numbeOfStore = Integer.parseInt(input[2]);
			source.add(i, Integer.parseInt(input[3]));
			destination.add(i, Integer.parseInt(input[4]));
			edges = new ArrayList<Edge>();
			stores = new HashMap<Integer, Integer>();
			for (int j = 0; j < numberOfRoads; j++) {
				String[] points = scanner.nextLine().split(" ");
				edges.add(new Edge(Integer.parseInt(points[0]), Integer
						.parseInt(points[1]), Integer.parseInt(points[2])));
			}
			for (int j = 0; j < numbeOfStore; j++) {
				String[] points = scanner.nextLine().split(" ");
				if (stores.containsKey(points[0])) {
					if (stores.get(Integer.parseInt(points[0])) > Integer
							.parseInt(points[1])) {
						stores.put(Integer.parseInt(points[0]),
								Integer.parseInt(points[1]));
					}
				} else {
					stores.put(Integer.parseInt(points[0]),
							Integer.parseInt(points[1]));
				}
			}
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			edgesList.add(i, edges);
			storesList.add(i, stores);
		}
		MarketPoint sm = new MarketPoint();
		String result = "";
		for (int i = 0; i < numberOfTests; i++) {
			result = sm.getShortestRoute(numberOfCities.get(i),
					edgesList.get(i), storesList.get(i), source.get(i),
					destination.get(i));
			System.out.println("Case #" + (i + 1) + ": " + result);
		}
	}
}
/* *shy* i give up...good night cruel world */
