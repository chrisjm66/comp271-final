// Chris Mangan COMP271

import cards.Card;
import cards.UnoCard;
import hand.Hand;
import player.Player;

public class Assignment2_1 {
	final static int DECK_LENGTH = 52;
	final static int UNO_DECK_LENGTH = 108;
	public static void main(String[] args) {
		// Create std deck
		Card[] deck = new Card[DECK_LENGTH];
		for (int i = 0; i < deck.length; i++) {
			deck[i] = new Card(i+1); // i+1 as i is one less than the actual card number
		}
		
		// Create Uno Deck
		UnoCard[] unoDeck = new UnoCard[UNO_DECK_LENGTH];
		for (int i = 0; i < unoDeck.length; i++) {
			unoDeck[i] = new UnoCard(i+1); // i+1 as i is one less than the actual card number
		}
		
		// Print Decks
		System.out.println("\n\nHere is the unshuffled standard deck:");
		printDeck(deck);

		System.out.println("\n\nHere is the unshuffled UNO deck:");
		printDeck(unoDeck);
		
		// Shuffle both decks
		shuffleDeck(deck);
		shuffleDeck(unoDeck);
		
		// Reprint both decks
		System.out.println("\n\nHere is the shuffled standard deck:");
		printDeck(deck);
	
		System.out.println("\n\nHere is the shuffled UNO deck:");
		printDeck(unoDeck);
		
		// Create player objects
		Player chris = new Player("123", "Chris", 1000);
		Player professorSquires = new Player("321", "Squires", 1000000000);
		Hand chrisHand = chris.getHand();
		Hand squiresHand = professorSquires.getHand();
		
		// Deal cards
		for (int i = 0; i < 7; i++) {
			chrisHand.addCard(unoDeck[i]);
		}
		
		for (int i = 0; i < 5; i++) {
			squiresHand.addCard(deck[i]);
		}
		
		printHands(chris, professorSquires);
	}

	public static void printHands(Player player1, Player player2) {
		Hand player1Hand = player1.getHand();
		Hand player2Hand = player2.getHand();
		
		System.out.println("\n");
		System.out.println(player1.getName() + "'s Hand: " + player1Hand);
		System.out.println(player2.getName() + "'s Hand: " + player2Hand);
	}
	
	public static void printDeck( Card[] deck ) {
		for (int i = 1; i <= deck.length; i++) {
			System.out.print(deck[i-1] + "\t");
			if(i%14 == 0) {
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
}
