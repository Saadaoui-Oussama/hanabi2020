package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleGameData {
	
	//TODO listener sur redTokens, field et defausse ?
	
	private int redTokens; // Jetons obtenus en jouant une mauvaise carte, a 3 jetons, les joueurs ont perdu
	private int blueTokens; // Peut être utilisé pour donner un indice - défausser une carte ou compléter une série en génère un
	
	private List<Player> players;
	private int nbPlayers;
	
	private ArrayList<Card> deck;
	private Map<FireworkColor, Integer> field;
	private Map<FireworkColor, ArrayList<Integer>> discardZone;
	
	
	/** Création de la classe gestion de données 
	 * @param nbPlayers: nombre de joueurs **/
	public SimpleGameData(int nbPlayers) {
		this.redTokens = 0;
		this.blueTokens = 8;
		
		this.nbPlayers = nbPlayers;
		this.players = new ArrayList<Player>();
		
		this.deck = generateDeck();
		this.field = generateField();
		this.discardZone = generateDiscardZone();
	}

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
				case 2, 3, 4: //TODO enable preview ou pas ?
					deck.add(new Card(c, i));
					deck.add(new Card(c, i));
					break;
				case 5:
					deck.add(new Card(c, i));
					break;
				}
			}
			Collections.shuffle(deck);
		}
		return deck;
	}
	
	private Map<FireworkColor, Integer> generateField() {
		Map<FireworkColor, Integer> map = new HashMap<FireworkColor, Integer>();
		
		for (FireworkColor c : FireworkColor.values()) {
			map.put(c, 0);
		}
		
		return map;
	}
	
	private Map<FireworkColor, ArrayList<Integer>> generateDiscardZone() {
		Map<FireworkColor, ArrayList<Integer>> map = new HashMap<FireworkColor, ArrayList<Integer>>();
		
		for (FireworkColor c : FireworkColor.values()) {
			map.put(c, new ArrayList<Integer>(0));
		}
		
		return map;
	}
	
	public Card draw() {
		if (deck.size()<=0)
			throw new IllegalStateException("Deck vide");
		return deck.remove(0);
	}
	
	public boolean lastTurn() {
		if (deck.size() == 0)
			return true;
		return false;
	}
	
	/* Getters/Setters */
	
	public int getNbPlayers() {
		return this.nbPlayers;
	}
	
	public int getRedTokens() {
		return redTokens;
	}

	public int getBlueTokens() {
		return blueTokens;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Map<FireworkColor, Integer> getField() {
		return field;
	}

	public Map<FireworkColor, ArrayList<Integer>> getDefausse() {
		return discardZone;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void addBlueToken() {
		if (blueTokens < 8)
			this.blueTokens++;
	}
	
	public void removeBlueToken() {
		if (blueTokens > 0)
			this.blueTokens--;
	}
	
	public void addRedToken() {
		if (redTokens < 3)
			this.redTokens++;
	}

	public boolean isSetComplete() {
		for (FireworkColor c : field.keySet()) {
			if (field.get(c) != 5)
				return false;
		}
		return true;
	}
	
	public int score() {
		int score = 0;
		for (FireworkColor c : field.keySet()) {
			score += field.get(c);
		}
		return score;
	}
	
	public void addToField(Card c) {
		field.put(c.getColor(), c.getValue());
	}

	public void addToDefausse(Card c) {
		ArrayList<Integer> itemsList = discardZone.get(c.getColor());
        itemsList.add(c.getValue());
		discardZone.put(c.getColor(), itemsList);
	}
	
	public void showField() {
		System.out.println("FIELD");
		
		for (FireworkColor c : field.keySet()) {
			System.out.println("Couleur "+c+": "+field.get(c));
		}
	}
	
	public void showDiscardZone() {
		System.out.println("DEFAUSSE");
		
		for (FireworkColor c : discardZone.keySet()) {
			System.out.print("Couleur "+c+": ");
			for (int value : discardZone.get(c)) {
				System.out.print(value+" ");
			}
			System.out.println();
		}
	}

	public List<Player> getListWithoutPlayer(Player player) {
		return getPlayers().stream().filter(p -> !p.getName().equals(player.getName())).collect(Collectors.toList());
	}

}
