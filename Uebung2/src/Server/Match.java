package Server;

public class Match implements Runnable {

	private Client clientA, clientB;
	
	public Match() {
		
	}

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
	
	public boolean isComplete() {
		if(this.clientA == null && this.clientB == null) {
			return true;
		}
		return false;
	}
	
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
					this.clientA.writeMessage("You won. Your choice: " + this.clientA.getCurrentChoice() + "; your enemy chose: " + this.clientB.getCurrentChoice());
					this.clientB.writeMessage("You lost. Your choice: " + this.clientB.getCurrentChoice() + "; your enemy chose: " + this.clientA.getCurrentChoice());
				}
				else {
					this.clientB.writeMessage("You won. Your choice: " + this.clientB.getCurrentChoice() + "; your enemy chose: " + this.clientA.getCurrentChoice());
					this.clientA.writeMessage("You lost. Your choice: " + this.clientA.getCurrentChoice() + "; your enemy chose: " + this.clientB.getCurrentChoice());
				}
			}
		}
	}
	
}
