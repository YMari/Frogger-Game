package Game.GameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

public class GameOverState extends State {

    private int count = 0; 
    private UIManager uiManager;
    
    JLabel scoreDisplay = new JLabel("Score: 0");
    

    public GameOverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);
        

        /*
         * Adds a button that by being pressed changes the State
         */
        uiManager.addObjects(new UIImageButton(33, handler.getGame().getHeight() - 150, 128, 64, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
            handler.getGame().reStart();
        }));

        uiManager.addObjects(new UIImageButton(33 + 192,  handler.getGame().getHeight() - 150, 128, 64, Images.Options, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));

        uiManager.addObjects(new UIImageButton(33 + 192 * 2,  handler.getGame().getHeight() - 150, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
        
        
        
//        ScoreDisplay();
        
//    	uiManager.addObjects(scoreDisplay.setBounds(33 + 192,  handler.getGame().getHeight() - 100, 128, 64, () -> {
//    		scoreDisplay.setForeground(Color.WHITE);
//    		scoreDisplay.setText("Score: " + handler.getPlayer().score);
//    	}));
        
    }
    
    public void ScoreDisplay() {
    	scoreDisplay.setBounds(33 + 192,  handler.getGame().getHeight() - 200, 128, 64);
    	scoreDisplay.setForeground(Color.WHITE);
    	scoreDisplay.setText("Score: " + String.valueOf(handler.getPlayer().score));
    }
    
    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;
            State.setState(handler.getGame().gameState);
        }

    }
    
	@Override
    public void render(Graphics g) {
        g.drawImage(Images.gameOver,0,0,handler.getGame().getWidth(),handler.getGame().getHeight(),null);
//        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
//        g.setColor(Color.WHITE);
//        g.drawString("Score: " + handler.getPlayer().score, 10, 20);
        uiManager.Render(g);

	}
}
