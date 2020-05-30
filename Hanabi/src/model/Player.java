package model;

public class Player {
	
	private String name;
	private Card[] hand;
	private List<Card> hand ;
	
	public Player(String name, SimpleGameData game, int handSize) {
		this.name = name;
		this.hand = Arrays.asList(new Card[handSize]);
		for (int i = 0; i < handSize; i++) {
			hand[i] = game.draw();
		}
	}
	
	/** Donner une information */
	public void giveIntel() {
		
	}
	
	/** Piocher une carte */
	public void playCard() {
		
	}
	
	/** Défausser une carte */
	public void discardCard(int card_nb) {
		if (card_nb >= 4)
			throw new IllegalStateException("Cette carte n'est pas dans le deck");	
		
		Card[] new_hand = new Card[this.handSize-1];
		for (int i=0; i < this.handSize; i++) {
			if(i == card_nb) {
				i++;
				new_hand[i-1] = hand[i];
			}else {
					new_hand[i] = hand[i];
			}
		}
		return ;
	}
	
	@Override
	public String toString() {
		String val = "Main de "+name+": [";
		for (Card c : hand)
			val += c.toString()+" ";
		val += "]";
		return val;
	}

}
