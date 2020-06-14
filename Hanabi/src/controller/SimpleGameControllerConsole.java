package controller;

import java.util.List;
import java.util.Scanner;

import model.*;
import model.Player;
import model.SimpleGameData;
import view.SimpleGameViewConsole;

public class SimpleGameControllerConsole {
	
	private SimpleGameData data;
	private SimpleGameViewConsole view;
	private Scanner saisie;
	
	/** Initialize the start of the game*/
	public void start() {
		System.out.println("Bienvenue sur Hanabi !");
		saisie = new Scanner(System.in);
		
		int nbPlayers = 0;
		do {
			System.out.println(" Veuillez entrer le nombre de joueurs (2 à 5 joueurs): ");
			nbPlayers = inputNumber();
		} while (nbPlayers < 2 || nbPlayers > 5);
		
		data = new SimpleGameData();
		view = new SimpleGameViewConsole(data);
		
		// 4 cartes si 4 à 5 joueurs, 5 cartes si 2 à 3 joueurs
		int handSize = (nbPlayers > 3)? 4: 5;
		
		for (int i = 1; i <= nbPlayers; i++) {
			System.out.println("Création du joueur n°"+i);
			Player p = createPlayer(handSize);
			data.addPlayer(p);
		}
		
		gameloop();
		
		if (data.getRedTokens() == 3) 
			view.showDefeat();
		else {
			view.showScore(data.score());
		}
	}
	
	/** The game loop. Breaks when the player completes all sets, loses, or if the deck is empty after the last turn */
	public void gameloop() {
		// Boucle du jeu
			while (data.getRedTokens() != 3 && !data.isSetComplete()) {
				
				for (Player p : data.getPlayers()) {
					turn(p);
				}
				
				data.addCountTurns();
				if (data.lastTurn())
					break;
			}
			
			
	}

	/** Let the player in arguments plays his turn (give a intel to another player, play a card or discard a card)*/
	private void turn(Player player) {
		view.showInformations(player);
		
		int choice = 0;
		do {
			System.out.println("1. Donner un indice | 2. Jouer une carte | 3. Défausser une carte");
			choice = inputNumber();
			
			if (choice == 1 && data.getBlueTokens() == 0) { // If no blue tokens are available, the player can't give an intel
				System.out.println("Pas assez de jetons pour donner un indice");
				choice = 0;
			}
		} while (choice <1 || choice > 3);
		
		switch (choice) {
			case 1:
				actionGiveIntel(player);
			break;
			case 2:
				actionPlayCard(player);
			break;
			case 3:
				actionDiscardCard(player);
			break;
		}
		data.addCountTurns();
		cleanConsole();
	}

	/** Very cheap function to clean the console for each player's turn */
	private void cleanConsole() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

	/** Let the player in argument give an intel to another player 
	 * @param player - player who's doing the action*/
	private void actionGiveIntel(Player player) {
		int playerSaisie = 0;
		// On veut travailler avec une liste ou il n'y a pas le joueur actuel (on ne peut pas se donner un indice)
		List<Player> l = data.getListWithoutPlayer(player);
		do {
			System.out.println("A quel joueur souhaitez-vous donner un indice ?");
			Player pIntel;
			
			for (int i = 0; i < l.size(); i++) {
				pIntel = l.get(i);
				System.out.println((i+1)+". "+pIntel.openHand());
			}
			
			playerSaisie = inputNumber();
		} while (playerSaisie <1 || playerSaisie > l.size());
		
		playerSaisie--; // On décrémente car on a affiché i+1 pour la sélection
		Player playerChoice = l.get(playerSaisie);
		System.out.println("Joueur sélectionné: "+playerChoice.getName());
		
		
		playerSaisie = 0; // On remet le playerChoice a 0 pour que l'utilisateur fasse un nouveau choix
		System.out.println("Indice de couleur ou de valeur ?");
		do {
			System.out.println("1. Indice de couleur | 2. Indice de valeur");
			playerSaisie = inputNumber();
		} while (playerSaisie != 1 && playerSaisie != 2);
		
		int intelChoice = playerSaisie;
		
		playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte lié à l'indice");
			for (int i = 0; i < playerChoice.getHand().size(); i++) {
				System.out.println((i+1)+". "+playerChoice.getCardInHand(i).openCard());
			}
			playerSaisie = inputNumber();
		} while (playerSaisie < 1 || playerSaisie > playerChoice.getHand().size());
		
