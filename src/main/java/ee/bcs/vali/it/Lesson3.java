package ee.bcs.vali.it;

import java.util.*;


public class Lesson3 {

    public static void main(String[] args) {
        /*exercise1
        System.out.println(sum(3,4));

        //exercise2
        int [] myNumbers = {1, 3, 5, 8, -6};
        int [] yourNumbers = {2,5,6};
        sum(myNumbers);
        System.out.println(sum(myNumbers));
        System.out.println(sum(yourNumbers));
        */

        //exercise3
        //System.out.println(factorial(5));

        //exercise4
        //int [] someNumbers = {1, 0, 7, 2, 8, 3, 6};
        //System.out.println(Arrays.toString(sort(someNumbers)));

        //exercise5
        //String myString= "Yes";
        //System.out.println(reverseString(myString));

        //exercise6
        System.out.println(isPrime(33));

    }



    public static int sum(int x, int y) {
        // TODO liida kokku ja tagasta x ja y väärtus
        int mySum = x+y;
        return mySum;
    }

    public static int sum(int[] x){
        // Todo liida kokku kõik numbrid massivis x

        int mySum = 0;
        for (int elem: x){
            mySum +=elem;
        }
        return mySum;
    }

    public static int factorial(int x) {
        // TODO tagasta x faktoriaal.
        // Näiteks
        // x = 5
        // return 5*4*3*2*1 = 120
        int result = 1;
        for (int i=0; i<x; i++){
            result *= (x-i);

        }
        return result;
    }

    public static int[] sort(int[] a) {
        // TODO sorteeri massiiv suuruse järgi
        // Näiteks {2, 6, 8, 1}
        // Väljund {1, 2, 6, 8}
        int temp;
        int [] newArray = new int [a.length];

        for (int i=0; i<a.length; i++){
            for (int k=0; k<a.length-1; k++){
                if (a[k] > a[k+1]) {
                    temp = a[k];
                    a[k] = a[k + 1];
                    a[k + 1] = temp;

                }

            }
        }

        return a;


    }

    public static String reverseString(String a) {
        // TODO tagasta string tagurpidi
        // Näiteks:
        // a = "Test";
        // return tseT";
        String reversedStr = "";
        for (int i=a.length()-1; i>=0; i--){
            reversedStr = reversedStr + a.charAt(i);
        }

        return reversedStr;

    }

    public static boolean isPrime(int x){
        // TODO tagasta kas sisestatud arv on primaar arv (jagub ainult 1 ja iseendaga)
        if (x <= 1){
            return false;
        }
        for(int i=2; i<x; i++){
            if (x%i == 0){
                return false;
            }
        }
        return true;
    }

}
