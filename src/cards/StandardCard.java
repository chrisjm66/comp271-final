// Chris Mangan COMP271
package cards;

public class StandardCard extends Card {
	// Attributes
	private String suit;
	private int suitIndex;
	private int cardRank;
	
	// Constructor
	public StandardCard(int cardNumber) {
		super(cardNumber);
	}
	
	// Private Methods
	@Override
	protected void createCard() {
		int processingNum = cardNumber-1; // Since computers like the number 0 I added this variable to reduce human confusion and the "deck" is now 0 > 51 instead of 1 > 52 to the computer
		cardRank = (processingNum % 13) + 1;
		suitIndex = processingNum / 13;
		cardImage = cardNumber + ".png";
		face = getCardFace(cardRank);
		suit = getCardSuit(suitIndex);
		color = (suitIndex == 1 || suitIndex == 2) ? "r" : "b";
	}
	
	private static String getCardSuit(int suitIndex) {
		String currentSuit;
		switch(suitIndex) {
			case 0:
				currentSuit = "s";
				break;
			case 1:
				currentSuit = "h";
				break;
			case 2:
				currentSuit = "d";
				break;
			case 3:
				currentSuit = "c";
				break;
			default:
				currentSuit = "invalid";
				System.out.println("Invalid Suit");
				break;
		}
		return currentSuit;
	}
	
	private static String getCardFace(int cardRank) {
		String cardFace;
		switch(cardRank) {
			case 1:
				cardFace = "A";
				break;
			case 10:
				cardFace = "T";
				break;
			case 11:
				cardFace = "J";
				break;
			case 12:
				cardFace = "Q";
				break;
			case 13:
				cardFace = "K";
				break;
			default:
				cardFace = String.valueOf(cardRank);
				break;
		}
		return cardFace;
	}
	
	// Public Methods
	public int getCardRank() {
		return cardRank;
	}
	public String getSuit() {
		return suit;
	}
	public int getSuitIndex() {
		return suitIndex;
	}
	public String toString() {
		return face + suit;
	}
}