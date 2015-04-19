package week9;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by hang on 15.12.14.
 */
public class Lake {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PriorityQueue<Soldier> pq = new PriorityQueue<Soldier>(1000, new Comparator<Soldier>() {
            @Override
            public int compare(Soldier o1, Soldier o2) {
                return o1.time - o2.time;
            }

        });
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int T = sc.nextInt();
            System.out.println("Case #" + tc + ":");
            int index = 1;
            boolean progress = false;
            Soldier curSoldier = new Soldier(0, 0);
            for (int i = 0; i < 1000000; i++) {
                if(i <= T) {
                    int nk = sc.nextInt();
                    for (int j = 0; j < nk; j++) {
                        int time = sc.nextInt();
                        pq.add(new Soldier(index++, time));
                    }
                }
                if(pq.isEmpty() && i > T){
                    break;
                }
                if(!progress){
                    if(pq.isEmpty()){
                        System.out.println("wait");
                    }else {
                        curSoldier = pq.peek();
                        if(i >=curSoldier.time){
                            pq.poll();
                            System.out.println("send " + curSoldier.index);
                            curSoldier.time--;
                            progress = true;
                        }else {
                            System.out.println("wait");
                        }
                    }
                }else {
                    System.out.println("wait");
                    curSoldier.time--;
                }
                if (curSoldier.time == 0) {
                    progress = false;
                }
            }
        }
    }
    static class Soldier{
        int index;
        int time;
        public Soldier(int index, int time) {
            this.index = index;
            this.time = time;
        }
    }
}
