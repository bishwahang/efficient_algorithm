package week4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hang on 02.11.14.
 */
public class VaultVampire {
    public static void main(String[] args) {
        VaultVampire vv = new VaultVampire();
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int tc = 1; tc <= t; tc++) {
            int n = sc.nextInt();
            String d = sc.nextLine().trim();
            System.out.format("Case #%d: %s\n", tc, vv.process(n, d));
        }
    }

    // ScriptEngineManager manager = new ScriptEngineManager();
    // ScriptEngine engine = manager.getEngineByName("js");
    // Object result = engine.eval("2+3");
    // System.out.println(result.toString());

    private String process(int n, String d) {
        String[] dices = d.split("\\+"); //split in + to get dices of different sides
        ArrayList<Dice> diceList = new ArrayList<Dice>();
        diceList.add(0, new Dice(1)); // just for easy of accessing the list later with index starting from 1
        for (String s : dices) {
            String[] values = s.split("d"); // get the number of dice with particular sides
            for (int i = 0; i < Integer.parseInt(values[0]); i++) {
                diceList.add(new Dice(Integer.parseInt(values[1]))); // add all the dices in single list
            }
        }

        RationalNumber[][] p = new RationalNumber[diceList.size()][n + 1];
        int numberOfDice = diceList.size() - 1;
        Dice currentDice = diceList.get(1);
        int marker = currentDice.sides + 1;
        for (int j = 0; j <= n; j++) {
            if (j == 0) {
                p[1][j] = new RationalNumber("1", "1");
            } else {
                int num = marker - j;
                if(num > 0){
                    p[1][j] = new RationalNumber(num+"", currentDice.sides + "");
                }else{
                    p[1][j] = new RationalNumber("0","1");
                }
            }
        }
        for (int i = 2; i <= numberOfDice; i++) {
            currentDice = diceList.get(i);
            for (int j = 0; j <= n; j++) {
                RationalNumber sum = new RationalNumber("0", "1");
                for (int k = 1; k <= currentDice.sides; k++) {
                    RationalNumber prob;
                    if (k > j) {
                        prob = new RationalNumber("1", "1");
                    } else {
                        prob = p[i - 1][j - k];
                    }
                    BigInteger A = sum.numerator.multiply(prob.denominator.multiply(BigInteger.valueOf(currentDice.sides)));
                    BigInteger B = sum.denominator.multiply(prob.numerator);
                    sum.numerator = A.add(B);
                    sum.denominator = sum.denominator.multiply(prob.denominator.multiply(BigInteger.valueOf(currentDice.sides)));
                    sum.performGcd();
                }
                p[i][j] = sum;
            }

        }
        RationalNumber res = p[numberOfDice][n];
        if("0".equalsIgnoreCase(res.numerator.toString())){
            res.denominator= BigInteger.valueOf(1);
        }
//        res.performGcd();
        return res.numerator.toString() + "/" + res.denominator.toString();
    }

    static class Dice {

        int sides;

        Dice(int sides) {
            this.sides = sides;
        }
    }

    static class RationalNumber {

        public BigInteger numerator;
        public BigInteger denominator;

        RationalNumber(String n, String d) {
            this.numerator = new BigInteger(n);
            this.denominator = new BigInteger(d);
            performGcd();
        }

        void performGcd() {
            BigInteger gcd = numerator.gcd(denominator);
            if (!("0".equalsIgnoreCase(gcd.toString()))) {
                numerator = numerator.divide(gcd);
                denominator = denominator.divide(gcd);
            }

        }
    }
}
