package test;

import javax.swing.JFrame;

import game.Game;
import game.IHM_Player;
import junit.framework.*;

/**
 * A test for the class Game
 */
public class GameTest extends TestCase {
	
	/**
	 * creates a new test with a given name
	 * @param testName the name of the test
	 */
	public GameTest(String testName) {
		super(testName);
	}
	
	/**
	 * Tests the chooseLevel method
	 */
	public void testChooseLevel() {
		IHM_Player ihm = new IHM_Player(new JFrame());
		Game game = ihm.getTheGame();
		game.chooseLevel(-5);
		TestCase.assertEquals(game.getCurrentLevel(), 0); //It's equal to 0 since the value -5 is not applied
		
		game.chooseLevel(0);
		TestCase.assertEquals(game.getCurrentLevel(), 0);
		
		game.chooseLevel(5);
		TestCase.assertEquals(game.getCurrentLevel(), 5); //Since there's at least 6 levels, it load the sixth level
		
		game.chooseLevel(12);
		TestCase.assertFalse(game.getCurrentLevel() == 12); //There are 12 levels, so the thirteenth does not exist
		
	}
	
	/**
	 * Tests the displayLevel method
	 */
	public void testDisplayLevel() {
		IHM_Player ihm = new IHM_Player(new JFrame());
		Game game = ihm.getTheGame();
		
		try {
			game.displayLevel(0, null);
			TestCase.assertEquals(game.getCurrentLevel(), 0);
		} catch(Exception e) {
			//Nothing to do
		}
		
		try {
			game.displayLevel(-5, null);
			TestCase.assertEquals(game.getCurrentLevel(), 0); //If the levelId is too low, it sets to 0
		} catch(Exception e) {
			//Nothing to do
		}
		
		try {
			game.displayLevel(6, null);
			TestCase.assertEquals(game.getCurrentLevel(), 6); //There are at least 7 levels, so it must work
		} catch(Exception e) {
			//Nothing to do
		}
		
		try {
			game.displayLevel(12, null);
			TestCase.assertEquals(game.getCurrentLevel(), 0); //There are less than 13 levels, so it sets to 0
		} catch(Exception e) {
			//Nothing to do
		}
		
	}
	
	/**
	 * Tests the setCurrentSelection method
	 */
	public void testSetCurrentSelection() {
		IHM_Player ihm = new IHM_Player(new JFrame());
		Game game = ihm.getTheGame();
		
		game.setCurrentSelection(2);
		TestCase.assertEquals(game.getCurrentSelection(), 2); //The main menu has 4 options, so it should work
		
		ihm.displayMenu(2); //We open the level selection menu
		game.setCurrentSelection(12); 
		TestCase.assertEquals(game.getCurrentSelection(), 0);//We only have 12 levels, so it sets to 0
		
		game.setCurrentSelection(-1);
		TestCase.assertEquals(game.getCurrentSelection(), 0); //The given number is too low, it should be equals to 0
	}
	
}
