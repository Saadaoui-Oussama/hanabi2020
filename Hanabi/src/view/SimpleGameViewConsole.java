package view;

import model.Player;
import model.SimpleGameData;

public class SimpleGameViewConsole {
	
	private SimpleGameData data;
	
	public SimpleGameViewConsole(SimpleGameData data) {
		this.data = data;
	}
	
	public void showInformations(Player player) {
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
		
		// Affichage du terrain et de la d�fausse (fonctions de test - � mieux red�finir pour phase 3 ?) TODO
		data.showField();
		data.showDiscardZone();
	}
	
	public void showScore() {
		int scoreFinal = data.score();
		System.out.println("Calcul du score: "+scoreFinal);

		if (scoreFinal <= 5)
			System.out.println("Horrible, hu�es de la foule...");
		else if (scoreFinal >= 6 && scoreFinal <= 10)
			System.out.println("M�diocre, � peine quelques applaudissements.");
		else if (scoreFinal >= 11 && scoreFinal <= 15)
			System.out.println("Honorable, mais ne restera  pas dans les m�moires...");
		else if(scoreFinal >= 16 && scoreFinal <= 20)
			System.out.println("Excellente, ravit la foule.");
		else if(scoreFinal >= 21 && scoreFinal <= 24)
			System.out.println("Extraordinaire, restera grav�e dans les m�moires !");
		else
			System.out.println("L�gendaire, petits et grands sans voix,  des �toiles dans les yeux");
		
		System.out.println("A bient�t !");
	}
}
