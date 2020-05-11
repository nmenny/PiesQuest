import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * The interface between the player and the game
 */
public class IHM_Player extends JPanel implements Runnable, KeyListener {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The game being played
	 */
	private Game theGame;
	
	/**
	 * The parameters of the current game
	 */
	private Parameter theParameters;
	
	/**
	 * Creates a new player for the game
	 */
	public IHM_Player() {
		this.theGame = new Game(this);
		this.theParameters = this.theGame.getParameter();
	}
	
	/**
	 * Displays a message to the player
	 * @param message the message to transmit
	 */
	public void inform(String message) {
		//TODO implement the method
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
