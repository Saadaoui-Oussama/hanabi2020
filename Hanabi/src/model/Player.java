package model;

public class Player {
	
	private String name;
	private Card[] hand;
	
	public Player(String name, SimpleGameData game, int handSize) {
		this.name = name;
		this.hand = new Card[handSize];
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
	public void discardCard(SimpleGameData game, int card_nb) {
		if (card_nb >= 4)
			throw new IllegalStateException("Cette carte n'est pas dans le deck");	
		
		hand[card_nb] = game.draw();
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
