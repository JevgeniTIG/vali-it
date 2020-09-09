package ee.bcs.vali.it;

import java.util.*;

// Enne kui seda tegema hakkad tee ära Lesson 2 (välja arvatud ülesanded 6, 8, 9)
public class Lesson3Hard {
    public static void main(String[] args) {
        //exercise1
        //System.out.println(evenFibonacci(13));

        //exercise2
        randomGame();

        //exercise3
        //System.out.println(morseCode("1acba1"));
    }

    public static int evenFibonacci(int x){
        // TODO liida kokku kõik paaris fibonacci arvud kuni numbrini x
        // Fibonacci jada on fib(n) = fib(n-1) + fib(n-2);
        // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144
        int elementN;
        int [] fibonacciArray = new int [x];
        fibonacciArray[0] = 0;
        fibonacciArray[1] = 1;
        for(int i=2; i<x; i++) {
            fibonacciArray[i] = fibonacciArray[i-1] + fibonacciArray[i-2];

        }
        int sum = 0;
        for(int k=0; k<fibonacciArray.length; k++){
            if (fibonacciArray[k]%2==0){
                sum = sum + fibonacciArray[k];
            }
        }
        return sum;
    }

    public static void randomGame(){
        // TODO kirjuta mäng mis võtab suvalise numbri 0-100, mille kasutaja peab ära arvama
        // iga kord pärast kasutaja sisestatud täis arvu peab programm ütlema kas number oli suurem või väiksem
        // ja kasutaja peab saama uuesti arvata
        // numbri ära arvamise korral peab programm välja trükkima mitu katset läks numbri ära arvamiseks
        Random random = new Random();
        int i = random.nextInt(100);
        //System.out.println("Computer randomly guessed number " + "\033[1;94m" + i);
        System.out.println("\u001B[0m" + "Please guess a number 0-100: ");
        Scanner sc = new Scanner(System.in);
        int count = 0;
        while (true){
            int userGuess = sc.nextInt();
            count++;
            if(userGuess < 0 || userGuess > 100){
                System.out.println("Your guess is out of bounds!");
                break;
            }
            else if(userGuess == i){
                System.out.println("Congratulations! You WIN! The guessed number is " + userGuess);
                System.out.println("You managed to guess the number on " + count + " attempt");
            }
            else if(userGuess < i){
                System.out.println("Guessed number is bigger. Try again: ");

            }
            else if(userGuess > i){
                System.out.println("Guessed number is smaller. Try again: ");
            }


        }


    }

    public static String morseCode(String text){
        // TODO kirjuta programm, mis tagastab sisestatud teksti morse koodis (https://en.wikipedia.org/wiki/Morse_code)
        // Kasuta sümboleid . ja -
        String [] textMessageAsArray = new String [text.length()];
        String [] symbolsArray = {"a", "b", "c", "1"};
        String [] morseArray = {".-", "-...", "-.-.", ".----"};
        String [] morseMessage = new String[text.length()];

        for(int j = 0; j < text.length(); j++){
            textMessageAsArray[j] = text.substring(j, j+1);
        }

        for (int i = 0; i < text.length(); i++){
            for(int k = 0; k<symbolsArray.length; k++){
                if (symbolsArray[k].equals(textMessageAsArray[i])){
                    morseMessage[i] = morseArray[k];
                }
            }
        }

        return Arrays.toString(morseMessage);
    }
}
