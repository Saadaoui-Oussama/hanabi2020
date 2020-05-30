package model;

public class Card {
	
	private FireworkColor color;
	private int value;
	
	public Card(FireworkColor c, int val) {
		color = c;
		value = val;
	}
	
	public FireworkColor getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}
	
	public boolean sameValue(Card c) {
		if (this.getValue() == c.getValue())
			return true;
		return false;
	}
	
	public boolean sameColor(Card c) {
		if (this.getColor().equals(c.getColor()))
			return true;
		return false;
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

	@Override
	public String toString() {
		return "Carte "+color+" n°"+value;
	}

}
