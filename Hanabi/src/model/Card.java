package model;

public class Card {
	
	private FireworkColor color;
	private int value;
	private boolean ind_color;
	private boolean ind_value;
	
	public Card(FireworkColor c, int val) {
		color = c;
		value = val;
		ind_color = false;
		ind_value = false;
	}
	
	/** Getter */
	public FireworkColor getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}
	
	public boolean getIndColor() {
		return ind_color;
	}
	
	public boolean getIndValue() {
		return ind_value;
	}
	
	/** Setter */
	public void setIndColor() {
		this.ind_color = true;
	}
	
	public void setIndValue() {
		this.ind_value = true;
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

	public String openCard() {
		return "Carte "+ color +" n°"+value;
	}
	
	@Override
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
