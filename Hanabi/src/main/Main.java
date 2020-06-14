package main;
import java.util.Scanner;

import controller.SimpleGameController;
import controller.SimpleGameControllerConsole;

public class Main {
	public static void main(String[] args) {
		
		Scanner saisie = new Scanner(System.in);;

		int gameChoice = 0;
		do {
			System.out.println("Quel mode d'affichage souhaitez-vous ?");
			System.out.println("1. Console || 2. Graphique");
			gameChoice = saisie.nextInt();
		} while (gameChoice < 1 || gameChoice > 2);
		
		
		
		
		if (gameChoice == 1) {
			SimpleGameControllerConsole game = new SimpleGameControllerConsole();
			game.start();
		}
		else {
			SimpleGameController game = new SimpleGameController();
			game.start();
		}
		
		saisie.close();
			
	}
}
 