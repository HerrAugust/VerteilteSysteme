import java.util.Scanner;

public class Teil2 {
	
    static int anzahl = 0; //number of found prime numbers
    
    public static void main(String[] args) {
        Scanner c = new Scanner(System.in);

        System.out.println("Bitte schreiben Sie seine Obergranz: ");
        int max = c.nextInt();
        
        methodeA(max);
        
        c.close();
    }
    
//Teta(n)
    private static void methodeA(int n) {
        if(n < 0) {
            System.out.println("You should enter a number greter than 0");
        }
        
        for(int i = 1; i <= n; i++) {
            if(isPrime(i)) {
                anzahl++;
                
            }
        }
        System.out.println(anzahl);
    }
    
//O(n)
    public static boolean isPrime(int n) {
        //a number is prime if you can divide it only by 1 and for the number itself

        for(int i = 2; i < n; i++) {
            if(n % i == 0)
                return false;
        }
        return true;
    }

}