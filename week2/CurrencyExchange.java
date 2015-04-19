package conpra;

import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class CurrencyExchange {
	private final int INFINITY = Integer.MAX_VALUE;

	static class Edge {
		int v1;
		int v2;
		double weight;

		public Edge(int v1, int v2, double d) {
			this.v1 = v1;
			this.v2 = v2;
			this.weight = d;
		}
	}

	private String getExchangeRate(List<Edge> edges, int noOfVertice) {
		Queue<Integer> q = new LinkedList<Integer>();
		int[] previous = new int[noOfVertice];
		double[] weight = new double[noOfVertice];
		int start = 0;

		for (int i = 0; i < noOfVertice; i++) {
			if (i == start) {
				previous[i] = -1;
				weight[i] = 0;
			} else {
				previous[i] = -1;
				weight[i] = INFINITY;
			}
		}
		// Wikipedia way
//		for (int i = 0; i < noOfVertice - 1; i++) {
//			for (Edge e : edges) {
//				if (weight[e.v1] + e.weight < weight[e.v2]) {
//					weight[e.v2] = weight[e.v1] + e.weight;
//					previous[e.v2] = e.v1;
//				}
//			}
//		}
//		for (Edge e : edges) {
//			if (weight[e.v1] + e.weight < weight[e.v2]) {
//				return "Jackpot";
//			}
//
//		}
		
		// Lecture way
		q.add(start);
		while (!q.isEmpty()) {
			int w = q.poll();
			for (Edge e : edges) {
				if (e.v1 == w) {
					if (weight[e.v2] > weight[w] + e.weight) {
						if(checkCycle(e.v1, e.v2, previous)){
							return "Jackpot";
						}
						weight[e.v2] = weight[w] + e.weight;
						previous[e.v2] = e.v1;
						q.add(e.v2);
					}
				}
			}
		}
		int dest = noOfVertice - 1;
		do {
			dest = previous[dest];
		} while ((dest != -1) && (dest != 0));
		if (dest == -1) {
			return "impossible";
		}
		return new DecimalFormat("#0.000000").format(Math.pow(10,
				weight[noOfVertice - 1]));
	}

	private boolean checkCycle(int v1, int v2, int[] previous) {
		int dest = v2;
		do {
			dest = previous[dest];
		} while ((dest != -1) && (dest != v1));
		if(dest == v1){
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		CurrencyExchange ce = new CurrencyExchange();
		List<List<Edge>> edgesList = new ArrayList<List<Edge>>();
		List<Edge> edges;
		List<Integer> noOfVertices = new ArrayList<Integer>();
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			String[] input = scanner.nextLine().split(" ");
			noOfVertices.add(i, Integer.parseInt(input[0]));
			int noOfEdges = Integer.parseInt(input[1]);
			edges = new ArrayList<Edge>();
			for (int j = 0; j < noOfEdges; j++) {
				String[] points = scanner.nextLine().split(" ");
				edges.add(new Edge(Integer.parseInt(points[0]) - 1, Integer
						.parseInt(points[1]) - 1, Math.log10(Double
						.parseDouble(points[2]))));
			}
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			edgesList.add(i, edges);
		}
		// pretty print
		for (int i = 0; i < edgesList.size(); i++) {
			String result = ce.getExchangeRate(edgesList.get(i),
					noOfVertices.get(i));
			System.out.println("Case #" + (i + 1) + ": " + result);
		}
	}

}
