import java.util.Scanner
public class Client {
	public static void main(String args[]) {
		InetAddress inet = InetAddress.getByName(localhost);
		System.out.println(inet.getHostAddress());
		String eingabe;
		boolean Playerfound = false;
		Socket clientSocket = new Socket("localhost", 6789);
		/**
		 * PvP
		*/
		if (Playerfound = true) {
			System.out.println("Bitte wähle weise!\n 1: Schere \n 2: Stein\n 3: Papier");
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			eingabe = inFromUser.readLine();
			outToServer.writeBytes(eingabe + '\n');
			Ergebnis = inFromServer.readLine();
			System.out.println("Ergebnis: " + modifiedSentence);
		}
		/**
		 * PvC
		*/
		else {
			System.out.println("Bitte wähle weise!\n 1: Schere \n 2: Stein\n 3: Papier");		
		}
		
		clientSocket.close(); 
	}
}
