package controller;

import java.util.List;
import java.util.Scanner;

import model.Card;
import model.Player;
import model.SimpleGameData;
import view.SimpleGameViewConsole;

public class SimpleGameControllerConsole {
	
	private SimpleGameData data;
	private SimpleGameViewConsole view;
	private Scanner saisie;
	
	private int nbTours = 1; // Rep�re de tour
	
	public void start() {
		System.out.println("Bienvenue sur Hanabi !");
		saisie = new Scanner(System.in);
		
		int nbPlayers = 0;
		do {
			System.out.println(" Veuillez entrer le nombre de joueurs (2 � 5 joueurs): ");
			nbPlayers = saisie.nextInt();
		} while (nbPlayers < 2 || nbPlayers > 5);
		
		data = new SimpleGameData(nbPlayers);
		view = new SimpleGameViewConsole(data);
		
		// 4 cartes si 4 � 5 joueurs, 5 cartes si 2 � 3 joueurs
		int handSize = (nbPlayers > 3)? 4: 5;
		
		for (int i = 1; i <= nbPlayers; i++) {
			System.out.println("Cr�ation du joueur n�"+i);
			Player p = createPlayer(handSize);
			data.addPlayer(p);
		}
		
		// Boucle du jeu
		while (data.getRedTokens() != 3 && !data.isSetComplete()) {
			System.out.println("Tour n�"+nbTours);
			
			for (Player p : data.getPlayers()) {
				turn(p);
				if (data.lastTurn())
					break;
			}
			
			nbTours++;
			if (data.lastTurn())
				break;
		}
		
		if (data.getRedTokens() == 3) 
			System.out.println("Vous avez PERDU");
		else {
			view.showScore();
		}
	}

	private void turn(Player player) {
		view.showInformations(player);
		
		int choice = 0;
		do {
			System.out.println("1. Donner un indice | 2. Jouer une carte | 3. D�fausser une carte");
			choice = saisie.nextInt();
			
			if (choice == 1 && data.getBlueTokens() == 0) {
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
		
		default:
			throw new IllegalStateException("choix action: "+choice); // On n'est jamais cens� arriv� la
		}
		
		cleanConsole();
	}

	/** Fonction tr�s "cheap" pour nettoyer la console � chaque nouveau tour pendant la partie */
	private void cleanConsole() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

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
			
			playerSaisie = saisie.nextInt();
		} while (playerSaisie <1 || playerSaisie > l.size());
		
		playerSaisie--; // On d�cr�mente car on a affich� i+1 pour la s�lection
		Player playerChoice = l.get(playerSaisie);
		System.out.println("Joueur s�lectionn�: "+playerChoice.getName());
		
		
		playerSaisie = 0; // On remet le playerChoice a 0 pour que l'utilisateur fasse un nouveau choix
		System.out.println("Indice de couleur ou de valeur ?");
		do {
			System.out.println("1. Indice de couleur | 2. Indice de valeur");
			playerSaisie = saisie.nextInt();
		} while (playerSaisie != 1 && playerSaisie != 2);
		
		int intelChoice = playerSaisie;
		
		playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte li� � l'indice");
			for (int i = 0; i < playerChoice.getHand().size(); i++) {
				System.out.println((i+1)+". "+playerChoice.getCardInHand(i).openCard());
			}
			playerSaisie = saisie.nextInt();
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
	
	private void actionPlayCard(Player player) {
		int playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte � jouer");
			for (int i = 0; i < player.getHand().size(); i++) {
				System.out.println((i+1)+". "+player.getCardInHand(i));
			}
			playerSaisie = saisie.nextInt();
		} while (playerSaisie < 1 || playerSaisie > player.getHand().size());
		
		playerSaisie--;
		Card choice = player.discardCard(playerSaisie); // On retire la carte � jouer de la main du joueur
		
		if (choice.getValue() == data.getField().get(choice.getColor()) + 1) { // Si la carte est correcte (carte jou�e = carte sur terrain + 1)
			data.addToField(choice);
			
			if (choice.getValue() == 5) //Si on compl�te une pile, on gagne un jeton bleu suppl�mentaire !
				data.addBlueToken();
		}
		else { // Carte incorrecte, on d�fausse la carte et on ajoute un jeton rouge
			data.addToDefausse(choice);
			data.addRedToken();
		}
		
		player.addCard(data.draw()); // Peu importe le r�sultat, le joueur repioche une carte (peut se positionner juste apr�s Card choice)
		
	}
	
	private void actionDiscardCard(Player player) {
		int playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte � d�fausser");
			for (int i = 0; i < player.getHand().size(); i++) {
				System.out.println((i+1)+". "+player.getCardInHand(i));
			}
			playerSaisie = saisie.nextInt();
		} while (playerSaisie < 1 || playerSaisie > player.getHand().size());
		
		playerSaisie--;
		Card discarded = player.discardCard(playerSaisie);
		data.addToDefausse(discarded);
		player.addCard(data.draw());
	}

	private Player createPlayer(int handSize) {
		String nom;
		boolean correct_name;
		
		System.out.print("Nom du joueur: ");
		do {
			correct_name = true;
			nom = saisie.nextLine();
			
			for (Player p : data.getPlayers()) {
				if (p.getName().equals(nom)) {
					System.out.println("Nom d�j� pris par un autre joueur");
					correct_name = false;
				}
			}
		} while (nom.isBlank() || !correct_name);
		
		return new Player(nom, data, handSize);
	}

}