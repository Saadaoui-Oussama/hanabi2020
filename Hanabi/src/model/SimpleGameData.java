package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimpleGameData {
	
	private int redTokens;
	private int blueTokens;
	
	private List<Player> players;
	private int nbPlayers;
	
	private ArrayList<Card> deck;
	private Map<FireworkColor, ArrayList<Card>> field;
	private Map<FireworkColor, ArrayList<Card>> defausse;
	
	
	/** Création de la classe gestion de données 
	 * @param nbPlayers: nombre de joueurs **/
	public SimpleGameData(int nbPlayers) {
		this.redTokens = 3;
		this.blueTokens = 8;
		
		this.nbPlayers = nbPlayers;
		this.players = new ArrayList<Player>();
		
		this.deck = generateDeck();
		this.field = generateField();
		this.defausse = generateField(); // La défausse marche comme un terrain
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
	
	private Map<FireworkColor, ArrayList<Card>> generateField() {
		Map<FireworkColor, ArrayList<Card>> map = new HashMap<FireworkColor, ArrayList<Card>>();
		
		for (FireworkColor c : FireworkColor.values()) {
			map.put(c, new ArrayList<Card>());
		}
		
		return map;
	}
	
	public Card draw() {
		if (deck.size()<=0)
			throw new IllegalStateException("Deck vide");
		return deck.remove(0);
	}
	
	public boolean lastTurn() {
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

	public Map<FireworkColor, ArrayList<Card>> getField() {
		return field;
	}

	public Map<FireworkColor, ArrayList<Card>> getDefausse() {
		return defausse;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public boolean isSetComplete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int score() {
		int score = 0;
		
		for (Entry<FireworkColor, ArrayList<Card>> c : field.entrySet()) {
			
		}
		
		return score;
	}

}
