package Game.Entities.Dynamic;

import Game.Entities.EntityBase;
import Game.Entities.Static.LillyPad;
import Game.Entities.Static.Log;
import Game.Entities.Static.Tree;
import Game.Entities.Static.Turtle;
import Game.GameStates.State;
import Game.World.WaterArea;
import Game.World.WorldManager;

import Main.Handler;
import Main.Launch;
import Resources.Images;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/*
 * The Frog.
 */
public class Player extends EntityBase {
	private Handler handler;



	public Rectangle player;
	public String facing = "UP";
	public Boolean moving = false;
	private int moveCoolDown=0;


	private int index =0;
	public int score = 0;
	public int highScore = 0;
	
	// set the borders of the screen
	// offset (by 96 on sides, 128 on top) from the actual measurement of the screen
	// so the animation looks good
	public Line2D topBorder = new Line2D.Double(-128,-96,672,-96);
	public Line2D bottomBorder = new Line2D.Double(-96,864,672,864);
	public Line2D leftBorder = new Line2D.Double(-96,-96,-96,864);
	public Line2D rightBorder = new Line2D.Double(672,-96,672,864);
	



	public Player(Handler handler) {
		super(handler);
		this.handler = handler;
		this.handler.getEntityManager().getEntityList().add(this);

		player = new Rectangle(); 	// see UpdatePlayerRectangle(Graphics g) for its usage.
	}

	public void tick(){

		if(moving) {
			animateMovement();
		}

		if(!moving){
			move();
		}
		
		//WaterCollision();
	}

