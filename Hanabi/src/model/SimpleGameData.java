package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleGameData {
	
	private int turn;
	private int redTokens; // Tokens earned by playing a wrong card - at 3 tokens, it's game over
	private int blueTokens; // Token used to give an intel
	
	private List<Player> players;
	private ArrayList<Card> deck;
	private Map<FireworkColor, Integer> field; // Only the top value of a card interests us for a field, hence the map with a Integer value
	private Map<FireworkColor, ArrayList<Integer>> discardZone; // All the values of the discarded cards are important
	
	/** Creates all the game's data: the deck, a list of players, the field, the discard zone, and the blue and red tokens **/
	public SimpleGameData() {
		this.turn = 1;
		this.redTokens = 0;
		this.blueTokens = 8;
		
		this.players = new ArrayList<Player>();
		
		this.deck = generateDeck();
		this.field = generateField();
		this.discardZone = generateDiscardZone();
	}

	/** Generate the deck of Hanabi, and then shuffle it
	 * @return ArrayList List of cards named as deck*/
	private ArrayList<Card> generateDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		for (FireworkColor c : FireworkColor.values()) { // 5 couleurs
			// pour chacune des 5 couleurs des cartes, la distribution des valeurs est 1, 1, 1, 2, 2, 3, 3, 4, 4, 5
			for (int i = 1; i <= 5; i++) {
				switch (i) {
				case 1:
					addCardToDeck(deck, c, i, 3);
					break;
				default: //2, 3, 4
					addCardToDeck(deck, c, i, 2);
					break;
				case 5:
					addCardToDeck(deck, c, i, 1);
					break;
				}
			}
		}
		Collections.shuffle(deck);
		return deck;
	}
	
	/** Used to generate the deck. Add n times a card with the color and value as argument in the deck 
	 * @param deck deck in which the cards shall be aded
	 * @param color color of the card
	 * @param value value of the card
	 * @param n number of times the card should be added*/
	public void addCardToDeck(ArrayList<Card> deck, FireworkColor color, int value, int n) {
		for (int i = 0; i < n; i++)
			deck.add(new Card(color, value));
	}
	
	/** Generate a field, which consists of 5 stacks of card of each color, and containing the top value of the stack (starting at 0) 
	 * @return Map generated field*/
	private Map<FireworkColor, Integer> generateField() {
		Map<FireworkColor, Integer> map = new HashMap<FireworkColor, Integer>();
		
		for (FireworkColor c : FireworkColor.values()) {
			map.put(c, 0);
		}
		
		return map;
	}
	
	/** 
	 * Generate a discard zone, which consists of 5 stacks of card of each color, and containing the value of all discarded cards of each stack
	 * @return Map generated discard zone
	 * */
	private Map<FireworkColor, ArrayList<Integer>> generateDiscardZone() {
		Map<FireworkColor, ArrayList<Integer>> map = new HashMap<FireworkColor, ArrayList<Integer>>();
		
		for (FireworkColor c : FireworkColor.values()) {
			map.put(c, new ArrayList<Integer>(0));
		}
		
		return map;
	}
	
	/** Return a card drawed (removed from the top of the deck). If the deck is empty, we can't draw and return a null (no card) 
	 * @return Card card drawed*/
	public Card draw() {
		if (deck.size()<=0) {
			System.out.println("Deck vide, pioche impossible !");
			return null;
		}
		
		return deck.remove(0);
	}
	
	/** Check if the current game is on it's last turn by checking if the deck is empty. If it is, we are in the last turn.
	 * @return boolean true if last turn, false if not*/
	public boolean lastTurn() {
		if (deck.size() == 0)
			return true;
		return false;
	}
	
	/** Returns the number of red tokens availables 
	 * @return int number of red tokens*/
	public int getRedTokens() {
		return redTokens;
	}

	/** Returns the number of blue tokens availables
	 * @return int number of blue tokens*/
	public int getBlueTokens() {
		return blueTokens;
	}

	/** Returns the deck
	 * @return ArrayList the deck*/
	public ArrayList<Card> getDeck() {
		return deck;
	}

	/** Returns the field 
	 * @return Map the field*/
	public Map<FireworkColor, Integer> getField() {
		return field;
	}

	/** Return the discard zone
	 * @return Map the discard zone*/
	public Map<FireworkColor, ArrayList<Integer>> getDiscardZone() {
		return discardZone;
	}

	/** Return a list with all players
	 * @return List list of all players*/
	public List<Player> getPlayers() {
		return players;
	}

	/** Add a player to the data's list of players
	 * @param player player added*/
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	/** Add a blue token for the players to use. Since 8 tokens is the maximum, if we already have 8 tokens, no tokens are added */
	public void addBlueToken() {
		if (blueTokens < 8)
			this.blueTokens++;
	}
	
	/** Remove a blue tokken to be used */
	public void removeBlueToken() {
		if (blueTokens > 0)
			this.blueTokens--;
	}
	
	/** Add a red token */
	public void addRedToken() {
		if (redTokens < 3)
			this.redTokens++;
	}

	/** If all stack of cards is complete (the top card of all stacks is 5), returns true
	 * @return boolean true if set complete, false if not*/
	public boolean isSetComplete() {
		for (FireworkColor c : field.keySet()) {
			if (field.get(c) != 5)
				return false;
		}
		return true;
	}
	
	/** returns the score of the field
	 * @return int Final score*/
	public int score() {
		int score = 0;
		for (FireworkColor c : field.keySet()) {
			score += field.get(c);
		}
		return score;
	}
	
	/** Plays a card on the field
	 * @param c Card player to the field*/
	public void addToField(Card c) {
		field.put(c.getColor(), c.getValue());
	}

	/** Discard a card by giving its value to the right color stack
	 * @param c Discarded card*/
	public void addToDiscardZone(Card c) {
		ArrayList<Integer> itemsList = discardZone.get(c.getColor());
        itemsList.add(c.getValue());
		discardZone.put(c.getColor(), itemsList);
	}

	/** Returns the list of players without the player given as arguments
	 * @param player - the player which should not appear in the list
	 * @return List The list of players without the player in parameter*/
	public List<Player> getListWithoutPlayer(Player player) {
		return getPlayers().stream().filter(p -> !p.getName().equals(player.getName())).collect(Collectors.toList());
	}

	/** Return the number of the current turn 
	 * @return int number of turn*/
	public int getNbTurns() {
		return turn;
	}

	/* Increments the number of turns */
	public void addCountTurns() {
		turn++;
	}

}
