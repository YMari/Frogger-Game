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
        uiManager.Render(g);
      g.setFont(new Font("TimesRoman", Font.BOLD, 35));
      g.setColor(Color.WHITE);
      g.drawString("Score: " + handler.getPlayer().highScore, 33 + 192, handler.getGame().getHeight() - 250);

	}
}
