// Chris Mangan COMP271
package cards;

abstract public class Card {
	// Attributes
	protected int cardNumber;
	protected String color;
	protected String face;
	protected String cardImage;
	protected static int cardsCreated;
	
	// Constructor
	public Card(int cardNumber) {
		this.cardNumber = cardNumber;
		cardsCreated++;
		createCard();
	}
	
	// Private Methods
	abstract protected void createCard();
	
	// Public Methods
	public static int getCardsCreated() {
		return cardsCreated;
	}
	public int getCardNumber() {
		return cardNumber;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getFace() {
		return face;
	}
	public String getCardImage() {
		return cardImage;
	}

}