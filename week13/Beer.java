package week13;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by hang on 25.01.15.
 */
public class Beer {
    private double[] distance;
    private double[] excess;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Beer beer = new Beer();
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            ArrayList<FlowEdge>[] adj = (ArrayList<FlowEdge>[]) new ArrayList[n];
            for (int v = 0; v < n; v++) {
                adj[v] = new ArrayList<FlowEdge>();
            }
            int v, w, k;
            double x, area;
            for (int i = 0; i < m; i++) {
                v = sc.nextInt() -1;
                w = sc.nextInt() -1;
                k = sc.nextInt();
                x = sc.nextDouble();
                if (k == 0) {
                    area = (Math.PI * x * x);
                }else {
                    area = (x * x * k) / (4 * Math.tan(Math.PI / k));
                }
                FlowEdge vw = new FlowEdge(v, w, area);
                FlowEdge wv = new FlowEdge(w, v, area);
                vw.setOppositeEdge(wv);
                wv.setOppositeEdge(vw);
                adj[v].add(vw);
                adj[w].add(wv);
            }
            double res = beer.maxFlow(adj);
            if (res > 0) {
                System.out.println("Case #"+tc+": "+res);
            } else {
                System.out.println("Case #" + tc + ": impossible");
            }

        }
    }
    private double maxFlow(ArrayList<FlowEdge>[] adj) {
        int n = adj.length;
        int t = n - 1;

        distance = new double[n];
        excess = new double[n];
        Queue<Integer> q = new LinkedList<Integer>();
        // initialization

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
        // calculate distance
        LinkedList<Integer> ll = new LinkedList<Integer>();
        boolean[] visited = new boolean[adj.length];
        ll.push(t);
        while (!ll.isEmpty()) {
            int current = ll.pollFirst();
            visited[current] = true;
            for (FlowEdge e : adj[current]) {
                if (e.w != 0 && !visited[e.w]) {
                    double h = distance[current] + 1;
                    if (distance[e.w] > h) {
                        distance[e.w] = h;
                    }
                    if (!ll.contains(e.w)) {
                        ll.add(e.w);
                    }
                }
            }
        }
        int v;
        double delta;
        while (!q.isEmpty()) {
            v = q.poll();
            double oldDistance = distance[v];
            while (excess[v] > 0) {
                for (FlowEdge e : adj[v]) {
                    if (excess[e.v] > 0 && e.residualCapacityTo(e.w) > 0) {
                        if (distance[e.v] == (distance[e.w] + 1)) {
                            // push operation
                            delta = Math.min(excess[e.v], e.residualCapacityTo(e.w));
                            e.addResidualFlowTo(e.w, delta);
                            e.getOppositeEdge().addResidualFlowTo(e.w, delta);
                            excess[e.v] -= delta;
                            excess[e.w] += delta;
                            if (e.w != 0 && e.w != t && !q.contains(e.w)) {
                                q.add(e.w);
                            }
                        }
                    }
                }
                // relabel
                if (excess[v] > 0) {
                    double temp = -1;
                    for (FlowEdge e : adj[v]) {
                        if (e.residualCapacityTo(e.w) > 0) {
                            if (temp == -1 || temp > distance[e.w]) {
                                temp = distance[e.w];
                            }
                        }
                    }
                    distance[v] = temp + 1;
                }
            }
            if (distance[v] > oldDistance) {
                if (!q.contains(v))
                    q.add(v);
            }
        }
        double result = 0;
        for (FlowEdge e : adj[0]) {
            result += e.residualCapacityTo(e.v);
        }
        return result;
    }

    static class FlowEdge {
        private final int v; // from
        private final int w; // to
        private double capacity; // capacity
        private double flow; // flow
        private FlowEdge opposite;

        public FlowEdge(int v, int w, double capacity) {
            this.v = v;
            this.w = w;
            this.capacity = capacity;
            this.flow = 0;
        }
        public int from() {
            return v;
        }

        public int to() {
            return w;
        }

        public double capacity() {
            return capacity;
        }

        public double flow() {
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

        public double residualCapacityTo(int vertex) {
            if (vertex == v)
                return flow; // backward edge
            else if (vertex == w)
                return capacity - flow; // forward edge
            else
                throw new IllegalArgumentException("Illegal endpoint");
        }

        public void addResidualFlowTo(int vertex, double delta) {
            if (vertex == v)
                flow -= delta; // backward edge
            else if (vertex == w)
                flow += delta; // forward edge
            else
                throw new IllegalArgumentException("Illegal endpoint");
        }
    }
}
