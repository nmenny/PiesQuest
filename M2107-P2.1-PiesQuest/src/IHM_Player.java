import java.awt.Dimension;
import java.awt.Graphics;
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
	 * Allows us to know where is the player moving to
	 */
	private boolean playerMovingLeft, playerMovingRight;
	
	/**
	 * The states of the jump
	 */
	private boolean playerJumping, playerFalling;
	
	/**
	 * Creates a new player for the game and starts the game
	 */
	public IHM_Player() {
		this.theGame = new Game(this);
		this.theParameters = this.theGame.getParameter();
		
		//Configure the screen
		setPreferredSize(new Dimension(this.theParameters.getWidth(), this.theParameters.getHeight()));
		 
		addKeyListener(this);
		setFocusable(true);
		
		//Launches the game
		this.isRunning = true;
		this.gameThread = new Thread(this);
		this.gameThread.start();
		
		this.playerMovingLeft = this.playerMovingRight = this.playerFalling = this.playerJumping = false;
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
	 * @param e the key pressed
	 */
	public void keyPressed(KeyEvent e) {
		//Depending on the menu, the action possible are different
		switch(this.theGame.menuDisplayed) {
		case 0: //The main menu
			//If we press the up arrow, we move up in the menu
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				this.theGame.gotoSelect(-1);
			}
			//If we press the down arrow, we move down in the menu
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.theGame.gotoSelect(+1);
			}
			//If we press on "enter", we open the current menu
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				switch(this.theGame.getCurrentSelection()) {
				case 0: //Start level 1 option
					this.theGame.chooseLevel(0);
					this.theGame.menuDisplayed = 3;
					break;
				case 1: //Level selection option
					//TODO handle events to choose a level
					break;
				case 2: //Parameters Option
					//TODO handle events to open the parameters menu
					break;
				case 3: //Quit option
					System.exit(0);
					break;
				default: 
					break;
				}
			}
			
			break;
		case 1: //The parameters menu
			//TODO handle events for the parameters
			break;
		case 2: //The level selection menu
			//TODO handle events for the selection menu
			break;
		case 3: //The level
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.playerMovingLeft = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.playerMovingRight = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				
			}
			break;
		case 4: //The game over menu
			//TODO handle events for the game over
			break;
		case 5: //The victory menu
			//TODO handle events for the victory
			break;
		default:
			break;
		}
	}

	@Override
	/**
	 * When a key is released
	 * @param e the key released
	 */
	public void keyReleased(KeyEvent e) {
		//Depending on the menu, the action possible are different
		switch(this.theGame.menuDisplayed) {
			case 3: //The level
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					this.playerMovingLeft = false;
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					this.playerMovingRight = false;
				}
			break;
		}
	}

	@Override
	/**
	 * When a key is typed
	 * @param e the key typed
	 */
	public void keyTyped(KeyEvent e) {}

	@Override
	/**
	 * The render loop of our game
	 */
	public void run() {
		long start, elapsed, wait;
		
		while(this.isRunning) {
			start = System.nanoTime();

			repaint();
			
			elapsed = System.nanoTime() - start;
			wait = IHM_Player.targetTime - elapsed / 1000000;
			
			if(wait <= 0) {
				wait = 5;
			}
			
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Display a component at the screen
	 * @param g the graphical component
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Clears the screen
		g.clearRect(0,  0, this.theParameters.getWidth(), this.theParameters.getHeight());
		
		//The element displayed varies depending on the menu displayed
		switch(this.theGame.menuDisplayed) {
		case 0: //The main menu
			this.theGame.displayMainScreen(g);
			break;
		case 1: //The parameters menu
			this.theParameters.displayMenu(this);
			break;
		case 2: //The level selection menu
			this.theGame.displayAllLevels(g);
			break;
		case 3: //The level
			if(this.playerMovingLeft) {
				this.theGame.movePlayer(-1);
			}
			if(this.playerMovingRight) {
				this.theGame.movePlayer(1);
			}
			this.theGame.displayLevel(g);
			break;
		case 4: //The game over Screen
			this.theGame.displayGameOver(g);
			break;
		case 5: //The victory Screen
			this.theGame.displayVictoryScreen(g);
			break;
		default:
			break;
		}
		
	}
	
}
