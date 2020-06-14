package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private final String name;
	private List<Card> hand;
	
	/** Create a player with a name different from the others players 
	 * @param name - The name of the player
	 * @param game - Let the player draw from the game deck
	 * @param handSize - the numbers of cards the players draws (4 or 5 depending on the numbers of players)*/
	public Player(String name, SimpleGameData game, int handSize) {
		this.name = name;
		this.hand = new ArrayList<Card>();
		for (int i = 0; i < handSize; i++) {
			hand.add(game.draw());
		}
	}
	
	/** Reveals to the player the color of each card which is the same as the argument's color
	 * @param color - The color revealed */
	public void addIndColor(FireworkColor color) {
		for(Card cd  : getHand()) {
			if(cd.getColor().equals(color))
				cd.giveIndColor();
		}
	}
	
	/** Reveals to the player the value of each card which is the same as the argument's value
	 * @param value - The value revealed */
	public void addIndValue(int value) {
		for(Card cd  : getHand()) {
			if(cd.getValue() == value)
				cd.giveIndValue();
		}
	}
	
	/** Remove and return the selected card to be discarded
	 * @param index - index of the selected card in the player's hand
	 * @return Card discarded card*/
	public Card discardCard(int index) {
		Card discarded = this.hand.remove(index);
		return discarded;
	}
	
	/** Add a card to the player's hand
	 * @param card - Card added to the hand*/
	public void addCard(Card card) {
		if (card != null)
			this.hand.add(card);
	}
	
	/** Return a selected card in the hand
	 * @param index - index of the selected card in the player's hand
	 * @return Card the selected card*/
	public Card getCardInHand(int index) {
		return this.getHand().get(index);
	}
	
	/** Returns the player's name
	 * @return String player name*/
	public String getName() {
		return name;
	}

	/** Returns a list of cards which corresponds to the player's hand
	 * @return List player's hand*/
	public List<Card> getHand() {
		return hand;
	}
	
	@Override
	/** Shows the player's hand
	 * @return String toString of the player's hand*/
	public String toString() {
		String val = "Main de "+name+": | ";
		for (Card c : hand)
			val += c.toString()+" | ";
		return val;
	}

	/** Shows the player's revealed hand
	 * @return String toString of the player's revealed hand*/
	public String openHand() {
		String val = "Main visible de "+name+": | ";
		for (Card c : hand)
			val += c.openCard()+" | ";
		return val;
	}

}
