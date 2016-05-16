package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	private Socket s; //socket corresponding to this client
	private DataOutputStream w;
	private DataInputStream r;
	private Match m; //match to which this client belongs
	private String currentChoice; //current choice for this client, among papier, stein and schore. It get renewed for each mini match
	
	public Client(Socket s) {
		this.s = s;
		try {
			this.w = new DataOutputStream(s.getOutputStream());
			this.r = new DataInputStream(s.getInputStream());
		}
		catch(IOException e) {
			System.err.println("Client with port " + s.getPort() + " got an error in Sever.Client costructor");
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the match to which this client belongs
	 */
	public void setMatch(Match m) {
		this.m = m;
	}
	
	/**
	 * Set current choice for this mini match.
	 * @param choice choice for this mini match, among stein, schere and papier
	 * @return false if choice is neither stein, nor schere, nor papier
	 */
	public boolean setCurrentChoice(String choice) {
		choice = choice.toLowerCase();
		if(choice.equals("schere") || choice.equals("stein") || choice.equals("papier")) {
			this.currentChoice = choice;
			return true;
		}
		return false;
	}
	
	public String getCurrentChoice() {
		return this.currentChoice;
	}
	
	public Match getMatch() {
		return this.m;
	}
	
	public Socket getSocket() {
		return this.s;
	}
	
	/**
	 * Gets the port from which this client requested the connection
	 */
	public int getPort() {
		return this.s.getPort();
	}
	
	/**
	 * Used to send a message to this client (synchrunous)
	 * @param msg the message to send
	 * @return true if message was sent
	 */
	public boolean writeMessage(String msg) {
		try {
			w.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Used to retrive a message from this client
	 * @return the message sent (synchronous)
	 */
	public String readMessage() {
		try {
			return r.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean isEqual(Client c) {
		if(this.s.getPort() == c.s.getPort())
			return true;
		return false;
	}
	
	public void finalize() {
		try {
			r.close();
			w.close();
			s.close();
		}
		catch(IOException e) {
			// Auto generated
		}
	}

}