	private void reGrid() {
		if(facing.equals("UP")) {
			if(this.getX() % 64 >= 64 / 2 ) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			}
			else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY()-64);
		}
	}
	
	///////////if frogger is in water, game over//////////////////
	public void WaterCollision() {
		for(int i = 0; i < handler.getWorld().SpawnedAreas.size(); i++) {
			if (handler.getWorld().SpawnedAreas.get(i) instanceof WaterArea && handler.getWorld().SpawnedAreas.get(i).getYPosition() == player.getY()) {
				for (int j = 0; j < handler.getWorld().SpawnedHazards.size(); j++) {
					if (getX() > handler.getWorld().SpawnedHazards.get(j).getX() 
							&& getX() < handler.getWorld().SpawnedHazards.get(j).getX() + handler.getWorld().SpawnedHazards.get(j).getWidth()
							&& getY() > handler.getWorld().SpawnedHazards.get(j).getY()
							&& getY() < handler.getWorld().SpawnedHazards.get(j).getY() + handler.getWorld().SpawnedHazards.get(j).getHeight()) {
						return;
						//State.setState(handler.getGame().gameOverState);
						}
					}
				}
			State.setState(handler.getGame().gameOverState);
			//return;
			}
		}
	
	private void move(){
		if(moveCoolDown< 25){
			moveCoolDown++;
		}
		index=0;		
		
		//////////// if frogger in water, game over///////////
		
//		handler.getWorld().WaterCollision();
		
//		if (handler.get water, die)
		
		
		//////////if frogger gets dragged off screen by log/turtle, game over/////////
		// try if player Y > edge, die
			
//		Line2D leftBorderWater = new Line2D.Double(-112,-96,-112,864);
//		Line2D rightBorderWater = new Line2D.Double(688,-96,688,864);
//		if (new Rectangle(getX(),getY(),getWidth(),getHeight()).intersectsLine(leftBorderWater)) {
//			State.setState(handler.getGame().gameOverState);
//		}
//		else if (new Rectangle(getX(),getY(),getWidth(),getHeight()).intersectsLine(rightBorderWater)) {
//			State.setState(handler.getGame().gameOverState);
//		}
//		
		/////////prevent frogger from stepping outside the screen///////////////
		
		if(new Rectangle(getX(),getY()-128,getWidth(),getHeight()).intersectsLine(topBorder)) { // up
			setY(getY()+64);
			return;
		}
		else if (new Rectangle(getX(),getY()+64,getWidth(),getHeight()).intersectsLine(bottomBorder)){ // down, should prompt game over screen instead
            State.setState(handler.getGame().gameOverState);
            
		}
		else if (new Rectangle(getX()-64,getY(),getWidth(),getHeight()).intersectsLine(leftBorder)){ // left
			setX(getX()+32);
			return;
		}
		else if (new Rectangle(getX(),getY(),getWidth(),getHeight()).intersectsLine(rightBorder)){ // right
			setX(getX()-32);
			return;
		}
		
		/////////////////MOVE UP///////////////
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && facing.equals("UP")){
			
			///////if Frogger touches water, game over//////////	
			
//			for (int i = 0; i < handler.getWorld().SpawnedAreas.size(); i++) {
//				if (handler.getWorld().SpawnedAreas.get(i) instanceof WaterArea) {
//					if (player.intersects(handler.) {
//						
//					}
//				}
//			}
			
			// disables Frogger from jumping on trees (found on each side below)
			
			for (int i = 0; i < handler.getWorld().SpawnedHazards.size(); i++) {
				if (handler.getWorld().SpawnedHazards.get(i) instanceof Tree) {
					if (handler.getWorld().SpawnedHazards.get(i).GetCollision() != null
							&& new Rectangle(getX(),getY()-128,getWidth(),getHeight()).intersects(handler.getWorld().SpawnedHazards.get(i).GetCollision())) {
						return;
					}
				}
			}
			
			
			////// keeps the score of the player, only when moving forward on tiles
			if (highScore == score) {
				score += 10;
				highScore += 10;
			}
			else {
				score += 10;
			}
			moving=true;
			//System.out.println(highScore);
			
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && !facing.equals("UP")){
			if(facing.equals("DOWN")) {
				if(this.getX() % 64 >= 64 / 2 ) {

					this.setX(this.getX() + (64 - this.getX() % 64));
				}
				else {
					this.setX(this.getX() - this.getX() % 64);
				}
				setY(getY() + 64);
			}
			if(facing.equals("LEFT")) {
				setY(getY() + 64);
			}
			if(facing.equals("RIGHT")) {
				setX(getX()-64);
				setY(getY()+64);
			}
			facing = "UP";
		}

		/////////////////MOVE LEFT///////////////
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving && facing.equals("LEFT")){
			for (int i = 0; i < handler.getWorld().SpawnedHazards.size(); i++) {
				if (handler.getWorld().SpawnedHazards.get(i) instanceof Tree) {
					if (handler.getWorld().SpawnedHazards.get(i).GetCollision() != null
							&& new Rectangle(getX()-64,getY(),getWidth(),getHeight()).intersects(handler.getWorld().SpawnedHazards.get(i).GetCollision())) {
						return;
					}
				}
			}
			moving=true;
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving&& !facing.equals("LEFT")){
			if(facing.equals("RIGHT")) {
				setX(getX()-64);
			}
			reGrid();
			facing = "LEFT";
		}

		/////////////////MOVE DOWN///////////////
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && facing.equals("DOWN")){
			for (int i = 0; i < handler.getWorld().SpawnedHazards.size(); i++) {
				if (handler.getWorld().SpawnedHazards.get(i) instanceof Tree) {
					if (handler.getWorld().SpawnedHazards.get(i).GetCollision() != null
							&& new Rectangle(getX(),getY()+64,getWidth(),getHeight()).intersects(handler.getWorld().SpawnedHazards.get(i).GetCollision())) {
						return;
					}
				}
			}
			score -= 10;	////// if frogger goes down, deduct points
			if (score < 0) {
				score = 0;
			}
			//System.out.println(highScore);
			moving=true;
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && !facing.equals("DOWN")){
			reGrid();
			if(facing.equals("RIGHT")){
				setX(getX()-64);
			}
			facing = "DOWN";
		}

		/////////////////MOVE RIGHT///////////////
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving && facing.equals("RIGHT")){
			for (int i = 0; i < handler.getWorld().SpawnedHazards.size(); i++) {
				if (handler.getWorld().SpawnedHazards.get(i) instanceof Tree) {
					if (handler.getWorld().SpawnedHazards.get(i).GetCollision() != null
							&& new Rectangle(getX(),getY(),getWidth(),getHeight()).intersects(handler.getWorld().SpawnedHazards.get(i).GetCollision())) {
						return;
					}
				}
			}
			moving=true;
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving&& !facing.equals("RIGHT")){
			if(facing.equals("LEFT")) {
				setX(getX()+64);
			}
			if(facing.equals("UP")) {
				setX(getX()+64);
				setY(getY()-64);
			}
			if(facing.equals("DOWN")) {
				if(this.getX() % 64 >= 64 / 2 ) {
					this.setX(this.getX() + (64 - this.getX() % 64));
				}
				else {
					this.setX(this.getX() - this.getX() % 64);
				}
				setX(getX()+64);
			}
			facing = "RIGHT";
		}
	}

	private void animateMovement(){
		if(index==8) {
			moving = false;
			index = 0;
		}
		moveCoolDown = 0;
		index++;
		switch (facing) {
		case "UP":
			if (this.getX() % 64 >= 64 / 2) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			} else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY() - (8));
			break;

		case "LEFT":
			setX(getX() - (8));
			break;

		case "DOWN":
			if (this.getX() % 64 >= 64 / 2) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			} else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY() + (8));
			break;

		case "RIGHT":
			setX(getX() + (8));
			break;

		}
	}

	public void render(Graphics g){

		if(index>=8){
			index=0;
			moving = false;
		}

		switch (facing) {
		case "UP":
			g.drawImage(Images.Player[index], getX(), getY(), getWidth(), -1 * getHeight(), null);
			break;
		case "DOWN":
			g.drawImage(Images.Player[index], getX(), getY(), getWidth(), getHeight(), null);
			break;
		case "LEFT":
			g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), getWidth(), getHeight(), null);
			break;
		case "RIGHT":
			g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), -1 * getWidth(), getHeight(), null);
			break;
		}


		UpdatePlayerRectangle(g);
        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + highScore, 10, 20);


	}

	// Rectangles are what is used as "collisions." 
	// The hazards have Rectangles of their own.
	// This is the Rectangle of the Player.
	// Both come in play inside the WorldManager.
	private void UpdatePlayerRectangle(Graphics g) {

		player = new Rectangle(this.getX(), this.getY(), getWidth(), getHeight());

		if (facing.equals("UP")){
			player = new Rectangle(this.getX(), this.getY() - 64, getWidth(), getHeight());
		}
		else if (facing.equals("RIGHT")) {
			player = new Rectangle(this.getX() - 64, this.getY(), getWidth(), getHeight());
		}
	}

	@SuppressWarnings("SuspiciousNameCombination")
	private static BufferedImage rotateClockwise90(BufferedImage src) {
		int width = src.getWidth();
		int height = src.getHeight();

		BufferedImage dest = new BufferedImage(height, width, src.getType());

		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.translate((height - width) / 2, (height - width) / 2);
		graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
		graphics2D.drawRenderedImage(src, null);

		return dest;
	}


    public Rectangle getPlayerCollision() {
        return player;
    }
}
