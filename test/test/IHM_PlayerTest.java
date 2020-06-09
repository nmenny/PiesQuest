package test;

import javax.swing.JFrame;

import game.IHM_Player;
import junit.framework.*;

/**
 * Tests the class IHM_Player
 */
public class IHM_PlayerTest extends TestCase {
	
	/**
	 * creates a new test with a given name
	 * @param testName the name of the test
	 */
	public IHM_PlayerTest(String testName) {
		super(testName);
	}
	
	/**
	 * Tests the method displayMenu
	 */
	public void testDisplayMenu() {
		IHM_Player ihm = new IHM_Player(new JFrame());
		
		ihm.displayMenu(0);
		TestCase.assertEquals(ihm.getDisplayedMenu(), 0);
		
		ihm.displayMenu(4);
		TestCase.assertEquals(ihm.getDisplayedMenu(), 4); //We have 6 different menus, so 4 is correct
		
		ihm.displayMenu(-5);
		TestCase.assertEquals(ihm.getDisplayedMenu(), 0); //When the value is too low, it goes to 0
		
		ihm.displayMenu(6);
		TestCase.assertEquals(ihm.getDisplayedMenu(), 0); //When the value is to high, it goes to 0
	}
	
}
