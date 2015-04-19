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

public class SnakeLadder {
	private final int INFINITY = Integer.MAX_VALUE;

	private String getDiceSide(int target, HashMap<Integer, Integer> snakes,
			HashMap<Integer, Integer> laders) {
		HashMap<Integer, Integer> diceMap = new HashMap<Integer, Integer>();
		boolean atLeastOne = false;
		if (target <= 2) {
			return "1 2 3 4 5 6";
		}
		for (int side = 1; side <= 6; side++) {
			Queue<Integer> q = new LinkedList<Integer>();
			int[] previous = new int[target];
			int[] weight = new int[target];
			int start = 0;
			for (int i = 0; i < target; i++) {
				if (i == start) {
					previous[i] = -1;
					weight[i] = 0;
				} else {
					previous[i] = -1;
					weight[i] = INFINITY;
				}
			}
			q.add(start);
			while (!q.isEmpty()) {
				int w = q.poll();
				// check if ended in another snake 
				// if continue to next tail of snake
				if(snakes.containsKey(w)){
					int temp = snakes.get(w);
					if (weight[temp] > weight[w]) {
						weight[temp] = weight[w];
						q.add(temp);
						previous[temp] = w;
					}
					continue;
				}
				// if ended in ladder
				// continue to top of ladder
				// since optional continue normal going to
				if(laders.containsKey(w)){
					int temp = laders.get(w);
					if (weight[temp] > weight[w]) {
						weight[temp] = weight[w];
						q.add(temp);
						previous[temp] = w;
					}
				}
				int probableNode = w + side;
				// if next turn will hit the target
				// update if this is optimal path
				if (probableNode >= target - 1) {
					if (weight[target - 1] > weight[w] + 1) {
						weight[target - 1] = weight[w] + 1;
						previous[target - 1] = w;
					}
				} else { 
					// else check if it is a mouth of snake
					// if the tail of snake is still optimal path
					// update it, else, same node is hit twice, continue.
					if (snakes.containsKey(probableNode)) {
						int temp = snakes.get(probableNode);
						if (weight[temp] > weight[w] + 1) {
							weight[temp] = weight[w] + 1;
							q.add(temp);
							previous[temp] = w;
						}
						continue;
					}
					// check if probable next node has ladders
					// get to the head of ladder if it is optimal path
					if (laders.containsKey(probableNode)) {
						int temp = laders.get(probableNode);
						if (weight[temp] > weight[w] + 1) {
							weight[temp] = weight[w] + 1;
							q.add(temp);
							previous[temp] = w;
						}
					}
					// since we can skip the ladders
					// get to the probable node if it is optimal path
					if (weight[probableNode] > weight[w] + 1) {
						weight[probableNode] = weight[w] + 1;
						q.add(probableNode);
						previous[probableNode] = w;
					}
				}

			}
			if (previous[target - 1] != -1) {
				atLeastOne = true;
				diceMap.put(side, weight[target - 1]);
			}
		}
		// if none of the sides continuously can go to the top
		if (!atLeastOne) {
			return "not possible";
		}
		
		// organizing the results
		// get the minimum cost
		int min = INFINITY;
		for (Entry<Integer, Integer> entry : diceMap.entrySet()) {
			if (entry.getValue() < min) {
				min = entry.getValue();
			}
		}
		// get the dice sides that got minimum cost
		List<Integer> list = new ArrayList<Integer>();
		for (Entry<Integer, Integer> entry : diceMap.entrySet()) {
			if (entry.getValue() == min) {
				list.add(entry.getKey());
			}
		}
		
		// sort the sides
		Collections.sort(list);
		
		// build the result
		String result = "";
		for (Integer i : list) {
			result += i + " ";
		}
		return result.trim();
	}

	public static void main(String[] args) {
		List<Integer> targets = new ArrayList<Integer>();
		HashMap<Integer, Integer> snakes;
		HashMap<Integer, Integer> laders;
		List<HashMap<Integer, Integer>> listSankes = new ArrayList<HashMap<Integer, Integer>>();
		List<HashMap<Integer, Integer>> listLadders = new ArrayList<HashMap<Integer, Integer>>();
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		int numberOfTests = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfTests; i++) {
			String[] input = scanner.nextLine().split(" ");
			targets.add(i, Integer.parseInt(input[0]));
			int noOfSnakes = Integer.parseInt(input[1]);
			int noOfLaders = Integer.parseInt(input[2]);
			snakes = new HashMap<Integer, Integer>();
			laders = new HashMap<Integer, Integer>();
			for (int j = 0; j < noOfSnakes; j++) {
				String[] s = scanner.nextLine().split(" ");
				snakes.put(Integer.parseInt(s[0]) - 1,
						Integer.parseInt(s[1]) - 1);
			}
			for (int j = 0; j < noOfLaders; j++) {
				String[] s = scanner.nextLine().split(" ");
				laders.put(Integer.parseInt(s[0]) - 1,
						Integer.parseInt(s[1]) - 1);
			}
			if (i < numberOfTests - 1) {
				scanner.nextLine();
			}
			listSankes.add(i, snakes);
			listLadders.add(i, laders);
		}
		SnakeLadder sl = new SnakeLadder();
		String result = "";
		for (int i = 0; i < numberOfTests; i++) {
			result = sl.getDiceSide(targets.get(i), listSankes.get(i),
					listLadders.get(i));
			System.out.println("Case #" + (i + 1) + ": " + result);
		}
	}

}
