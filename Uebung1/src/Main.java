//Uebung 1 main method

import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
    	int n = 0;
    	System.out.println("Bitte schreiben Sie, welcher Teil Sie pruefen moechten [1,2,3,4]: ");
        Scanner c = new Scanner(System.in);
        if(c.hasNext())
        	n = c.nextInt();
        
        switch(n) {
        case 1:
        	Teil1.run();
        	break;
        case 2:
        	Teil2.run();
        	break;
        case 3:
        	Teil3.run();
        	break;
        case 4:
        	Teil4.run();
        	break;
    	default:
    		Main.main(null);
    		break;
        }
        
        c.close();
        
    }

}
}
