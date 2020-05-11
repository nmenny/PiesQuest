import java.awt.Dimension;
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
	 * Is the game running
	 */
	private boolean isRunning;
	
	/**
	 * The thread to handle the game
	 */
	private Thread gameThread;
	
	/**
	 * Frame Per Seconds to render the game
	 */
	private static final int FPS = 60;
	
	/**
	 * The time to wait between each frame
	 */
	private static final long targetTime = 1000 / IHM_Player.FPS;
	
	/**
	 * The parameters of the current game
	 */
	private Parameter theParameters;
	
	/**
	 * Creates a new player for the game and starts the game
	 */
	public IHM_Player() {
		this.theGame = new Game(this);
		this.theParameters = this.theGame.getParameter();
		
		int width = 800;
		int height = 600;
		
		//Configure the screen
		setPreferredSize(new Dimension(width, height));
		 
		addKeyListener(this);
		setFocusable(true);
		
		//Launches the game
		this.isRunning = true;
		this.gameThread = new Thread(this);
		this.gameThread.start();
	}
	
	/**
	 * Displays a message to the player
	 * @param message the message to transmit
	 */
	public void inform(String message) {
		//TODO implement the method
	}

	@Override
	/**
	 * when a key is pressed
	 */
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * When a key is released
	 */
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * When a key is typed
	 */
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * The render loop of our game
	 */
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
