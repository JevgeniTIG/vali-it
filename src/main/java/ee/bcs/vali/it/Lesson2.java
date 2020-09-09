package ee.bcs.vali.it;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;

public class Lesson2 {

    public static void main(String[] args) throws FileNotFoundException {
        //exercise1();
        //exercise2(5);
        //exercise3(3, 3);
        //System.out.println(fibonacci(7));
        //exercise5();
        /*
        try {
            exercise6();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */

        try {
            exercise6a();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        //exercise7();


        //exercise8();
    }

    public static void exercise1() {
        // TODO loo 10 elemendile täisarvude massiv
        // TODO loe sisse konsoolist 10 täisarvu
        // TODO trüki arvud välja vastupidises järiekorras
        int [] myArray = new int[10];

        Scanner sc = new Scanner(System.in);
        System.out.println("Input 10 numbers: ");
        for (int i=0; i<10; i++){
            int userInput=sc.nextInt();
            myArray[i] = userInput;
        }

        for (int k=myArray.length-1; k>0; k--){
            System.out.print(myArray[k]);
        }
    }

    public static void exercise2(int x) {
        // TODO prindi välja x esimest paaris arvu
        // Näide:
        // Sisend 5
        // Väljund 2 4 6 8 10
        int temp = 0;
        for (int i =0; i<100; i+=2){
            if(temp<x){
                System.out.print(i + " ");
                temp+=1;
            }

        }
    }

    public static void exercise3(int x, int y) {
        // TODO trüki välja korrutustabel mis on x ühikut lai ja y ühikut kõrge
        // TODO näiteks x = 3 y = 3
        // TODO väljund
        // 1 2 3
        // 2 4 6
        // 3 6 9
        int [][] myTable = new int[x][y];

        for (int i=0; i<x; i++){
            for (int k = 0; k<y; k++){

                System.out.print((i+1)*(k+1));
            }
            System.out.println(" ");

        }

    }

    public static int fibonacci(int n) {
        // TODO
        // Fibonacci jada on fib(n) = fib(n-1) + fib(n-2);
        // 0, 1, 1, 2, 3, 5, 8, 13, 21
        // Tagasta fibonacci jada n element

        int elementN;
        int [] fibonacciArray = new int [n];
        fibonacciArray[0] = 0;
        fibonacciArray[1] = 1;
            for(int i=2; i<n; i++) {
                fibonacciArray[i] = fibonacciArray[i-1] + fibonacciArray[i-2];

            }

            for(int k=0; k<fibonacciArray.length; k++){
                System.out.print(fibonacciArray[k]);
            }
            System.out.println(" ");
            return fibonacciArray[n-1];


    }

    public static void exercise5() {
        // https://onlinejudge.org/index.php?option=onlinejudge&Itemid=8&page=show_problem&problem=36
        /*

        input n
        print n
        if n = 1 then STOP
        if n is odd then n ←− 3n + 1
        else n ←− n/2 GOTO 2
         */
        Scanner sc = new Scanner(System.in);
        System.out.println("Input a number n: ");
        int n = sc.nextInt();
        while(true){


            System.out.print(n + " ");
            if (n==1){
                break;
            }
            else if(n%2==0){
                n = 3*n + 1;
            }
            else{
                n = n/2;
            }
        }


    }

    public static void exercise6() throws FileNotFoundException {
        /*
            Kirjutada Java programm, mis loeb failist visits.txt sisse looduspargi külastajad erinevatel jaanuari päevadel ning
            a) sorteerib külastuspäevad külastajate arvu järgi kasvavalt ning prindib tulemuse konsoolile;
            b) prindib konsoolile päeva, mil külastajaid oli kõige rohkem.
            Faili asukoht tuleb programmile ette anda käsurea parameetrina.
         */
        File file = new File("/Users/ZEKA/Documents/GitHub/vali-it/resources/visits.txt");
        Scanner sc = new Scanner(file);
        List<String> january = new ArrayList<String>();
        String numberOfGuests;
        List <Integer> numberOfGuestsNumeric = new ArrayList<Integer>();
        int i;

        while (sc.hasNextLine()){
            String lineInFile = sc.nextLine();
            if (lineInFile.length() == 15){
                numberOfGuests = lineInFile.substring(12, 15);
                january.add(numberOfGuests);
                i = Integer.parseInt(numberOfGuests);
                numberOfGuestsNumeric.add(i);
            }
            else if(lineInFile.length() == 16){
                numberOfGuests = lineInFile.substring(12, 16);
                january.add(numberOfGuests);
                i = Integer.parseInt(numberOfGuests);
                numberOfGuestsNumeric.add(i);

            }


        }
        System.out.println(january);
        Collections.sort(numberOfGuestsNumeric);
        System.out.println(numberOfGuestsNumeric);

        int largest = Collections.max(numberOfGuestsNumeric);
        System.out.println(largest);
        int theLineIndex;
        for (var lineInFile: january){
            if(lineInFile.contains(Integer.toString(largest))){
                theLineIndex = january.indexOf(lineInFile);
                System.out.println(theLineIndex);
            }
        }

        Scanner newsc = new Scanner(file);
        List<String> theDay = new ArrayList<String>();
        while (newsc.hasNextLine()){
            String biggestNumberOfGuests = newsc.nextLine();
            //System.out.println(biggestNumberOfGuests);

            theDay.add(biggestNumberOfGuests);



        }
        //System.out.println(theDay.get(theLineIndex).toString());

    }

    public static void exercise6a() throws FileNotFoundException {
        /*
            Kirjutada Java programm, mis loeb failist visits.txt sisse looduspargi külastajad erinevatel jaanuari päevadel ning
            a) sorteerib külastuspäevad külastajate arvu järgi kasvavalt ning prindib tulemuse konsoolile;
            b) prindib konsoolile päeva, mil külastajaid oli kõige rohkem.
            Faili asukoht tuleb programmile ette anda käsurea parameetrina.

         */

        File file = new File("/Users/ZEKA/Documents/GitHub/vali-it/resources/visits.txt");
        Scanner sc = new Scanner(file);
        Map<String, Integer> januaryGuests1 = new HashMap<>();
        Map<Integer, String> januaryGuests2 = new HashMap<>();
        String line;

        while(sc.hasNextLine()){
            line = sc.nextLine();
            januaryGuests1.put(line.substring(0, 11), Integer.parseInt(line.substring(12, line.length())));
            januaryGuests2.put(Integer.parseInt(line.substring(12, line.length())),line.substring(0,11));

        }

        System.out.println(januaryGuests1);//when date is a key and number is a value
        System.out.println(januaryGuests2);//when number is a key and date is a value
        Map<Integer, String> sortedJanuaryGuests = new TreeMap<>(januaryGuests2);
        for (Map.Entry entry: sortedJanuaryGuests.entrySet()){
            System.out.println("Number of guests on " + entry.getValue().toString().substring(0, 10) + " was " + entry.getKey());

        }
        System.out.println("");
        System.out.println("On " + sortedJanuaryGuests.get(7421).substring(0,10) +
                " was the biggest number of guests");

        Map<String, Integer> sortedJanuaryGuests2 = new TreeMap<>(januaryGuests1);
        int maxNumberOfGuests = 0;
        for (Map.Entry entry: sortedJanuaryGuests2.entrySet()){
            //System.out.println(Integer.parseInt(entry.getValue().toString()));
            if(Integer.parseInt(entry.getValue().toString()) > maxNumberOfGuests){
                maxNumberOfGuests = Integer.parseInt(entry.getValue().toString());

            }
        }

        System.out.println(januaryGuests2.get(maxNumberOfGuests).substring(0,10));


    }



    public static void exercise7() {
        // TODO arvuta kasutades BigDecimali 1.89 * ((394486820340 / 15 ) - 4 )
        BigDecimal a = new BigDecimal("1.89");
        BigDecimal b = new BigDecimal("394486820340");
        BigDecimal c = new BigDecimal("15");
        BigDecimal d = new BigDecimal("4");

        BigDecimal result = a.multiply((b.divide(c)).subtract(d));
        System.out.println(result);
    }

    public static void exercise8() throws FileNotFoundException {
        /*
        Failis nums.txt on üksteise all 150 60-kohalist numbrit.

        Kirjuta programm, mis loeks antud numbrid failist sisse ja liidaks need arvud kokku ning kuvaks ekraanil summa.
        Faili nimi tuleb programmile ette anda käsurea parameetrina.

        VASTUS:
        Õige summa: 77378062799264987173249634924670947389130820063105651135266574
         */
        File fileNumbers = new File("/Users/ZEKA/Documents/GitHub/vali-it/resources/nums.txt");
        Scanner sc = new Scanner(fileNumbers);
        List<String> arrayOfBigNumbers = new ArrayList<>();
        int count=0;
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            arrayOfBigNumbers.add(line);
            count++;
        }
        BigInteger sum = BigInteger.ZERO;
        for (int i=0; i<count; i++ ){
            sum = sum.add(new BigInteger(arrayOfBigNumbers.get(i)));

        }
        System.out.println("Sum of all numbers in file nums.txt is: " + sum);

    }

    public static void exercise9() {
        /* TODO
        Sama mis eelmises ülesandes aga ära kasuta BigInt ega BigDecimal klassi
         */
    }

}
