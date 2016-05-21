package Server;
import java.util.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

	private static List<Match> matches = new LinkedList<Match>();
	private static ServerSocket ss;
	
	public static void removeMatch(int ID) {
		for(Match m : Server.matches) {
			if(m.getID() == ID) {
				Server.matches.remove(m);
				break;
			}
		}
	}
	
	@Override
	public void finalize() throws IOException {
		for(Match m : matches) { //closes connection with each clients 
			m.finalize();
		}
		ss.close(); //closes server socket
	}
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("You must indicate a valid port");
			return;
		}
		
		/**
		 * Handler for CTRL-C
		 */
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { 
		    	try {
					this.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
		    }
		 });
		
		String port = args[0];
		
		/*
		 * Beim Start des Servers einstellbarer Port (z.B. über Kommandozeilenargument)
		 * mit Validierung, dass dieser im Bereich zwischen 8000 und 8100 liegt.
		 */
		int p = 0;
		try {
			p = Integer.parseInt(port);
		}catch(NumberFormatException e) {
			System.err.println("You must enter a number for port");
		}
		if(p < 10000 || p > 10100) {
			System.err.println("You must insert a port s.t. 10000 < port < 10100");
		}
			
		//This is the socket of this server:
		ss = null;
		try {
			ss = new ServerSocket(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server waiting on this coordinate: (" + ss.getInetAddress() + ", " + ss.getLocalPort() + ")");
		
		//As a new connection is required, server accepts it
		while(true) {
			Socket newconn = null;
			try {
			newconn = ss.accept();
			}
			catch(IOException e) {
				System.err.println("Could not accept client connection. Trying again (forever)");
				continue;
			}
			
			//find a match and assign to that match
			Match freeMatch = null;
			for(Match m : Server.matches) {
				if(m.isComplete() == false) {
					freeMatch = m;
					break;
				}
			}
			Client newClient = new Client(newconn);
			if(freeMatch == null) { //if no match with only one player is found, create a new one
				freeMatch = new Match();
				freeMatch.join(newClient);
				Server.matches.add(freeMatch);
			}
			else {
				freeMatch.join(newClient);
			}
			newClient.setMatch(freeMatch);
			newClient.writeMessage("You have just been assigned to a match.");
			//END find a match
			
			//start match
			if(freeMatch.isComplete()) {
				Client[] c = freeMatch.getPartecipants(null);
				c[0].writeMessage("Match starting");
				c[1].writeMessage("Match starting");
				Thread t = new Thread(freeMatch);
				t.start();
			}
			else {
				newClient.writeMessage("Waiting for an enemy...");
			}
			
		}
		
	}

}
