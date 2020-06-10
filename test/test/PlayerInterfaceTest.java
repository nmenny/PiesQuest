package test;

import javax.swing.JFrame;

import game.PlayerInterface;
import junit.framework.*;

/**
 * Tests the class IHM_Player
 */
public class PlayerInterfaceTest extends TestCase {
	
	/**
	 * creates a new test with a given name
	 * @param testName the name of the test
	 */
	public PlayerInterfaceTest(String testName) {
		super(testName);
	}
	
	/**
	 * Tests the method displayMenu
	 */
	public void testDisplayMenu() {
		PlayerInterface ihm = new PlayerInterface(new JFrame());
		
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
