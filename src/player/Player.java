// Chris Mangan COMP271
package player;
import hand.Hand;

public class Player {
    // Attributes
    private String name;
    private String id;
    private int bank;
    Hand hand;

    // Constructors
    public Player(){
    	this("00000","Guest",1000);
    }

    public Player(String id, String name){
    	this(id,name,1000);
    }
    
    public Player(String id, String name, int bank){
    	this.id = id;
    	this.name = name;
    	this.bank = bank;
    	hand = new Hand();
    }
    
    // Methods

    public int getBank() {
		return bank;
	}
    
    public Hand getHand() {
		return hand;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public String toString() {
		return "Player " + name + ", ID: " + id + " has a bank of " + bank;
	}
}
