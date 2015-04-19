package week9;

import java.util.Scanner;

/**
 * Created by hang on 11.12.14.
 */
public class BridgeBall {
    public static void main(String[] args) {
//        BridgeBall bb = new BridgeBall();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            System.out.println("Case #" + tc+":");
            int oneTimePrice = sc.nextInt();
            int flatRate = sc.nextInt();
            sc.nextLine();
            int failureCount = 0;
            boolean flatRateBought = false;
            while (true){
                String result = sc.nextLine();
                if(result.equalsIgnoreCase("success")) break;
                failureCount++;
                if (((failureCount + 1) * oneTimePrice) < flatRate) {
                    System.out.println("buy_one");
                }else{
                    if(flatRateBought){
                        System.out.println("use_flatrate");
                    }
                    else {
                        System.out.println("buy_flatrate");
                        flatRateBought = true;
                    }
                }
            }
            if(tc < t -1){
                sc.nextLine();
            }
        }
    }
}
