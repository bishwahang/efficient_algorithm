package flow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Football {
	private int mostWins, noOfTeams;
	private int[] wins, remains;
	private Edge[] edgeTo;
	private boolean[] marked;
	// private ArrayList<Integer> loosers;
	private ArrayList<Integer> leaders;
	private int[][] matches;

	private String possibleWinners(int edges) {
		String result = "";
		noOfTeams = wins.length;
		mostWins = 0;
		// loosers = new ArrayList<Integer>();
		leaders = new ArrayList<Integer>();
		for (int i = 0; i < noOfTeams; i++) {
			if (wins[i] > mostWins) {
				mostWins = wins[i];
				leaders.clear();
				leaders.add(i);
			} else if (wins[i] == mostWins) {
				leaders.add(i);
			} else {
				continue;
			}
		}
		if (edges == 0) {
			for (int i = 0; i < noOfTeams; i++) {
				if (leaders.contains(i)) {
					result += "yes ";
				} else {
					result += "no ";
				}
			}
			return result.trim();
		}
		remains = new int[noOfTeams];
		for (int i = 0; i < matches.length; i++) {
			for (int j = 0; j < matches.length; j++) {
				if (i == j) {
					continue;
				}
				remains[i] += matches[i][j];
			}
		}

		for (int i = 0; i < noOfTeams; i++) {
			if (isEliminated(i)) {
				// loosers.add(i);
				result += "no ";
			} else {
				result += "yes ";
			}
		}
		return result.trim();
	}

	public void setWins(int[] w) {
		this.wins = new int[w.length];
		this.wins = w;
	}

	public void setMatches(int[][] m) {
		this.matches = new int[m.length][];
		this.matches = m;
	}

	private boolean isEliminated(int team) {
		if (!trivialSearch(team)) {
			return fullSearch(team);
		}
		return true;
		// return fullSearch(team);
	}

	private boolean trivialSearch(int team) {
		if (!leaders.contains(team) && wins[team] + remains[team] < mostWins) {
			return true;
		}
		return false;
	}

	private boolean fullSearch(int team) {
		return runFloyd(team);
	}

	private boolean runFloyd(int id) {
		int source = noOfTeams;
		int sink = noOfTeams + 1;
		int match = noOfTeams + 2;
		int maxcap = wins[id] + remains[id];
		ArrayList<Edge> edges = new ArrayList<Edge>();
		// add the edges
		int checkValue = 0;
		for (int i = 0; i < noOfTeams; i++) {
			if (i == id)
				continue;
			for (int j = i + 1; j < noOfTeams; j++) {
				if (j == id || matches[i][j] == 0) {
					continue;
				}
				checkValue += matches[i][j];
				edges.add(new Edge(source, match, matches[i][j]));
				edges.add(new Edge(match, i, Integer.MAX_VALUE));
				edges.add(new Edge(match, j, Integer.MAX_VALUE));
				match++;
			}
			edges.add(new Edge(i, sink, maxcap - wins[i]));
		}
		int totalVertices = match;
		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] adj = (ArrayList<Edge>[]) new ArrayList[totalVertices];
		for (int i = 0; i < totalVertices; i++) {
			adj[i] = new ArrayList<Edge>();
		}
		for (Edge e : edges) {
			int v = e.from();
			int w = e.to();
			adj[v].add(e);
			adj[w].add(e);
		}
		int maxFlow = getMaxFlow(adj, source, sink, totalVertices);
		if (maxFlow < checkValue) {
			return true;
		}
		return false;
	}

	private int getMaxFlow(ArrayList<Edge>[] adj, int s, int t, int total) {

		// int value = excess(adj, t);
		int value = 0;

		while (hasAugmentingPath(adj, s, t, total)) {
			// get the lowest value
			int bottle = Integer.MAX_VALUE;
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
			}
			// add it across the flow
			for (int v = t; v != s; v = edgeTo[v].other(v)) {
				edgeTo[v].addResidualFlowTo(v, bottle);
			}

			value += bottle;
		}
		return value;
	}

	// return excess flow at vertex v
	// private int excess(ArrayList<Edge>[] adj, int v) {
	// int excess = 0;
	// for (Edge e : adj[v]) {
	// if (v == e.from())
	// excess -= e.flow();
	// else
	// excess += e.flow();
	// }
	// return excess;
	// }

	// is there an augmenting path?
	// if so, upon termination edgeTo[] will contain a parent-link
	// representation of such a path
	private boolean hasAugmentingPath(ArrayList<Edge>[] adj, int s, int t,
			int total) {
		marked = new boolean[total];
		edgeTo = new Edge[total];
		// breadth-first search
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(s);
		marked[s] = true;
		while (!q.isEmpty()) {
			int v = q.poll();
			for (Edge e : adj[v]) {
				int w = e.other(v);
				// if residual capacity from v to w
				if (e.residualCapacityTo(w) > 0) {
					if (!marked[w]) {
						edgeTo[w] = e;
						marked[w] = true;
						q.add(w);
					}
				}
			}
		}
		return marked[t];
	}

	public static void main(String[] args) {
		Football b = new Football();
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for (int tc = 1; tc <= t; tc++) {
			int n = sc.nextInt(); // number of teams
			int m = sc.nextInt(); // number of matches
			int[] wins = new int[n];
			for (int i = 0; i < n; i++) {
				wins[i] = sc.nextInt(); // wins
			}
			int[][] matches = new int[n][n];
			for (int i = 0; i < m; i++) {
				int team1 = sc.nextInt() - 1;
				int team2 = sc.nextInt() - 1;
				matches[team1][team2] += 1;
				matches[team2][team1] += 1;
			}
			b.setMatches(matches);
			b.setWins(wins);
			String res = b.possibleWinners(m);
			System.out.format("Case #%d: %s\n", tc, res);
		}
		sc.close();
	}

	/*
	 * source for this class from
	 * http://algs4.cs.princeton.edu/64maxflow/FlowEdge.java.html
	 */
	static class Edge {
		private final int v; // from
		private final int w; // to
		private final int capacity; // capacity
		private int flow; // flow

		public Edge(int v, int w, int capacity) {
			this.v = v;
			this.w = w;
			this.capacity = capacity;
			this.flow = 0;
		}

		public Edge(Edge e) {
			this.v = e.v;
			this.w = e.w;
			this.capacity = e.capacity;
			this.flow = e.flow;
		}

		public int from() {
			return v;
		}

		public int to() {
			return w;
		}

		public int capacity() {
			return capacity;
		}

		public int flow() {
			return flow;
		}

		public int other(int vertex) {
			if (vertex == v)
				return w;
			else if (vertex == w)
				return v;
			else
				throw new IllegalArgumentException("Illegal endpoint");
		}

		public int residualCapacityTo(int vertex) {
			if (vertex == v)
				return flow; // backward edge
			else if (vertex == w)
				return capacity - flow; // forward edge
			else
				throw new IllegalArgumentException("Illegal endpoint");
		}

		public void addResidualFlowTo(int vertex, int delta) {
			if (vertex == v)
				flow -= delta; // backward edge
			else if (vertex == w)
				flow += delta; // forward edge
			else
				throw new IllegalArgumentException("Illegal endpoint");
		}
	}
}
