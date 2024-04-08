// Chris Mangan COMP271
package cards;

public class UnoCard extends Card {
	// Attributes
	private boolean reverse;
	private boolean wild;
	private boolean skip;
	private int drawAmount;
	
	public UnoCard(int cardNumber) {
		super(cardNumber);
		
	}
	
	// Methods
	@Override
	protected void createCard() {
		int processingNum = cardNumber-1; // Since computers like the number 0 I added this variable to reduce human confusion and the "deck" is now 0 > 51 instead of 1 > 52 to the computer
		int rank = (processingNum % 12); // 12 cards follow a set pattern so we will be using those as the rank.
		int colorIndex =  (processingNum / 24) + 1;
		cardImage = cardNumber + ".png";
		
		// This runs first since it's the most likely outcome
		if (processingNum < 96) {
			setupFace(rank);
			setupColor(colorIndex);
		} else if (processingNum >= 96 && processingNum <= 99) {// Brute force final 12 cards
			wild = true;
			face = "WD";
			color = "w";
		} else {
			switch(processingNum) {
				case 100:
					color = "r";
					face = "D4";
					drawAmount = 4;
					break;
				case 101:
					color = "y";
					face = "D4";
					drawAmount = 4;
					break;
				case 102:
					color = "g";
					face = "D4";
					drawAmount = 4;
					break;
				case 103:
					color = "b";
					face = "D4";
					drawAmount = 4;
					break;
				case 104:
					color = "r";
					face = "0";
					break;
				case 105:
					color = "y";
					face = "0";
					break;
				case 106:
					color = "g";
					face = "0";
					break;
				case 107:
					color = "b";
					face = "0";
					break;
			}
		}
		
		//System.out.println(cardNumber + " " + processingNum + " " + cardRank + " " + color + " " + face + " " + suitIndex + " " + toString());
	}
	
	private void setupColor(int colorIndex) {
		switch(colorIndex) {
			case 1:
				color = "r";
				break;
			case 2:
				color = "y";
				break;
			case 3:
				color = "g";
				break;
			case 4:
				color = "b";
				break;
		}
	}
	
	private void setupFace(int rank) {
		switch(rank) {
			case 9:
				face = "SK";
				skip = true;
				break;
			case 10:
				face = "RV";
				reverse = true;
				break;
			case 11:
				face = "DR2";
				drawAmount = 2;
				break;
			default:
				face = (Integer.toString(rank + 1)); // add one since otherwise the deck would go from 0-8 and not 1-9	
				break;
		}
	}

	@Override
	public String toString() {
		if (wild) {
			return face;
		} else {
			return face + color;
		}
	}
	
	// Getters
	public boolean isReverse() {
		return reverse;
	}

	public boolean isWild() {
		return wild;
	}

	public boolean isSkip() {
		return skip;
	}

	public int getDrawAmount() {
		return drawAmount;
	}
	
}
