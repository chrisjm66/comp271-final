// Chris Mangan COMP271

import cards.Card;
import player.Player;
import hand.Hand;
public class Assignment1_2 {
	private static final int DECK_LENGTH = 52;
	private static final int DEAL_AMOUNT = 5;
	public static void main(String[] args) {
		// Initialize and shuffle deck
		Card[] deck = new Card[DECK_LENGTH];
		for (int i = 0; i < deck.length; i++) {
			deck[i] = new Card(i+1); // i+1 as i is one less than the actual card number
		}

		// Shuffle deck
		shuffleDeck(deck);
		
		// Print deck
		System.out.println("This is the shuffled deck:\n");
		printDeck(deck);
		
		// Instantiate players and hands
		Player fred = new Player("9765467", "FastFreddy", 2650);
		Player jack = new Player("2435573", "OneEyedJack", 1400);
		Hand fredHand = fred.getHand();
		Hand jackHand = jack.getHand();
		
		// Deal Cards
		for (int i = 0; i < (DEAL_AMOUNT * 2); i++) {
			if (i%2 == 0) {
				fredHand.addCard(deck[i]);
			} else {
				jackHand.addCard(deck[i]);
			}
		}
		
		// 5 Card Poker
		System.out.println("\n5 Card:");
		fredHand.evaluateHand();
		jackHand.evaluateHand();
		printResult(fred, jack, false);
		
		
		// Deuces Wild
		System.out.println("\nDeuces Wild:");
		fredHand.evaluateHand("DeucesWild");
		jackHand.evaluateHand("DeucesWild");
		printResult(fred, jack, true);
		
	}
	
	public static void printResult(Player player1, Player player2, boolean deucesWild) {
		Hand player1Hand = player1.getHand();
		Hand player2Hand = player2.getHand();
		
		System.out.println(player1.getName() + "'s Hand: " + player1Hand + "; " + player1Hand.getHandDescr());
		System.out.println(player2.getName() + "'s Hand: " + player2Hand + "; " + player2Hand.getHandDescr());
		
		int result;
		if (deucesWild) {
			result = player1Hand.compareHand(player2Hand, "DeucesWild");
		} else {
			result = player1Hand.compareHand(player2Hand);
		}
		
		switch(result) {
			case 1:
				System.out.println(player1.getName() + " wins!");
				break;
			case 0:
				System.out.println("It's a tie!");
				break;
			case -1:
				System.out.println(player2.getName() + " wins!");
		}
	}
	
	// Copied from last assignment
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

	public static void printDeck( Card[] deck ) {
		for (int i = 0; i < deck.length; i++) {
			System.out.print(deck[i] + "\t");
			if((i+1)%13 == 0) {
				System.out.print("\n");
			}
		}
	}
}