		playerSaisie--;
		int indexCardChoice = playerSaisie;
		Card cardChoice = playerChoice.getCardInHand(indexCardChoice);

		if (intelChoice == 1)
			playerChoice.addIndColor(cardChoice.getColor());
		else
			playerChoice.addIndValue(cardChoice.getValue());
		
		data.removeBlueToken();
	}
	
	/** Let the player choose a card and play it on the field. If the card is incorrect, the card is instead discarded and a red tokken is added
	 * @param player - player who's doing the action */
	private void actionPlayCard(Player player) {
		int playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte à jouer");
			for (int i = 0; i < player.getHand().size(); i++) {
				System.out.println((i+1)+". "+player.getCardInHand(i));
			}
			playerSaisie = inputNumber();
		} while (playerSaisie < 1 || playerSaisie > player.getHand().size());
		
		playerSaisie--;
		Card choice = player.discardCard(playerSaisie); // On retire la carte à jouer de la main du joueur
		
		if (choice.getValue() == data.getField().get(choice.getColor()) + 1) { // Si la carte est correcte (carte jouée = carte sur terrain + 1)
			data.addToField(choice);
			
			if (choice.getValue() == 5) //Si on complète une pile, on gagne un jeton bleu supplémentaire !
				data.addBlueToken();
		}
		else { // Carte incorrecte, on défausse la carte et on ajoute un jeton rouge
			data.addToDiscardZone(choice);
			data.addRedToken();
		}
		
		player.addCard(data.draw()); // Peu importe le résultat, le joueur repioche une carte (peut se positionner juste après Card choice)
		
	}
	
	/** Let the player choose a card and discard it. The players gain a blue tokken by doing this.
	 * @param player - player who's doing the action */
	private void actionDiscardCard(Player player) {
		int playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte à défausser");
			for (int i = 0; i < player.getHand().size(); i++) {
				System.out.println((i+1)+". "+player.getCardInHand(i));
			}
			playerSaisie = inputNumber();
		} while (playerSaisie < 1 || playerSaisie > player.getHand().size());
		
		playerSaisie--;
		Card discarded = player.discardCard(playerSaisie);
		data.addToDiscardZone(discarded);
		player.addCard(data.draw());
	}

	/** Create and return a new player by asking his name
	 * @param handSize - size of the hand (depending on the total numbers of players)
	 * @return Player - The new player */
	private Player createPlayer(int handSize) {
		String nom;
		boolean correct_name;
		
		System.out.print("Nom du joueur: ");
		do {
			correct_name = true;
			nom = saisie.nextLine();
			
			for (Player p : data.getPlayers()) {
				if (p.getName().equals(nom)) {
					System.out.println("Nom déjà pris par un autre joueur");
					correct_name = false;
				}
			}
		} while (nom.isBlank() || !correct_name);
		
		return new Player(nom, data, handSize);
	}
	
	/** let the user input his integer choice. If the input is not an integer, this function returns 0, which forces the input loop to cycle back.
	 *  @return The number inputted if the input is correct, 0 if it's not*/
	private int inputNumber() {
		String input = saisie.nextLine();
		int numericInput = 0;
		if (input.isEmpty())
			return 0;
		
		try {
			numericInput = Integer.parseInt(input);
		}
		catch(Exception e) {
			return 0;
		}
		
		return numericInput;
	}

}
