import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner c = new Scanner(System.in);

        System.out.println("Bitte schreiben Sie, welcher Teil Sie prüfen möchten [1,2,3,4]: ");
        int n = c.nextInt();
        c.close();
        
        switch(n) {
        case 1:
        	Teil1.main(null);
        	break;
        case 2:
        	Teil2.main(null);
        	break;
        case 3:
        	break;
        case 4:
        	break;
    	default:
    		break;
        }
        
    }

}