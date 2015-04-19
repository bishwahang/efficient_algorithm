package week4;

import java.util.*;

/**
 * Created by hang on 01.11.14.
 */
public class ContactList {

    private int getAnswers(String[] contacts) {
        Node tree = new Node('0');
        for (int i = 0; i < contacts.length; i++) {
            String name = contacts[i];
            insertWord(tree, name);
        }
        int result = 0;
        LinkedList<Node> q = new LinkedList<Node>();
        q.add(tree);
        while(!q.isEmpty()){
            Node n = q.poll();
            if(n.fullWord && !n.links.isEmpty()){
                result += 1;
            }
            if(!n.links.isEmpty()){
                for (Node l : n.links.values())
                    q.add(l);
            }
        }
        return result;
    }
    void insertWord(Node root, String word)
    {
        int l = word.length();
        char[] letters = word.toCharArray();
        Node curNode = root;

        for (int i = 0; i < l; i++)
        {
            if (!curNode.links.keySet().contains(letters[i]))
                curNode.links.put(letters[i], new Node(letters[i]));
            curNode = curNode.links.get(letters[i]);
        }
        curNode.fullWord = true;
    }

    public static void main(String[] args) {
        ContactList cl = new ContactList();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            String[] names = new String[n];
            for (int i = 0; i < n; i++) {
                names[i] = sc.next();
            }
            int res = cl.getAnswers(names);
            System.out.format("Case #%d: %d\n", tc, res);
        }
        sc.close();
    }


    static class Node {
        char value;
        HashMap<Character, Node> links;
        boolean fullWord;

        Node(char value) {
            this.value = value;
            links = new HashMap<Character, Node>();
            this.fullWord = false;
        }
    }
}
