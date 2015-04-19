//package b;
//
//import java.util.NoSuchElementException;
//
//public class SpanningTree {
//	private int NMAX; // maximum number of elements on PQ
//	private int N; // number of elements on PQ
//	private int[] pq; // binary heap using 1-based indexing
//	private int[] qp; // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
//	private Key[] keys; // keys[i] = priority of i
//
//	public SpanningTree(int NMAX) {
//		// TODO Auto-generated constructor stub
//		if (NMAX < 0) throw new IllegalArgumentException();
//		this.NMAX = NMAX;
//	}
//	public void insert(int i, Key key) {
//    }
//	public int delMin() { 
//    }
//	public boolean contains(int i) {
//        if (i < 0 || i >= NMAX) throw new IndexOutOfBoundsException();
//        return qp[i] != -1;
//    }
//}
