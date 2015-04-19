//package bonus;
//
//import java.io.IOException;
//import java.util.Scanner;
//
///**
// * Created by hang on 30.11.14.
// */
//public class Lines {
//    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int percent = sc.nextInt();
//        int times = (int) Math.ceil((double) (n * percent) / 100);
//        int[] yCordinates = new int[n];
//        int[] xCordinates = new int[n];
//        for (int i = 0; i < n; i++) {
//            yCordinates[i] = sc.nextInt();
//            xCordinates[i] = sc.nextInt();
//        }
//        for (int i = 0; i < n; i++) {
//            int count = 0;
//            int pointX = xCordinates[i];
//            int pointY = yCordinates[i];
//            for (int j = 0; j < n; j++) {
//                int secondPointX = xCordinates[j];
//                int secondPointY = yCordinates[j];
//                int dx = secondPointX - pointX;
//                int dy = secondPointY - pointY;
//                if(dx == 0 || dy == 0 || dx/dy)
//            }
//        }
//    }
//}
