package deck;
import java.util.ArrayList;
import cards.Card;
import cards.StandardCard;
import cards.UnoCard;

public class Deck {
	// Attributes
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> usedCards = new ArrayList<Card>();
	
	// Constructor
	public Deck(int deckSize) {
		for(int i = 1; i <= deckSize; i++) {
			cards.add(new StandardCard(i));
		}
	}
	
	public Deck(int deckSize, boolean unoDeck) { // using a boolean for now, but if we added more cards we needed to do this with it would probably be easiest to pass an object and test which object it is and loop from there.
		if (unoDeck) { // for loops are copied so there is only one comparison instead of whatever deckSize is equal to
			for(int i = 1; i <= deckSize; i++) {
				cards.add(new UnoCard(i));
			}
		} else {
			for(int i = 1; i <= deckSize; i++) {
				cards.add(new StandardCard(i));
			}
		}
		
	}
	
	// Methods
	public void shuffleDeck() {
		int deckSize = cards.size();
		for (int i = 0; i < deckSize; i++) {
			Card temp = cards.get(i);
			int random = (int)(Math.random() * deckSize);
			
			cards.set(i, cards.get(random));
			cards.set(random, temp);
		}
	}
	
	public void restack() {
		while (usedCards.size() > 0) {
			cards.add(usedCards.remove(0));
		}
	}
	
	public Card dealCard(int index) {
		// ERROR HERE: Deck does not restack and eventually returns to 0.
		return cards.remove(index);
	}
	
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	public void addUsedCards(Card card) {
		usedCards.add(card);
	}
	
	public Card[] getCards() {
		Card[] cardArray = new Card[cards.size()];
		cards.toArray(cardArray);
		return cardArray;
	}
	
	public Card[] getUsedCards() {
		Card[] cardArray = new Card[usedCards.size()];
		usedCards.toArray(cardArray);
		return cardArray;
	}
	
	@Override
	public String toString() {
		String cardString = "";
		for (int i = 1; i <= cards.size(); i++) {
			cardString += cards.get(i-1) + "\t";
			if(i%13 == 0) {
				cardString += "\n";
			}
		}
		
		if (usedCards.size() > 0) {
			cardString += "\n\nUsed Cards:\n";
			for (int i = 1; i <= usedCards.size(); i++) {
				cardString += usedCards.get(i-1) + "\t";
				if(i%13 == 0) {
					cardString += "\n";
				}
			}
		}
		return cardString;
	}
}
