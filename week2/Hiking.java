package conpra;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Hiking {
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

	private String getShortestPath(List<Edge> edges, int target) {
		
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(edges.size(),
				new Comparator<Edge>() {
					@Override
					public int compare(Edge e1, Edge e2) {
						return e1.weight - e2.weight;
					}
				});
		int[] previous = new int[target+1];
		boolean[] visited = new boolean[target+1];
		int[] distance = new int[target+1];
		int start = 0;

		for (int i = 0; i < target+1; i++) {
			if (i == start) {
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
			if (e.v1 == start || e.v2 == start) {
				pq.add(e);
			}
		}
		while (!pq.isEmpty()) {
			Edge min = pq.poll();
			if(visited[min.v1]){
				visited[min.v2] = true;
				distance[min.v2] = min.weight;
			}else{
				visited[min.v1] = true;
				distance[min.v1] = min.weight;
			}
			previous[min.v2] = min.v1;
			if (min.v2 == target || min.v1 == target) {
				break;
			}
			List<Edge> newlist = new ArrayList<Edge>();
			for (Edge e : edges) {
				if((visited[e.v1] && visited[e.v2]) || (!visited[e.v1] && !visited[e.v2]) ){
					continue;
				}else{
					if (visited[e.v1]){
						int w = distance[e.v1] + e.weight;
						newlist.add(new Edge(e.v1, e.v2, w));
					}else {
						int w = distance[e.v2] + e.weight;
						newlist.add(new Edge(e.v1, e.v2, w));
					}
				}

			}
			pq.clear();
			pq.addAll(newlist);
		}
		return distance[target]+"";
	}

	public static void main(String[] args) {
		Hiking hike = new Hiking();
		List<List<Edge>> edgesList = new ArrayList<List<Edge>>();
		List<Edge> edges;
		List<Integer> targets = new ArrayList<Integer>();
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			String[] input = scanner.nextLine().split(" ");
			targets.add(i, Integer.parseInt(input[0]) - 1);
			int noOfEdges = Integer.parseInt(input[1]);
			edges = new ArrayList<Edge>();
			for (int j = 0; j < noOfEdges; j++) {
				String[] points = scanner.nextLine().split(" ");
				edges.add(new Edge(Integer.parseInt(points[0]) - 1, Integer
						.parseInt(points[1]) - 1, Integer.parseInt(points[2])));
			}
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			edgesList.add(i, edges);
		}
		// pretty print
		for (int i = 0; i < edgesList.size(); i++) {
			String result = hike.getShortestPath(edgesList.get(i),
					targets.get(i));
			System.out.println("Case #" + (i + 1) + ": " + result);
		}
	}

}
