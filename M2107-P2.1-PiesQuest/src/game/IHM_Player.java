package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	 * The maximum time with the message displayed on the screen (in fps (Frame Per Second))
	 */
	private static final int MAX_MESSAGE_DISPLAYED_TIME = 240;

	/**
	 * The game being played
	 */
	private Game theGame;
	
	/**
	 * Allows us to know which screen has to be displayed by looking at the value
	 * 0 - main menu displayed
	 * 1 - parameters displayed
	 * 2 - level selection displayed
	 * 3 - level displayed
	 * 4 - game over screen displayed
	 * 5 - victory screen displayed
	 */
	private int menuDisplayed;
	
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
	private boolean playerJumping;
	
	/**
	 * <tt>true</tt> When a message is displayed with the method inform
	 */
	private boolean messageDisplayed;
	
	/**
	 * The message to display on the screen
	 */
	private String messageToDisplay;
	
	/**
	 * The time passed since the message is displayed
	 */
	private int intervalMessageDisplayed;
	
	/**
	 * Creates a new player for the game and starts the game
	 */
	public IHM_Player() {
		
		this.messageDisplayed = false;
		this.messageToDisplay = "";
		this.intervalMessageDisplayed = 0;
		
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
		
		this.menuDisplayed = 0;
		
		this.initMovements();
	}
	
	/**
	 * Gets the menu being currently displayed
	 * @return the value of the menu
	 * 		0 - main menu displayed
	 *  	1 - parameters displayed
	 *  	2 - level selection displayed
     *  	3 - level displayed
	 *  	4 - game over screen displayed
	 *  	5 - victory screen displayed
	 */
	public int getDisplayedMenu() {
		return this.menuDisplayed;
	}
	
	/**
	 * Changes the currently displayed menu with the given value
	 * @param menuValue the value corresponding of the menu
	 */
	public void displayMenu(int menuValue) {
		if(menuValue < 0 || menuValue > 5) {
			this.menuDisplayed = 0;
		} else {
			this.menuDisplayed = menuValue;
		}
	}
	
	/**
	 * Initialize the movement's values
	 */
	public void initMovements() {
		this.playerMovingLeft = this.playerMovingRight = this.playerJumping = false;
	}
	
	/**
	 * Displays a message to the player
	 * @param message the message to transmit
	 */
	public void inform(String message) {
		this.intervalMessageDisplayed = 0;
		this.messageToDisplay = message;
		this.messageDisplayed = true;
	}

	@Override
	/**
	 * when a key is pressed
	 * @param e the key pressed
	 */
	public void keyPressed(KeyEvent e) {
		//Depending on the menu, the action possible are different
		switch(this.menuDisplayed) {
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
					try {
						this.menuDisplayed = 3;
						this.theGame.chooseLevel(0);
						
					} catch(NullPointerException e1) {
						this.inform("Error while loading the first level ! Look at the level's files !");
					}
					break;
				case 1: //Level selection option
					this.menuDisplayed = 2;

					//Puts the level selection cursor on the first level
					this.theGame.setCurrentSelection(0);
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
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.theGame.gotoSelect(-1);
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.theGame.gotoSelect(+1);
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				this.theGame.gotoSelect(+3);
			}
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				this.theGame.gotoSelect(-3);
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				//If the selected level is unlock, we can select it
				if(!this.theGame.getLevel(this.theGame.getCurrentSelection()).isLock()) {
					this.menuDisplayed = 3;
					this.theGame.chooseLevel(this.theGame.getCurrentSelection());
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.menuDisplayed = 0;
			}
			break;
		case 3: //The level
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				this.playerMovingLeft = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				this.playerMovingRight = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				this.playerJumping = true;
			}
			break;
		case 4: //The game over menu
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
					this.menuDisplayed = 0;
					break;
				default: 
					break;
				}
			}
			break;
		case 5: //The victory menu
			//If we press on "enter", we open the current menu
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				switch(this.theGame.getCurrentSelection()) {
				case 0: //Start level 1 option
					this.menuDisplayed = 0;
					break;
				default: 
					break;
				}
			}
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
		switch(this.menuDisplayed) {
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
			
			//Draws the game
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
		switch(this.menuDisplayed) {
		case 0: //The main menu
			this.theGame.displayMainScreen(g);
			break;
		case 1: //The parameters menu
			this.theParameters.displayMenu(this);
			break;
		case 2: //The level selection menu
			try {
				this.theGame.displayAllLevels(g);
			} catch(NullPointerException e) {
				this.inform("Error while loading the levels ! Please look at \"LevelNames.txt\"");
				this.menuDisplayed = 0;
			}
			break;
		case 3: //The level
			if(this.playerMovingLeft) {
				this.theGame.movePlayer(-1);
			}
			if(this.playerMovingRight) {
				this.theGame.movePlayer(1);
			}
			if(this.playerJumping) {
				//If the character hits the ground, he can jump again
				if(this.theGame.jumpPlayer()) {
					this.playerJumping = false;
				}
			} else {
				this.theGame.makeFall();
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
		
		
		if(this.messageDisplayed) {
			if(this.intervalMessageDisplayed < IHM_Player.MAX_MESSAGE_DISPLAYED_TIME) {
				g.setColor(Color.gray);
				g.fillRoundRect(0, 8*this.theParameters.getHeight() / 9, this.theParameters.getWidth(), this.theParameters.getHeight(), 15, 15);
				
				g.setColor(Color.black);
				g.setFont(new Font("Arial", Font.PLAIN, 20));
				g.drawString(this.messageToDisplay, this.messageToDisplay.length(), (this.theParameters.getHeight() + 8*this.theParameters.getHeight() / 9) / 2);
				this.intervalMessageDisplayed++;
			} else {
				this.messageDisplayed = false;
				this.intervalMessageDisplayed = 0;
			}
		}
		
	}
	
}