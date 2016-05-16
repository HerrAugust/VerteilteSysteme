package Server;

public class Match {

	private Client clientA, clientB;
	
	public Match(Client c1, Client c2) {
		this.clientA = c1;
		this.clientB = c2;
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
	
}
