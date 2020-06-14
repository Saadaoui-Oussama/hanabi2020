package controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Scanner;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import model.Card;
import model.Player;
import model.SimpleGameData;
import view.SimpleGameView;
import view.SimpleGameView_BACK;

public class SimpleGameController {
	
	private SimpleGameData data;
	private SimpleGameView view;
	private Scanner saisie;
	
	private int nbTours = 1; // Repère de tour
	
	public void start() {
		System.out.println("Bienvenue sur Hanabi !");
		saisie = new Scanner(System.in);
		
		int nbPlayers = 0;
		do {
			System.out.println(" Veuillez entrer le nombre de joueurs (2 à 5 joueurs): ");
			nbPlayers = saisie.nextInt();
		} while (nbPlayers < 2 || nbPlayers > 5);
		
		data = new SimpleGameData();
		
		// 4 cartes si 4 à 5 joueurs, 5 cartes si 2 à 3 joueurs
		int handSize = (nbPlayers > 3)? 4: 5;
		
		for (int i = 1; i <= nbPlayers; i++) {
			System.out.println("Création du joueur n°"+i);
			Player p = createPlayer(handSize);
			data.addPlayer(p);
		}
		
		Application.run(Color.GRAY, context -> {
	        SimpleGameView view = new SimpleGameView(context);
	        view.showField(data);
	        view.showMenu();
	        view.showDiscardZone(data);
	        view.showPlayer(data);
	        mainLoop(view, data, context);   
	    });
	}
		
		private void mainLoop(SimpleGameView view, SimpleGameData data, ApplicationContext context) {
			// Boucle du jeu
			while (data.getRedTokens() != 3 && !data.isSetComplete()) {
				System.out.println("Tour n°"+nbTours);
				view.showTurn(nbTours);
				view.showTokens(data);
				for (Player p : data.getPlayers()) {
					//view.clearWin(context);
					view.showTurnName(p.getName());
					turn(p);
					if (data.lastTurn())
						break;
				}
				nbTours++;
				if (data.lastTurn())
					break;
				}
				endGame();
			}

	private void endGame() {
		if (data.getRedTokens() == 3) 
			System.out.println("Vous avez PERDU");
		else {
			int scoreFinal = data.score();
			System.out.println("Calcul du score: "+scoreFinal);
	
			if (scoreFinal <= 5)
				System.out.println("Horrible, huées de la foule...");
			else if (scoreFinal >= 6 && scoreFinal <= 10)
				System.out.println("Médiocre, à peine quelques applaudissements.");
			else if (scoreFinal >= 11 && scoreFinal <= 15)
				System.out.println("Honorable, mais ne restera  pas dans les mémoires...");
			else if(scoreFinal >= 16 && scoreFinal <= 20)
				System.out.println("Excellente, ravit la foule.");
			else if(scoreFinal >= 21 && scoreFinal <= 24)
				System.out.println("Extraordinaire, restera gravée dans les mémoires !");
			else
				System.out.println("Légendaire, petits et grands sans voix,  des étoiles dans les yeux");
			
			System.out.println("A bientôt !");
		}
	}

	private void turn(Player player) {
		
		
		
		int choice = 0;
		do {
			System.out.println("1. Donner un indice | 2. Jouer une carte | 3. Défausser une carte");
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
			throw new IllegalStateException("choix action: "+choice); // On n'est jamais censé arrivé la
		}
		
		cleanConsole();
	}

	/** Fonction très "cheap" pour nettoyer la console à chaque nouveau tour pendant la partie */
	private void cleanConsole() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

	private void showInformations(Player player) {
		if (data.lastTurn())
			System.out.println("LAST TURN !");
		System.out.println("A ton tour, "+player.getName());
		System.out.println("Jetons bleus: "+data.getBlueTokens());
		System.out.println("Jetons rouges: "+data.getRedTokens());
		System.out.println("Cartes restantes: "+data.getDeck().size());
		
		// Main des joueurs
		System.out.println(player.toString());
		for (Player p : data.getListWithoutPlayer(player))
			System.out.println(p.openHand());
		
		// Affichage du terrain et de la défausse (fonctions de test - à mieux redéfinir pour phase 3 ?) TODO
		view.showField(data);
		view.showDiscardZone(data);
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
		
		playerSaisie--; // On décrémente car on a affiché i+1 pour la sélection
		Player playerChoice = l.get(playerSaisie);
		System.out.println("Joueur sélectionné: "+playerChoice.getName());
		
		
		playerSaisie = 0; // On remet le playerChoice a 0 pour que l'utilisateur fasse un nouveau choix
		System.out.println("Indice de couleur ou de valeur ?");
		do {
			System.out.println("1. Indice de couleur | 2. Indice de valeur");
			playerSaisie = saisie.nextInt();
		} while (playerSaisie != 1 && playerSaisie != 2);
		
		int intelChoice = playerSaisie;
		
		playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte lié à l'indice");
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
			System.out.println("Choisissez la carte à jouer");
			for (int i = 0; i < player.getHand().size(); i++) {
				System.out.println((i+1)+". "+player.getCardInHand(i));
			}
			playerSaisie = saisie.nextInt();
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
	
	private void actionDiscardCard(Player player) {
		int playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte à défausser");
			for (int i = 0; i < player.getHand().size(); i++) {
				System.out.println((i+1)+". "+player.getCardInHand(i));
			}
			playerSaisie = saisie.nextInt();
		} while (playerSaisie < 1 || playerSaisie > player.getHand().size());
		
		playerSaisie--;
		Card discarded = player.discardCard(playerSaisie);
		data.addToDiscardZone(discarded);
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
					System.out.println("Nom déjà pris par un autre joueur");
					correct_name = false;
				}
			}
		} while (nom.isBlank() || !correct_name);
		
		return new Player(nom, data, handSize);
	}

}
