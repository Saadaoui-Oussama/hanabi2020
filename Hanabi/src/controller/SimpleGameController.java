package controller;

import java.util.Scanner;

import model.Player;
import model.SimpleGameData;
import view.SimpleGameView;

public class SimpleGameController {
	
	private SimpleGameData data;
	private SimpleGameView view;
	private Scanner saisie;
	
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
			data.addPlayer( createPlayer(data, handSize));
		}
		
		System.out.println(data.getPlayers());
		
		
		System.out.println("END");
	}

	private Player createPlayer(SimpleGameData data, int handSize) {
		String nom;
		
		System.out.print("Nom du joueur: ");
		do {
			nom = saisie.nextLine();
		} while (nom.isBlank());
		
		return new Player(nom, data, handSize);
	}

}
