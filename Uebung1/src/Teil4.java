/**
 * Erweitern Sie das Programm um eine Angabe der benotigten Ausfuhrungszeit
 * (nach Beendigung) sowie eine Fortschrittsanzeige (eine Konsolenausgabe ist 
 * ausreichend). Geben Sie die Laufzeiten an, die Ihr Programm benotigt, um alle
 * Primzahlen fur einen von den ¨ Ubungs-Betreuern vorgegebenen Wert von max
 * zu finden, wenn 2, 4 und 8 Threads eingesetzt werden, sowie wenn gar keine
 * Nebenlaufigkeit zum Einsatz kommt (3 Punkte).
 * @author agostino
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Teil4 {
    
    public static void run() {
    	int max = 0;
        Scanner c = new Scanner(System.in);

        System.out.println("Please write your maximum: ");
        try {
        	max = c.nextInt();
        	if(max <= 0) {
                System.out.println("You must enter a number greter than 0. Restarting...");
                Teil4.run();
                return;
            }
        }
        catch(InputMismatchException e) {
        	System.out.println("You must enter an integer!");
        	Teil4.run();
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
        //Here threads are used. Using Runnable and AtomicBoolean
        noThread(max); System.out.println("");
        twoThreads(max); System.out.println("");
        fourThreads(max); System.out.println("");
        eightThreads(max); System.out.println("");
        
    }
    
    private static void noThread(int max) {
    	System.out.println("Calculating number of prime numbers with no thread");
    	long starttime = System.currentTimeMillis();
    	int anzahl = 1; //1 already counted as prime number
        for(int i = 2; i <= max; i++) {
            if(isPrime(i)) {
                anzahl++;
            }
        }
        long stoptime = System.currentTimeMillis();
        System.out.println("This is the number of prime numbers between 1 and " + max + ": " + anzahl);
        System.out.println("Total elapsed time (millisec): " + (stoptime - starttime));
    }
    
    private static void twoThreads(int max) {
    	System.out.println("Calculating number of prime numbers with two threads");
    	int anzahl = 0;
    	long elapsedTime = 0;
    	
    	if(max == 1) {
    		anzahl = 1;
    	}
    	else {
	    	ThreadedCounter tc1 = new ThreadedCounter(1,max/2);
	        Thread t1 = new Thread(tc1);
	        t1.start();
	        ThreadedCounter tc2 = new ThreadedCounter(max/2 + 1, max);
	        Thread t2 = new Thread(tc2);
	        t2.start();
	        
	        try {
	        	t1.join(); //wait for t1 completion
	        	t2.join();
	        	anzahl = tc1.anzahlForThisThread + tc2.anzahlForThisThread;
	        	elapsedTime = tc1.elapsedTimeForThisThread + tc2.elapsedTimeForThisThread;
	        }
	        catch(InterruptedException e) {}
    	}
	    
	    System.out.println("The number of prime numbers between 1 and " + max + " is: " + anzahl);
	    System.out.println("Total elapsed time (millisec): " + elapsedTime);
    }
    
    private static void fourThreads(int max) {
    	System.out.println("Calculating number of prime numbers with four threads");
    	int anzahl = 0;
        long elapsedTime = 0;
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
	        	elapsedTime = tc1.elapsedTimeForThisThread + tc2.elapsedTimeForThisThread + tc3.elapsedTimeForThisThread + tc4.elapsedTimeForThisThread;
	        }
	        catch(InterruptedException e) {}
        }
        
        System.out.println("The number of prime numbers between 1 and " + max + " is: " + anzahl);
        System.out.println("Total elapsed time (millisec): " + elapsedTime);
    }
    
    private static void eightThreads(int max) {
    	System.out.println("Calculating number of prime numbers with eight threads");
    	int anzahl = 0;
        long elapsedTime = 0;
        switch(max) {
        case 1:
        	anzahl = 1;
        	break;
        case 2:
        	anzahl = 2;
        	break;
        case 3:
        	anzahl = 3;
        	break;
        case 4:
        	anzahl = 3;
        	break;
        case 5:
        	anzahl = 5;
        	break;
        case 6:
        	anzahl = 5;
        	break;
        case 7:
        	anzahl = 6;
        	break;
        default: //without these checks there will be problems of division with max < 4
        	ThreadedCounter tc1 = new ThreadedCounter(1,max/8);
	        Thread t1 = new Thread(tc1);
	        t1.start();
	        ThreadedCounter tc2 = new ThreadedCounter(max/8 + 1, max/4);
	        Thread t2 = new Thread(tc2);
	        t2.start();
	        ThreadedCounter tc3 = new ThreadedCounter(max/4 + 1, 3*max/8);
	        Thread t3 = new Thread(tc3);
	        t3.start();
	        ThreadedCounter tc4 = new ThreadedCounter(3*max/8 + 1, max/2);
	        Thread t4 = new Thread(tc4);
	        t4.start();
	        ThreadedCounter tc5 = new ThreadedCounter(max/2 + 1, 5*max/8);
	        Thread t5 = new Thread(tc5);
	        t5.start();
	        ThreadedCounter tc6 = new ThreadedCounter(5*max/8 + 1, 6*max/8);
	        Thread t6 = new Thread(tc6);
	        t6.start();
	        ThreadedCounter tc7 = new ThreadedCounter(6*max/8 + 1, 7*max/8);
	        Thread t7 = new Thread(tc7);
	        t7.start();
	        ThreadedCounter tc8 = new ThreadedCounter(7*max/8 + 1, max);
	        Thread t8 = new Thread(tc8);
	        t8.start();
	        try {
	        	t1.join(); //wait for t1 completion
	        	t2.join();
	        	t3.join();
	        	t4.join();
	        	t5.join();
	        	t6.join();
	        	t7.join();
	        	t8.join();
	        	anzahl = tc1.anzahlForThisThread + tc2.anzahlForThisThread + tc3.anzahlForThisThread + tc4.anzahlForThisThread;
	        	anzahl += tc5.anzahlForThisThread + tc6.anzahlForThisThread + tc7.anzahlForThisThread + tc8.anzahlForThisThread;
	        	elapsedTime = tc1.elapsedTimeForThisThread + tc2.elapsedTimeForThisThread + tc3.elapsedTimeForThisThread + tc4.elapsedTimeForThisThread;
	        	elapsedTime += tc5.elapsedTimeForThisThread + tc6.elapsedTimeForThisThread + tc7.elapsedTimeForThisThread + tc8.elapsedTimeForThisThread;
	        }
	        catch(InterruptedException e) {}
	        break;
        }
        
        System.out.println("The number of prime numbers between 1 and " + max + " is: " + anzahl);
        System.out.println("Total elapsed time (millisec): " + elapsedTime);
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
