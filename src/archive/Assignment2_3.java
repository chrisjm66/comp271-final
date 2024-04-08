// Chris Mangan COMP271
import deck.Deck;
import hand.Hand;
import player.Player;
import player.Dealer;
import cards.Card;
import cards.StandardCard;
public class Assignment2_3 {
	public static final int DECK_SIZE = 52;
	public static void main(String[] args) {
		// Instantiate Dealer object
		Deck dealerDeck = new Deck(DECK_SIZE);
		Dealer dealer = new Dealer(dealerDeck);
		
		// Print Deck
		System.out.println("Here is the deck:\n" + dealer.getDeck());
		
		// Instantiate player and deal cards to player and dealer
		Player player1 = new Player("00000", "Player");
		
		for (int i = 0; i < 5; i++) {
			dealer.dealCard(dealer);
			dealer.dealCard(player1);
		}
		
		// Print cards
		System.out.println("Here is the deck after dealing cards:\n" + dealer.getDeck());
		printResult(dealer, player1, false);
		
		// Gets used cards
		dealer.gatherUsedCards(dealer); // FYI: This does not match the method name on the UML (gatherCards())
		dealer.gatherUsedCards(player1);
		
		// Print Decks
		Deck deckAfterRound = dealer.getDeck();
		System.out.println("\nHere is the deck after the round:\n" + deckAfterRound);
		
		// Restack and reprint deck
		deckAfterRound.restack();
		System.out.println("\n\nHere is the deck after restacking:\n" + deckAfterRound);
		
		
		//-----------------------------------------------------------------------------------
		// EXTRA CREDIT
		System.out.println("\n----------------------EXTRA CREDIT------------------------------\n");
		Deck unoDeck = new Deck(108, true);
		Dealer unoDealer = new Dealer(unoDeck);
		System.out.println("Here is the uno deck:\n" + unoDealer.getDeck());
		
		
	}

	// COPIED FROM A1.2
	public static void printResult(Player player1, Player player2, boolean deucesWild) {
		Hand player1Hand = player1.getHand();
		Hand player2Hand = player2.getHand();
		
		int result;
		if (deucesWild) {
			result = player1Hand.compareHand(player2Hand, "DeucesWild");
		} else {
			result = player1Hand.compareHand(player2Hand);
		}
		
		System.out.print("\n");
		printCardRank(player1Hand.getCards(), player1.getName());
		printCardRank(player2Hand.getCards(), player2.getName());
		System.out.print("\n");
		
		System.out.println(player1.getName() + "'s Hand: " + player1Hand + "; " + player1Hand.getHandDescr());
		System.out.println(player2.getName() + "'s Hand: " + player2Hand + "; " + player2Hand.getHandDescr());
		
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
	
	public static void printCardRank(Card[] cards, String name) {
		System.out.print(name + "'s Card Ranks: ");
		for (int i = 0; i < cards.length; i++) {
			System.out.print((((StandardCard) cards[i]).getCardRank()) + " ");
		}
		System.out.print("\n");
	}
}
