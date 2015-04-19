package flow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bank {
	private int[] distance;
	private int[] excess;
	private boolean[] marked;

	private int maxFlow(ArrayList<Edge>[] adj) {
		int n = adj.length;
		int t = n - 1;

		distance = new int[n];
		excess = new int[n];
		Queue<Integer> q = new LinkedList<Integer>();
		// initialization
		initialize(adj, q, t);
		// calculate distance
		calculateDistance(adj, t);

		int v;
		while (!q.isEmpty()) {
			v = q.poll();
			int oldDistance = distance[v];
			while (excess[v] > 0) {
				for (Edge e : adj[v]) {
					if (excess[e.v] > 0 && e.residualCapacityTo(e.w) > 0) {
						if (distance[e.v] == (distance[e.w] + 1)) {
							// push operation
							push(e, adj);
							if (e.w != 0 && e.w != t && !q.contains(e.w)) {
								q.add(e.w);
							}
						}
					}
				}
				// relabel
				if (excess[v] > 0) {
					relabel(v, adj);
				}
			}
			if (distance[v] > oldDistance) {
				if (!q.contains(v))
					q.add(v);
			}

		}
		return calculateMinCut(adj);
	}

	// push operation on particular edge
	void push(Edge e, ArrayList<Edge>[] adj) {
		int delta = Math.min(excess[e.v], e.residualCapacityTo(e.w));
		e.addResidualFlowTo(e.w, delta);
		e.getOppositeEdge().addResidualFlowTo(e.w, delta);
		excess[e.v] -= delta;
		excess[e.w] += delta;
	}

	// relabel operation on vertex
	void relabel(int v, ArrayList<Edge>[] adj) {
		int temp = -1;
		for (Edge e : adj[v]) {
			if (e.residualCapacityTo(e.w) > 0) {
				if (temp == -1 || temp > distance[e.w]) {
					temp = distance[e.w];
				}
			}
		}
		distance[v] = temp + 1;
	}

	// initialize the graph
	private void initialize(ArrayList<Edge>[] adj, Queue<Integer> q, int t) {
		// set source and target distance to number of vertex and zero resp.
		for (int i = 0; i < adj.length; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		distance[0] = t + 1;
		distance[t] = 0;
		// for all edge coming out of source add the flow, and to the Queue if
		// not target
		for (Edge e : adj[0]) {
			e.flow = e.capacity;
			excess[e.w] += e.flow;
			if (e.w != t) {
				q.add(e.w);
			}
		}
	}

	// calculate the distance from target to other nodes except source
	void calculateDistance(ArrayList<Edge>[] adj, int dest) {
		LinkedList<Integer> n = new LinkedList<Integer>();
		boolean[] visited = new boolean[adj.length];
		n.push(dest);
		while (!n.isEmpty()) {
			int current = n.pollFirst();
			visited[current] = true;
			for (Edge e : adj[current]) {
				if (e.w != 0 && !visited[e.w]) {
					int h = distance[current] + 1;
					if (distance[e.w] > h) {
						distance[e.w] = h;
					}
					if (!n.contains(e.w))
						n.add(e.w);
				}
			}
		}
	}

	int calculateMinCut(ArrayList<Edge>[] adj) {
		int result = 0;
		findReachableNodes(adj, 0);
		for (int i = 0; i < adj.length; i++) {
			if(marked[i]){
				for (Edge e : adj[i]) {
					if(!marked[e.other(i)]){
						result += e.capacity;
					}
				}
			}
				
		}

		return result;
	}
	private void findReachableNodes(ArrayList<Edge>[] adj, int s) {
        marked = new boolean[adj.length];

        // breadth-first search
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        marked[s] = true;
        int v;
        while (!q.isEmpty()) {
           v = q.poll();
            for (Edge e : adj[v]) {
                int w = e.other(v);
                // if residual capacity from v to w
                if (e.residualCapacityTo(w) > 0) {
                    if (!marked[w]) {
//                        edgeTo[w] = e;
                        marked[w] = true;
                        q.add(w);
                    }
                }
            }
        }
    }
	public static void main(String[] args) {
		Bank b = new Bank();
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for (int tc = 1; tc <= t; tc++) {
			int total = sc.nextInt();
			int n = sc.nextInt();
			int m = sc.nextInt();
			@SuppressWarnings("unchecked")
			ArrayList<Edge>[] adj = (ArrayList<Edge>[]) new ArrayList[n];
			for (int v = 0; v < n; v++) {
				adj[v] = new ArrayList<Edge>();
			}
			for (int i = 0; i < m; i++) {
				int v, w, c;
				v = sc.nextInt() - 1;
				w = sc.nextInt() - 1;
				c = sc.nextInt();
				if (v == w) {
					continue;
				}
				Edge vw = new Edge(v, w, c);
				Edge wv = new Edge(w, v, c);
				vw.setOppositeEdge(wv);
				wv.setOppositeEdge(vw);
				adj[v].add(vw);
				adj[w].add(wv);
			}
			int res = b.maxFlow(adj);
			if (res > total) {
				System.out.format("Case #%d: %s\n", tc, "no");
			} else {
				System.out.format("Case #%d: %s\n", tc, "yes");
			}

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
		private Edge opposite;

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

		public Edge getOppositeEdge() {
			return this.opposite;
		}

		public void setOppositeEdge(Edge e) {
			this.opposite = e;
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
