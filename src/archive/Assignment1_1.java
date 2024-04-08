// Chris Mangan COMP271


import cards.Card;
import player.Player;
public class Assignment1_1 {
	private static final int DECK_LENGTH = 52;
	private static final int DEAL_AMOUNT = 5;
	public static void main(String[] Args) {
		// Initialize Deck
		Card[] deck = new Card[DECK_LENGTH];
		for (int i = 0; i < deck.length; i++) {
			deck[i] = new Card(i+1); // i+1 as i is one less than the actual card number
		}
		// Print Deck
		System.out.println("\nHere is the unshuffled deck:");
		printDeck(deck);
		
		// Copy, Shuffle, and Print Shuffled Deck
		Card[] shuffledDeck = new Card[DECK_LENGTH];
		for (int i = 0; i < deck.length; i++) {
			shuffledDeck[i] = deck[i];
		}
		shuffleDeck(shuffledDeck);
		
		System.out.println("\nHere is your shuffled deck:");
		printDeck(shuffledDeck);
		
		// Create player objects
		Player player1 = new Player("675", "Chris" , 100000);
		Player player2 = new Player("38103", "John", 3000);
		
		// Generate hands
		Card[] player1Hand = new Card[DEAL_AMOUNT];		
		Card[] player2Hand = new Card[DEAL_AMOUNT];
		deal(shuffledDeck, player1Hand, player2Hand);

		// Output player's hands
		System.out.println("\n" + player1.getName() + "'s hand:");
		printDeck(player1Hand);
		System.out.print("\n");
		System.out.println("\n" + player2.getName() + "'s hand:");
		printDeck(player2Hand);
	}
	
	public static void printDeck( Card[] deck ) {
		for (int i = 1; i <= deck.length; i++) {
			System.out.print(deck[i-1] + "\t");
			if(i%13 == 0) {
				System.out.print("\n");
			}
		}
	}
	public static void shuffleDeck(Card[] deck) {
		// Very simple shuffle, loops through the array and swaps it with another random item.
		for (int i = 0; i < deck.length; i++) {
			int random = (int)(Math.random() * (deck.length - 1) + 1);
			swap(deck, i, random);
		}
	}
	
	public static void swap(Card[] deck, int index1, int index2) {
		Card temp = deck[index1];
		deck[index1] = deck[index2];
		deck[index2] = temp;
	}
	
	public static void deal(Card[] deck, Card[] hand1, Card[] hand2) { // In the future this could be converted to a Card[] ... hands to support multiple players. Just a thought
		for (int i = 0; i < 2 * DEAL_AMOUNT; i++) { // This is currently a hardcoded 2 as we are dealing two decks, but in conjunction with the last statement this can be something like hands.length for 2+ players.
			Card currentCard = deck[i];
			if (i%2 == 0)
				hand1[i/2] = currentCard;
			else
				hand2[i/2] = currentCard;
		}
		
	}
}
