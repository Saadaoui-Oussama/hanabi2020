package model;

public class Card {
	
	private FireworkColor color;
	private int value;
	
	public Card(FireworkColor c, int val) {
		color = c;
		value = val;
	}
	
	@Override
	public String toString() {
		return "Carte "+color+" n°"+value;
	}

}
