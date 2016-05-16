package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	private Socket s;
	private DataOutputStream w;
	private DataInputStream r;
	private Match m;
	private String currentChoice;
	
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
	
	public void setMatch(Match m) {
		this.m = m;
	}
	
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
	
	public int getPort() {
		return this.s.getPort();
	}
	
	public boolean writeMessage(String msg) {
		try {
			w.write(msg.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
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

}
