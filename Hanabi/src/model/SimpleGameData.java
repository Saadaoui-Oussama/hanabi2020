package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleGameData {
	
	private int redTokens; // Jetons obtenus en jouant une mauvaise carte, a 3 jetons, les joueurs ont perdu
	private int blueTokens; // Peut être utilisé pour donner un indice - défausser une carte ou compléter une série en génère un
	
	private List<Player> players;
	
	private ArrayList<Card> deck;
	private Map<FireworkColor, Integer> field;
	private Map<FireworkColor, ArrayList<Integer>> discardZone;
	
	
	/** Creates all the game's data: the deck, a list of players, the field, the discard zone, and the blue and red tokens **/
	public SimpleGameData() {
		this.redTokens = 0;
		this.blueTokens = 8;
		
		this.players = new ArrayList<Player>();
		
		this.deck = generateDeck();
		this.field = generateField();
		this.discardZone = generateDiscardZone();
	}

	/** Generate the deck of Hanabi, and then shuffle it*/
	private ArrayList<Card> generateDeck() {
		ArrayList<Card> deck = new ArrayList<Card>();
		for (FireworkColor c : FireworkColor.values()) { // 5 couleurs
			// pour chacune des 5 couleurs des cartes, la distribution des valeurs est 1, 1, 1, 2, 2, 3, 3, 4, 4, 5
			for (int i = 1; i <= 5; i++) {
				switch (i) {
				case 1:
					deck.add(new Card(c, i));
					deck.add(new Card(c, i));
					deck.add(new Card(c, i));
					break;
				case 2, 3, 4: //TODO enable preview à activer
					deck.add(new Card(c, i));
					deck.add(new Card(c, i));
					break;
				case 5:
					deck.add(new Card(c, i));
					break;
				}
			}
		}
		System.out.println(deck);
		Collections.shuffle(deck);
		return deck;
	}
	
	/** Generate a field, which consists of 5 stacks of card of each color, and containing the top value of the stack (starting at 0) */
	private Map<FireworkColor, Integer> generateField() {
		Map<FireworkColor, Integer> map = new HashMap<FireworkColor, Integer>();
		
		for (FireworkColor c : FireworkColor.values()) {
			map.put(c, 0);
		}
		
		return map;
	}
	
	/** Generate a discard zone, which consists of 5 stacks of card of each color, and containing the value of all discarded cards of each stack*/
	private Map<FireworkColor, ArrayList<Integer>> generateDiscardZone() {
		Map<FireworkColor, ArrayList<Integer>> map = new HashMap<FireworkColor, ArrayList<Integer>>();
		
		for (FireworkColor c : FireworkColor.values()) {
			map.put(c, new ArrayList<Integer>(0));
		}
		
		return map;
	}
	
	/** Return a card drawed (removed from the top of the deck). If the deck is empty, we can't draw and return a null (no card) */
	public Card draw() {
		if (deck.size()<=0) {
			System.out.println("Deck vide, pioche impossible !");
			return null;
		}
		
		return deck.remove(0);
	}
	
	/** Check if the current game is on it's last turn by checking if the deck is empty. If it is, we are in the last turn.*/
	public boolean lastTurn() {
		if (deck.size() == 0)
			return true;
		return false;
	}
	
	/** Returns the number of red tokens availables */
	public int getRedTokens() {
		return redTokens;
	}

	/** Returns the number of blue tokens availables*/
	public int getBlueTokens() {
		return blueTokens;
	}

	/** */
	public ArrayList<Card> getDeck() {
		return deck;
	}

	/** Returns the field */
	public Map<FireworkColor, Integer> getField() {
		return field;
	}

	/** Return the discard zone */
	public Map<FireworkColor, ArrayList<Integer>> getDiscardZone() {
		return discardZone;
	}

	/** Return a list with all players*/
	public List<Player> getPlayers() {
		return players;
	}

	/** Add a player to the data's list of players*/
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

	/** If all stack of cards is complete (the top card of all stacks is 5), returns true*/
	public boolean isSetComplete() {
		for (FireworkColor c : field.keySet()) {
			if (field.get(c) != 5)
				return false;
		}
		return true;
	}
	
	/** returns the score of the field*/
	public int score() {
		int score = 0;
		for (FireworkColor c : field.keySet()) {
			score += field.get(c);
		}
		return score;
	}
	
	/** Plays a card on the field*/
	public void addToField(Card c) {
		field.put(c.getColor(), c.getValue());
	}

	/** Discard a card by giving its value to the right color stack*/
	public void addToDiscardZone(Card c) {
		ArrayList<Integer> itemsList = discardZone.get(c.getColor());
        itemsList.add(c.getValue());
		discardZone.put(c.getColor(), itemsList);
	}

	/** Returns the list of players without the player given as arguments
	 * @param player - the player which should not appear in the list*/
	public List<Player> getListWithoutPlayer(Player player) {
		return getPlayers().stream().filter(p -> !p.getName().equals(player.getName())).collect(Collectors.toList());
	}

}
