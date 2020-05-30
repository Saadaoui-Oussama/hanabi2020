package model;

public class Player {
	
	private String name;
	private Card[] hand;
	private int handSize;
	
	public Player(String name, SimpleGameData game) {
		this.name = name;
		this.handSize = game.getNbPlayers();
		this.hand = new Card[handSize];
		for (int i=0; i < handSize; i++)
			hand[i] = game.draw();
	}
	
	@Override
	public String toString() {
		String val = "Main de "+name+": [";
		for (Card c : hand)
			val += c.toString();
		val += "]";
		return val;
	}

}
