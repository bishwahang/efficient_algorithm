package b;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Singularity {
	// Map to store parent and rank
	HashMap<String, String> parent;
	HashMap<String, Integer> rank;

	static class Edge {
		String vertex1;
		String vertex2;
		int weight;

		public Edge(String v1, String v2, int w) {
			this.vertex1 = v1;
			this.vertex2 = v2;
			this.weight = w;
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

	private String deriveMST(ArrayList<Edge> edges, int noOfPeoples) {
		List<List<Edge>> result = new ArrayList<>();
		parent = new HashMap<String, String>();
		rank = new HashMap<String, Integer>();
		List<Edge> mst = new ArrayList<Edge>();
		// Initialize the set
		for (int i = 1; i <= noOfPeoples; i++) {
			makeSet(i + "");
		}
		// sort the edges with lowest weight first
		Collections.sort(edges, new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.weight - o2.weight;
			}
		});
		for (Edge e : edges) {
			String root1 = find(e.vertex1);
			String root2 = find(e.vertex2);
			// if disjoint add to spanning tree
			if (!root1.equals(root2)) {
				mst.add(e);
				union(root1, root2);
			}
		}
		int total = 0;
		// since Edges is anlways number of vertex - 1
		// anything else means all vertex has not been visited
		if(mst.size() == (noOfPeoples -1)){
			for (Edge edge : mst) {
				total += edge.weight;
			}
			return total+"";
		}
		return "not possible";
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		Singularity sn = new Singularity();
		List<ArrayList<Edge>> edgeList = new ArrayList<ArrayList<Edge>>();
		List<String> result = new ArrayList<String>();
		List<Integer> peoples = new ArrayList<Integer>();
		ArrayList<Edge> edges;
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			peoples.add(scanner.nextInt());
			int numberOfPairs = scanner.nextInt();
			scanner.nextLine();
			edges = new ArrayList<Edge>();
			for (int j = 1; j <= numberOfPairs; j++) {
				String line = scanner.nextLine();
				// parse string and create edges
				String[] points = line.split(" ");
				edges.add(new Edge(points[0], points[1], Integer
						.parseInt(points[2])));
			}
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			edgeList.add(edges);
		}
		// for every input edges generate MST
		for (int i = 0; i < edgeList.size(); i++) {
			result.add(sn.deriveMST(edgeList.get(i), peoples.get(i)));
		}
		int count = 1;
		// pretty print
		for (String res : result) {
			System.out.println("Case #" + count + ": " + res);
			count++;
		}
	}

}
