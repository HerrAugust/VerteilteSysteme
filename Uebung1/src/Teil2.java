/**
 * Schreiben Sie ein Programm, das fur alle Werte zwischen 1 und der Variablen max per Aufruf
 * der zuvor entwickelten Methode ermittelt, ob es sich um Primzahlen handelt und am Ende
 * die Anzahl aller Primzahlen ausgibt. Setzen Sie fur max hierbei initial den Wert
 * 4.000.000 ein (2 Punkte).
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Teil2 {
	
    static int anzahl = 0; //number of found prime numbers
    
    public static void run() {
    	int max = 4000000;
        Scanner c = new Scanner(System.in);

        System.out.println("Please write your max: ");
        try {
        	max = c.nextInt();
        	if(max <= 1) {
                System.out.println("You must enter a number greter than 1. Restarting...");
                Teil2.run();
                return;
            }
        }
        catch(InputMismatchException e) {
        	System.out.println("You must enter an integer!");
        	Teil2.run();
        }
        
        //Determine if max is prime
        boolean rsp = isPrime(max);
        if(rsp) {
        	System.out.println(max + " is prime.");
        }
        else {
        	System.out.println(max + " is NOT prime.");
        }
        //Determine the number of prime numbers between 1 and max
        methodeA(max);
    }
    
    /**
     * Determines how many prime numbers there are between 1 and max
     * @param n the max
     */
    private static void methodeA(int n) {
    	anzahl = 1; //1 already counted as prime number
        for(int i = 2; i <= n; i++) {
            if(isPrime(i)) {
                anzahl++;
            }
        }
        System.out.println("This is the number of prime numbers between 1 and " + n + ": " + anzahl);
    }
    
    /**
     * This method determine if n is prime or not
     * @param n the number to determine if it is prime or not
     * @return true if n is prime, false otherwise
     */
    private static boolean isPrime(int n) {
        //a number is prime if you can divide it only by 1 and for the number itself
    	//Test: 1,2,3,5,7,11 are prime
        for(int i = 2; i < n; i++) {
            if(n % i == 0)
                return false;
        }
        return true;
    }

}
