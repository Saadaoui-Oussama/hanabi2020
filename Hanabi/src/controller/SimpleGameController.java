package controller;

import java.util.Scanner;

import model.Player;
import model.SimpleGameData;
import view.SimpleGameView;

public class SimpleGameController {
	
	private SimpleGameData data;
	private SimpleGameView view;
	private Scanner saisie;
	
	private int nbTours = 0; // Repère de tour
	
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
		while (data.getRedTokens() != 0 || !data.isSetComplete() || data.lastTurn()) {
			System.out.println("Tour n°"+nbTours);
			
			for (Player p : data.getPlayers()) {
				turn(p);
			}
			
			nbTours++;
		}
		
		System.out.println("END");
	}

	private void turn(Player p) {
		System.out.println("A ton tour, "+p.getName());
		System.out.println("Jetons bleus: "+data.getBlueTokens());
		System.out.println("Jetons rouges: "+data.getRedTokens());
		System.out.println(p.toString());
		
		int choice = 0;
		do {
			System.out.println("1. Donner un indice | 2. Jouer une carte | 3. Défausser une carte");
			choice = saisie.nextInt();
		} while (choice <1 || choice > 3);
		
		switch (choice) {
		case 1:
			int playerChoice = 0;
			do {
				System.out.println("A quel joueur souhaitez-vous donner un indice ?");
				
				for (int i = 0; i < data.getPlayers().size(); i++) {
					Player pIntel = data.getPlayers().get(i);
					System.out.println((i+1)+". "+pIntel.getName()+": "+pIntel.openHand());
				}
				
				// demander couleur ou valeur, choisir carte, appeler fonction
				
				choice = saisie.nextInt();
			} while (choice <1 || choice > 3);
		break;
		
		case 2:
		break;
		
		case 3:
		break;
		
		default:
		break;
		}
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
