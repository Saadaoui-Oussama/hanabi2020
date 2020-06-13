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
		
		// Affichage du terrain et de la défausse (fonctions de test - à mieux redéfinir pour phase 3 ?) TODO
		data.showField();
		data.showDiscardZone();
	}
	
	public void showScore() {
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
