/**
 * Schreiben Sie eine Methode, die fur eine gegebene Zahl bestimmt, ob es sich
 * um eine Primzahl handelt (3 Punkte).
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Teil1 {
    
    public static void run() {
    	int zahl = 0;
    	System.out.println("Please write your max: ");
        Scanner c = new Scanner(System.in);
        if(c.hasNext())
        	try {
        		zahl = c.nextInt();
        	}
        catch(InputMismatchException e) {
        	System.out.println("You must enter an integer number. Restarting...");
        	Teil1.run();
        	return;
        }
        
        if(zahl <= 1) {
        	System.out.println(zahl + " must be greter than 1");
        	return;
        }
        
        boolean rsp = isPrime(zahl);
        if(rsp) {
        	System.out.println(zahl + " is prime.");
        }
        else {
        	System.out.println(zahl + " is NOT prime.");
        }
        
    }
    
    /**
     * This method determine if n is prime or not
     * @param n the number to determine if it is prime or not
     * @return true if n is prime, false otherwise
     */
    private static boolean isPrime(int n) {
    	//A number is prime if it can be devided for 1 and itself
    	//Test: 1,2,3,5,7,11 are prime
    	for(int i = 2; i < n; i++) {
    		if(n % i == 0)
    			return false;
    	}
    	return true;
    	
    }

}
