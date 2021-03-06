package view;

import model.FireworkColor;
import model.Player;
import model.SimpleGameData;

public class SimpleGameViewConsole {
	
	private SimpleGameData data;
	
	/** Creates the view of the game (console)
	 * @param data - SimpleGameData of the game */
	public SimpleGameViewConsole(SimpleGameData data) {
		this.data = data;
	}
	
	/** Shows all informations for the player turn: field, discard zone, his hand and other players hands
	 * @param player Player which has the view */
	public void showInformations(Player player) {
		System.out.println("Tour n�"+data.getNbTurns());
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
		
		// Affichage du terrain et de la d�fausse
		showField();
		showDiscardZone();
	}
	
	/**
	 * Shows the field
	 */
	public void showField() {
		System.out.println("Field:");
		
		for (FireworkColor c : data.getField().keySet()) {
			System.out.println("Couleur "+c+": "+data.getField().get(c));
		}
	}
	
	/**
	 * Shows the discard zone
	 */
	public void showDiscardZone() {
		System.out.println("Discard zone:");
		
		for (FireworkColor c : data.getDiscardZone().keySet()) {
			System.out.print("Couleur "+c+": ");
			for (int value : data.getDiscardZone().get(c)) {
				System.out.print(value+" ");
			}
			System.out.println();
		}
	}
	
	/**
	 * Shows the final score of the players
	 * @param scoreFinal calculated score
	 */
	public void showScore(int scoreFinal) {
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
			System.out.println("L�gendaire, petits et grands sans voix, des �toiles dans les yeux");
		
		System.out.println("A bient�t !");
	}

	/**
	 * Prints a defeat phrase
	 */
	public void showDefeat() {
		System.out.println("Vous avez PERDU");
	}
	
	
}
