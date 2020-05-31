package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String name;
	private List<Card> hand;
	
	public Player(String name, SimpleGameData game, int handSize) {
		this.name = name;
		this.hand = new ArrayList<Card>();
		for (int i = 0; i < handSize; i++) {
			hand.add(game.draw());
		}
	}
	
	/** Donner une information de couleur */
	public void addIndColor(FireworkColor color) {
		for(Card cd  : getHand()) {
			if(cd.getColor().equals(color))
				cd.giveIndColor();
		}
	}
	
	/** Donner une information de valeur */
	public void addIndValue(int value) {
		for(Card cd  : getHand()) {
			if(cd.getValue() == value)
				cd.giveIndValue();
		}
	}
	
	/** Piocher une carte */
	public void playCard(int occurrence) {
		
	}
	
	/** D�fausse une carte et renvoie la carte d�fauss�e*/
	public Card discardCard(int index_card) {
		Card discarded = this.hand.remove(index_card);
		return discarded;
	}
	
	public void addCard(Card c) {
		this.hand.add(c);
	}
	
	public Card getCardInHand(int index) {
		return this.getHand().get(index);
	}
	
	public String getName() {
		return name;
	}

	public List<Card> getHand() {
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
