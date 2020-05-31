package controller;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.Card;
import model.Player;
import model.SimpleGameData;
import view.SimpleGameView;

public class SimpleGameController {
	
	private SimpleGameData data;
	//private SimpleGameView view;
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
		
		data = new SimpleGameData(nbPlayers);
		
		// 4 cartes si 4 à 5 joueurs, 5 cartes si 2 à 3 joueurs
		int handSize = (nbPlayers > 3)? 4: 5;
		
		for (int i = 1; i <= nbPlayers; i++) {
			System.out.println("Création du joueur n°"+i);
			data.addPlayer( createPlayer(handSize));
		}
		
		// Boucle du jeu
		while (data.getRedTokens() != 3 && !data.isSetComplete()) {
			if (data.lastTurn())
				System.out.println("LAST TURN !");
			
			System.out.println("Tour n°"+nbTours);
			
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
		
		if (data.lastTurn())
			System.out.println("Calcul du score: "+data.score());
		
		System.out.println("END");
	}

	private void turn(Player player) {
		System.out.println("A ton tour, "+player.getName());
		System.out.println("Jetons bleus: "+data.getBlueTokens());
		System.out.println("Jetons rouges: "+data.getRedTokens());
		System.out.println("Cartes restantes: "+data.getDeck().size());
		System.out.println(player.toString());
		
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
			
		break;
		
		case 3:
			
		break;
		
		default:
		break;
		}
	}

	private void actionGiveIntel(Player player) {
		int playerSaisie = 0;
		// On veut travailler avec une liste ou il n'y a pas le joueur actuel (on ne peut pas se donner un indice)
		List<Player> l = data.getPlayers().stream().filter(p -> !p.getName().equals(player.getName())).collect(Collectors.toList());
		do {
			System.out.println("A quel joueur souhaitez-vous donner un indice ?");
			Player pIntel;
			
			for (int i = 0; i < l.size(); i++) {
				pIntel = l.get(i);
				System.out.println((i+1)+". "+pIntel.getName()+": "+pIntel.openHand());
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
				System.out.println((i+1)+". "+playerChoice.getHand().get(i).openCard());
			}
			playerSaisie = saisie.nextInt();
		} while (playerSaisie < 1 || playerSaisie > playerChoice.getHand().size());
		
		playerSaisie--;
		int indexCardChoice = playerSaisie;
		Card cardChoice = playerChoice.getHand().get(indexCardChoice);

		if (intelChoice == 1)
			playerChoice.addIndColor(cardChoice.getColor());
		else
			playerChoice.addIndValue(cardChoice.getValue());
		
		data.removeBlueToken();
		
	}

	private Player createPlayer(int handSize) {
		String nom;
		boolean correct_name;
		
		do {
			System.out.print("Nom du joueur: ");
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
