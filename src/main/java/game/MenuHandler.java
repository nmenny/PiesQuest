package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Handles the different menus
 */
public class MenuHandler {

	/**
	 * The selection of the options in the menus
	 */
	private int currentSelection;
	
	/**
	 * The Game which is being played
	 */
	private Game theGame;
	
	
	/**
	 * Creates a new handler for the menu
	 * @param theGame the game which is being played
	 */
	public MenuHandler(Game theGame) {
		this.currentSelection = 0;
		this.theGame = theGame;
	}
	
	/**
	 * Gets the current option in the displayed menu
	 * @return the current option selected in the displayed menu
	 */
	public int getCurrentSelection() {
		return this.currentSelection;
	}
	
	/**
	 * Sets the selection cursor
	 * @param theSelection the menu selection index
	 */
	public void setCurrentSelection(int theSelection) {
		if(theSelection < 0 || theSelection >= this.getNumberOption()) {
			this.currentSelection = 0;
		} else {
			this.currentSelection = theSelection;
		}
		
	}
	
	/**
	 * Gets the number of options possible for the current menu
	 * @return the number of option of the current menu
	 */
	private int getNumberOption() {
		//For each menu, there's a defined number of menus
		switch(this.theGame.getPlayerInterface().getDisplayedMenu()) {
		case 0:
			return 4;
		case 2:
			return this.theGame.getNumberLevels();
		case 4:
			return 1;
		case 5: 
			return 1;
		default: 
			return 0;
		}
	}
	

	
	/**
	 * Moves the selection attribute in a given direction
	 * @param go the direction of the move
	 */
	public void gotoSelect(int go) {
		this.currentSelection += go;
		//If the selection is less than 0, we go to the last option
		if(this.currentSelection < 0) {
			this.currentSelection = this.getNumberOption() - 1;
		}
		//If the selection is more or equals to the number of menus, we go to the first option
		if(this.currentSelection >= this.getNumberOption()) {
			this.currentSelection = 0;
		}
	}
	
	
	/**
	 * Displays all the levels at the screen
	 * @param g the drawing object
	 */
	public void displayAllLevels(Graphics g) {
		int width = this.theGame.getParameter().getWidth(), height = this.theGame.getParameter().getHeight();
	
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		//Display the title of the menu
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 50));
		g.drawString("CHOOSE THE LEVEL", (width / 2) - 220, height / 6);
		
		int y = 0;
		int x = 0;
		int levelInitialIndex = 0;
		int levelSelection = this.currentSelection;
		int nbLevelDisplayedAtOnce = 8;
		
		//Can can only display "nbLevelDisplayedAtOnce" different levels at the same time, so only the wanted levels will be displayed and not the first ones
		while(levelSelection > nbLevelDisplayedAtOnce) {
			levelInitialIndex += 3;
			levelSelection -= 3;
		}
		for(int level = levelInitialIndex; level < this.theGame.getNumberLevels(); level++) {
			
			if(level == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else if(this.theGame.getLevel(level).isLocked()){
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			
			//Each line has 3 levels
			if(level % 3 == 0 && level != levelInitialIndex) {
				y += 1;
				x = 0;
			}
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString(this.theGame.getLevel(level).getName(), ((width / 2) - 110) * x + ((int) this.theGame.getLevel(level).getName().length() / 2), height / 3 + 150 * y);
			x++;
		}
	}
	
	
	
	/**
	 * displays the game over screen to the player
	 * @param g the drawing object
	 */
	public void displayGameOver(Graphics g) {
		String[] menus = {"Back to main menu"};
		int width = this.theGame.getParameter().getWidth(), height = this.theGame.getParameter().getHeight();
	
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		//The title of the menu
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 100));
		g.drawString("GAME OVER", (width / 2) - 270, height / 3);
		
		//For every menu option, we draw it on the screen
		for(int menu = 0; menu < menus.length; menu++) {
			//If the current menu is selected, its color changes
			if(menu == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString(menus[menu], (width / 2) - 210, 2 *height / 3);
		}
	}
	
	/**
	 * displays the main screen to the player
	 * @param g the drawing object
	 */
	public void displayMainScreen(Graphics g) {
		String[] menus = {"Start", "Choose level", "Parameters", "Quit"};
		int width = this.theGame.getParameter().getWidth(), height = this.theGame.getParameter().getHeight();
		
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		g.setFont(new Font("Arial", Font.PLAIN, 75));
		g.setColor(new Color(0, 0, 0));
		g.drawString("Pie's Quest", (width / 2) - 5 * 35, 2*height/9);
		
		//For every menu option, we draw it on the screen
		for(int menu = 0; menu < menus.length; menu++) {
			//If the current menu is selected, its color changes
			if(menu == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString(menus[menu], (width / 2) - menus[menu].length() * 10, 4*height/9 + menu * height/9);
		}
	}
	
	/**
	 * displays the victory screen to the player
	 * @param g the drawing object
	 */
	public void displayVictoryScreen(Graphics g) {
		String[] menus = {"Back to main menu"};
		int width = this.theGame.getParameter().getWidth(), height = this.theGame.getParameter().getHeight();
	
		
		//Background
		g.setColor(new Color(50, 150, 200));
		g.fillRect(0,  0,  width, height);
		
		//Displays the title of the menu
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 100));
		g.drawString("VICTORY", (width / 2) - 210, height / 3);
		
		//For every menu option, we draw it on the screen
		for(int menu = 0; menu < menus.length; menu++) {
			//If the current menu is selected, its color changes
			if(menu == this.currentSelection) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString(menus[menu], (width / 2) - 210, 2 *height / 3);
		}
	}
	
	/**
	 * Displays the menu corresponding on the given id
	 * @param menuId the id of the menu
	 * 	0 - main menu displayed
	 * 	1 - parameters displayed
	 * 	2 - level selection displayed
	 * 	3 - level displayed
	 * 	4- game over screen displayed
	 * 	5 - victory screen displayed
	 * @param g the graphical component
	 * */
	public void displayCorrespondingMenu(int menuId, Graphics g) {
		switch(menuId) {
		case 0:
			this.displayMainScreen(g);
			break;
		case 2:
			this.displayAllLevels(g);
			break;
		case 4:
			this.displayGameOver(g);
			break;
		case 5:
			this.displayVictoryScreen(g);
			break;
		default:
			break;
		}
	}
	
}
