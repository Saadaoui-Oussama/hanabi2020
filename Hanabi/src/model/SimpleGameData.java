package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleGameData {
	
	private int redTokens;
	private int blueTokens;
	private ArrayList<Card> deck;
	private List<Player> players;
	private int nbPlayers;
	
	/** Création de la classe gestion de données 
	 * @param nbPlayers: nombre de joueurs **/
	public SimpleGameData(int nbPlayers) {
		this.redTokens = 3;
		this.blueTokens = 8;
		this.deck = generateDeck();
		this.nbPlayers = nbPlayers;
		this.players = new ArrayList<Player>();
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
	
	public Card draw() {
		if (deck.size()<=0)
			throw new IllegalStateException("Deck vide");	
		return deck.remove(0);
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

	public List<Player> getPlayers() {
		return players;
	}

}
