package week4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by hang on 03.11.14.
 */
public class God {

    private Node tree;

    public static void main(String[] args) {
        God g = new God();
        Scanner sc = new Scanner(System.in);
        long a = sc.nextLong();
        long c = sc.nextLong();
        long s = sc.nextLong();
        long modulus = 2147483648L;
        String[] strategies = new String[10000];
        strategies[0] = String.format("%31s", Long.toBinaryString(s)).replace(' ','0');
        for (int i = 1; i < strategies.length; i++) {
            s = ((a * s) + c) % modulus;
            strategies[i] = String.format("%31s", Long.toBinaryString(s)).replace(' ','0');
        }
        g.buildTries(strategies);

        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            long umbrellaStrategy = sc.nextLong();
            System.out.format("Case #%d: %d\n", tc, g.process(umbrellaStrategy));
        }
    }

    void buildTries(String[] godStrategies) {
        tree = new Node('0');
        for (int i = 0; i < godStrategies.length; i++) {
            insertWord(tree, godStrategies[i]);
        }
    }

    void insertWord(Node root, String word) {
        char[] letters = word.toCharArray();
        Node curNode = root;
        for (int i = 0; i < letters.length; i++) {
            if (!curNode.links.keySet().contains(letters[i]))
                curNode.links.put(letters[i], new Node(letters[i]));
            curNode = curNode.links.get(letters[i]);
        }
    }

    private long process(long umbrellaStrategy) {
        char[] uStrategy = String.format("%31s", Long.toBinaryString(umbrellaStrategy)).replace(' ','0').toCharArray();
        char[] testString = new char[uStrategy.length];
        for (int i = 0; i < uStrategy.length ; i++) {
            if(uStrategy[i] == '1'){
                testString[i] = '0';
            }else{
                testString[i] = '1';
            }
        }
        StringBuilder sb = new StringBuilder();
        LinkedList<Node> q = new LinkedList<Node>();

        q.add(tree);
        char curValue = testString[0];
        int curPointer = 0;
        while(!q.isEmpty()){
            Node n = q.poll();
            sb.append(n.value);
            if(!n.links.isEmpty()){
                if(n.links.containsKey(curValue)){
                    q.add(n.links.get(curValue));
                }else{
                    if(curValue == '0'){
                        q.add(n.links.get('1'));
                    }else{
                        q.add(n.links.get('0'));
                    }
                }
                curPointer += 1;
                if(curPointer < 31){
                    curValue = testString[curPointer];
                }

            }
        }

        return Long.parseLong(sb.toString(),2);
    }


    static class Node {
        char value;
        HashMap<Character, Node> links;

        Node(char value) {
            this.value = value;
            links = new HashMap<Character, Node>();
        }
    }
}
