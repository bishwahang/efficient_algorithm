package week5;

import java.io.IOException;

/**
 * Created by hang on 07.11.14.
 */
public class Bank {
    final int MAX = 10000000;

    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Bank b = new Bank();
//        int t = Integer.parseInt(br.readLine());
//        for (int tc = 1; tc <= t; tc++) {
//            String[] input = br.readLine().split(" ");
//            double l = Double.parseDouble(input[0]);
//            double r = Double.parseDouble(input[1]);
//            double p = Double.parseDouble(input[2]);
//            double y = Double.parseDouble(input[3]);
//            String maxL = b.calculateMaxL(r, p, y);
//            String maxR = b.calculateMaxR(l, p, y);
//            String minP = b.calculateMinP(l, r, y);
//            System.out.format("Case #%d: %s %s %s\n", tc, maxL, maxR, minP);
//        }
//        System.out.println(b.calculateMaxR(100, 5, 1));
//        System.out.println(b.calculateMaxR(50, 10, 2));
//        System.out.println(b.calculateMaxL(5, 10, 1));
//        System.out.println(b.calculateMaxL(10, 50, 2));
//        System.out.println(b.calculateMinP(100, 5, 1));
//        System.out.println(b.calculateMinP(50, 10, 2));
        System.out.println(b.resultantAmount(104243, 13, 12000, 5));
    }

    private String calculateMaxL(double r, double p, double y) {
        int lower = 0;
        int upper = MAX;
        int current = (lower + upper) / 2;
        int lastCurrent = -1;
        for (int i = 0; i < MAX; i++) {
            double curAmount = resultantAmount(current, r, p, y);
            if (curAmount == 0) {
                return Integer.toString(current);
            }
            if (curAmount < 0) {
                lastCurrent = current;
                if ((upper - current) == 0)
                    break;
                if ((upper - current) == 1)
                    current = upper;
                lower = current;
                current = (current + upper) / 2;
            } else {
                if ((upper - current) == 0)
                    break;
                if ((current - lower) == 1) {
                    current = lower;
                }
                upper = current;
                current = (current + lower) / 2;

            }
        }
        if(lastCurrent == -1){
            return "0";
        }else{
            return Integer.toString(lastCurrent);
        }

    }

    private String calculateMaxR(double l, double p, double y) {
        int lower = 0;
        int upper = MAX;
        int current = (lower + upper) / 2;
        int lastCurrent = -1;
        for (int i = 0; i < MAX; i++) {
            double curAmount = resultantAmount(l, current, p, y);
            if (curAmount == 0) {
                return Integer.toString(current);
            }
            if (curAmount < 0) {
                lastCurrent = current;
                if ((upper - current) == 0)
                    break;
                if ((upper - current) == 1)
                    current = upper;
                lower = current;
                current = (current + upper) / 2;

            } else {
                if ((upper - current) == 0)
                    break;
                if ((current - lower) == 1) {
                    current = lower;
                }
                upper = current;
                current = (current + lower) / 2;

            }
        }
        if (lastCurrent == MAX) {
            return "infinity";
        } else if (lastCurrent == -1) {
            return "0";
        } else {
            return Integer.toString(lastCurrent);
        }
    }

    private String calculateMinP(double l, double r, double y) {
        int lower = 0;
        int upper = MAX;
        int current = (lower + upper) / 2;
        int lastCurrent = -1;
        for (int i = 0; i < MAX; i++) {
            double curAmount = resultantAmount(l, r, current, y);
            if (curAmount == 0) {
                return Integer.toString(current);
            }
            if (curAmount > 0) {
                if ((upper - current) == 0)
                    break;
                if ((upper - current) == 1)
                    current = upper;
                lower = current;
                current = (current + upper) / 2;
            } else {
                lastCurrent = current;
                if ((upper - current) == 0)
                    break;
                if ((current - lower) == 1) {
                    current = lower;
                }
                upper = current;
                current = (current + lower) / 2;

            }
        }
        return Integer.toString(lastCurrent);
    }

    private double resultantAmount(double l, double r, double p, double y) {
        double curAmount = l;
        double rate = r / 100;
        double months = y * 12;
        for (double i = 0; i < months; i++) {
            curAmount = curAmount - p;
            curAmount = Math.floor(curAmount + (curAmount * rate));
        }
        return curAmount;
    }

}
