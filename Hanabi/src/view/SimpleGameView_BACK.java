package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.List;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import model.Card;
import model.FireworkColor;
import model.Player;
import model.SimpleGameData;

public class SimpleGameView {
	
	private float XPlayerOrigin = 0;
	private float YPlayerOrigin  = 0;
	
	
	private void drawMenu(ApplicationContext context) {
		float width = context.getScreenInfo().getWidth();
		float height = context.getScreenInfo().getHeight();
		
    	context.renderFrame(graphics -> {
    		graphics.setColor(Color.RED);
    		Rectangle2D play_bp = new Rectangle2D.Float(0, height/4, width/3, height/4);
    		graphics.fill(play_bp);
    		graphics.setColor(Color.BLUE);
    		Rectangle2D indice_bp = new Rectangle2D.Float(width/3, height/4, width/3, height/4);
    		graphics.fill(indice_bp);
    		graphics.setColor(Color.GREEN);
    		Rectangle2D discard_bp = new Rectangle2D.Float((width/3)*2, height/4, width/3, height/4);
    		graphics.fill(discard_bp);
    	});
	}
	
	
	private void drawField(ApplicationContext context) {
		float width = context.getScreenInfo().getWidth();
		float height = context.getScreenInfo().getHeight();
		
    	context.renderFrame(graphics -> {
    		graphics.setColor(Color.GRAY);
    		Rectangle2D Field = new Rectangle2D.Float(0, 0, width/2, height/4);
    		graphics.fill(Field);
    	});
	}
	
	private void drawDiscardZone(ApplicationContext context) {
		float width = context.getScreenInfo().getWidth();
		float height = context.getScreenInfo().getHeight();
		
    	context.renderFrame(graphics -> {
    		graphics.setColor(Color.ORANGE);
    		Rectangle2D DiscardZone = new Rectangle2D.Float(width/2, 0, width/2, height/4);
    		graphics.fill(DiscardZone);
    	});
	}
	
	private void drawPlayer(ApplicationContext context, SimpleGameData data) {
		float width = context.getScreenInfo().getWidth();
		float height = context.getScreenInfo().getHeight();
		
		YPlayerOrigin = height-(height/4);
		
    	context.renderFrame(graphics -> {
    		for(var i=0; i < data.getNbPlayers(); i++) {
    			graphics.setColor(Color.cyan);
        		Rectangle2D Field = new Rectangle2D.Float(0, YPlayerOrigin, (width/5), height);
        		graphics.fill(Field);
        		XPlayerOrigin = XPlayerOrigin + (width/5);
    		}
    	});
	}
	
	private void drawCard(ApplicationContext context, List<Card> hand) {
		
		context.renderFrame(graphics -> {		
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
		});
	}
	
	private void printBlueTokens(ApplicationContext context, int tokens) {
		context.renderFrame(graphics -> {	
			for(var i=0; i < tokens; i++) {
    			graphics.setColor(Color.BLUE);
    			Ellipse2D Field = new Ellipse2D.Float();  //TODO
        		graphics.fill(Field);
        		XPlayerOrigin = XPlayerOrigin + (width/5);
    		}
		});
	}
	
	private void printRedTokens(ApplicationContext context, int tokens) {
		
	}
	
	
	
	Application.run(Color.BLACK, context -> {
        SimpleGameView view = new SimpleGameView(context);
        view.showField();
        view.showMenu();

    });
}
