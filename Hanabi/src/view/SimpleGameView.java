package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.List;

import fr.umlv.zen5.ApplicationContext;
import model.Card;
import model.FireworkColor;
import model.Player;
import model.SimpleGameData;

public class SimpleGameView {
	
	ApplicationContext context;
	private float XPlayerOrigin = 0;
	private float YPlayerOrigin  = 0;
	float width ; 
	float height; 
	
	public SimpleGameView(ApplicationContext context) {
		this.context = context;
		this.width = context.getScreenInfo().getWidth();
		this.height = context.getScreenInfo().getHeight();
	}
	
	public void showMenu() {
		context.renderFrame(graphics -> {
			drawMenu(graphics);
			
		});
	}
	
	public void showField() {
		context.renderFrame(graphics -> {
			drawField(graphics);
		});
	}
	
	public void showDiscardZone() {
		context.renderFrame(graphics -> {
			drawDiscardZone(graphics);
		});
	}
	
	public void showPlayer(SimpleGameData data) {
		context.renderFrame(graphics -> {
			drawPlayer(graphics, data);
		});
	}
	
	public void showTokens(SimpleGameData data) {
		context.renderFrame(graphics -> {
			printBlueTokens(graphics, data.getBlueTokens());
			printRedTokens(graphics, data.getRedTokens());
		});
	}
	
	public void showCards(List<Card> hand) {
		context.renderFrame(graphics -> {
			drawCard(graphics, hand);
		});
	}
	public void showTurn(int nb) {
		String str = "Tour " + nb + " :";
		context.renderFrame(graphics -> {
			graphics.setColor(Color.WHITE);
			graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
			graphics.drawString(str, width/4+width/6, height/2+40);
		});
	}
	
	public void showTurnName(String name) {
		String str = "A " + name + " de jouer !";
		context.renderFrame(graphics -> {
			graphics.setColor(Color.WHITE);
			graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
			graphics.drawString(str, width/2-60, height/2+40);
		});
	}
	
	private void drawMenu(Graphics2D graphics) {

    		graphics.setColor(Color.RED);
    		graphics.draw(new Rectangle.Float(0, height/4, width/3, height/4));
    		graphics.setColor(Color.BLUE);
    		graphics.draw(new Rectangle.Float(width/3, height/4, width/3, height/4));
    		graphics.setColor(Color.GREEN);
    		graphics.draw(new Rectangle.Float((width/3)*2, height/4, width/3, height/4));

	}
	 
	private void drawField(Graphics2D graphics) {
    		graphics.setColor(Color.GRAY);
    		graphics.draw(new Rectangle.Float(0, 0, width/2, height/4));

	}
	
	private void drawDiscardZone(Graphics2D graphics) {
    		graphics.setColor(Color.ORANGE);
    		graphics.draw(new Rectangle.Float(width/2, 0, width/2, height/4));
	}
	
	private void drawPlayer(Graphics2D graphics, SimpleGameData data) {	
		YPlayerOrigin = height-(height/4);
		String str = "";
		List<Player> list = data.getPlayers();
		
    		for(Player pl : list) {
    			graphics.setColor(Color.cyan);
    			graphics.draw(new Rectangle.Float(XPlayerOrigin, YPlayerOrigin, (width/5), height));
    			graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
    			str = pl.getName();
				graphics.drawString(str, XPlayerOrigin, YPlayerOrigin-10);  // TODO mettre les bonnes coordonnées 
				drawCard(graphics, pl.getHand());
        		XPlayerOrigin = XPlayerOrigin + (width/5);
        		YPlayerOrigin = height-(height/4);
    		}
	}
	
	private void drawCard(Graphics2D graphics, List<Card> hand) {
			float YCard = height-(height/4)+50;
			float XCard = XPlayerOrigin;
			for(Card cd : hand) {
				
				switch(cd.getColor()) {
				
				case white:
					graphics.setColor(Color.WHITE);
					graphics.draw(new Rectangle.Float(XCard+40, YCard-40, 50, 100));
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), XCard+60, YCard);
					XCard = XCard+60;
					break;
				
				case blue:
					graphics.setColor(Color.BLUE);
					graphics.draw(new Rectangle.Float(XCard+40, YCard-40, 50, 100));
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), XCard+60, YCard);
					XCard = XCard+60;
					break;
				
				case yellow:
					graphics.setColor(Color.YELLOW);
					graphics.draw(new Rectangle.Float(XCard+40, YCard-40, 50, 100));
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), XCard+60, YCard);
					XCard = XCard+60;
					break;
					
				case red:
					graphics.setColor(Color.RED);
					graphics.draw(new Rectangle.Float(XCard+40, YCard-40, 50, 100));
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), XCard+60, YCard);
					XCard = XCard+60;
					break;
					
				case green:
					graphics.setColor(Color.GREEN);
					graphics.draw(new Rectangle.Float(XCard+40, YCard-40, 50, 100));
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), XCard+60, YCard);
					XCard = XCard+60;
					break;
				}
			}
	}
	
	public void printBlueTokens(Graphics2D graphics, int tokens) {
		float X = (width - width/4) - 100;
		float Y = height/2+100;
		
		for(var i=0; i < tokens; i++) {
			graphics.setColor(Color.BLUE);
        	graphics.draw(new Ellipse2D.Float(X, Y, 50, 50));
        	X = X +60;
    	}
	}
	
	public void printRedTokens(Graphics2D graphics, int tokens) {
		float X = (width - width/4) - 100;
		float Y = height/2+40;
		
		for(var i=0; i < tokens; i++) {
			graphics.setColor(Color.BLUE);
        	graphics.draw(new Ellipse2D.Float(X, Y, 50, 50));
        	X = X +60;
    	}
	}
	
	public void printWin(Graphics2D graphics) {
		clearWin(context);
		graphics.setColor(Color.YELLOW);
		graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
		graphics.drawString("YOU WIN ! \n WELL PLAY !", width/2, height/2-400);  // TODO mettre les bonnes coordonnées 
	}
	
	public void printLose(Graphics2D graphics) {
		clearWin(context);
		graphics.setColor(Color.RED);
		graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
		graphics.drawString("YOU LOSE ... \n TRY AGAIN !", width/2, height/2-400);  // TODO mettre les bonnes coordonnées 
	}
	
	public void clearWin(ApplicationContext context) {
		context.renderFrame(graphics -> {
			graphics.clearRect(0,0, (int) width+1, (int) height+1);
		});
	}
	
	
}
