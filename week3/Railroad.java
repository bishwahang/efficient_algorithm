package flow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Railroad {
	private int[] distance;
	private int[] excess;

	private int maxFlow(ArrayList<FlowEdge>[] adj) {
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
				for (FlowEdge e : adj[v]) {
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
		int result = 0;
		for (FlowEdge e : adj[0]) {
			result += e.residualCapacityTo(e.v);
		}
		return result;
	}

	// push operation on particular edge
	void push(FlowEdge e, ArrayList<FlowEdge>[] adj) {
		int delta = Math.min(excess[e.v], e.residualCapacityTo(e.w));
		e.addResidualFlowTo(e.w, delta);
		e.getOppositeEdge().addResidualFlowTo(e.w, delta);
		excess[e.v] -= delta;
		excess[e.w] += delta;
	}

	// relabel operation on vertex
	void relabel(int v, ArrayList<FlowEdge>[] adj) {
		int temp = -1;
		for (FlowEdge e : adj[v]) {
			if (e.residualCapacityTo(e.w) > 0) {
				if (temp == -1 || temp > distance[e.w]) {
					temp = distance[e.w];
				}
			}
		}
		distance[v] = temp + 1;
	}

	// initialize the graph
	private void initialize(ArrayList<FlowEdge>[] adj, Queue<Integer> q, int t) {
		// set source and target distance to number of vertex and zero resp.
		for (int i = 0; i < adj.length; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		distance[0] = t + 1;
		distance[t] = 0;
		// for all edge coming out of source add the flow, and to the Queue if
		// not target
		for (FlowEdge e : adj[0]) {
			e.flow = e.capacity;
			excess[e.w] += e.flow;
			if (e.w != t) {
				q.add(e.w);
			}
		}
	}

	// calculate the distance from target to other nodes except source
	void calculateDistance(ArrayList<FlowEdge>[] adj, int dest) {
		LinkedList<Integer> n = new LinkedList<Integer>();
		boolean[] visited = new boolean[adj.length];
		n.push(dest);
		while (!n.isEmpty()) {
			int current = n.pollFirst();
			visited[current] = true;
			for (FlowEdge e : adj[current]) {
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

	public static void main(String[] args) {
		Railroad rr = new Railroad();
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for (int tc = 1; tc <= t; tc++) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			ArrayList<FlowEdge>[] adj = (ArrayList<FlowEdge>[]) new ArrayList[n];
			for (int v = 0; v < n; v++) {
				adj[v] = new ArrayList<FlowEdge>();
			}
			for (int i = 0; i < m; i++) {
				int v, w, c;
				v = sc.nextInt() - 1;
				w = sc.nextInt() - 1;
				c = sc.nextInt();
				FlowEdge vw = new FlowEdge(v, w, c);
				FlowEdge wv = new FlowEdge(w, v, c);
				vw.setOppositeEdge(wv);
				wv.setOppositeEdge(vw);
				adj[v].add(vw);
				adj[w].add(wv);
			}
			int res = rr.maxFlow(adj);
			if (res > 0) {
				System.out.format("Case #%d: %d\n", tc, res);
			} else {
				System.out.format("Case #%d: %s\n", tc, "not possible");
			}

		}
		sc.close();
	}

	/*
	 * source for this class from
	 * http://algs4.cs.princeton.edu/64maxflow/FlowEdge.java.html
	 */
	static class FlowEdge {
		private final int v; // from
		private final int w; // to
		private final int capacity; // capacity
		private int flow; // flow
		private FlowEdge opposite;

		public FlowEdge(int v, int w, int capacity) {
			this.v = v;
			this.w = w;
			this.capacity = capacity;
			this.flow = 0;
		}

		public FlowEdge(FlowEdge e) {
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

		public FlowEdge getOppositeEdge() {
			return this.opposite;
		}

		public void setOppositeEdge(FlowEdge e) {
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
