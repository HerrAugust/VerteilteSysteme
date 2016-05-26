import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String args[]) {
		System.out.println("My (client) PID: " + ManagementFactory.getRuntimeMXBean().getName());
		if (args.length < 2) {
			System.err.println("You have to insert more than these arguments (required 2: (IP, port) of server)");
			return;
		}
		int sport = Integer.parseInt(args[1]);
		final Socket s;
		try {
			s = new Socket(args[0], sport);
		} catch (IOException e) {
			System.err.println("Error of connection to server. Server not started or false port?");
			e.printStackTrace();
			return;
		}
		try {
			final Scanner rl = new Scanner(System.in);
			final BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			final OutputStreamWriter w = new OutputStreamWriter(s.getOutputStream());

			/**
			 * Handler for CTRL-C
			 */
			Runtime.getRuntime().addShutdownHook(new Thread() {
			    public void run() { 
			    	try {
			    		System.out.println("Finalizing this client");
			    		if(s.isClosed())
			    			return;
						w.write("STOP\n");
						w.flush();
						if(s.isClosed() == false)
							s.close();
						System.out.println("Finalized");
						return;
					} catch(IOException e) {
						System.out.println("Problems while finalizing with CTRL-C");
						e.printStackTrace();
					} catch (Throwable e) {
						e.printStackTrace();
					}
			    }
			 });

			String read = null;
			while (true) {
				read = r.readLine();
				if (read != null) {
					if (read.equals("Match starting")) {
						System.out.println(r.readLine());
						break;
					}
					System.out.println(read);
				}
			}

			// read schore, stein, papier
			while (true) {
				String msg = null;
				System.out.println("Please enter schere, stein or papier (STOP to close connection): ");
				if (rl.hasNextLine())
					msg = rl.nextLine();
				if (msg == null) {
					System.err.println("Closing connection, read msg = null");
					w.close();
					r.close();
					rl.close();
					return;
				}
				if (!msg.equals("stein") && !msg.equals("papier")
						&& !msg.equals("schere") && !msg.equals("STOP"))
					continue;
				w.write(msg + "\n");
				w.flush();
				read = r.readLine(); //who won
				if (read.equals("A client requested to terminate match. Bye bye!")) {
					System.out.println(read);
					w.close();
					r.close();
					rl.close();
					if(s.isClosed() == false)
						s.close();
					return;
				}
				System.out.println(read);
				read = r.readLine(); //points
				System.out.println(read);
			}
		} catch (IOException e) {
			System.err.println("Error while communicating with server");
			return;
		}
	}
}
