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
	public void addIndColor(Player p, FireworkColor color) {
		Card c = new Card(color, 0);
		for(Card cd  : p.getHand()) {
			if(cd.sameColor(c))
				cd.setIndColor();
		}
	}
	
	/** Piocher une carte */
	public void playCard(int occurrence) {
		Card c = new Card(FireworkColor.white, occurrence);
		for(Card cd  : this.hand) {
			if(cd.sameValue(c))
				cd.setIndValue();
		}
	}
	
	/** Défausser une carte */
	public void discardCard(SimpleGameData game, int card_nb) {
		hand[card_nb] = game.draw();
	}
	
	public String getName() {
		return name;
	}

	public Card[] getHand() {
		return hand;
	}
	
	@Override
	public String toString() {
		String val = "Main de "+name+": | ";
		for (Card c : hand)
			val += c.toString()+" | ";
		return val;
	}

	public String openHand() {
		String val = "Main visible de "+name+": | ";
		for (Card c : hand)
			val += c.openCard()+" | ";
		return val;
	}

}
