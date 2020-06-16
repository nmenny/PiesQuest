package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The interface between the player and the game
 */
public class PlayerInterface extends JPanel implements Runnable, KeyListener {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The maximum time with the message displayed on the screen (in frames)
	 */
	private static final int MAX_MESSAGE_DISPLAYED_TIME = 240;

	/**
	 * The game being played
	 */
	private Game theGame;
	
	/**
	 * Allows us to know which screen (menu) must be displayed by looking at the value
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
	 * The number of graphical updates in a second (Frame Per Second)
	 */
	private static final int FPS = 60;
	
	/**
	 * The time to wait between each frame (in milliseconds)
	 */
	private static final long targetTime = 1000 / PlayerInterface.FPS;
	
	/**
	 * The parameters of the current game
	 */
	private Parameter theParameters;
	
	/**
	 * Is the player moving on the left ?
	 * <tt>true</tt> if he is moving on the left; <tt>false</tt> else
	 */
	private boolean playerMovingLeft;
	
	/**
	 * Is the player moving on the right ?
	 * <tt>true</tt> if he is moving on the right; <tt>false</tt> else
	 */
	private boolean playerMovingRight;
	
	/**
	 * Is the player jumping ?
	 */
	private boolean playerJumping;
	
	/**
	 * <tt>true</tt> When a message is displayed with the "inform" method; <tt>false</tt> else
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
	 * The frame in which the interface is displayed
	 */
	private JFrame frame;
	
	/**
	 * Creates a new player for the game and starts the game
	 * @param theFrame the frame in which the game is being displayed
	 */
	public PlayerInterface(JFrame theFrame) {
		
		this.messageDisplayed = false;
		this.messageToDisplay = "";
		this.intervalMessageDisplayed = 0;
		
		this.frame = theFrame;
		
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
	 * @return the value corresponding of the menu
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
	 * Gets the game which is being played
	 * @return the game being played
	 */
	public Game getTheGame() {
		return this.theGame;
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
						//If a message is displayed, it's now hidden
						this.messageDisplayed = false;
					} catch(NullPointerException e1) {
						this.inform("Error while loading the first level ! Look at the level's files !");
					}
					break;
				case 1: //Level selection option
					this.menuDisplayed = 2;
					//If a message is displayed, it's now hidden
					this.messageDisplayed = false;
					//Puts the level selection cursor on the first level
					this.theGame.setCurrentSelection(0);
					break;
				case 2: //Parameters Option
					//this.inform("Functionality unimplemented !");
					this.menuDisplayed = 1;
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
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.theParameters.setDisplay(false);
				this.menuDisplayed = 0;
				this.theGame.setCurrentSelection(0);
			}
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
				if(!this.theGame.getLevel(this.theGame.getCurrentSelection()).isLocked()) {
					this.messageDisplayed = false;
					this.menuDisplayed = 3;
					this.theGame.chooseLevel(this.theGame.getCurrentSelection());
				} else {
					this.inform("Level " +(this.theGame.getCurrentSelection() + 1) + " locked !");
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.menuDisplayed = 0;
				this.theGame.setCurrentSelection(0);
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
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				this.menuDisplayed = 0;
				this.theGame.setCurrentSelection(0);
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
			
			//Processing the time passed to display the game
			elapsed = System.nanoTime() - start;
			
			//Determine the time to wait
			wait = PlayerInterface.targetTime - elapsed / 1000000;
			
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
		//If the parameters are not displayed; we can refresh the all screen
		if(!this.theParameters.isDisplayed()) {
			super.paintComponent(g);
		
			//Clears the screen
			g.clearRect(0,  0, this.theParameters.getWidth(), this.theParameters.getHeight());
		
			//The element displayed varies depending on the menu displayed
			switch(this.menuDisplayed) {
			case 0: //The main menu
				this.theGame.displayMainScreen(g);
				break;
			case 1: //The parameters menu
				this.theParameters.displayMenu(this.frame, this);
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
					this.theGame.movePlayer(EnumMovements.LEFT.value);
				}
				if(this.playerMovingRight) {
					this.theGame.movePlayer(EnumMovements.RIGHT.value);
				}
				if(this.playerJumping) {
					//If the character hits the ground, he can jump again
					if(this.theGame.jumpPlayer()) {
						this.playerJumping = false;
					}
				} else {
					//Tries to make the player fall
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
		}
		
		//If a message have to be displayed
		if(this.messageDisplayed) {
			//If the time the message is displayed is not too long
			if(this.intervalMessageDisplayed < PlayerInterface.MAX_MESSAGE_DISPLAYED_TIME) {
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
