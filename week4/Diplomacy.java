package week4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hang on 01.11.14.
 */
public class Diplomacy {
    public static void main(String[] args) {
        Diplomacy d = new Diplomacy();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int tc = 1; tc <= n; tc++) {
            int countries = sc.nextInt();
            Node[] adj = new Node[countries];
            for (int j = 0; j < countries; j++) {
                adj[j] = new Node(j);
            }
            int relations = sc.nextInt();
            for (int j = 0; j < relations; j++) {
                String c = sc.next();
                int c1 = sc.nextInt() - 1;
                int c2 = sc.nextInt() - 1;
                // reflexive so both way
                if ("F".equalsIgnoreCase(c)) {
                    adj[c1].friends.add(c2);
                    adj[c2].friends.add(c1);
                } else {
                    adj[c1].foes.add(c2);
                    adj[c2].foes.add(c1);
                }
            }
            System.out.format("Case #%d: %s\n", tc, d.isPossible(adj));
        }
    }

    private String isPossible(Node[] adj) {
        boolean[] visited = new boolean[adj.length];
        adj[0].isFriend = true; // Lea is friend with herself
        LinkedList<Integer> q = new LinkedList<Integer>();
        q.add(0); // start from the lea as root
        int count = 0;
        while(!q.isEmpty()){
            int n = q.pollFirst();
            visited[n] = true; // node is visited
            boolean currentFriend = adj[n].isFriend; // current node is either friend or foe
            if(currentFriend)
                count++; // if friend increase the friend count
            if(count > (adj.length/2))
                return "yes"; // short circuit
            for (int e : adj[n].friends){
                if(!visited[e]){
                    if(currentFriend){ // friend's friend is friend
                        adj[e].isFriend = true;
                    }// else default enemy's friend is enemy
                    if(!q.contains(e))
                        q.add(e);
                }
            }
            for (int e : adj[n].foes){
                if(!visited[e]){
                    if(!currentFriend){ //enemy's enemy is friend
                        adj[e].isFriend = true;
                    }// else default friend's enemy is enemy
                    if(!q.contains(e))
                        q.add(e);
                }
            }
        }
        return "no";
    }

    static class Node {
        int id;
        boolean isFriend;
        List<Integer> friends = new ArrayList<Integer>();
        List<Integer> foes = new ArrayList<Integer>();
        Node(int id) {
            this.id = id;
        }
    }

}
