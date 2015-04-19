package conpra;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;

public class Supermarket {
	private final int INFINITY = Integer.MAX_VALUE;
	private int MAXIMUM_SHOP_TIME = 1000000;
	public HashMap<Integer, Integer> storesMap;

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

	private String getShortestRoute(Integer numberOfCities, List<Edge> edges,
			HashMap<Integer, List<Integer>> storeMap, Integer source,
			Integer destination) {
		if (storeMap.size() == 0) {
			return "not possible";
		}
		storesMap = new HashMap<Integer, Integer>();
		// getting minimum valued store of one single place to one hashMap
		for (Entry<Integer, List<Integer>> storeEntry : storeMap.entrySet()) {
			Collections.sort(storeEntry.getValue());
			storesMap.put(storeEntry.getKey(), storeEntry.getValue().get(0));
		}

		Queue<Integer> q = new LinkedList<Integer>();
		int[] previous = new int[numberOfCities + 1];
		int[] weight = new int[numberOfCities + 1];
		boolean[] visited = new boolean[numberOfCities + 1];
		for (int i = 0; i < numberOfCities + 1; i++) {
			if (i == source) {
				previous[i] = -1;
				visited[i] = true;
				if (storesMap.containsKey(source)) {
					weight[i] = storesMap.get(source);
				} else {
					weight[i] = MAXIMUM_SHOP_TIME;
				}

			} else {
				previous[i] = -1;
				weight[i] = INFINITY;
				visited[i] = false;
			}
		}
		q.add(source);
		while (!q.isEmpty()) {
			int w = q.poll();
			// if destination found
//			if (w == destination) {
//				break;
//			}
			// else check all neighbors
			// update the variables weight, previous accordingly
			for (Edge e : edges) {
				if ((visited[e.v1] && visited[e.v2])
						|| (!visited[e.v1] && !visited[e.v2])) {
					continue;
				} else {
					if (e.v1 == w && !visited[e.v2]) {
						// if next node v2 has stores,
						// and the weight of store is less
						// update the weight of node accordingly
						if (storesMap.containsKey(e.v2)) {
							int currentStore = storesMap.get(e.v2);
							int previousStore = checkPreviousStore(e.v1,
									previous);
							if (currentStore <= previousStore) {
								if (weight[e.v2] > ((weight[w] - previousStore)
										+ e.weight + currentStore)) {
									weight[e.v2] = ((weight[w] - previousStore)
											+ e.weight + currentStore);
									previous[e.v2] = e.v1;
									visited[e.v2] = true;
									q.add(e.v2);
									continue;
								}
							}
						}
						// else normal Bellman
						if (weight[e.v2] > weight[w] + e.weight) {
							weight[e.v2] = weight[w] + e.weight;
							previous[e.v2] = e.v1;
							visited[e.v2] = true;
							q.add(e.v2);
						}
					} else if (e.v2 == w && !visited[e.v1]) { // same if next node is v1
						if (storesMap.containsKey(e.v1)) {
							int currentStore = storesMap.get(e.v1);
							int previousStore = checkPreviousStore(e.v2,
									previous);
							if (currentStore <= previousStore) {
								if (weight[e.v1] > ((weight[w] - previousStore)
										+ e.weight + currentStore)) {
									weight[e.v1] = ((weight[w] - previousStore)
											+ e.weight + currentStore);
									previous[e.v1] = e.v2;
									q.add(e.v1);
									visited[e.v1] = true;
									continue;
								}
							}
						}
						if (weight[e.v1] > weight[w] + e.weight) {
							weight[e.v1] = weight[w] + e.weight;
							previous[e.v1] = e.v1;
							visited[e.v1] = true;
							q.add(e.v1);
						}
					} else {
						continue;
					}
				}
			}

		}

		int minimum_amount = weight[destination];
		if (minimum_amount == INFINITY || minimum_amount > MAXIMUM_SHOP_TIME) {
			return "not possible";
		}
		String hour = String.format("%01d", (minimum_amount / 60));
		String min = String.format("%02d", (minimum_amount % 60));
		return hour + ":" + min;
	}

	private int checkPreviousStore(int v, int[] previous) {
		// get the previous minimum store value in path taken
		int pointer = v;
		int min = MAXIMUM_SHOP_TIME;
		do {
			if (storesMap.containsKey(pointer)) {
				if (storesMap.get(pointer) < min) {
					min = storesMap.get(pointer);
				}
			}
			pointer = previous[pointer];
		} while ((pointer != -1));
		return min;
	}

	public static void main(String[] args) {
		List<Integer> numberOfCities = new ArrayList<Integer>();
		HashMap<Integer, List<Integer>> stores;
		List<HashMap<Integer, List<Integer>>> storesList = new ArrayList<HashMap<Integer, List<Integer>>>();
		List<Edge> edges;
		List<List<Edge>> edgesList = new ArrayList<List<Edge>>();
		List<Integer> destination = new ArrayList<Integer>();
		List<Integer> source = new ArrayList<Integer>();
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
			stores = new HashMap<Integer, List<Integer>>();
			// putting all network with same key in format "uv"
			// where u < v as roads can be used in both direction
			// i.e "1 2" and "2 1" is treated as same and only "12" is used
			for (int j = 0; j < numberOfRoads; j++) {
				String[] points = scanner.nextLine().split(" ");
				edges.add(new Edge(Integer.parseInt(points[0]), Integer
						.parseInt(points[1]), Integer.parseInt(points[2])));
			}
			for (int j = 0; j < numbeOfStore; j++) {
				String[] points = scanner.nextLine().split(" ");
				if (stores.containsKey(points[0])) {
					stores.get(Integer.parseInt(points[0])).add(
							Integer.parseInt(points[1]));
				} else {
					List<Integer> list = new ArrayList<Integer>();
					list.add(Integer.parseInt(points[1]));
					stores.put(Integer.parseInt(points[0]), list);
				}
			}
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			edgesList.add(i, edges);
			storesList.add(i, stores);
		}
		// applying the function
		Supermarket sm = new Supermarket();
		String result = "";
		for (int i = 0; i < numberOfTests; i++) {
			result = sm.getShortestRoute(numberOfCities.get(i),
					edgesList.get(i), storesList.get(i), source.get(i),
					destination.get(i));
			System.out.println("Case #" + (i + 1) + ": " + result);
		}
	}

}