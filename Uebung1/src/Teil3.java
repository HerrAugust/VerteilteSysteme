/**
 * Das Programm soll nun beschleunigt werden, indem Tests auf Primzahlen in 
 * nebenlaufig ausgefuhrten Threads durchgefuhrt werden. Erweitern Sie das 
 * Programm derart, dass es mit Hilfe von Threads die Anzahl der in einem
 * Wertebereich befindlichen Primzahlen bestimmen kann. Verwenden Sie 4
 * nebenlaufige Threads, die jeweils ein Viertel des Wertebereichs prufen und
 * starten Sie die Analyse aus Aufgabenteil (b) erneut (9 Punkte).
 * 
 * Use 4 concurrent threads to calculate the number of prime numbers between 1 and max.
 * Each thread should calculate if the number max is prime and the number of prime numbers of
 * a quarter of max.
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Teil3 {
    
    public static void run() {
    	int max = 0;
        Scanner c = new Scanner(System.in);

        System.out.println("Please write your max: ");
        try {
        	max = c.nextInt();
        	if(max <= 1) {
                System.out.println("You must enter a number greter than 1. Restarting...");
                Teil3.run();
                return;
            }
        }
        catch(InputMismatchException e) {
        	System.out.println("You must enter an integer!");
        	Teil3.run();
        }
        
        //Determine if max is prime
        boolean rsp = isPrime(max);
        if(rsp) {
        	System.out.println(max + " is prime.");
        }
        else {
        	System.out.println(max + " is NOT prime.");
        }
        //Determine the number of prime numbers between 1 and max.
        //Here threads are used. Using Runnable interface and Thread class
        int anzahl = 0;
        if(max == 1)
        	anzahl = 1;
        else if(max == 2)
        	anzahl = 2;
        else if(max == 3)
        	anzahl = 3;
        else { //without these checks there will be problems of division with max < 4
        	ThreadedCounter tc1 = new ThreadedCounter(1,max/4);
	        Thread t1 = new Thread(tc1);
	        t1.start();
	        ThreadedCounter tc2 = new ThreadedCounter(max/4 + 1, max/2);
	        Thread t2 = new Thread(tc2);
	        t2.start();
	        ThreadedCounter tc3 = new ThreadedCounter(max/2 + 1, 3*max/4);
	        Thread t3 = new Thread(tc3);
	        t3.start();
	        ThreadedCounter tc4 = new ThreadedCounter(3*max/4 + 1, max);
	        Thread t4 = new Thread(tc4);
	        t4.start();
	        try {
	        	t1.join(); //wait for t1 completion
	        	t2.join();
	        	t3.join();
	        	t4.join();
	        	anzahl = tc1.anzahlForThisThread + tc2.anzahlForThisThread + tc3.anzahlForThisThread + tc4.anzahlForThisThread;
	        }
	        catch(InterruptedException e) {}
        }
        
        System.out.println("The number of prime numbers between 1 and " + max + " is: " + anzahl);
    }
    
    /**
     * This method determine if n is prime or not
     * @param n the number to determine if it is prime or not
     * @return true if n is prime, false otherwise
     */
    static boolean isPrime(int n) {
        //a number is prime if you can divide it only by 1 and for the number itself
    	//Test: 1,2,3,5,7,11 are prime
        for(int i = 2; i < n; i++) {
            if(n % i == 0)
                return false;
        }
        return true;
    }
}
