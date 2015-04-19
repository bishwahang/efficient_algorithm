package week8;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by hang on 28.11.14.
 */
public class Exam {
    Topic[] topics;
    int M;
    public static void main(String[] args) throws IOException {
        Exam exam = new Exam();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int tc = 1; tc <= t; tc++) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            Topic[] topics = new Topic[n];
            for (int i = 0; i < n; i++) {
                int l = sc.nextInt();
                int s = sc.nextInt();
                topics[i] = new Topic(s, l, i+1);
            }
            exam.topics = topics;
            exam.M = m;
            sb.append("Case #" + tc + ": " + exam.findApprox()+"\n");
        }
        System.out.print(sb.toString());
    }

    private String findApprox() {
        StringBuilder sb = new StringBuilder();
        Arrays.sort(topics, new Comparator<Topic>() {
            @Override
            public int compare(Topic o1, Topic o2) {
                return ((o2.score / o2.length) - (o1.score / o1.length));
            }
        });
        int pointer = 0;
        int totalLen = 0;
        while (pointer < topics.length) {
            int len = topics[pointer].length;
            int tempLen = totalLen + len;
            if(tempLen > M){
                pointer++;
                continue;
            }
            totalLen = tempLen;
            sb.append(topics[pointer].id + " ");
        }
        return sb.toString().trim();
    }
    static class Topic{
        int length;
        int score;
        int id;

        public Topic(int score, int length, int id) {
            this.score = score;
            this.length = length;
            this.id = id;
        }
    }
}
