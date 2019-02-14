package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Game.Entities.Dynamic.Player;
import Main.Handler;
import Resources.Images;

public class Tree extends StaticBase {
	
    public Rectangle tree;



    public Tree(Handler handler,int xPosition, int yPosition) {
        super(handler);
        // Sets original position to be this one.
        this.setY(yPosition);
        this.setX(xPosition);
	}

	@Override
	public void render(Graphics g) {
    	g.drawImage(Images.tree, this.getX(), this.getY(), 64, 64, null);
    	tree = new Rectangle(this.getX(), this.getY(), 64, 64);

	}
	
    @Override
    public Rectangle GetCollision() {
    		
    	return tree;
//    	return new Rectangle();
    	
    }
//    public Rectangle UpdateTreeRectangle(Graphics g) {
//        tree = new Rectangle(this.getX(), this.getY(), getWidth(), getHeight());
//        return tree;
//    }
    
}