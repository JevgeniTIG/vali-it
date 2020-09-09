package ee.bcs.vali.it;

import java.util.Arrays;

public class OneTest {

    public static String loginme(String a){
        if (a.equals("Hello")){
            return "Hi, man";
        }else {
            return "Bad Bad";
        }

    }

    public static String loginform(int pass){
        if (pass == 666){
            return "Welcome, Mr.Twister";
        }else {
            return "Password incorrect";
        }

    }

    public static String array2d(int a){
        int [] [] myFancyArray = new int [a] [a];
        for (int i = 0; i < a; i++){
            for (int k = 0; k < a; k++){
                myFancyArray[i] [k] = (i+1)*(k+1);
            }

        }
        return Arrays.toString(myFancyArray);

    }



}
