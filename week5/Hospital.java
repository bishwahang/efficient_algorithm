package week5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by hang on 11.11.14.
 */
public class Hospital {
    int[] masterRoom;
    static class Station{
        int start;
        int end;
        public Station(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    public static void main(String[] args) throws IOException {
        Hospital hh = new Hospital();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= t; tc++) {
            String[] sf = br.readLine().split(" ");
            int s = Integer.parseInt(sf[0]);
            int f = Integer.parseInt(sf[1]);
            ArrayList<Station> stations = new ArrayList<Station>();

            for (int i = 0; i < s; i++) {
                String st[] = br.readLine().split(" ");
                stations.add( new Station(Integer.parseInt(st[0]),Integer.parseInt(st[1])));
            }
            ArrayList<Integer> fRooms = new ArrayList<Integer>();
            for (int i = 0; i < f; i++) {
                fRooms.add(Integer.parseInt(br.readLine()));
            }
            hh.process(stations);
            System.out.format("Case #%d:\n", tc);
            for (int i = 0; i < f; i++) {
//                System.out.println(hh.findRoom(fRooms.get(i)));
            }
        }
    }

    private void process(ArrayList<Station> stations) {
        masterRoom = new int[2147483647];
        StringBuilder sb = new StringBuilder();
        Collections.sort(stations, new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                return o1.start - o2.start;
            }
        });
        Station first = stations.get(0);
        int last = -1;
        for (int i = first.start; i <= first.end ; i++) {
            last++;
            masterRoom[last] = i;
        }
        for (int i = 1; i < stations.size(); i++) {
            Station s = stations.get(i);
            if(s.start >= masterRoom[last]){
                // TODO: add all
                continue;
            }
            int current = s.start;
        }
    }

//    private int findRoom(Integer i) {
////        return masterList.get(i-1);
//    }

}
