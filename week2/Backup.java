package conpra;

public class Backup {

}
//package conpra;
//
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map.Entry;
//import java.util.PriorityQueue;
//import java.util.Scanner;
//
//public class Supermarket {
//	private final int INFINITY = Integer.MAX_VALUE;
//
//	static class Edge {
//		int v1;
//		int v2;
//		int weight;
//
//		public Edge(int v1, int v2, int w) {
//			this.v1 = v1;
//			this.v2 = v2;
//			this.weight = w;
//		}
//	}
//
//	private String getShortestRoute(Integer numberOfCities,
//			HashMap<Integer, List<Integer>> roadMap,
//			HashMap<Integer, List<Integer>> storeMap, Integer source,
//			Integer destination) {
//		List<Edge> edges = new ArrayList<Edge>();
//		// finding the lowest path between two cities and keeping it only
//		for (Entry<Integer, List<Integer>> roadEntry : roadMap.entrySet()) {
//			Collections.sort(roadEntry.getValue());
//			int k = roadEntry.getKey();
//			edges.add(new Edge(k / 10, k % 10, roadEntry.getValue().get(0)));
//		}
//
//		List<Integer> timesList = new ArrayList<Integer>();
//		// for each stores in a city, get the store with shortest time
//		// find the time to each store and to gea house
//		for (Entry<Integer, List<Integer>> storeEntry : storeMap.entrySet()) {
//			Collections.sort(storeEntry.getValue());
//			int timeToBuy = storeEntry.getValue().get(0);
//			int storeCity = storeEntry.getKey();
//			int timeToStore = getTime(source, storeCity, numberOfCities, edges);
//			if (timeToStore == -1) {
//				continue;
//			}
//			int timeToHome = getTime(storeCity, destination, numberOfCities,
//					edges);
//			if (timeToHome == -1) {
//				continue;
//			}
//			timesList.add(timeToStore + timeToBuy + timeToHome);
//		}
//		// if none found return impossible
//		if (timesList.size() == 0) {
//			return "not possible";
//		}
//		// get the shortest time
//		Collections.sort(timesList);
//		// format the output
//		int time = timesList.get(0);
//		String hour = String.format("%01d", (time / 60));
//		String min = String.format("%02d", (time % 60));
//		return hour + ":" + min;
//	}
//
//	// shortest path implementation
//	private int getTime(Integer source, Integer destination,
//			Integer numberOfCities, List<Edge> edges) {
//		if (source == destination) {
//			return 0;
//		}
//		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(edges.size(),
//				new Comparator<Edge>() {
//					@Override
//					public int compare(Edge e1, Edge e2) {
//						return e1.weight - e2.weight;
//					}
//				});
//		int[] previous = new int[numberOfCities + 1];
//		boolean[] visited = new boolean[numberOfCities + 1];
//		int[] distance = new int[numberOfCities + 1];
//		int start = source;
//		for (int i = 0; i < numberOfCities + 1; i++) {
//			if (i == start) {
//				visited[i] = true;
//				previous[i] = -1;
//				distance[i] = 0;
//			} else {
//				visited[i] = false;
//				previous[i] = -1;
//				distance[i] = -1;
//			}
//		}
//		for (Edge r : edges) {
//			if (r.v1 == start || r.v2 == start) {
//				pq.add(r);
//			}
//		}
//		while (!pq.isEmpty()) {
//			Edge min = pq.poll();
//			if (visited[min.v1]) {
//				visited[min.v2] = true;
//				distance[min.v2] = min.weight;
//				previous[min.v2] = min.v1;
//			} else {
//				visited[min.v1] = true;
//				distance[min.v1] = min.weight;
//				previous[min.v1] = min.v2;
//			}
//			if (min.v2 == destination || min.v1 == destination) {
//				break;
//			}
//			List<Edge> newlist = new ArrayList<Edge>();
//			for (Edge e : edges) {
//				if ((visited[e.v1] && visited[e.v2])
//						|| (!visited[e.v1] && !visited[e.v2])) {
//					continue;
//				} else {
//					if (visited[e.v1]) {
//						int w = distance[e.v1] + e.weight;
//						newlist.add(new Edge(e.v1, e.v2, w));
//					} else {
//						int w = distance[e.v2] + e.weight;
//						newlist.add(new Edge(e.v1, e.v2, w));
//					}
//				}
//
//			}
//			pq.clear();
//			pq.addAll(newlist);
//		}
//		return distance[destination];
//	}
//
//	public static void main(String[] args) {
//		List<Integer> numberOfCities = new ArrayList<Integer>();
//		// List<List<Edge>> edgesList = new ArrayList<List<Edge>>();
//		// List<Edge> roads;
//		HashMap<Integer, List<Integer>> stores;
//		List<HashMap<Integer, List<Integer>>> storesList = new ArrayList<HashMap<Integer, List<Integer>>>();
//		HashMap<Integer, List<Integer>> edges;
//		List<HashMap<Integer, List<Integer>>> edgesList = new ArrayList<HashMap<Integer, List<Integer>>>();
//		List<Integer> destination = new ArrayList<Integer>();
//		List<Integer> source = new ArrayList<Integer>();
//		Scanner scanner = new Scanner(new InputStreamReader(System.in));
//		int numberOfTests = scanner.nextInt();
//		scanner.nextLine();
//		for (int i = 0; i < numberOfTests; i++) {
//			String[] input = scanner.nextLine().split(" ");
//			numberOfCities.add(i, Integer.parseInt(input[0]));
//			int numberOfRoads = Integer.parseInt(input[1]);
//			int numbeOfStore = Integer.parseInt(input[2]);
//			source.add(i, Integer.parseInt(input[3]));
//			destination.add(i, Integer.parseInt(input[4]));
//			edges = new HashMap<Integer, List<Integer>>();
//			stores = new HashMap<Integer, List<Integer>>();
//			// putting all network with same key in format "uv"
//			// where u < v as roads can be used in both direction
//			// i.e "1 2" and "2 1" is treated as same and only "12" is used
//			for (int j = 0; j < numberOfRoads; j++) {
//				String[] points = scanner.nextLine().split(" ");
//				int v1 = Integer.parseInt(points[0]);
//				int v2 = Integer.parseInt(points[1]);
//				int w = Integer.parseInt(points[2]);
//				int k = v1 < v2 ? ((v1 * 10) + v2) : ((v2 * 10) + v1);
//				if (edges.containsKey(k)) {
//					edges.get(k).add(w);
//				} else {
//					List<Integer> list = new ArrayList<Integer>();
//					list.add(w);
//					edges.put(k, list);
//				}
//			}
//			// putting all stores with same key
//			// in single list
//			for (int j = 0; j < numbeOfStore; j++) {
//				String[] points = scanner.nextLine().split(" ");
//				if (stores.containsKey(points[0])) {
//					stores.get(Integer.parseInt(points[0])).add(
//							Integer.parseInt(points[1]));
//				} else {
//					List<Integer> list = new ArrayList<Integer>();
//					list.add(Integer.parseInt(points[1]));
//					stores.put(Integer.parseInt(points[0]), list);
//				}
//			}
//			if (i < numberOfTests - 1) {
//				scanner.nextLine();
//			}
//			edgesList.add(i, edges);
//			storesList.add(i, stores);
//		}
//		Supermarket sm = new Supermarket();
//		String result = "";
//		for (int i = 0; i < numberOfTests; i++) {
//			result = sm.getShortestRoute(numberOfCities.get(i),
//					edgesList.get(i), storesList.get(i), source.get(i),
//					destination.get(i));
//			System.out.println("Case #" + (i + 1) + ": " + result);
//		}
//	}
//
//}
//package conpra;
//
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map.Entry;
//import java.util.PriorityQueue;
//import java.util.Scanner;
//
//public class Supermarket {
//	// private final int INFINITY = Integer.MAX_VALUE;
//
//	static class Edge {
//		int v1;
//		int v2;
//		int weight;
//
//		public Edge(int v1, int v2, int w) {
//			this.v1 = v1;
//			this.v2 = v2;
//			this.weight = w;
//		}
//	}
//
//	private String getShortestRoute(Integer numberOfCities,
//			HashMap<Integer, List<Integer>> roadMap,
//			HashMap<Integer, List<Integer>> storeMap, Integer source,
//			Integer destination) {
//		List<Edge> edges = new ArrayList<Edge>();
//		// finding the lowest path between two cities and keeping it only
//		for (Entry<Integer, List<Integer>> roadEntry : roadMap.entrySet()) {
//			Collections.sort(roadEntry.getValue());
//			int k = roadEntry.getKey();
//			// adding edges back with only shortest route
//			edges.add(new Edge(k / 100000, k % 100000, roadEntry.getValue()
//					.get(0)));
//		}
//
//		List<Integer> timesList = new ArrayList<Integer>();
//		// for each stores in a city, get the store with shortest time
//		// find the time to each store and to gea house
//		for (Entry<Integer, List<Integer>> storeEntry : storeMap.entrySet()) {
//			Collections.sort(storeEntry.getValue());
//			int timeToBuy = storeEntry.getValue().get(0);
//			int storeCity = storeEntry.getKey();
//			int timeToStore = getTime(source, storeCity, numberOfCities, edges);
//			if (timeToStore == -1) {
//				continue;
//			}
//			int timeToHome = getTime(storeCity, destination, numberOfCities,
//					edges);
//			if (timeToHome == -1) {
//				continue;
//			}
//			timesList.add(timeToStore + timeToBuy + timeToHome);
//		}
//		// if none found return impossible
//		if (timesList.size() == 0) {
//			return "not possible";
//		}
//		// get the shortest time
//		Collections.sort(timesList);
//		// format the output
//		int time = timesList.get(0);
//		String hour = String.format("%01d", (time / 60));
//		String min = String.format("%02d", (time % 60));
//		return hour + ":" + min;
//	}
//
//	// shortest path implementation
//	private int getTime(Integer source, Integer destination,
//			Integer numberOfCities, List<Edge> edges) {
//		if (source == destination) {
//			return 0;
//		}
//		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(numberOfCities - 1,
//				new Comparator<Edge>() {
//					@Override
//					public int compare(Edge e1, Edge e2) {
//						return e1.weight - e2.weight;
//					}
//				});
//		// initialization of variables
//		boolean[] visited = new boolean[numberOfCities + 1];
//		int[] distance = new int[numberOfCities + 1];
//		for (int i = 0; i < numberOfCities + 1; i++) {
//			if (i == source) {
//				visited[i] = true;
//				distance[i] = 0;
//			} else {
//				visited[i] = false;
//				distance[i] = -1;
//			}
//		}
//		// filling up of neighbours of source
//		for (Edge r : edges) {
//			if (r.v1 == source || r.v2 == source) {
//				pq.add(r);
//			}
//		}
//		// Dijkstra
//		while (!pq.isEmpty()) {
//			Edge min = pq.poll();
//			if (visited[min.v1]) {
//				visited[min.v2] = true;
//				distance[min.v2] = min.weight;
//			} else {
//				visited[min.v1] = true;
//				distance[min.v1] = min.weight;
//			}
//			if (min.v2 == destination || min.v1 == destination) {
//				break;
//			}
//			List<Edge> newlist = new ArrayList<Edge>();
//			for (Edge e : edges) {
//				if ((visited[e.v1] && visited[e.v2])
//						|| (!visited[e.v1] && !visited[e.v2])) {
//					continue;
//				} else {
//					if (visited[e.v1]) {
//						int w = distance[e.v1] + e.weight;
//						newlist.add(new Edge(e.v1, e.v2, w));
//					} else {
//						int w = distance[e.v2] + e.weight;
//						newlist.add(new Edge(e.v1, e.v2, w));
//					}
//				}
//
//			}
//			pq.clear();
//			pq.addAll(newlist);
//		}
//		return distance[destination];
//	}
//
//	public static void main(String[] args) {
//		List<Integer> numberOfCities = new ArrayList<Integer>();
//		HashMap<Integer, List<Integer>> stores;
//		List<HashMap<Integer, List<Integer>>> storesList = new ArrayList<HashMap<Integer, List<Integer>>>();
//		HashMap<Integer, List<Integer>> edges;
//		List<HashMap<Integer, List<Integer>>> edgesList = new ArrayList<HashMap<Integer, List<Integer>>>();
//		List<Integer> destination = new ArrayList<Integer>();
//		List<Integer> source = new ArrayList<Integer>();
//		Scanner scanner = new Scanner(new InputStreamReader(System.in));
//		int numberOfTests = scanner.nextInt();
//		scanner.nextLine();
//		for (int i = 0; i < numberOfTests; i++) {
//			String[] input = scanner.nextLine().split(" ");
//			numberOfCities.add(i, Integer.parseInt(input[0]));
//			int numberOfRoads = Integer.parseInt(input[1]);
//			int numbeOfStore = Integer.parseInt(input[2]);
//			source.add(i, Integer.parseInt(input[3]));
//			destination.add(i, Integer.parseInt(input[4]));
//			edges = new HashMap<Integer, List<Integer>>();
//			stores = new HashMap<Integer, List<Integer>>();
//			// putting all network with same key in format "uv"
//			// where u < v as roads can be used in both direction
//			// i.e "1 2" and "2 1" is treated as same and only "12" is used
//			for (int j = 0; j < numberOfRoads; j++) {
//				String[] points = scanner.nextLine().split(" ");
//				int v1 = Integer.parseInt(points[0]);
//				int v2 = Integer.parseInt(points[1]);
//				int w = Integer.parseInt(points[2]);
//				int k = v1 < v2 ? ((v1 * 100000) + v2) : ((v2 * 100000) + v1);
//				if (edges.containsKey(k)) {
//					edges.get(k).add(w);
//				} else {
//					List<Integer> list = new ArrayList<Integer>();
//					list.add(w);
//					edges.put(k, list);
//				}
//			}
//			// putting all stores with same key
//			// in single list
//			for (int j = 0; j < numbeOfStore; j++) {
//				String[] points = scanner.nextLine().split(" ");
//				if (stores.containsKey(points[0])) {
//					stores.get(Integer.parseInt(points[0])).add(
//							Integer.parseInt(points[1]));
//				} else {
//					List<Integer> list = new ArrayList<Integer>();
//					list.add(Integer.parseInt(points[1]));
//					stores.put(Integer.parseInt(points[0]), list);
//				}
//			}
//			if (i < numberOfTests - 1) {
//				scanner.nextLine();
//			}
//			edgesList.add(i, edges);
//			storesList.add(i, stores);
//		}
//		Supermarket sm = new Supermarket();
//		String result = "";
//		for (int i = 0; i < numberOfTests; i++) {
//			result = sm.getShortestRoute(numberOfCities.get(i),
//					edgesList.get(i), storesList.get(i), source.get(i),
//					destination.get(i));
//			System.out.println("Case #" + (i + 1) + ": " + result);
//		}
//	}
//
//}
