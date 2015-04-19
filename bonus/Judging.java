package bonus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by hang on 30.11.14.
 */
public class Judging {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String[] firstArray = new String[n];
        String[] secondArray = new String[n];
        for (int i = 0; i < n; i++) {
            firstArray[i] = br.readLine();
        }
        for (int i = 0; i < n; i++) {
            secondArray[i] = br.readLine();
        }
        int count = 0;
//        int pointer = 0;
        Arrays.sort(firstArray);
        Arrays.sort(secondArray);
        int firstCounter = 0;
        int secondCounter = 0;
//        int times = 0;
        do {
//            times++;
            int diff = firstArray[firstCounter].compareTo(secondArray[secondCounter]);
            if(diff == 0){
                count++;
                firstCounter++;
                secondCounter++;
            }else if(diff < 0){
                firstCounter++;
            }else{
                secondCounter++;
            }
        } while (firstCounter < n && secondCounter < n);
//        System.out.println("Times: "+times);
        System.out.println(count);
    }
}
