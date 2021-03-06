package controller;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Scanner;

import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import model.*;
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
			System.out.println(" Veuillez entrer le nombre de joueurs (2 � 5 joueurs): ");
			nbPlayers = inputNumber();
		} while (nbPlayers < 2 || nbPlayers > 5);
		
		data = new SimpleGameData();
		
		// 4 cartes si 4 � 5 joueurs, 5 cartes si 2 � 3 joueurs
		int handSize = (nbPlayers > 3)? 4: 5;
		
		for (int i = 1; i <= nbPlayers; i++) {
			System.out.println("Cr�ation du joueur n�"+i);
			Player p = createPlayer(handSize);
			data.addPlayer(p);
		}
		
        view = new SimpleGameView(data);
        gameloop();
		
		if (data.getRedTokens() == 3) 
			view.printLose();
		else {
			view.printWin();
		}
	}
	
	/** The game loop. Breaks when the player completes all sets, loses, or if the deck is empty after the last turn */
	public void gameloop() {
		// Boucle du jeu
		while (data.getRedTokens() != 3 && !data.isSetComplete()) {
			view.showTurn(data.getNbTurns());
            view.showTokens(data);
            
			for (Player p : data.getPlayers()) {
				view.showTurnName(p.getName());
				turn(p);
			}
			
			if (data.lastTurn())
				break;
		}
	}

	/** Let the player in arguments plays his turn (give a intel to another player, play a card or discard a card)*/
	private void turn(Player player) {
		int choice = 0;
		do {
			choice = getActionClick();
			
			if (choice == 1 && data.getBlueTokens() == 0) { // If no blue tokens are available, the player can't give an intel
				System.out.println("Pas assez de jetons pour donner un indice");
				choice = 0;
			}
		} while (choice <1 || choice > 3);
		
		//TODO Mettre � jour les m�thodes d'action
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
		
		data.addCountTurns();
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
		
		playerSaisie--; // On d�cr�mente car on a affich� i+1 pour la s�lection
		Player playerChoice = l.get(playerSaisie);
		System.out.println("Joueur s�lectionn�: "+playerChoice.getName());
		
		
		playerSaisie = 0; // On remet le playerChoice a 0 pour que l'utilisateur fasse un nouveau choix
		System.out.println("Indice de couleur ou de valeur ?");
		do {
			System.out.println("1. Indice de couleur | 2. Indice de valeur");
			playerSaisie = inputNumber();
		} while (playerSaisie != 1 && playerSaisie != 2);
		
		int intelChoice = playerSaisie;
		
		playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte li� � l'indice");
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
			System.out.println("Choisissez la carte � jouer");
			for (int i = 0; i < player.getHand().size(); i++) {
				System.out.println((i+1)+". "+player.getCardInHand(i));
			}
			playerSaisie = inputNumber();
		} while (playerSaisie < 1 || playerSaisie > player.getHand().size());
		
		playerSaisie--;
		Card choice = player.discardCard(playerSaisie); // On retire la carte � jouer de la main du joueur
		
		if (choice.getValue() == data.getField().get(choice.getColor()) + 1) { // Si la carte est correcte (carte jou�e = carte sur terrain + 1)
			data.addToField(choice);
			
			if (choice.getValue() == 5) //Si on compl�te une pile, on gagne un jeton bleu suppl�mentaire !
				data.addBlueToken();
		}
		else { // Carte incorrecte, on d�fausse la carte et on ajoute un jeton rouge
			data.addToDiscardZone(choice);
			data.addRedToken();
		}
		
		player.addCard(data.draw()); // Peu importe le r�sultat, le joueur repioche une carte (peut se positionner juste apr�s Card choice)
		
	}
	
	/** Let the player choose a card and discard it. The players gain a blue tokken by doing this.
	 * @param player - player who's doing the action */
	private void actionDiscardCard(Player player) {
		int playerSaisie = 0;
		do {
			System.out.println("Choisissez la carte � d�fausser");
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
	 * @return Player created player*/
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
	
	/** 
	 * Get where the user clicked in the action menu, if he doesn't click on any option of the menu, returns 0
	 * @return Choice (between 1 and 3)*/
	private int getActionClick() {
		while (true) {
	        Event event = view.getContext().pollEvent();
	        if (event == null) continue;
	        
	        if (event.getAction() == Action.POINTER_DOWN) {
		        Point2D.Float location = event.getLocation();
		        float frameWidth = ((view.getWidth() / 2) / 3);

		        if (location.y > view.getHeight() / 4 && location.y < view.getHeight() / 2) {
		            for (int i = 0; i <= 3; i++) {
		                if (location.x > frameWidth * i && location.x < frameWidth * i + frameWidth)
		                    return i;
		            }
		        }
	        }
		}
    }

}
