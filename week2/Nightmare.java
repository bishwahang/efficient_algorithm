package conpra;

import java.io.InputStreamReader;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Nightmare {

	static class Edge {
		String id;
		int v1;
		int v2;
		int weight;
		int base;

		public Edge(String id, int v1, int v2, int w) {
			this.id = id;
			this.v1 = v1;
			this.v2 = v2;
			this.weight = w;
			this.base = -2;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Edge) {
				Edge toCompare = (Edge) o;
				return this.id.equals(toCompare.id);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return id.hashCode();
		}
	}

	// 	http://en.wikipedia.org/wiki/Suurballe%27s_algorithm#Algorithm
	private String getShortestRoute(Integer nodes, List<Edge> edges,
			Integer source, Integer destination) {
		if (source == destination) {
			return "0";
		}
		if (edges.size() == 0) {
			return "not possible";
		}
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(edges.size(),
				new Comparator<Edge>() {
					@Override
					public int compare(Edge e1, Edge e2) {
						return e1.weight - e2.weight;
					}
				});
		int result = 0;
		int[] previous = new int[nodes];
		boolean[] visited = new boolean[nodes];
		int[] distance = new int[nodes];
		Edge[] edgeID = new Edge[nodes];
		for (int i = 0; i < nodes; i++) {
			edgeID[i] = null;
			if (i == source) {
				visited[i] = true;
				previous[i] = -1;
				distance[i] = 0;
			} else {
				visited[i] = false;
				previous[i] = -1;
				distance[i] = -1;
			}
		}

		for (Edge e : edges) {
			if (e.v1 == source || e.v2 == source) {
				pq.add(e);
			}
		}
		List<Edge> mst = new ArrayList<Edge>();
		while (!pq.isEmpty()) {
			Edge min = pq.poll();
			if (visited[min.v1]) {
				visited[min.v2] = true;
				distance[min.v2] = min.weight;
				previous[min.v2] = min.v1;
				edgeID[min.v2] = new Edge(min.id, min.v1, min.v2, min.weight);
			} else {
				visited[min.v1] = true;
				distance[min.v1] = min.weight;
				previous[min.v1] = min.v2;
				edgeID[min.v1] = new Edge(min.id, min.v2, min.v1, min.weight);
			}
			mst.add(min);
			// if (min.v2 == destination || min.v1 == destination) {
			// break;
			// }
			List<Edge> newlist = new ArrayList<Edge>();
			for (Edge e : edges) {
				if ((visited[e.v1] && visited[e.v2])
						|| (!visited[e.v1] && !visited[e.v2])) {
					continue;
				} else {
					if (visited[e.v1]) {
						int w = distance[e.v1] + e.weight;
						newlist.add(new Edge(e.id, e.v1, e.v2, w));
					} else {
						int w = distance[e.v2] + e.weight;
						newlist.add(new Edge(e.id, e.v1, e.v2, w));
					}
				}

			}
			pq.clear();
			pq.addAll(newlist);
		}
		if (distance[destination] == -1) {
			return "not possible";
		}
		int current = destination;
		List<Edge> temp = new ArrayList<Edge>();
		List<Edge> first = new ArrayList<Edge>();
		do {
			Edge e = edgeID[current];

			if (e != null) {
				e.weight = 0;
				e.base = current;
				temp.add(e);
			}
			current = previous[current];
		} while (current != -1);
		
		for (Edge edge : edges) {
			if(temp.contains(edge)){
				first.add(edge);
			}
		}
		
		for (Edge e : temp) {
			if(edges.contains(e)){
				edges.remove(e);
				edges.add(e);
			}
		}

		// start
		pq = new PriorityQueue<Edge>(edges.size(), new Comparator<Edge>() {
			@Override
			public int compare(Edge e1, Edge e2) {
				return e1.weight - e2.weight;
			}
		});
		for (int i = 0; i < nodes; i++) {
			edgeID[i] = null;
			if (i == source) {
				visited[i] = true;
				previous[i] = -1;
				distance[i] = 0;
			} else {
				visited[i] = false;
				previous[i] = -1;
				distance[i] = -1;
			}
		}

		for (Edge e : edges) {
			if (e.v1 == source || e.v2 == source) {
				if (e.base == -2) {
					pq.add(e);
				} else {
					if (e.base == source) {
						pq.add(e);
					}
				}

			}
		}
		mst = new ArrayList<Edge>();
		while (!pq.isEmpty()) {
			Edge min = pq.poll();
			if (visited[min.v1]) {
				visited[min.v2] = true;
				distance[min.v2] = min.weight;
				previous[min.v2] = min.v1;
				edgeID[min.v2] = new Edge(min.id, min.v1, min.v2, min.weight);
//				pointer = min.v2;
			} else {
				visited[min.v1] = true;
				distance[min.v1] = min.weight;
				previous[min.v1] = min.v2;
				edgeID[min.v1] = new Edge(min.id, min.v2, min.v1, min.weight);
//				pointer = min.v1;
			}
			mst.add(min);
			// if (min.v2 == destination || min.v1 == destination) {
			// break;
			// }
			List<Edge> newlist = new ArrayList<Edge>();
			for (Edge e : edges) {
				if ((visited[e.v1] && visited[e.v2])
						|| (!visited[e.v1] && !visited[e.v2])) {
					continue;
				} else {
					if (visited[e.v1]) {
						if (e.base == -2) {
							int w = distance[e.v1] + e.weight;
							newlist.add(new Edge(e.id, e.v1, e.v2, w));
						} else {
							if (e.base == e.v1) {
								int w = distance[e.v1] + e.weight;
								newlist.add(new Edge(e.id, e.v1, e.v2, w));
							}

						}

					} else {
						if (e.base == -2) {
							int w = distance[e.v2] + e.weight;
							newlist.add(new Edge(e.id, e.v1, e.v2, w));
						} else {
							if (e.base == e.v2) {
								int w = distance[e.v2] + e.weight;
								newlist.add(new Edge(e.id, e.v1, e.v2, w));
							}

						}

					}
				}

			}
			pq.clear();
			pq.addAll(newlist);
		}
		if (distance[destination] == -1) {
			return "not possible";
		}
		List<Edge> second = new ArrayList<Edge>();
		current = destination;
		do {
			Edge e = edgeID[current];

			if (e != null) {
				second.add(e);

			}
			current = previous[current];
		} while (current != -1);
		for (Edge edge : edges) {
			if(second.contains(edge)){
				second.remove(edge);
				second.add(edge);
			}
		}
		List<Edge> dup = new ArrayList<Edge>();
		for (Edge e : second) {
			if (first.contains(e)) {
				dup.add(e);
			}
		}
		for (Edge edge : dup) {
			first.remove(edge);
			second.remove(edge);
		}
		for (Edge e : first) {
			result += e.weight;
		}
		for (Edge e : second) {
			result += e.weight;
		}
		return result + "";
	}

	public static void main(String[] args) {
		List<Integer> numberOfNodes = new ArrayList<Integer>();
		List<Edge> edges;
		List<List<Edge>> edgesList = new ArrayList<List<Edge>>();
		List<Integer> destination = new ArrayList<Integer>();
		List<Integer> source = new ArrayList<Integer>();
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			String[] input = scanner.nextLine().split(" ");
			numberOfNodes.add(i, Integer.parseInt(input[0]));
			int numberOfEdge = Integer.parseInt(input[1]);
			source.add(i, Integer.parseInt(input[2]) - 1);
			destination.add(i, Integer.parseInt(input[3]) - 1);
			edges = new ArrayList<Edge>();
			for (int j = 0; j < numberOfEdge; j++) {
				String[] points = scanner.nextLine().split(" ");
				edges.add(new Edge(j + "", Integer.parseInt(points[0]) - 1,
						Integer.parseInt(points[1]) - 1, Integer
								.parseInt(points[2])));
			}
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			edgesList.add(i, edges);
		}
		// applying the function
		Nightmare sm = new Nightmare();
		String result = "";
		for (int i = 0; i < numberOfTests; i++) {
			result = sm.getShortestRoute(numberOfNodes.get(i),
					edgesList.get(i), source.get(i), destination.get(i));
			System.out.println("Case #" + (i + 1) + ": " + result);
		}
	}

}
