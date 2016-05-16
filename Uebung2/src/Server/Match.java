/**
 * Represents a match. In a match there are two clients that can join and get away.
 * These two clients play a potentially infinite number of mini matches.
 * A mini match is composed of a single choice for both partecipants (among papier, schore and stein). 
 */

package Server;

public class Match implements Runnable {

	private Client clientA, clientB;
	
	/**
	 * Creates a match. A match is initially with no player because connection is synchronous: each
	 * time a client connects to the server, it is soon added to a match, without waiting for the enemy.
	 */
	public Match() {
		
	}

	/**
	 * Removes c from this match
	 * @param c client to remove
	 * @return true if client was removed, else false
	 */
	public boolean remove(Client c) {
		if(this.clientA == c) {
			this.clientA = null;
			return true;
		}
		if(this.clientB == c) {
			this.clientB = null;
			return true;
		}
		return false;
	}
	 
	/**
	 * Used when a client wants to join a match.
	 * In a match there must be 2 players.
	 * @param c client to add
	 * @return true if c was added, else false. False is returned if for example there are already 2 players
	 */
	public boolean join(Client c) {
		if(c == null)
			return false;
		
		if(this.isComplete())
			return false;
		
		if(this.clientA == null)
			this.clientA = c;
		else
			this.clientB = c;
		return false;
	}
	
	/**
	 * True if this match has both client A and B
	 */
	public boolean isComplete() {
		if(this.clientA == null && this.clientB == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns client A and B. If you pass c=null than you will simply get both of them.
	 * If c=clientA or B, than you will get an array that does not contain the client equals to c.
	 */
	public Client[] getPartecipants(Client c) {
		if(this.clientA == null && this.clientB == null) {
			return new Client[] { null, null };
		}
		
		if(c == null) {
			return new Client[] { this.clientA, this.clientB };
		}
		
		if(this.clientA.equals(c)) {
			return new Client[] { this.clientB, null };
		}
		else {
			return new Client[] { this.clientA, null };
		}
	}
	
	/**
	 * Used to understands if client A or B win the current mini match.
	 * @return the winning client or null if both made the same choice (e.g., two "papier")
	 */
	private Client getWinner() {
		String a = this.clientA.getCurrentChoice();
		String b = this.clientB.getCurrentChoice();
		Client winner = null;
		
		switch(a) {
		case "papier":
			switch(b) {
			case "schore":
				winner = this.clientB;
				break;
			case "stein":
				winner = this.clientA;
				break;
			}
			break;
		case "schore":
			switch(b) {
			case "papier":
				winner = this.clientA;
				break;
			case "stein":
				winner = this.clientB;
				break;
			}
			break;
		case "stein":
			switch(b) {
			case "schore":
				winner = this.clientA;
				break;
			case "papier":
				winner = this.clientB;
				break;
			}
			break;
			default:
				break;
		}
		return winner;
	}

	@Override
	/**
	 * Runs the thread for a match. In fact many match can be requested: for N client that request a
	 * connection, N/2 matches must be created.
	 * This function controls the main logic of the match.
	 * A match is composed of a potentially infinite number of mini matches (till when a client decides to get out). 
	 */
	public void run() {
		this.clientA.writeMessage("Welcome to this match! Your enemy is on port " + this.clientB.getPort());
		this.clientB.writeMessage("Welcome to this match! Your enemy is on port " + this.clientA.getPort());
		
		//Wait for players choices (synchronously)
		while(true) {
			boolean isokA = this.clientA.setCurrentChoice(this.clientA.readMessage());
			boolean isokB = this.clientB.setCurrentChoice(this.clientB.readMessage());
			
			if(isokA && isokB)
			{
				Client winner = this.getWinner();
				if(winner == clientA) {
					boolean a = this.clientA.writeMessage("You won. Your choice: " + this.clientA.getCurrentChoice() + "; your enemy chose: " + this.clientB.getCurrentChoice());
					boolean b = this.clientB.writeMessage("You lost. Your choice: " + this.clientB.getCurrentChoice() + "; your enemy chose: " + this.clientA.getCurrentChoice());
				}
				else {
					boolean b = this.clientB.writeMessage("You won. Your choice: " + this.clientB.getCurrentChoice() + "; your enemy chose: " + this.clientA.getCurrentChoice());
					boolean a = this.clientA.writeMessage("You lost. Your choice: " + this.clientA.getCurrentChoice() + "; your enemy chose: " + this.clientB.getCurrentChoice());
				}
			}
		}
	}
	
}