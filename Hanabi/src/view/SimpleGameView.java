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
	
	public void showCards(List<Card> hand) {
		context.renderFrame(graphics -> {
			drawCard(graphics, hand);
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
		
    		for(var i=0; i < data.getNbPlayers(); i++) {
    			graphics.setColor(Color.cyan);
    			graphics.draw(new Rectangle.Float(0, YPlayerOrigin, (width/5), height));
        		XPlayerOrigin = XPlayerOrigin + (width/5);
    		}
	}
	
	private void drawCard(Graphics2D graphics, List<Card> hand) {
			for(Card cd : hand) {
				switch(cd.getColor()) {
				
				case white:
					graphics.setColor(Color.WHITE);
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), 0, 0);  // TODO mettre les bonnes coordonnées 
					break;
				
				case blue:
					graphics.setColor(Color.BLUE);
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), 0, 0);  // TODO mettre les bonnes coordonnées 
					break;
				
				case yellow:
					graphics.setColor(Color.YELLOW);
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), 0, 0);  // TODO mettre les bonnes coordonnées 
					break;
					
				case red:
					graphics.setColor(Color.RED);
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), 0, 0);  // TODO mettre les bonnes coordonnées 
					break;
					
				case green:
					graphics.setColor(Color.GREEN);
					graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
					graphics.drawString(String.valueOf(cd.getValue()), 0, 0);  // TODO mettre les bonnes coordonnées 
					break;
				}
			}
	}
	
	public void printBlueTokens(Graphics2D graphics, int tokens) {
			for(var i=0; i < tokens; i++) {
    			graphics.setColor(Color.BLUE);
        		graphics.draw(new Ellipse2D.Float());  // TODO mettre les coordonnées
    		}
	}
	
	public void printRedTokens(Graphics2D graphics, int tokens) {

			for(var i=0; i < tokens; i++) {
    			graphics.setColor(Color.RED);
        		graphics.draw(new Ellipse2D.Float());  // TODO mettre les coordonnées
    		}
	}
	
	public void printWin(Graphics2D graphics) {
			graphics.setColor(Color.YELLOW);
			graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
			graphics.drawString("YOU WIN ! \n WELL PLAY !", width/3+(width/3/2), height/2);  // TODO mettre les bonnes coordonnées 
	}
	
	public void printLose(Graphics2D graphics) {
			graphics.setColor(Color.RED);
			graphics.setFont(graphics.getFont().deriveFont((float) 25.0));
			graphics.drawString("YOU LOSE ... \n TRY AGAIN !", width/3+(width/3/2), height/2);  // TODO mettre les bonnes coordonnées 
	}
	
	
}
