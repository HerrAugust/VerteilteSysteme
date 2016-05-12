package Server;
import java.util.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

	List<Match> matches = new LinkedList<Match>();
	
	public static void main(String[] args) throws IOException {
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
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server waiting on this coordinate: (" + ss.getInetAddress() + ", " + ss.getLocalPort() + ")");
		
		new 
		
	}
	
	private Match allMatchesAreFull() {
		for(Match m : this.matches) {
			if(m.isComplete() == false) {
				return m;
			}
		}
		return null;
	}

}
