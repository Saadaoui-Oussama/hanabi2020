package model;

public class Card {
	
	private FireworkColor color;
	private int value;
	private boolean ind_color; // Permet de savoir si l'indice sur la valeur/couleur a été donné
	private boolean ind_value;
	
	/** Create a Card which can be used by the players to play Hanabi.
	 * @param c - Color of the card
	 * @param val - Value of the card*/
	public Card(FireworkColor c, int val) {
		color = c;
		value = val;
		ind_color = false;
		ind_value = false;
	}
	
	/** Get a card's color */
	public FireworkColor getColor() {
		return color;
	}

	/** Get a card's value */
	public int getValue() {
		return value;
	}
	
	/** Get to know if the color of this card was revealed */
	public boolean getIndColor() {
		return ind_color;
	}
	
	/** Get to know if the value of this card was revealed */
	public boolean getIndValue() {
		return ind_value;
	}
	
	/** Make the color of the card visible to all players */
	public void giveIndColor() {
		this.ind_color = true;
	}
	
	/** Make the value of the card visible to all players */
	public void giveIndValue() {
		this.ind_value = true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card)
			return false;
		
		Card c = (Card)obj;
		if (this.getValue() != c.getValue() || !this.getColor().equals(c.getColor()))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		
		hash = 31 * hash + value;
		hash = 31 * hash + color.hashCode();
		
		return hash;
	}

	/** Show the revealed card (its value and color are not hidden) */
	public String openCard() {
		String stringColor = color.toString();
		String stringValue = value+"";
		
		if (ind_color)
			stringColor = "("+stringColor+")";
		
		if (ind_value)
			stringValue = "("+stringValue+")";
		
		return "Carte "+ stringColor +" n°"+ stringValue;
	}
	
	@Override
	/** Show the value and color of this card if it was revealed. If not, the value or color will be hidden */
	public String toString() {
		String stringColor = "?";
		String stringValue = "?";
		
		if (ind_color)
			stringColor = color.toString();
		
		if (ind_value)
			stringValue = value+"";
		
		return "Carte "+ stringColor +" n°"+ stringValue;
	}

}
