package week7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by hang on 24.11.14.
 */
public class BoxStack {
    Box[] boxes;
    int maxH;
    int N;
    public static void main(String[] args) {
        BoxStack bs = new BoxStack();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int maxH = sc.nextInt();
            int n = sc.nextInt();
            Box[] boxes = new Box[n * 3];
            int index = 0;
            boolean found = false;
            for (int i = 0; i < n; i++) {
                int h = sc.nextInt();
                int d = sc.nextInt();
                int w = sc.nextInt();
                if (h >= maxH || d >= maxH || w >= maxH) found = true;
                if (!found) {
                    boxes[index] = new Box(h, d, w);
                    index++;

                    // First rotation of box
                    boxes[index] = new Box(w, Math.max(h, d), Math.min(h, d));
                    index++;

                    // Second rotation of box
                    boxes[index] = new Box(d, Math.max(h, w), Math.min(h, w));
                    index++;
                }

            }
            if(found){
                System.out.format("Case #%d: %s\n", tc, "yes");
            }else{
                bs.N = n*3;
                bs.maxH = maxH;
                bs.boxes = boxes;
                System.out.format("Case #%d: %s\n", tc, bs.computeResult());
            }

        }
    }

    private String computeResult() {
        Arrays.sort(boxes, new Comparator<Box>() {
            @Override
            public int compare(Box o1, Box o2) {
                return (o2.depth * o2.width) - (o1.depth * o1.width);
            }
        });
        int[] msh = new int[N];
        for (int i = 0; i < N; i++) {
            msh[i] = boxes[i].height;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if(boxes[i].width < boxes[j].width && boxes[i].depth < boxes[j].depth && msh[i] < msh[j] + boxes[i].height){
                    msh[i] = msh[j] + boxes[i].height;
                    if(msh[i] >= maxH) return "yes";
                }

            }
        }
        return "no";
    }

    static class Box {
        int width;
        int depth;
        int height;

        public Box(int h, int d, int w) {
            this.width = w;
            this.depth = d;
            this.height = h;
        }

    }
}
